package ar.edu.itba.cep.playground_service.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.util.Assert;

/**
 * Represents an execution result.
 */
@EqualsAndHashCode(
        of = "id"
)
@Getter
public class ExecutionResult {

    /**
     * The execution result's id.
     */
    private final long id;
    /**
     * The {@link ExecutionRequest} to which this result belongs to.
     */
    private final ExecutionRequest executionRequest;


    /**
     * Default constructor.
     */
    public ExecutionResult() {
        // Initialize final fields with default values.
        this.id = 0;
        this.executionRequest = null;
    }

    /**
     * Constructor.
     *
     * @param executionRequest The {@link ExecutionRequest} to which this result belongs to.
     */
    public ExecutionResult(final ExecutionRequest executionRequest) {
        assertExecutionRequest(executionRequest);
        this.id = 0;
        this.executionRequest = executionRequest;
    }


    // ================================
    // Assertions
    // ================================

    /**
     * Asserts that the given {@code executionRequest} is valid.
     *
     * @param executionRequest The {@link ExecutionRequest} to be checked.
     * @throws IllegalArgumentException If the {@code executionRequest} is not valid.
     */
    private static void assertExecutionRequest(final ExecutionRequest executionRequest)
            throws IllegalArgumentException {
        Assert.notNull(executionRequest, "The execution request must not be null");
    }
}
