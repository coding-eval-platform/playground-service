package ar.edu.itba.cep.playground_service.rest.controller.dtos;

import ar.edu.itba.cep.executor.models.Language;
import ar.edu.itba.cep.playground_service.rest.controller.validation.NotNullsInIterable;
import com.bellotapps.webapps_commons.errors.ConstraintViolationError.ErrorCausePayload.IllegalValue;
import com.bellotapps.webapps_commons.errors.ConstraintViolationError.ErrorCausePayload.MissingValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * Represents an execution request.
 */
@Getter
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true)
public class ExecutionRequestDto {

    /**
     * The code to be run.
     */
    @NotNull(message = "The code is missing.", payload = MissingValue.class)
    private final String code;
    /**
     * The program arguments to be passed to the execution.
     */
    @NotNullsInIterable(message = "The program arguments list contains nulls", payload = IllegalValue.class)
    private final List<String> programArguments;
    /**
     * The elements to be passed to the standard input.
     */
    @NotNullsInIterable(message = "The program arguments list contains nulls", payload = IllegalValue.class)
    private final List<String> stdin;
    /**
     * The compiler flags. Should be null if the {@link #language} is compiled.
     */
    // No validation for this field, as it can be any String.
    private final String compilerFlags;
    /**
     * The time given to execute, in milliseconds.
     */
    @Positive(message = "The timeout must be positive", payload = IllegalValue.class)
    private final Long timeout;
    /**
     * The name of the file in which the "main" will be placed (i.e the name of the file where the code will be copied).
     */
    private final String mainFileName;
    /**
     * The programming language in which the {@link #code} is written.
     */
    @NotNull(message = "The language is missing.", payload = MissingValue.class)
    private final Language language;


    /**
     * Constructor.
     *
     * @param code             The code to be run.
     * @param programArguments The input arguments to be passed to the execution.
     * @param stdin            The elements to be passed to the standard input.
     * @param compilerFlags    The compiler flags. Should be null if the {@link #language} is compiled.
     * @param timeout          The time given to execute, in milliseconds.
     * @param language         The programming language in which the {@code code} is written.
     * @throws IllegalArgumentException If any argument is not valid.
     */
    @JsonCreator
    public ExecutionRequestDto(
            @JsonProperty(value = "code", access = WRITE_ONLY) final String code,
            @JsonProperty(value = "programArguments", access = WRITE_ONLY) final List<String> programArguments,
            @JsonProperty(value = "stdin", access = WRITE_ONLY) final List<String> stdin,
            @JsonProperty(value = "compilerFlags", access = WRITE_ONLY) final String compilerFlags,
            @JsonProperty(value = "timeout", access = WRITE_ONLY) final Long timeout,
            @JsonProperty(value = "mainFileName", access = WRITE_ONLY) final String mainFileName,
            @JsonProperty(value = "language", access = WRITE_ONLY) final Language language)
            throws IllegalArgumentException {
        this.code = code;
        this.programArguments = programArguments;
        this.stdin = stdin;
        this.compilerFlags = compilerFlags;
        this.timeout = timeout;
        this.mainFileName = mainFileName;
        this.language = language;
    }
}
