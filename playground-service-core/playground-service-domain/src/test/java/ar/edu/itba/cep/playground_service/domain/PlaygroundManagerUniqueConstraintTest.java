package ar.edu.itba.cep.playground_service.domain;

import ar.edu.itba.cep.playground_service.commands.ExecutorServiceCommandMessageProxy;
import ar.edu.itba.cep.playground_service.models.*;
import ar.edu.itba.cep.playground_service.repositories.ExecutionRequestRepository;
import ar.edu.itba.cep.playground_service.repositories.ExecutionResultRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * Test class for {@link PlaygroundManager}, containing tests for the non-existence condition
 * (i.e how the manager behaves when trying to operate over entities that do not exist).
 */
@ExtendWith(MockitoExtension.class)
class PlaygroundManagerUniqueConstraintTest extends AbstractPlaygroundManagerTest {

    /**
     * Constructor.
     *
     * @param executionRequestRepository A mocked {@link ExecutionRequestRepository} passed to super class.
     * @param executionResultRepository  A mocked {@link ExecutionResultRepository} passed to super class.
     * @param executorServiceProxy       A mocked {@link ExecutorServiceCommandMessageProxy} passed to super class.
     */
    PlaygroundManagerUniqueConstraintTest(
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
     * Tests that trying to save a {@link FinishedExecutionResult} when there is already one
     * for an {@link ExecutionRequest} that does not fails, and does not try to save it again.
     *
     * @param mockedRequest A mocked {@link ExecutionRequest}
     *                      (the one owning the {@link ExecutionResult} that already exists).
     */
    @Test
    void testSaveFinishedExecutionResultForExecutionRequestThatDoesNotExist(
            @Mock(name = "request") final ExecutionRequest mockedRequest) {
        final var exitCode = TestHelper.validExitCode();
        final var stdout = TestHelper.validInputOutputList();
        final var stderr = TestHelper.validInputOutputList();
        abstractUniqueConstraintTest(
                mockedRequest,
                (manager, id) -> manager.receiveFinished(exitCode, stdout, stderr, id),
                "Trying to save a Finished Execution Result for an Execution Request that does not exist" +
                        " does not throw a NoSuchEntityException."
        );
    }

    /**
     * Tests that trying to save a {@link TimedOutExecutionResult} when there is already one
     * for an {@link ExecutionRequest} that does not fails, and does not try to save it again.
     *
     * @param mockedRequest A mocked {@link ExecutionRequest}
     *                      (the one owning the {@link ExecutionResult} that already exists).
     */
    @Test
    void testSaveTimedOutExecutionResultWhenItAlreadyExistsForExecutionRequest(
            @Mock(name = "request") final ExecutionRequest mockedRequest) {
        abstractUniqueConstraintTest(
                mockedRequest,
                PlaygroundManager::receiveTimedOut,
                "Trying to save a Timed Out Execution Result for an Execution Request that does not exist" +
                        " does not throw a NoSuchEntityException."
        );
    }

    /**
     * Tests that trying to save a {@link CompileErrorExecutionResult} when there is already one
     * for an {@link ExecutionRequest} that does not fails, and does not try to save it again.
     *
     * @param mockedRequest A mocked {@link ExecutionRequest}
     *                      (the one owning the {@link ExecutionResult} that already exists).
     */
    @Test
    void testSaveCompileErrorExecutionResultWhenItAlreadyExistsForExecutionRequest(
            @Mock(name = "request") final ExecutionRequest mockedRequest) {
        final var compilerErrors = TestHelper.validInputOutputList();
        abstractUniqueConstraintTest(
                mockedRequest,
                (manager, id) -> manager.receiveCompileError(compilerErrors, id),
                "Trying to save an Compile Error Execution Result" +
                        " for an Execution Request that does not exist" +
                        " does not throw a NoSuchEntityException."
        );
    }

    /**
     * Tests that trying to save a {@link InitializationErrorExecutionResult} when there is already one
     * for an {@link ExecutionRequest} that does not fails, and does not try to save it again.
     *
     * @param mockedRequest A mocked {@link ExecutionRequest}
     *                      (the one owning the {@link ExecutionResult} that already exists).
     */
    @Test
    void testSaveInitializationErrorExecutionResultWhenItAlreadyExistsForExecutionRequest(
            @Mock(name = "request") final ExecutionRequest mockedRequest) {
        abstractUniqueConstraintTest(
                mockedRequest,
                PlaygroundManager::receiveInitializationError,
                "Trying to save an Initialization Error Execution Result" +
                        " for an Execution Request that does not exist" +
                        " does not throw a NoSuchEntityException."
        );
    }

    /**
     * Tests that trying to save a {@link UnknownErrorExecutionResult} when there is already one
     * for an {@link ExecutionRequest} that does not fails, and does not try to save it again.
     *
     * @param mockedRequest A mocked {@link ExecutionRequest}
     *                      (the one owning the {@link ExecutionResult} that already exists).
     */
    @Test
    void testSaveUnknownErrorExecutionResultWhenItAlreadyExistsForExecutionRequest(
            @Mock(name = "request") final ExecutionRequest mockedRequest) {
        abstractUniqueConstraintTest(
                mockedRequest,
                PlaygroundManager::receiveUnknownError,
                "Trying to save an Unknown Error Execution Result" +
                        " for an Execution Request that does not exist" +
                        " does not throw a NoSuchEntityException."
        );
    }

    // ================================================================================================================
    // Abstract tests
    // ================================================================================================================

    /**
     * An abstract test for unique constraint testing.
     *
     * @param mockedRequest    An {@link ExecutionRequest}
     *                         (the one owning the {@link ExecutionResult} that already exists).
     * @param managerOperation The {@link PlaygroundManager} operation.
     * @param message          The assertion message to be displayed in case the test fails.
     */
    void abstractUniqueConstraintTest(
            final ExecutionRequest mockedRequest,
            final BiConsumer<PlaygroundManager, Long> managerOperation,
            final String message) {
        final var requestId = TestHelper.validExecutionRequestId();
        Mockito.when(executionRequestRepository.findById(requestId)).thenReturn(Optional.of(mockedRequest));
        Mockito.when(executionResultRepository.existsFor(mockedRequest)).thenReturn(true);
        Assertions.assertDoesNotThrow(
                () -> managerOperation.accept(playgroundManager, requestId),
                message
        );
        Mockito.verify(executionRequestRepository, Mockito.only()).findById(requestId);
        Mockito.verify(executionResultRepository, Mockito.only()).existsFor(mockedRequest);
        Mockito.verifyZeroInteractions(executorServiceProxy);
    }
}
