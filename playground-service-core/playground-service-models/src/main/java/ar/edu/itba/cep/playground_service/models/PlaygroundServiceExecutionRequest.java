package ar.edu.itba.cep.playground_service.models;

import ar.edu.itba.cep.executor.models.ExecutionRequest;
import lombok.*;
import org.springframework.util.Assert;

/**
 * Represents an execution request (wrapping an {@link ExecutionRequest}, and adding an id).
 */
@Getter
@ToString(doNotUseGetters = true, callSuper = true)
@EqualsAndHashCode(of = "id", doNotUseGetters = true, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PACKAGE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaygroundServiceExecutionRequest {

    /**
     * The request's id.
     */
    private final long id;
    /**
     * The {@link ExecutionRequest} being wrapped.
     */
    private final ExecutionRequest request;


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


    // ================================
    // Builders
    // ================================

    /**
     * Builds a {@link PlaygroundServiceExecutionRequest} from the given {@link ExecutionRequest}.
     *
     * @param executionRequest The {@link PlaygroundServiceExecutionRequest} being wrapped.
     * @return The created {@link PlaygroundServiceExecutionResponse}.
     */
    public static PlaygroundServiceExecutionRequest fromCommonsRequest(final ExecutionRequest executionRequest) {
        assertExecutionRequest(executionRequest);
        return new PlaygroundServiceExecutionRequest(0, executionRequest);
    }
}
