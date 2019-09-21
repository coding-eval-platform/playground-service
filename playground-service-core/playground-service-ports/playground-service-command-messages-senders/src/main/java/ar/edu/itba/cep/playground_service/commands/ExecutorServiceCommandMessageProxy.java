package ar.edu.itba.cep.playground_service.commands;

import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionRequest;

/**
 * A port out of the application that allows sending async command messages to the executor service.
 */
public interface ExecutorServiceCommandMessageProxy {

    /**
     * Requests an execution to the executor service.
     *
     * @param playgroundServiceExecutionRequest The {@link PlaygroundServiceExecutionRequest}
     *                                          to be sent to the executor service.
     */
    void requestExecution(final PlaygroundServiceExecutionRequest playgroundServiceExecutionRequest);
}
