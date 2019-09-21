package ar.edu.itba.cep.playground_service.commands;

import ar.edu.itba.cep.executor.models.ExecutionResponse;


/**
 * A port into the application that allows receiving playgrounds, in order to processed.
 * This will act as a callback when receiving the response of an execution request.
 */
public interface ExecutionResponseProcessor {

    /**
     * Process the given {@code response}, with the given {@code executionRequestId}.
     *
     * @param executionRequestId The
     *                           {@link ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionRequest}'s id
     * @param response           The {@link ExecutionResponse} to be processed.
     * @throws IllegalArgumentException If the given {@code response} is null.
     */
    void processResponse(final long executionRequestId, final ExecutionResponse response)
            throws IllegalArgumentException;
}
