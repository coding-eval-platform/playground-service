package ar.edu.itba.cep.playground_service.repositories;

import ar.edu.itba.cep.playground_service.models.ExecutionRequest;
import com.bellotapps.webapps_commons.persistence.repository_utils.repositories.BasicRepository;

/**
 * A port out of the application that allows {@link ExecutionRequest} persistence.
 */
public interface ExecutionRequestRepository extends BasicRepository<ExecutionRequest, Long> {
}
