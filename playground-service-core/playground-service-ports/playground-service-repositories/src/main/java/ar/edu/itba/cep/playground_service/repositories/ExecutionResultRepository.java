package ar.edu.itba.cep.playground_service.repositories;

import ar.edu.itba.cep.playground_service.models.ExecutionRequest;
import ar.edu.itba.cep.playground_service.models.ExecutionResult;
import com.bellotapps.webapps_commons.persistence.repository_utils.repositories.BasicRepository;

import java.util.Optional;

/**
 * A port out of the application that allows {@link ExecutionResult} persistence.
 */
public interface ExecutionResultRepository extends BasicRepository<ExecutionResult, Long> {

    /**
     * Indicates whether an {@link ExecutionResult} exists for the given {@link ExecutionRequest}.
     *
     * @param executionRequest The {@link ExecutionRequest} to be checked.
     * @return {@code true} if an {@link ExecutionResult} already exists for the given {@code executionRequest},
     * or {@code false} otherwise.
     */
    boolean existsFor(final ExecutionRequest executionRequest);

    /**
     * Retrieves the {@link ExecutionResult} corresponding to the given {@code executionRequest}.
     *
     * @param executionRequest The {@link ExecutionRequest} owning the returned {@link ExecutionResult}.
     * @return An {@link Optional} containing the {@link ExecutionResult} if there is such, or empty otherwise.
     */
    Optional<ExecutionResult> getResultFor(final ExecutionRequest executionRequest);
}
