package ar.edu.itba.cep.playground_service.commands;

import ar.edu.itba.cep.playground_service.models.ExecutionRequest;
import com.bellotapps.webapps_commons.exceptions.NoSuchEntityException;

import java.util.List;

/**
 * A port into the application that allows receiving execution results, in order to processed.
 * This will act as a callback when receiving the results of an execution that was requested
 * by the {@link ExecutorServiceCommandMessageProxy#requestExecution(ExecutionRequest)} method.
 */
public interface ExecutionResultProcessor {

    /**
     * Indicates the implementor of this interface that when executing
     * the {@link ExecutionRequest} with the given {@code executionRequestId}, the process had timed out.
     *
     * @param executionRequestId The id of the {@link ExecutionRequest} that timed out when executing.
     * @throws NoSuchEntityException If there is no {@link ExecutionRequest} with the given {@code executionRequestId}.
     */
    void receiveTimedOut(final long executionRequestId) throws NoSuchEntityException;

    /**
     * Indicates the implementor of this interface that when executing
     * the {@link ExecutionRequest} with the given {@code executionRequestId},
     * the process has finished, and the results are the given parameters.
     *
     * @param exitCode           The exit code of the process.
     * @param stdout             The standard output of the execution.
     * @param stderr             The standard error output of the execution.
     * @param executionRequestId The id of the {@link ExecutionRequest} that finished its execution.
     * @throws NoSuchEntityException If there is no {@link ExecutionRequest} with the given {@code executionRequestId}.
     */
    void receiveFinished(
            final int exitCode,
            final List<String> stdout,
            final List<String> stderr,
            final long executionRequestId
    ) throws NoSuchEntityException;
}
