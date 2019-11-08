package ar.edu.itba.cep.playground_service.services;

import ar.edu.itba.cep.executor.models.Language;
import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionRequest;
import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionResponse;
import com.bellotapps.webapps_commons.exceptions.NoSuchEntityException;

import java.util.List;
import java.util.Optional;

/**
 * A port into the application that allows creating {@link PlaygroundServiceExecutionRequest},
 * and querying for {@link PlaygroundServiceExecutionResponse}s.
 */
public interface PlaygroundService {

    /**
     * Creates a {@link PlaygroundServiceExecutionRequest} with the given arguments.
     * Once created, the {@link PlaygroundServiceExecutionRequest} is sent to the Executor Service to be run.
     *
     * @param code             The code to be executed.
     * @param programArguments The inputs for the program.
     * @param stdin            The elements to be passed to the standard input.
     * @param compilerFlags    The compiler flags. Should null if the {@code language} is compiled.
     * @param timeout          The timeout for the execution.
     * @param mainFileName     The name of the file in which the "main" will be placed
     *                         (i.e the name of the file where the code will be copied).
     * @param language         The language in which the {@code code} is written.
     * @return The created {@link PlaygroundServiceExecutionRequest}.
     */
    PlaygroundServiceExecutionRequest requestExecution(
            final String code,
            final List<String> programArguments,
            final List<String> stdin,
            final String compilerFlags,
            final Long timeout,
            final String mainFileName,
            final Language language
    );

    /**
     * Retrieves the {@link PlaygroundServiceExecutionResponse} corresponding
     * to the {@link PlaygroundServiceExecutionRequest} with the given {@code executionRequestId}.
     *
     * @param executionRequestId The id of the {@link PlaygroundServiceExecutionRequest}
     *                           to which the returned {@link PlaygroundServiceExecutionResponse} corresponds to.
     * @return An {@link Optional} holding the corresponding {@link PlaygroundServiceExecutionResponse} if it exists,
     * or empty otherwise.
     * @throws NoSuchEntityException If there is no {@link PlaygroundServiceExecutionRequest}
     *                               with the given {@code executionRequestId}.
     */
    Optional<PlaygroundServiceExecutionResponse> getResponseFor(final long executionRequestId) throws NoSuchEntityException;
}
