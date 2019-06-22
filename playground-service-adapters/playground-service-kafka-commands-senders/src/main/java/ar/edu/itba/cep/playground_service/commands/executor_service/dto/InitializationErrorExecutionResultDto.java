package ar.edu.itba.cep.playground_service.commands.executor_service.dto;

import ar.edu.itba.cep.playground_service.models.InitializationErrorExecutionResult;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * An {@link ExecutionRequestDto} for {@link InitializationErrorExecutionResult}s.
 */
public class InitializationErrorExecutionResultDto implements ExecutionResultDto {

    /**
     * Constructor.
     */
    @JsonCreator
    public InitializationErrorExecutionResultDto() {
    }
}
