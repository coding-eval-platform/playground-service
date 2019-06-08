package ar.edu.itba.cep.playground_service.spring_data;

import ar.edu.itba.cep.playground_service.models.ExecutionRequest;
import ar.edu.itba.cep.playground_service.repositories.ExecutionRequestRepository;
import com.bellotapps.webapps_commons.exceptions.NotImplementedException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A mocked {@link ar.edu.itba.cep.playground_service.models.ExecutionRequest} repository used to boot the application.
 * Remove when Spring Data dependencies are added.
 */
@Repository
public class MockedExecutionRequestRepository implements ExecutionRequestRepository {
    @Override
    public long count() {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public boolean existsById(final Long aLong) throws IllegalArgumentException {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public Optional<ExecutionRequest> findById(final Long aLong) throws IllegalArgumentException {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public Iterable<ExecutionRequest> findAll() {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public <S extends ExecutionRequest> S save(final S entity) throws IllegalArgumentException {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public <S extends ExecutionRequest> void delete(final S entity) throws IllegalArgumentException {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public void deleteById(final Long aLong) throws IllegalArgumentException {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public void deleteAll() {
        throw new NotImplementedException("Not implemented yet!");
    }
}
