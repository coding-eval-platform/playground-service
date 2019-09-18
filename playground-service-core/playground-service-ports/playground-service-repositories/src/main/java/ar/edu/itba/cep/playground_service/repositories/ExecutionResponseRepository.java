package ar.edu.itba.cep.playground_service.repositories;

import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionRequest;
import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionResponse;
import com.bellotapps.webapps_commons.persistence.repository_utils.repositories.BasicRepository;

import java.util.Optional;

/**
 * A port out of the application that allows {@link PlaygroundServiceExecutionResponse} persistence.
 */
public interface ExecutionResponseRepository extends BasicRepository<PlaygroundServiceExecutionResponse, Long> {

    /**
     * Indicates whether a {@link PlaygroundServiceExecutionResponse}
     * exists for the given {@link PlaygroundServiceExecutionRequest}.
     *
     * @param executionRequest The {@link PlaygroundServiceExecutionRequest} to be checked.
     * @return {@code true} if a {@link PlaygroundServiceExecutionResponse}
     * already exists for the given {@code executionRequest}, or {@code false} otherwise.
     */
    boolean existsFor(final PlaygroundServiceExecutionRequest executionRequest);

    /**
     * Retrieves the {@link PlaygroundServiceExecutionResponse} corresponding to the given {@code request}.
     *
     * @param request The {@link PlaygroundServiceExecutionRequest}
     *                owning the returned {@link PlaygroundServiceExecutionResponse}.
     * @return An {@link Optional} containing the {@link PlaygroundServiceExecutionResponse}
     * if there is such, or empty otherwise.
     */
    Optional<PlaygroundServiceExecutionResponse> getResponseFor(final PlaygroundServiceExecutionRequest request);
}
