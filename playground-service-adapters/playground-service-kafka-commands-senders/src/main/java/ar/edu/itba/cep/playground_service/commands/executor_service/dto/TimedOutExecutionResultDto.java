package ar.edu.itba.cep.playground_service.commands.executor_service.dto;

import ar.edu.itba.cep.playground_service.models.TimedOutExecutionResult;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * An {@link ExecutionResultDto} for a {@link TimedOutExecutionResult}.
 */
public class TimedOutExecutionResultDto implements ExecutionResultDto {

    /**
     * Constructor.
     */
    @JsonCreator
    public TimedOutExecutionResultDto() throws IllegalArgumentException {
    }
}
