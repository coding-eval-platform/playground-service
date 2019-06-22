package ar.edu.itba.cep.playground_service.models;

import lombok.ToString;

/**
 * Represents an {@link ExecutionResult} that has timed-out.
 */
@ToString(doNotUseGetters = true, callSuper = true)
public class InitializationErrorExecutionResult extends ExecutionResult {


    /**
     * Constructor.
     *
     * @param executionRequest The {@link ExecutionRequest} to which this execution result belongs to.
     * @throws IllegalArgumentException If any argument is not valid.
     */
    public InitializationErrorExecutionResult(final ExecutionRequest executionRequest) throws IllegalArgumentException {
        super(executionRequest);
    }
}
