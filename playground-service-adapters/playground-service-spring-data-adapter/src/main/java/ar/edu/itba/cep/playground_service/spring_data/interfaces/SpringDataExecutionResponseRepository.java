package ar.edu.itba.cep.playground_service.spring_data.interfaces;

import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionRequest;
import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A repository for {@link PlaygroundServiceExecutionResponse}.
 */
@Repository
public interface SpringDataExecutionResponseRepository extends CrudRepository<PlaygroundServiceExecutionResponse, Long> {

    /**
     * Indicates whether a {@link PlaygroundServiceExecutionResponse} exists
     * for the given {@link PlaygroundServiceExecutionRequest}.
     *
     * @param executionRequest The {@link PlaygroundServiceExecutionRequest} to be checked.
     * @return {@code true} if a {@link PlaygroundServiceExecutionResponse} already exists
     * for the given {@code executionRequest},
     * or {@code false} otherwise.
     */
    boolean existsByPlaygroundServiceExecutionRequest(final PlaygroundServiceExecutionRequest executionRequest);

    /**
     * Retrieves the {@link PlaygroundServiceExecutionResponse} corresponding to the given {@code executionRequest}.
     *
     * @param executionRequest The {@link PlaygroundServiceExecutionRequest} owning the returned {@link PlaygroundServiceExecutionResponse}.
     * @return An {@link Optional} containing the {@link PlaygroundServiceExecutionResponse} if there is such, or empty otherwise.
     */
    Optional<PlaygroundServiceExecutionResponse> findByPlaygroundServiceExecutionRequest(
            final PlaygroundServiceExecutionRequest executionRequest
    );
}
