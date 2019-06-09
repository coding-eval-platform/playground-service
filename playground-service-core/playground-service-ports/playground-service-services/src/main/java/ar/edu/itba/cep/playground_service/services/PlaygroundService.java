package ar.edu.itba.cep.playground_service.services;

import ar.edu.itba.cep.playground_service.models.ExecutionRequest;
import ar.edu.itba.cep.playground_service.models.ExecutionResult;
import ar.edu.itba.cep.playground_service.models.Language;
import com.bellotapps.webapps_commons.exceptions.NoSuchEntityException;

import java.util.List;
import java.util.Optional;

/**
 * A port into the application that allows creating {@link ar.edu.itba.cep.playground_service.models.ExecutionRequest},
 * and querying for {@link ar.edu.itba.cep.playground_service.models.ExecutionResult}s.
 */
public interface PlaygroundService {

    /**
     * Creates an {@link ExecutionRequest} with the given arguments. Once created, the {@link ExecutionRequest}
     * is sent to the Executor Service to be run.
     *
     * @param code     The code to be executed.
     * @param inputs   The inputs for the program.
     * @param timeout  The timeout for the execution.
     * @param language The language in which the {@code code} is written.
     * @return The created {@link ExecutionRequest}.
     */
    ExecutionRequest requestExecution(
            final String code,
            final List<String> inputs,
            final Long timeout,
            final Language language
    );

    /**
     * Retrieves the {@link ExecutionResult} corresponding to the {@link ExecutionRequest}
     * with the given {@code executionRequestId}.
     *
     * @param executionRequestId The id of the {@link ExecutionRequest} to which the returned {@link ExecutionResult}
     *                           corresponds to.
     * @return An {@link Optional} holding the corresponding {@link ExecutionResult} if it exists, or empty otherwise.
     * @throws NoSuchEntityException If there is no {@link ExecutionRequest} with the given {@code executionRequestId}.
     */
    Optional<ExecutionResult> getResultFor(final long executionRequestId) throws NoSuchEntityException;
}
