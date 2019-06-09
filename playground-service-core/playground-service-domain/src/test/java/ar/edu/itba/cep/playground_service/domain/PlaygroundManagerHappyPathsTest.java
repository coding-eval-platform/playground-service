package ar.edu.itba.cep.playground_service.domain;

import ar.edu.itba.cep.playground_service.commands.ExecutorServiceCommandMessageProxy;
import ar.edu.itba.cep.playground_service.models.ExecutionRequest;
import ar.edu.itba.cep.playground_service.models.ExecutionResult;
import ar.edu.itba.cep.playground_service.models.FinishedExecutionResult;
import ar.edu.itba.cep.playground_service.models.TimedOutExecutionResult;
import ar.edu.itba.cep.playground_service.repositories.ExecutionRequestRepository;
import ar.edu.itba.cep.playground_service.repositories.ExecutionResultRepository;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.hamcrest.HamcrestArgumentMatcher;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Test class for {@link PlaygroundManager}, containing tests for the happy paths
 * (i.e how the manager behaves when operating with valid values, entity states, etc.).
 */
@ExtendWith(MockitoExtension.class)
class PlaygroundManagerHappyPathsTest extends AbstractPlaygroundManagerTest {

    /**
     * Constructor.
     *
     * @param executionRequestRepository A mocked {@link ExecutionRequestRepository} passed to super class.
     * @param executionResultRepository  A mocked {@link ExecutionResultRepository} passed to super class.
     * @param executorServiceProxy       A mocked {@link ExecutorServiceCommandMessageProxy} passed to super class.
     */
    PlaygroundManagerHappyPathsTest(
            @Mock(name = "requestRepository") final ExecutionRequestRepository executionRequestRepository,
            @Mock(name = "resultRepository") final ExecutionResultRepository executionResultRepository,
            @Mock(name = "executorServiceProxy") final ExecutorServiceCommandMessageProxy executorServiceProxy) {
        super(executionRequestRepository,
                executionResultRepository,
                executorServiceProxy);
    }


    // ================================================================================================================
    // Tests
    // ================================================================================================================

    /**
     * Tests that an {@link ExecutionRequest} is created (i.e is saved and sent to run) when arguments are valid.
     */
    @Test
    void testExecutionRequestIsCreatedUsingValidArguments() {
        final var code = TestHelper.validCode();
        final var inputs = TestHelper.validInputOutputList();
        final var timeout = TestHelper.validTimeout();
        final var language = TestHelper.validLanguage();

        Mockito.when(executionRequestRepository.save(Mockito.any(ExecutionRequest.class))).then(i -> i.getArgument(0));
        final var request = playgroundManager.requestExecution(code, inputs, timeout, language);
        Assertions.assertAll("Execution Request properties are not the expected",
                () -> Assertions.assertEquals(
                        code,
                        request.getCode(),
                        "There is a mismatch in the code"
                ),
                () -> Assertions.assertEquals(
                        inputs,
                        request.getInputs(),
                        "There is a mismatch in the inputs moment"
                ),
                () -> Assertions.assertEquals(
                        timeout,
                        request.getTimeout(),
                        "There is a mismatch in the timeout"
                ),
                () -> Assertions.assertEquals(
                        language,
                        request.getLanguage(),
                        "There is a mismatch in the language"
                )
        );
        Mockito.verify(executionRequestRepository, Mockito.only()).save(Mockito.any(ExecutionRequest.class));
        Mockito.verifyZeroInteractions(executionResultRepository);
        Mockito.verify(executorServiceProxy, Mockito.only()).requestExecution(request);
    }

    /**
     * Tests that an {@link ExecutionResult} is returned when it exists for a given {@link ExecutionRequest}.
     *
     * @param mockedRequest A mocked {@link ExecutionRequest} (the one owning the {@link ExecutionResult}).
     * @param mockedResult  A mocker {@link ExecutionResult} (the one being returned).
     */
    @Test
    void testResultIsReturnedWhenItExists(
            @Mock(name = "request") final ExecutionRequest mockedRequest,
            @Mock(name = "result") final ExecutionResult mockedResult) {
        final var requestId = TestHelper.validExecutionRequestId();
        Mockito.when(executionRequestRepository.findById(requestId)).thenReturn(Optional.of(mockedRequest));
        Mockito.when(executionResultRepository.getResultFor(mockedRequest)).thenReturn(Optional.of(mockedResult));
        final var result = playgroundManager.getResultFor(requestId);
        Assertions.assertTrue(
                result.isPresent(),
                "The manager returned an empty Optional when the result exists"
        );
        Assertions.assertEquals(
                mockedResult,
                result.get(),
                "The returned result is not the one returned by the repository."
        );
        Mockito.verify(executionRequestRepository, Mockito.only()).findById(requestId);
        Mockito.verify(executionResultRepository, Mockito.only()).getResultFor(mockedRequest);
        Mockito.verifyZeroInteractions(executorServiceProxy);
    }

    /**
     * Tests the creation of a {@link TimedOutExecutionResult}.
     *
     * @param mockedRequest A mocker {@link ExecutionRequest}
     *                      (the one owning the created {@link TimedOutExecutionResult})
     */
    @Test
    void testTimedOutResultIsCreated(@Mock(name = "request") final ExecutionRequest mockedRequest) {
        abstractTestCreationOfResult(
                PlaygroundManager::receiveTimedOut,
                mockedRequest,
                PlaygroundManagerHappyPathsTest::timedOutResultMatcher
        );
    }

    /**
     * Tests the creation of a {@link FinishedExecutionResult}.
     *
     * @param mockedRequest A mocker {@link ExecutionRequest}
     *                      (the one owning the created {@link FinishedExecutionResult})
     */
    @Test
    void testFinishedResultIsCreated(@Mock(name = "request") final ExecutionRequest mockedRequest) {
        final var exitCode = TestHelper.validExitCode();
        final var stdout = TestHelper.validInputOutputList();
        final var stderr = TestHelper.validInputOutputList();
        abstractTestCreationOfResult(
                (manager, id) -> manager.receiveFinished(exitCode, stdout, stderr, id),
                mockedRequest,
                req -> finishedResultMatcher(exitCode, stdout, stderr, req)
        );
    }


    // ================================================================================================================
    // Abstract tests
    // ================================================================================================================

    /**
     * Abstract test of creation of an {@link ExecutionResult}.
     *
     * @param saveOperation   A {@link BiConsumer} of {@link PlaygroundManager} and {@link Long}
     *                        (the id of the {@link ExecutionRequest}) that performs the action of saving the result
     *                        by the {@link PlaygroundManager}.
     * @param mockedRequest   The {@link ExecutionRequest} owning the created {@link ExecutionResult}.
     * @param matcherProvider A {@link Function} that takes an {@link ExecutionRequest} and returns an
     *                        {@link ArgumentMatcher} to check the saved {@link ExecutionResult}.
     */
    private void abstractTestCreationOfResult(
            final BiConsumer<PlaygroundManager, Long> saveOperation,
            final ExecutionRequest mockedRequest,
            final Function<ExecutionRequest, ArgumentMatcher<ExecutionResult>> matcherProvider) {
        final var requestId = TestHelper.validExecutionRequestId();

        Mockito.when(executionRequestRepository.findById(requestId)).thenReturn(Optional.of(mockedRequest));
        Mockito.when(executionResultRepository.existsFor(mockedRequest)).thenReturn(false);
        Mockito.when(executionResultRepository.save(Mockito.any(ExecutionResult.class))).then(i -> i.getArgument(0));

        saveOperation.accept(playgroundManager, requestId);

        Mockito.verify(executionRequestRepository, Mockito.only()).findById(requestId);
        Mockito.verify(executionResultRepository, Mockito.times(1)).existsFor(mockedRequest);
        Mockito.verify(executionResultRepository, Mockito.times(1))
                .save(Mockito.argThat(matcherProvider.apply(mockedRequest)));
        Mockito.verifyNoMoreInteractions(executionResultRepository);
    }


    // ================================================================================================================
    // Helpers
    // ================================================================================================================

    /**
     * Creates an {@link ArgumentMatcher} of {@link ExecutionResult} that expects the
     * result is instance of {@link TimedOutExecutionResult}, and has a {@code executionRequest} property
     * whose value is the given {@code request}.
     *
     * @param request The expected {@link ExecutionRequest}.
     * @return The created {@link ArgumentMatcher} of {@link ExecutionResult}.
     */
    private static ArgumentMatcher<ExecutionResult> timedOutResultMatcher(final ExecutionRequest request) {
        return new HamcrestArgumentMatcher<>(basicExecutionResultMatcher(TimedOutExecutionResult.class, request));
    }

    /**
     * Creates an {@link ArgumentMatcher} of {@link ExecutionResult} that expects the
     * result is instance of {@link FinishedExecutionResult},
     * has a {@code exitCode} property whose value is the given {@code exitCode},
     * has a {@code stdout} property whose value is the given {@code stdout},
     * has a {@code stderr} property whose value is the given {@code stderr},
     * and has a {@code executionRequest} property whose value is the given {@code request}.
     *
     * @param exitCode The expected exit code.
     * @param stdout   The expected stdout.
     * @param stderr   The expected stderr.
     * @param request  The expected {@link ExecutionRequest}.
     * @return The created {@link ArgumentMatcher} of {@link ExecutionResult}.
     */
    private static ArgumentMatcher<ExecutionResult> finishedResultMatcher(
            final int exitCode,
            final List<String> stdout,
            final List<String> stderr,
            final ExecutionRequest request) {
        return new HamcrestArgumentMatcher<>(Matchers.allOf(
                basicExecutionResultMatcher(FinishedExecutionResult.class, request),
                Matchers.hasProperty("exitCode", Matchers.equalTo(exitCode)),
                Matchers.hasProperty("stdout", Matchers.equalTo(stdout)),
                Matchers.hasProperty("stderr", Matchers.equalTo(stderr))
        ));
    }

    /**
     * Creates a {@link Matcher} with basic stuff of an {@link ExecutionResult} (i.e owner and subtype).
     *
     * @param subclass The subtype to be checked.
     * @param request  The expected {@link ExecutionRequest}.
     * @return The created {@link Matcher}.
     */
    private static Matcher<ExecutionResult> basicExecutionResultMatcher(
            final Class<?> subclass,
            final ExecutionRequest request) {
        return Matchers.allOf(
                Matchers.instanceOf(subclass),
                Matchers.hasProperty("executionRequest", Matchers.equalTo(request))
        );
    }
}
