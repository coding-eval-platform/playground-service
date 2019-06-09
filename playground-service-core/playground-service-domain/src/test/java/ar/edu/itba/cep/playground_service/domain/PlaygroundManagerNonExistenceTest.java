package ar.edu.itba.cep.playground_service.domain;

import ar.edu.itba.cep.playground_service.commands.ExecutorServiceCommandMessageProxy;
import ar.edu.itba.cep.playground_service.models.ExecutionRequest;
import ar.edu.itba.cep.playground_service.models.ExecutionResult;
import ar.edu.itba.cep.playground_service.models.FinishedExecutionResult;
import ar.edu.itba.cep.playground_service.models.TimedOutExecutionResult;
import ar.edu.itba.cep.playground_service.repositories.ExecutionRequestRepository;
import ar.edu.itba.cep.playground_service.repositories.ExecutionResultRepository;
import com.bellotapps.webapps_commons.exceptions.NoSuchEntityException;
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
class PlaygroundManagerNonExistenceTest extends AbstractPlaygroundManagerTest {

    /**
     * Constructor.
     *
     * @param executionRequestRepository A mocked {@link ExecutionRequestRepository} passed to super class.
     * @param executionResultRepository  A mocked {@link ExecutionResultRepository} passed to super class.
     * @param executorServiceProxy       A mocked {@link ExecutorServiceCommandMessageProxy} passed to super class.
     */
    PlaygroundManagerNonExistenceTest(
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
     * Tests that searching for an {@link ExecutionResult} that does not exist does not fail,
     * and returns an empty {@link Optional}.
     */
    @Test
    void testSearchForExecutionResultThatDoesNotExist(
            @Mock(name = "request") final ExecutionRequest mockedRequest) {
        final var requestId = TestHelper.validExecutionRequestId();
        Mockito.when(executionRequestRepository.findById(requestId)).thenReturn(Optional.of(mockedRequest));
        Mockito.when(executionResultRepository.getResultFor(mockedRequest)).thenReturn(Optional.empty());
        Assertions.assertTrue(
                playgroundManager.getResultFor(requestId).isEmpty(),
                "Searching for an Execution Result that does not exist does not return an empty optional."
        );
        Mockito.verify(executionRequestRepository, Mockito.only()).findById(requestId);
        Mockito.verify(executionResultRepository, Mockito.only()).getResultFor(mockedRequest);
        Mockito.verifyZeroInteractions(executorServiceProxy);
    }

    /**
     * Tests that searching for an {@link ExecutionResult} of an {@link ExecutionRequest} that does not exist
     * throws a {@link NoSuchEntityException}.
     */
    @Test
    void testSearchForExecutionResultOfExecutionRequestThatDoesNotExist() {
        abstractNonExistenceRequestTest(
                PlaygroundManager::getResultFor,
                "Searching for an Execution Result of an Execution Request that does not exist" +
                        " does not throw a NoSuchEntityException."
        );
    }

    /**
     * Tests that saving a {@link TimedOutExecutionResult} for an {@link ExecutionRequest} that does not exist
     * throws a {@link NoSuchEntityException}.
     */
    @Test
    void testSaveTimedOutExecutionResultForExecutionRequestThatDoesNotExist() {
        abstractNonExistenceRequestTest(
                PlaygroundManager::receiveTimedOut,
                "Trying to save a Timed Out Execution Result for an Execution Request that does not exist" +
                        " does not throw a NoSuchEntityException."
        );
    }

    /**
     * Tests that saving a {@link FinishedExecutionResult} for an {@link ExecutionRequest} that does not exist
     * throws a {@link NoSuchEntityException}.
     */
    @Test
    void testSaveFinishedExecutionResultForExecutionRequestThatDoesNotExist() {
        final var exitCode = TestHelper.validExitCode();
        final var stdout = TestHelper.validInputOutputList();
        final var stderr = TestHelper.validInputOutputList();
        abstractNonExistenceRequestTest(
                (manager, id) -> manager.receiveFinished(exitCode, stdout, stderr, id),
                "Trying to save a Finished Execution Result for an Execution Request that does not exist" +
                        " does not throw a NoSuchEntityException."
        );
    }


    // ================================================================================================================
    // Abstract tests
    // ================================================================================================================

    /**
     * An abstract test for {@link PlaygroundManager} operations in which the {@link ExecutionRequest} does not exist.
     *
     * @param managerOperation The {@link PlaygroundManager} operation.
     * @param message          The assertion message to be displayed in case the test fails.
     */
    void abstractNonExistenceRequestTest(
            final BiConsumer<PlaygroundManager, Long> managerOperation,
            final String message) {
        final var requestId = TestHelper.validExecutionRequestId();
        Mockito.when(executionRequestRepository.findById(requestId)).thenReturn(Optional.empty());
        Assertions.assertThrows(
                NoSuchEntityException.class,
                () -> managerOperation.accept(playgroundManager, requestId),
                message
        );
        Mockito.verify(executionRequestRepository, Mockito.only()).findById(requestId);
        Mockito.verifyZeroInteractions(executionResultRepository);
        Mockito.verifyZeroInteractions(executorServiceProxy);
    }
}
