package ar.edu.itba.cep.playground_service.repositories;

import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionRequest;
import com.bellotapps.webapps_commons.persistence.repository_utils.repositories.BasicRepository;

/**
 * A port out of the application that allows {@link PlaygroundServiceExecutionRequest} persistence.
 */
public interface ExecutionRequestRepository extends BasicRepository<PlaygroundServiceExecutionRequest, Long> {
}
