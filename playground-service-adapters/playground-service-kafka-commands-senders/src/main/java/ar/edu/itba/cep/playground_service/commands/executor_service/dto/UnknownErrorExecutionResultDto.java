package ar.edu.itba.cep.playground_service.commands.executor_service.dto;

import ar.edu.itba.cep.playground_service.models.UnknownErrorExecutionResult;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * An {@link ExecutionRequestDto} for {@link UnknownErrorExecutionResult}s.
 */
public class UnknownErrorExecutionResultDto implements ExecutionResultDto {

    /**
     * Constructor.
     */
    @JsonCreator
    public UnknownErrorExecutionResultDto() {
    }
}
