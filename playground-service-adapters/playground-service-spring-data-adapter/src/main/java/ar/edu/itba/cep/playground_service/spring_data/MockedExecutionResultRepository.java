package ar.edu.itba.cep.playground_service.spring_data;

import ar.edu.itba.cep.playground_service.models.ExecutionRequest;
import ar.edu.itba.cep.playground_service.models.ExecutionResult;
import ar.edu.itba.cep.playground_service.repositories.ExecutionResultRepository;
import com.bellotapps.webapps_commons.exceptions.NotImplementedException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * A mocked {@link ExecutionResult} repository used to boot the application.
 * Remove when Spring Data dependencies are added.
 */
@Repository
public class MockedExecutionResultRepository implements ExecutionResultRepository {
    @Override
    public long count() {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public boolean existsById(final Long aLong) throws IllegalArgumentException {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public Optional<ExecutionResult> findById(final Long aLong) throws IllegalArgumentException {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public Iterable<ExecutionResult> findAll() {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public <S extends ExecutionResult> S save(final S entity) throws IllegalArgumentException {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public <S extends ExecutionResult> void delete(final S entity) throws IllegalArgumentException {
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

    @Override
    public boolean existsFor(final ExecutionRequest executionRequest) {
        throw new NotImplementedException("Not implemented yet!");
    }

    @Override
    public Optional<ExecutionResult> getResultFor(final ExecutionRequest executionRequest) {
        throw new NotImplementedException("Not implemented yet!");
    }
}
