package ar.edu.itba.cep.playground_service.rest.controller.dtos;

import ar.edu.itba.cep.playground_service.models.CompileErrorExecutionResult;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A Data Transfer Object that wraps a {@link CompileErrorExecutionResult}.
 */
public final class CompileErrorExecutionResultDto implements ExecutionResultDto {

    private final CompileErrorExecutionResult compileErrorExecutionResult;

    /**
     * Constructor.
     *
     * @param compileErrorExecutionResult The {@link CompileErrorExecutionResult} being wrapped by this DTO.
     * @throws IllegalArgumentException If the given {@code compileErrorExecutionResult} is {@code null}.
     */
    public CompileErrorExecutionResultDto(final CompileErrorExecutionResult compileErrorExecutionResult)
            throws IllegalArgumentException {
        this.compileErrorExecutionResult = compileErrorExecutionResult;
    }


    /**
     * @return A {@link List} of {@link String}s that were reported by the compiler on failure.
     */
    @JsonProperty(value = "compilerErrors", access = JsonProperty.Access.READ_ONLY)
    public List<String> getCompilerErrors() {
        return compileErrorExecutionResult.getCompilerErrors();
    }
}
