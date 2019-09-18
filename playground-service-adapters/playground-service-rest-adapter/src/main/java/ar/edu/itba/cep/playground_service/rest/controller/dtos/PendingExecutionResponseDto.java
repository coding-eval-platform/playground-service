package ar.edu.itba.cep.playground_service.rest.controller.dtos;

import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionRequest;
import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * A Data Transfer Object that indicates that a {@link PlaygroundServiceExecutionResponse}
 * has not being created yet for a {@link PlaygroundServiceExecutionRequest}.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PendingExecutionResponseDto implements ExecutionResponseDto {

    /**
     * The unique instance of this type.
     */
    private static final PendingExecutionResponseDto instance = new PendingExecutionResponseDto();

    /**
     * The pending state value.
     */
    private static final String PENDING = "PENDING";


    /**
     * Property used to indicate that the request's response is still pending.
     *
     * @return The state.
     */
    @JsonProperty(value = "state", access = JsonProperty.Access.READ_ONLY)
    public String getState() {
        return PENDING;
    }


    /**
     * Singleton method.
     *
     * @return The instance of this type.
     */
    public static PendingExecutionResponseDto getInstance() {
        return instance;
    }
}
