package ar.edu.itba.cep.playground_service.commands;

import ar.edu.itba.cep.playground_service.models.ExecutionRequest;

/**
 * A port out of the application that allows sending async command messages to the executor service.
 */
public interface ExecutorServiceCommandMessageProxy {

    /**
     * Requests an execution to the executor service.
     *
     * @param executionRequest The {@link ExecutionRequest} to be sent to the executor service.
     */
    void requestExecution(final ExecutionRequest executionRequest);
}
