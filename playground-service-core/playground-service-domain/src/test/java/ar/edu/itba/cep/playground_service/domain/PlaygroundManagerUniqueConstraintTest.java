package ar.edu.itba.cep.playground_service.domain;

import ar.edu.itba.cep.executor.api.ExecutionRequestSender;
import ar.edu.itba.cep.executor.models.ExecutionResponse;
import ar.edu.itba.cep.playground_service.commands.ExecutionRequestId;
import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionRequest;
import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionResponse;
import ar.edu.itba.cep.playground_service.repositories.ExecutionRequestRepository;
import ar.edu.itba.cep.playground_service.repositories.ExecutionResponseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

/**
 * Test class for {@link PlaygroundManager}, containing tests for the non-existence condition
 * (i.e how the manager behaves when trying to operate over entities that do not exist).
 */
@ExtendWith(MockitoExtension.class)
class PlaygroundManagerUniqueConstraintTest extends AbstractPlaygroundManagerTest {

    /**
     * Constructor.
     *
     * @param executionRequestRepository  A mocked {@link ExecutionRequestRepository} passed to super class.
     * @param executionResponseRepository A mocked {@link ExecutionResponseRepository} passed to super class.
     * @param executorServiceProxy        A mocked {@link ExecutionRequestSender} passed to super class.
     */
    PlaygroundManagerUniqueConstraintTest(
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
     * Tests that another {@link PlaygroundServiceExecutionResponse} is not created if already exists
     * for a {@link PlaygroundServiceExecutionRequest} in the
     * {@link PlaygroundManager#processExecutionResponse(ExecutionResponse, ExecutionRequestId)} method.
     *
     * @param request  A mocked {@link PlaygroundServiceExecutionRequest}
     *                 (the one owning the {@link PlaygroundServiceExecutionResponse} that already exists).
     * @param response A mocked {@link ExecutionResponse}
     *                 to be passed to the {@link PlaygroundManager}'s method.
     */
    @Test
    void testUniquenessOfResponses(
            @Mock(name = "request") final PlaygroundServiceExecutionRequest request,
            @Mock(name = "response") final ExecutionResponse response) {
        final var requestId = TestHelper.validExecutionRequestId();
        when(executionRequestRepository.findById(requestId)).thenReturn(Optional.of(request));
        when(executionResponseRepository.existsFor(request)).thenReturn(true);

        playgroundManager.processExecutionResponse(response, ExecutionRequestId.create(requestId));

        verify(executionRequestRepository, only()).findById(requestId);
        verify(executionResponseRepository, only()).existsFor(request);
        verifyNoMoreInteractions(executionResponseRepository);
        verifyZeroInteractions(executorServiceProxy);
        verifyZeroInteractions(executorServiceProxy);
    }
}
