package ar.edu.itba.cep.playground_service.spring_data.interfaces;

import ar.edu.itba.cep.playground_service.models.ExecutionRequest;
import ar.edu.itba.cep.playground_service.models.ExecutionResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A repository for {@link ExecutionResult}.
 */
@Repository
public interface SpringDataExecutionResultRepository extends CrudRepository<ExecutionResult, Long> {

    /**
     * Indicates whether an {@link ExecutionResult} exists for the given {@link ExecutionRequest}.
     *
     * @param executionRequest The {@link ExecutionRequest} to be checked.
     * @return {@code true} if an {@link ExecutionResult} already exists for the given {@code executionRequest},
     * or {@code false} otherwise.
     */
    boolean existsByExecutionRequest(final ExecutionRequest executionRequest);

    /**
     * Retrieves the {@link ExecutionResult} corresponding to the given {@code executionRequest}.
     *
     * @param executionRequest The {@link ExecutionRequest} owning the returned {@link ExecutionResult}.
     * @return An {@link Optional} containing the {@link ExecutionResult} if there is such, or empty otherwise.
     */
    Optional<ExecutionResult> findByExecutionRequest(final ExecutionRequest executionRequest);
}
