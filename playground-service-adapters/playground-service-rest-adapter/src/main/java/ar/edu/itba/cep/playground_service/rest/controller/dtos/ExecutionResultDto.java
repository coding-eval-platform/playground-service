package ar.edu.itba.cep.playground_service.rest.controller.dtos;

import ar.edu.itba.cep.playground_service.models.ExecutionResult;
import ar.edu.itba.cep.playground_service.models.FinishedExecutionResult;
import ar.edu.itba.cep.playground_service.models.TimedOutExecutionResult;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Represents an execution result.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(
                value = FinishedExecutionResultDto.class,
                name = ExecutionResultDto.FINISHED_STRING_VALUE
        ),
        @JsonSubTypes.Type(
                value = TimedOutExecutionResultDto.class,
                name = ExecutionResultDto.TIMED_OUT_STRING_VALUE
        ),
        @JsonSubTypes.Type(
                value = PendingExecutionResultDto.class,
                name = ExecutionResultDto.PENDING_STRING_VALUE
        )
})
public interface ExecutionResultDto {

    /**
     * Value that marks a JSON to be deserialized into a {@link FinishedExecutionResultDto}.
     */
    /* package */ String PENDING_STRING_VALUE = "PENDING";
    /**
     * Value that marks a JSON to be deserialized into a {@link FinishedExecutionResultDto}.
     */
    /* package */ String FINISHED_STRING_VALUE = "FINISHED";
    /**
     * Value that marks a JSON to be deserialized into a {@link TimedOutExecutionResultDto}.
     */
    /* package */ String TIMED_OUT_STRING_VALUE = "TIMED_OUT";


    /**
     * Factory method to create a concrete instance of {@link ExecutionResultDto} from a given {@link ExecutionResult}.
     *
     * @param executionResult The {@link ExecutionResult} from which the {@link ExecutionResultDto} will be built.
     * @return The created {@link ExecutionResultDto}.
     */
    static ExecutionResultDto createFor(final ExecutionResult executionResult) {
        if (executionResult instanceof TimedOutExecutionResult) {
            return new TimedOutExecutionResultDto();
        }
        if (executionResult instanceof FinishedExecutionResult) {
            return new FinishedExecutionResultDto((FinishedExecutionResult) executionResult);
        }
        throw new IllegalArgumentException("Unknown subtype");
    }
}
