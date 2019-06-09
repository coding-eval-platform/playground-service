package ar.edu.itba.cep.playground_service.domain;

import ar.edu.itba.cep.playground_service.commands.ExecutionResultProcessor;
import ar.edu.itba.cep.playground_service.commands.ExecutorServiceCommandMessageProxy;
import ar.edu.itba.cep.playground_service.models.*;
import ar.edu.itba.cep.playground_service.repositories.ExecutionRequestRepository;
import ar.edu.itba.cep.playground_service.repositories.ExecutionResultRepository;
import ar.edu.itba.cep.playground_service.services.PlaygroundService;
import com.bellotapps.webapps_commons.exceptions.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Manager of {@link ExecutionRequest}s and {@link ExecutionResult}s.
 */
@Service
public class PlaygroundManager implements PlaygroundService, ExecutionResultProcessor {

    /**
     * An {@link ExecutionRequestRepository}.
     */
    private final ExecutionRequestRepository executionRequestRepository;
    /**
     * An {@link ExecutionResultRepository}.
     */
    private final ExecutionResultRepository executionResultRepository;
    /**
     * A proxy for the Executor Service.
     */
    private final ExecutorServiceCommandMessageProxy executorService;


    /**
     * Constructor.
     *
     * @param executionRequestRepository An {@link ExecutionRequestRepository}.
     * @param executionResultRepository  An {@link ExecutionResultRepository}.
     * @param executorService            A proxy for the Executor Service.
     */
    @Autowired
    public PlaygroundManager(
            final ExecutionRequestRepository executionRequestRepository,
            final ExecutionResultRepository executionResultRepository,
            final ExecutorServiceCommandMessageProxy executorService) {
        this.executionRequestRepository = executionRequestRepository;
        this.executionResultRepository = executionResultRepository;
        this.executorService = executorService;
    }


    @Override
    public ExecutionRequest requestExecution(
            final String code,
            final List<String> inputs,
            final Long timeout,
            final Language language) {
        final var request = executionRequestRepository.save(
                new ExecutionRequest(
                        code,
                        inputs,
                        timeout,
                        language
                )
        );
        executorService.requestExecution(request);
        return request;
    }

    @Override
    public Optional<ExecutionResult> getResultFor(final long executionRequestId) throws NoSuchEntityException {
        final var request = loadExecutionRequest(executionRequestId);
        return executionResultRepository.getResultFor(request);
    }

    @Override
    public void receiveTimedOut(final long executionRequestId) {
        storeResultFor(executionRequestId, TimedOutExecutionResult::new);
    }

    @Override
    public void receiveFinished(
            final int exitCode,
            final List<String> stdout,
            final List<String> stderr,
            final long executionRequestId) {
        storeResultFor(executionRequestId, request -> new FinishedExecutionResult(exitCode, stdout, stderr, request));
    }

    /**
     * Loads the {@link ExecutionRequest} with the given {@code executionRequestId}.
     *
     * @param executionRequestId The id of the {@link ExecutionRequest} to be loaded.
     * @return The {@link ExecutionRequest}.
     * @throws NoSuchEntityException If there is no {@link ExecutionRequest} with the given {@code executionRequestId}.
     */
    private ExecutionRequest loadExecutionRequest(final long executionRequestId) throws NoSuchEntityException {
        return executionRequestRepository.findById(executionRequestId).orElseThrow(NoSuchEntityException::new);
    }

    /**
     * Stores an {@link ExecutionResult} that corresponds to the {@link ExecutionRequest}
     * with the given {@code executionRequestId}.
     *
     * @param executionRequestId The id of the {@link ExecutionRequest} that owns the {@link ExecutionResult}.
     * @param resultSupplier     A {@link Function} that creates an {@link ExecutionResult} that belongs to an
     *                           input {@link ExecutionRequest}.
     * @throws NoSuchEntityException If there is no {@link ExecutionRequest} with the given {@code executionRequestId}.
     */
    private void storeResultFor(
            final long executionRequestId,
            final Function<ExecutionRequest, ExecutionResult> resultSupplier) throws NoSuchEntityException {
        final var request = loadExecutionRequest(executionRequestId);
        if (executionResultRepository.existsFor(request)) {
            return; // Avoid adding a new result for the given request (they should be the same though).
        }
        final var result = resultSupplier.apply(request);
        executionResultRepository.save(result);
    }
}
