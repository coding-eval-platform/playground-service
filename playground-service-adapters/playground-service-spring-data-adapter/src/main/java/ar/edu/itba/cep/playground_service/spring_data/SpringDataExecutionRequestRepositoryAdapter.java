package ar.edu.itba.cep.playground_service.spring_data;

import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionRequest;
import ar.edu.itba.cep.playground_service.repositories.ExecutionRequestRepository;
import ar.edu.itba.cep.playground_service.spring_data.interfaces.SpringDataExecutionRequestRepository;
import com.bellotapps.webapps_commons.persistence.spring_data.repository_utils_adapters.repositories.BasicRepositoryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * A mocked {@link PlaygroundServiceExecutionRequest} repository used to boot the application.
 * Remove when Spring Data dependencies are added.
 */
@Repository
public class SpringDataExecutionRequestRepositoryAdapter
        implements ExecutionRequestRepository, BasicRepositoryAdapter<PlaygroundServiceExecutionRequest, Long> {

    /**
     * A {@link SpringDataExecutionRequestRepository} to which all operations are delegated.
     */
    private final SpringDataExecutionRequestRepository repository;


    /**
     * Constructor.
     *
     * @param repository A {@link SpringDataExecutionRequestRepository} to which all operations are delegated.
     */
    @Autowired
    public SpringDataExecutionRequestRepositoryAdapter(final SpringDataExecutionRequestRepository repository) {
        this.repository = repository;
    }


    // ================================================================================================================
    // RepositoryAdapter
    // ================================================================================================================

    @Override
    public CrudRepository<PlaygroundServiceExecutionRequest, Long> getCrudRepository() {
        return repository;
    }
}
