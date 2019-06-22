package ar.edu.itba.cep.playground_service.commands.executor_service.dto;

import ar.edu.itba.cep.playground_service.models.CompileErrorExecutionResult;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

/**
 * An {@link ExecutionRequestDto} for {@link CompileErrorExecutionResult}s.
 */
@Getter
public class CompileErrorExecutionResultDto implements ExecutionResultDto {

    /**
     * A {@link List} of {@link String}s that were reported by the compiler on failure.
     */
    private final List<String> compilerErrors;

    /**
     * Constructor.
     *
     * @param compilerErrors A {@link List} of {@link String}s that were reported by the compiler on failure.
     * @throws IllegalArgumentException If the given {@code compileErrorExecutionResult} is {@code null}.
     */
    @JsonCreator
    public CompileErrorExecutionResultDto(
            @JsonProperty(value = "stdout", access = JsonProperty.Access.WRITE_ONLY) final List<String> compilerErrors)
            throws IllegalArgumentException {
        this.compilerErrors = compilerErrors;
    }
}
