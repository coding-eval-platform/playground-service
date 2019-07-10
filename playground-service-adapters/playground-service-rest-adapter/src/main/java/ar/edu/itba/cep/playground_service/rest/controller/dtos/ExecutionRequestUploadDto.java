package ar.edu.itba.cep.playground_service.rest.controller.dtos;

import ar.edu.itba.cep.playground_service.models.Language;
import ar.edu.itba.cep.playground_service.rest.controller.validation.NotNullsInIterable;
import com.bellotapps.webapps_commons.errors.ConstraintViolationError.ErrorCausePayload.IllegalValue;
import com.bellotapps.webapps_commons.errors.ConstraintViolationError.ErrorCausePayload.MissingValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * Represents an execution request.
 */
@Getter
public class ExecutionRequestUploadDto {

    /**
     * The code to be run.
     */
    @NotNull(message = "The code is missing.", payload = MissingValue.class)
    private final String code;
    /**
     * The input arguments to be passed to the execution.
     */
    @NotNull(message = "The inputs list must not be null", payload = MissingValue.class)
    @NotNullsInIterable(message = "The inputs list contains nulls", payload = IllegalValue.class)
    private final List<String> inputs;
    /**
     * The time given to execute, in milliseconds.
     */
    @Positive(message = "The timeout must be positive", payload = IllegalValue.class)
    private final Long timeout;
    /**
     * The programming language in which the {@link #code} is written.
     */
    @NotNull(message = "The language is missing.", payload = MissingValue.class)
    private final Language language;


    /**
     * Constructor.
     *
     * @param code     The code to be run.
     * @param inputs   The input arguments to be passed to the execution.
     * @param timeout  The time given to execute, in milliseconds.
     * @param language The programming language in which the {@code code} is written.
     * @throws IllegalArgumentException If any argument is not valid.
     */
    @JsonCreator
    public ExecutionRequestUploadDto(
            @JsonProperty(value = "code", access = JsonProperty.Access.WRITE_ONLY) final String code,
            @JsonProperty(value = "inputs", access = JsonProperty.Access.WRITE_ONLY) final List<String> inputs,
            @JsonProperty(value = "timeout", access = JsonProperty.Access.WRITE_ONLY) final Long timeout,
            @JsonProperty(value = "language", access = JsonProperty.Access.WRITE_ONLY) final Language language)
            throws IllegalArgumentException {
        this.code = code;
        this.inputs = inputs;
        this.timeout = timeout;
        this.language = language;
    }
}
