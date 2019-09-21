package ar.edu.itba.cep.playground_service.domain;

import ar.edu.itba.cep.executor.models.ExecutionResponse;
import ar.edu.itba.cep.playground_service.commands.ExecutorServiceCommandMessageProxy;
import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionRequest;
import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionResponse;
import ar.edu.itba.cep.playground_service.repositories.ExecutionRequestRepository;
import ar.edu.itba.cep.playground_service.repositories.ExecutionResponseRepository;
import com.bellotapps.webapps_commons.exceptions.NoSuchEntityException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.function.BiConsumer;

import static org.mockito.Mockito.*;

/**
 * Test class for {@link PlaygroundManager}, containing tests for the non-existence condition
 * (i.e how the manager behaves when trying to operate over entities that do not exist).
 */
@ExtendWith(MockitoExtension.class)
class PlaygroundManagerNonExistenceTest extends AbstractPlaygroundManagerTest {

    /**
     * Constructor.
     *
     * @param executionRequestRepository  A mocked {@link ExecutionRequestRepository} passed to super class.
     * @param executionResponseRepository A mocked {@link ExecutionResponseRepository} passed to super class.
     * @param executorServiceProxy        A mocked {@link ExecutorServiceCommandMessageProxy} passed to super class.
     */
    PlaygroundManagerNonExistenceTest(
            @Mock(name = "requestRepository") final ExecutionRequestRepository executionRequestRepository,
            @Mock(name = "responseRepository") final ExecutionResponseRepository executionResponseRepository,
            @Mock(name = "executorServiceProxy") final ExecutorServiceCommandMessageProxy executorServiceProxy) {
        super(executionRequestRepository,
                executionResponseRepository,
                executorServiceProxy);
    }


    // ================================================================================================================
    // Tests
    // ================================================================================================================

    /**
     * Tests that searching for a {@link PlaygroundServiceExecutionResponse} that does not exist does not fail,
     * and returns an empty {@link Optional}.
     */
    @Test
    void testSearchForExecutionResponseThatDoesNotExist(
            @Mock(name = "request") final PlaygroundServiceExecutionRequest mockedRequest) {
        final var requestId = TestHelper.validExecutionRequestId();
        when(executionRequestRepository.findById(requestId)).thenReturn(Optional.of(mockedRequest));
        when(executionResponseRepository.getResponseFor(mockedRequest)).thenReturn(Optional.empty());
        Assertions.assertTrue(
                playgroundManager.getResponseFor(requestId).isEmpty(),
                "Searching for an Execution Response that does not exist does not return an empty optional."
        );
        verify(executionRequestRepository, only()).findById(requestId);
        verify(executionResponseRepository, only()).getResponseFor(mockedRequest);
        verifyZeroInteractions(executorServiceProxy);
    }

    /**
     * Tests that searching for a {@link PlaygroundServiceExecutionResponse}
     * of a {@link PlaygroundServiceExecutionRequest} that does not exist throws a {@link NoSuchEntityException}.
     */
    @Test
    void testSearchForExecutionResponseOfExecutionRequestThatDoesNotExist() {
        abstractNonExistenceRequestTest(
                PlaygroundManager::getResponseFor,
                "Searching for an Execution Response of an Execution Request that does not exist" +
                        " does not throw a NoSuchEntityException."
        );
    }

    /**
     * Tests that processing an {@link ExecutionResponse} for a {@link PlaygroundServiceExecutionRequest}
     * that does not exist throws a {@link NoSuchEntityException}.
     *
     * @param response A mocked {@link ExecutionResponse} (the one being tried to process).
     */
    @Test
    void testProcessResponseForNonExistenceRequest(@Mock(name = "response") final ExecutionResponse response) {
        abstractNonExistenceRequestTest(
                (manager, id) -> manager.processResponse(id, response),
                "Trying to process an execution response for an execution request that does not exist" +
                        " does not throw a NoSuchEntityException."
        );
    }


    // ================================================================================================================
    // Abstract tests
    // ================================================================================================================

    /**
     * An abstract test for {@link PlaygroundManager} operations
     * in which the {@link PlaygroundServiceExecutionRequest} does not exist.
     *
     * @param managerOperation The {@link PlaygroundManager} operation.
     * @param message          The assertion message to be displayed in case the test fails.
     */
    void abstractNonExistenceRequestTest(
            final BiConsumer<PlaygroundManager, Long> managerOperation,
            final String message) {
        final var requestId = TestHelper.validExecutionRequestId();
        when(executionRequestRepository.findById(requestId)).thenReturn(Optional.empty());
        Assertions.assertThrows(
                NoSuchEntityException.class,
                () -> managerOperation.accept(playgroundManager, requestId),
                message
        );
        verify(executionRequestRepository, only()).findById(requestId);
        verifyZeroInteractions(executionResponseRepository);
        verifyZeroInteractions(executorServiceProxy);
    }
}
