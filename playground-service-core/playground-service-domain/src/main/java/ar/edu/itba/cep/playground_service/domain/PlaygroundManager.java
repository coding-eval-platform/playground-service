package ar.edu.itba.cep.playground_service.domain;

import ar.edu.itba.cep.executor.api.ExecutionRequestSender;
import ar.edu.itba.cep.executor.api.ExecutionResponseHandler;
import ar.edu.itba.cep.executor.models.ExecutionRequest;
import ar.edu.itba.cep.executor.models.ExecutionResponse;
import ar.edu.itba.cep.executor.models.Language;
import ar.edu.itba.cep.playground_service.commands.ExecutionRequestId;
import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionRequest;
import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionResponse;
import ar.edu.itba.cep.playground_service.repositories.ExecutionRequestRepository;
import ar.edu.itba.cep.playground_service.repositories.ExecutionResponseRepository;
import ar.edu.itba.cep.playground_service.services.PlaygroundService;
import com.bellotapps.webapps_commons.exceptions.NoSuchEntityException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

/**
 * Manager of {@link PlaygroundServiceExecutionRequest}s and {@link PlaygroundServiceExecutionResponse}s.
 */
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PlaygroundManager implements PlaygroundService, ExecutionResponseHandler<ExecutionRequestId> {

    /**
     * An {@link ExecutionRequestRepository}.
     */
    private final ExecutionRequestRepository executionRequestRepository;
    /**
     * An {@link ExecutionResponseRepository}.
     */
    private final ExecutionResponseRepository executionResponseRepository;
    /**
     * A proxy for the Executor Service.
     */
    private final ExecutionRequestSender<ExecutionRequestId> executorService;


    // ================================================================================================================
    // PlaygroundService methods
    // ================================================================================================================

    @Override
    @Transactional
    public PlaygroundServiceExecutionRequest requestExecution(
            final String code,
            final List<String> programArguments,
            final List<String> stdin,
            final String compilerFlags,
            final Long timeout,
            final Language language) {
        final var request = PlaygroundServiceExecutionRequest.fromCommonsRequest(
                new ExecutionRequest(
                        code,
                        programArguments,
                        stdin,
                        compilerFlags,
                        timeout,
                        language
                )
        );
        final var savedRequest = executionRequestRepository.save(request);
        executorService.requestExecution(savedRequest.getRequest(), ExecutionRequestId.create(savedRequest.getId()));
        return savedRequest;
    }

    @Override
    public Optional<PlaygroundServiceExecutionResponse> getResponseFor(final long executionRequestId)
            throws NoSuchEntityException {
        final var request = loadExecutionRequest(executionRequestId);
        return executionResponseRepository
                .getResponseFor(request)
                .map(response -> {
                    final var wrappedResponse = response.getResponse();
                    // Initialize lazy collections
                    wrappedResponse.getStdout().size();
                    wrappedResponse.getStderr().size();
                    return response;
                })
                ;
    }


    // ================================================================================================================
    // ExecutionResponseProcessor methods
    // ================================================================================================================

    @Override
    @Transactional
    public void processExecutionResponse(
            final ExecutionResponse response,
            final ExecutionRequestId executionRequestIdData)
            throws IllegalArgumentException {
        Assert.notNull(response, "The response must not be null");
        Assert.notNull(executionRequestIdData, "The id data must not be null");
        final var request = loadExecutionRequest(executionRequestIdData.getRequestId());
        if (executionResponseRepository.existsFor(request)) {
            return; // Avoid adding a new response for the given request (they should be the same though).
        }
        executionResponseRepository.save(PlaygroundServiceExecutionResponse.fromCommonsResponse(request, response));
        // TODO: notify reception of response from the executor service.
    }


    // ================================================================================================================
    // Helpers
    // ================================================================================================================

    /**
     * Loads the {@link PlaygroundServiceExecutionRequest} with the given {@code executionRequestId}.
     *
     * @param executionRequestId The id of the {@link PlaygroundServiceExecutionRequest} to be loaded.
     * @return The {@link PlaygroundServiceExecutionRequest}.
     * @throws NoSuchEntityException If there is no {@link PlaygroundServiceExecutionRequest}
     *                               with the given {@code executionRequestId}.
     */
    private PlaygroundServiceExecutionRequest loadExecutionRequest(final long executionRequestId)
            throws NoSuchEntityException {
        return executionRequestRepository.findById(executionRequestId).orElseThrow(NoSuchEntityException::new);
    }
}
