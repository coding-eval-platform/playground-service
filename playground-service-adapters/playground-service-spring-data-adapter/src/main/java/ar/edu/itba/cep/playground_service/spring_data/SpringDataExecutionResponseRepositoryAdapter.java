package ar.edu.itba.cep.playground_service.spring_data;

import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionRequest;
import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionResponse;
import ar.edu.itba.cep.playground_service.repositories.ExecutionResponseRepository;
import ar.edu.itba.cep.playground_service.spring_data.interfaces.SpringDataExecutionResponseRepository;
import com.bellotapps.webapps_commons.persistence.spring_data.repository_utils_adapters.repositories.BasicRepositoryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A mocked {@link PlaygroundServiceExecutionResponse} repository used to boot the application.
 * Remove when Spring Data dependencies are added.
 */
@Repository
public class SpringDataExecutionResponseRepositoryAdapter
        implements ExecutionResponseRepository, BasicRepositoryAdapter<PlaygroundServiceExecutionResponse, Long> {

    /**
     * A {@link SpringDataExecutionResponseRepository} to which all operations are delegated.
     */
    private final SpringDataExecutionResponseRepository repository;


    /**
     * Constructor.
     *
     * @param repository A {@link SpringDataExecutionResponseRepository} to which all operations are delegated.
     */
    @Autowired
    public SpringDataExecutionResponseRepositoryAdapter(final SpringDataExecutionResponseRepository repository) {
        this.repository = repository;
    }


    // ================================================================================================================
    // RepositoryAdapter
    // ================================================================================================================

    @Override
    public CrudRepository<PlaygroundServiceExecutionResponse, Long> getCrudRepository() {
        return repository;
    }


    // ================================================================================================================
    // ExecutionResponseRepository specific methods
    // ================================================================================================================

    @Override
    public boolean existsFor(final PlaygroundServiceExecutionRequest executionRequest) {
        return repository.existsByPlaygroundServiceExecutionRequest(executionRequest);
    }

    @Override
    public Optional<PlaygroundServiceExecutionResponse> getResponseFor(final PlaygroundServiceExecutionRequest request) {
        return repository.findByPlaygroundServiceExecutionRequest(request);
    }

}
