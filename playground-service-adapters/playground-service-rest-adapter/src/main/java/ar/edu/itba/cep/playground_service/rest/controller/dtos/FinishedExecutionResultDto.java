package ar.edu.itba.cep.playground_service.rest.controller.dtos;

import ar.edu.itba.cep.playground_service.models.FinishedExecutionResult;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * A Data Transfer Object that wraps a {@link FinishedExecutionResult}.
 */
public final class FinishedExecutionResultDto implements ExecutionResultDto {

    /**
     * The wrapped {@link FinishedExecutionResult}.
     */
    private final FinishedExecutionResult finishedExecutionResult;


    /**
     * Constructor.
     *
     * @param finishedExecutionResult The wrapped {@link FinishedExecutionResult}.
     * @throws IllegalArgumentException If any argument is not valid.
     */
    public FinishedExecutionResultDto(final FinishedExecutionResult finishedExecutionResult) {
        this.finishedExecutionResult = finishedExecutionResult;
    }


    @JsonProperty(value = "exitCode", access = JsonProperty.Access.READ_ONLY)
    public int getExitCode() {
        return finishedExecutionResult.getExitCode();
    }

    @JsonProperty(value = "stdout", access = JsonProperty.Access.READ_ONLY)
    public List<String> getStdout() {
        return finishedExecutionResult.getStdout();
    }

    @JsonProperty(value = "stderr", access = JsonProperty.Access.READ_ONLY)
    public List<String> getStderr() {
        return finishedExecutionResult.getStderr();
    }
}
