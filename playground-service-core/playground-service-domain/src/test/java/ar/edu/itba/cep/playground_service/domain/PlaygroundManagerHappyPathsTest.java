package ar.edu.itba.cep.playground_service.domain;

import ar.edu.itba.cep.executor.api.ExecutionRequestSender;
import ar.edu.itba.cep.executor.models.ExecutionResponse;
import ar.edu.itba.cep.playground_service.commands.ExecutionRequestId;
import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionRequest;
import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionResponse;
import ar.edu.itba.cep.playground_service.repositories.ExecutionRequestRepository;
import ar.edu.itba.cep.playground_service.repositories.ExecutionResponseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link PlaygroundManager}, containing tests for the happy paths
 * (i.e how the manager behaves when operating with valid values, entity states, etc.).
 */
@ExtendWith(MockitoExtension.class)
class PlaygroundManagerHappyPathsTest extends AbstractPlaygroundManagerTest {

    /**
     * Constructor.
     *
     * @param executionRequestRepository  A mocked {@link ExecutionRequestRepository} passed to super class.
     * @param executionResponseRepository A mocked {@link ExecutionResponseRepository} passed to super class.
     * @param executorServiceProxy        A mocked {@link ExecutionRequestSender} passed to super class.
     */
    PlaygroundManagerHappyPathsTest(
            @Mock(name = "requestRepository") final ExecutionRequestRepository executionRequestRepository,
            @Mock(name = "responseRepository") final ExecutionResponseRepository executionResponseRepository,
            @Mock(name = "executorServiceProxy") final ExecutionRequestSender<ExecutionRequestId> executorServiceProxy) {
        super(executionRequestRepository,
                executionResponseRepository,
                executorServiceProxy);
    }


    // ================================================================================================================
    // Tests
    // ================================================================================================================

    /**
     * Tests that a {@link PlaygroundServiceExecutionRequest} is created
     * (i.e is saved and sent to run) when arguments are valid.
     */
    @Test
    void testExecutionRequestIsCreatedUsingValidArguments() {
        final var code = TestHelper.validCode();
        final var programArguments = TestHelper.validInputOutputList();
        final var stdin = TestHelper.validInputOutputList();
        final var compilerFlags = TestHelper.validCompilerFlags();
        final var timeout = TestHelper.validTimeout();
        final var language = TestHelper.validLanguage();

        when(executionRequestRepository.save(any(PlaygroundServiceExecutionRequest.class))).then(i -> i.getArgument(0));
        final var playgroundServiceExecutionRequest = playgroundManager.requestExecution(
                code,
                programArguments,
                stdin,
                compilerFlags,
                timeout,
                language
        );
        final var commonsRequest = playgroundServiceExecutionRequest.getRequest();
        Assertions.assertAll("Execution Request properties are not the expected",
                () -> Assertions.assertEquals(
                        code,
                        commonsRequest.getCode(),
                        "There is a mismatch in the code"
                ),
                () -> Assertions.assertEquals(
                        programArguments,
                        commonsRequest.getProgramArguments(),
                        "There is a mismatch in the program arguments list"
                ),
                () -> Assertions.assertEquals(
                        stdin,
                        commonsRequest.getStdin(),
                        "There is a mismatch in the stdin list"
                ),
                () -> Assertions.assertEquals(
                        compilerFlags,
                        commonsRequest.getCompilerFlags(),
                        "There is a mismatch in the compiler flags"
                ),
                () -> Assertions.assertEquals(
                        timeout,
                        commonsRequest.getTimeout(),
                        "There is a mismatch in the timeout"
                ),
                () -> Assertions.assertEquals(
                        language,
                        commonsRequest.getLanguage(),
                        "There is a mismatch in the language"
                )
        );
        verify(executionRequestRepository, only()).save(any(PlaygroundServiceExecutionRequest.class));
        verifyZeroInteractions(executionResponseRepository);
        verify(executorServiceProxy, only()).requestExecution(
                playgroundServiceExecutionRequest.getRequest(),
                ExecutionRequestId.create(playgroundServiceExecutionRequest.getId())
        );
    }

    /**
     * Tests that a {@link PlaygroundServiceExecutionResponse} is returned
     * when it exists for a given {@link PlaygroundServiceExecutionRequest}.
     *
     * @param request            A mocked {@link PlaygroundServiceExecutionRequest}
     *                           (the one owning the {@link PlaygroundServiceExecutionResponse}).
     * @param playgroundResponse A mocker {@link PlaygroundServiceExecutionResponse} (the one being returned).
     */
    @Test
    void testResponseIsReturnedWhenItExists(
            @Mock(name = "request") final PlaygroundServiceExecutionRequest request,
            @Mock(name = "response") final ExecutionResponse response,
            @Mock(name = "playgroundResponse") final PlaygroundServiceExecutionResponse playgroundResponse) {
        final var requestId = TestHelper.validExecutionRequestId();
        when(executionRequestRepository.findById(requestId)).thenReturn(Optional.of(request));
        when(executionResponseRepository.getResponseFor(request)).thenReturn(Optional.of(playgroundResponse));
        when(playgroundResponse.getResponse()).thenReturn(response);
        final var retrievedResponse = playgroundManager.getResponseFor(requestId);
        Assertions.assertTrue(
                retrievedResponse.isPresent(),
                "The manager returned an empty Optional when the response exists"
        );
        Assertions.assertEquals(
                playgroundResponse,
                retrievedResponse.get(),
                "The returned response is not the one returned by the repository."
        );
        verify(executionRequestRepository, only()).findById(requestId);
        verify(executionResponseRepository, only()).getResponseFor(request);
        verifyZeroInteractions(executorServiceProxy);
    }

    /**
     * Tests the creation of a {@link PlaygroundServiceExecutionResponse}.
     *
     * @param response A mocked commons {@link ExecutionResponse} (the one to be processed).
     * @param request  A mocked {@link PlaygroundServiceExecutionRequest}
     *                 (the one owning the {@link PlaygroundServiceExecutionResponse}) to be created.
     */
    @Test
    void testExecutionResponseIsCreated(
            @Mock(name = "response") final ExecutionResponse response,
            @Mock(name = "request") final PlaygroundServiceExecutionRequest request) {

        final var requestId = TestHelper.validExecutionRequestId();

        when(executionRequestRepository.findById(requestId)).thenReturn(Optional.of(request));
        when(executionResponseRepository.existsFor(request)).thenReturn(false);
        when(executionResponseRepository.save(any(PlaygroundServiceExecutionResponse.class))).then(i -> i.getArgument(0));

        playgroundManager.processExecutionResponse(response, ExecutionRequestId.create(requestId));

        verify(executionRequestRepository, only()).findById(requestId);
        verify(executionResponseRepository, times(1)).existsFor(request);
        verify(executionResponseRepository, times(1)).save(
                argThat(resp -> resp.getPlaygroundServiceExecutionRequest().equals(request) && resp.getResponse() == response)
        );
        verifyNoMoreInteractions(executionResponseRepository);
    }
}
