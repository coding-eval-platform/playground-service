package ar.edu.itba.cep.playground_service.models;

import ar.edu.itba.cep.executor.models.ExecutionResponse;
import lombok.*;
import org.springframework.util.Assert;

/**
 * Represents an execution response (wrapping an {@link ExecutionResponse},
 * and adding an id and the {@link PlaygroundServiceExecutionRequest} to which the response belongs to).
 */
@Getter
@ToString(doNotUseGetters = true, callSuper = true)
@EqualsAndHashCode(of = "id", doNotUseGetters = true, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PACKAGE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaygroundServiceExecutionResponse {

    /**
     * The execution response's id.
     */
    private final long id;
    /**
     * The {@link PlaygroundServiceExecutionRequest} to which this response belongs to.
     */
    private final PlaygroundServiceExecutionRequest playgroundServiceExecutionRequest;
    /**
     * The {@link ExecutionResponse} being wrapped.
     */
    private final ExecutionResponse response;


    // ================================
    // Assertions
    // ================================

    /**
     * Asserts that the given {@code executionRequest} is valid.
     *
     * @param executionRequest The {@link PlaygroundServiceExecutionRequest} to be checked.
     * @throws IllegalArgumentException If the {@code executionRequest} is not valid.
     */
    private static void assertExecutionRequest(final PlaygroundServiceExecutionRequest executionRequest)
            throws IllegalArgumentException {
        Assert.notNull(executionRequest, "The execution request must not be null");
    }

    /**
     * Asserts that the given {@code executionResponse} is valid.
     *
     * @param executionResponse The {@link ExecutionResponse} to be checked.
     * @throws IllegalArgumentException If the {@code executionResponse} is not valid.
     */
    private static void assertExecutionResponse(final ExecutionResponse executionResponse)
            throws IllegalArgumentException {
        Assert.notNull(executionResponse, "The execution response must not be null");
    }


    // ================================
    // Builders
    // ================================

    /**
     * Builds a {@link PlaygroundServiceExecutionResponse}
     * from the given {@link ExecutionResponse} and {@link PlaygroundServiceExecutionRequest}.
     *
     * @param executionResponse The {@link ExecutionResponse} from where data is taken.
     * @param executionRequest  The {@link PlaygroundServiceExecutionRequest} to which this response belongs to.
     * @return The created {@link PlaygroundServiceExecutionResponse}.
     */
    public static PlaygroundServiceExecutionResponse fromCommonsResponse(
            final PlaygroundServiceExecutionRequest executionRequest,
            final ExecutionResponse executionResponse) {
        assertExecutionRequest(executionRequest);
        assertExecutionResponse(executionResponse);
        return new PlaygroundServiceExecutionResponse(0, executionRequest, executionResponse);
    }
}
