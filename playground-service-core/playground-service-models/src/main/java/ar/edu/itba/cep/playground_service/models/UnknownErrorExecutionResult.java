package ar.edu.itba.cep.playground_service.models;

import lombok.ToString;

/**
 * Represents an {@link ExecutionResult} for an execution that failed unexpectedly.
 */
@ToString(doNotUseGetters = true, callSuper = true)
public class UnknownErrorExecutionResult extends ExecutionResult {

    /**
     * Default constructor.
     */
    public UnknownErrorExecutionResult() {
    }

    /**
     * Constructor.
     *
     * @param executionRequest The {@link ExecutionRequest} to which this execution result belongs to.
     * @throws IllegalArgumentException If any argument is not valid.
     */
    public UnknownErrorExecutionResult(final ExecutionRequest executionRequest) throws IllegalArgumentException {
        super(executionRequest);
    }
}
