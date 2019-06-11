package ar.edu.itba.cep.playground_service.spring_data;

import ar.edu.itba.cep.playground_service.models.ExecutionRequest;
import ar.edu.itba.cep.playground_service.models.ExecutionResult;
import ar.edu.itba.cep.playground_service.repositories.ExecutionResultRepository;
import ar.edu.itba.cep.playground_service.spring_data.interfaces.SpringDataExecutionResultRepository;
import com.bellotapps.webapps_commons.persistence.spring_data.repository_utils_adapters.repositories.BasicRepositoryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A mocked {@link ExecutionResult} repository used to boot the application.
 * Remove when Spring Data dependencies are added.
 */
@Repository
public class SpringDataExecutionResultRepositoryAdapter
        implements ExecutionResultRepository, BasicRepositoryAdapter<ExecutionResult, Long> {

    /**
     * A {@link SpringDataExecutionResultRepository} to which all operations are delegated.
     */
    private final SpringDataExecutionResultRepository repository;


    /**
     * Constructor.
     *
     * @param repository A {@link SpringDataExecutionResultRepository} to which all operations are delegated.
     */
    @Autowired
    public SpringDataExecutionResultRepositoryAdapter(final SpringDataExecutionResultRepository repository) {
        this.repository = repository;
    }


    // ================================================================================================================
    // RepositoryAdapter
    // ================================================================================================================

    @Override
    public CrudRepository<ExecutionResult, Long> getCrudRepository() {
        return repository;
    }


    // ================================================================================================================
    // ExecutionResultRepository specific methods
    // ================================================================================================================

    @Override
    public boolean existsFor(final ExecutionRequest executionRequest) {
        return repository.existsByExecutionRequest(executionRequest);
    }

    @Override
    public Optional<ExecutionResult> getResultFor(final ExecutionRequest executionRequest) {
        return repository.findByExecutionRequest(executionRequest);
    }

}
