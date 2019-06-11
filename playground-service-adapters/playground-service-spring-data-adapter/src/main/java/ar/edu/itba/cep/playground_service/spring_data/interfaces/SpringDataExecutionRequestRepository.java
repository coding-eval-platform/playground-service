package ar.edu.itba.cep.playground_service.spring_data.interfaces;

import ar.edu.itba.cep.playground_service.models.ExecutionRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * A repository for {@link ExecutionRequest}.
 */
@Repository
public interface SpringDataExecutionRequestRepository extends CrudRepository<ExecutionRequest, Long> {
}
