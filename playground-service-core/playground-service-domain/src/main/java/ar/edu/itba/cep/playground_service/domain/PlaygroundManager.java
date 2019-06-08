package ar.edu.itba.cep.playground_service.domain;

import ar.edu.itba.cep.playground_service.models.ExecutionRequest;
import ar.edu.itba.cep.playground_service.models.ExecutionResult;
import ar.edu.itba.cep.playground_service.models.Language;
import ar.edu.itba.cep.playground_service.repositories.ExecutionRequestRepository;
import ar.edu.itba.cep.playground_service.repositories.ExecutionResultRepository;
import ar.edu.itba.cep.playground_service.services.PlaygroundService;
import com.bellotapps.webapps_commons.exceptions.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Manager of {@link ExecutionRequest}s and {@link ExecutionResult}s.
 */
@Service
public class PlaygroundManager implements PlaygroundService {

    /**
     * An {@link ExecutionRequestRepository}.
     */
    private final ExecutionRequestRepository executionRequestRepository;
    /**
     * An {@link ExecutionResultRepository}.
     */
    private final ExecutionResultRepository executionResultRepository;


    /**
     * Constructor.
     *
     * @param executionRequestRepository An {@link ExecutionRequestRepository}.
     * @param executionResultRepository  An {@link ExecutionResultRepository}.
     */
    @Autowired
    public PlaygroundManager(
            final ExecutionRequestRepository executionRequestRepository,
            final ExecutionResultRepository executionResultRepository) {
        this.executionRequestRepository = executionRequestRepository;
        this.executionResultRepository = executionResultRepository;
    }


    @Override
    public ExecutionRequest requestExecution(
            final String code,
            final List<String> inputs,
            final Long timeout,
            final Language language) {

        final var request = executionRequestRepository.save(
                new ExecutionRequest(
                        code,
                        inputs,
                        timeout,
                        language
                )
        );
        // TODO: send request to run
        return request;
    }

    @Override
    public Optional<ExecutionResult> getResultFor(final long executionRequestId) throws NoSuchEntityException {
        final var request = executionRequestRepository
                .findById(executionRequestId)
                .orElseThrow(NoSuchEntityException::new);
        return executionResultRepository.getResultFor(request);
    }
}
