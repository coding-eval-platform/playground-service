package ar.edu.itba.cep.playground_service.spring_data.interfaces;

import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * A repository for {@link PlaygroundServiceExecutionRequest}.
 */
@Repository
public interface SpringDataExecutionRequestRepository extends CrudRepository<PlaygroundServiceExecutionRequest, Long> {
}
