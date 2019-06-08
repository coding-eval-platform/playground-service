package ar.edu.itba.cep.playground_service.models;

import lombok.ToString;

/**
 * An {@link ExecutionResult} that corresponds to a timed-out execution.
 */
@ToString(
        callSuper = true
)
public final class TimedOutExecutionResult extends ExecutionResult {

    /**
     * Default constructor.
     */
    public TimedOutExecutionResult() {
    }

    /**
     * Constructor.
     *
     * @param executionRequest The {@link ExecutionRequest} to which this result belongs to.
     */
    public TimedOutExecutionResult(final ExecutionRequest executionRequest) {
        super(executionRequest);
    }
}
