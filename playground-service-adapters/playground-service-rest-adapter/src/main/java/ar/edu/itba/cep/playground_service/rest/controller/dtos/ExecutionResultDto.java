package ar.edu.itba.cep.playground_service.rest.controller.dtos;

import ar.edu.itba.cep.playground_service.models.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Represents an execution result.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(
                value = PendingExecutionResultDto.class,
                name = ExecutionResultDto.PENDING_STRING_VALUE
        ),
        @JsonSubTypes.Type(
                value = FinishedExecutionResultDto.class,
                name = ExecutionResultDto.FINISHED_STRING_VALUE
        ),
        @JsonSubTypes.Type(
                value = TimedOutExecutionResultDto.class,
                name = ExecutionResultDto.TIMED_OUT_STRING_VALUE
        ),
        @JsonSubTypes.Type(
                value = CompileErrorExecutionResultDto.class,
                name = ExecutionResultDto.COMPILE_ERROR_STRING_VALUE
        ),
        @JsonSubTypes.Type(
                value = InitializationErrorExecutionResultDto.class,
                name = ExecutionResultDto.INITIALIZATION_ERROR_STRING_VALUE
        ),
        @JsonSubTypes.Type(
                value = UnknownErrorExecutionResultDto.class,
                name = ExecutionResultDto.UNKNOWN_ERROR_STRING_VALUE
        ),
})
public interface ExecutionResultDto {

    /**
     * Value that marks a JSON to be deserialized into a {@link PendingExecutionResultDto}.
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
     * Value that marks a JSON to be deserialized into a {@link CompileErrorExecutionResultDto}.
     */
    /* package */ String COMPILE_ERROR_STRING_VALUE = "COMPILE_ERROR";
    /**
     * Value that marks a JSON to be deserialized into a {@link InitializationErrorExecutionResultDto}.
     */
    /* package */ String INITIALIZATION_ERROR_STRING_VALUE = "INITIALIZATION_ERROR";
    /**
     * Value that marks a JSON to be deserialized into a {@link UnknownErrorExecutionResultDto}.
     */
    /* package */ String UNKNOWN_ERROR_STRING_VALUE = "UNKNOWN_ERROR";


    /**
     * Factory method to create a concrete instance of {@link ExecutionResultDto} from a given {@link ExecutionResult}.
     *
     * @param executionResult The {@link ExecutionResult} from which the {@link ExecutionResultDto} will be built.
     * @return The created {@link ExecutionResultDto}.
     */
    static ExecutionResultDto createFor(final ExecutionResult executionResult) {
        if (executionResult instanceof FinishedExecutionResult) {
            return new FinishedExecutionResultDto((FinishedExecutionResult) executionResult);
        }
        if (executionResult instanceof TimedOutExecutionResult) {
            return new TimedOutExecutionResultDto();
        }
        if (executionResult instanceof CompileErrorExecutionResult) {
            return new CompileErrorExecutionResultDto((CompileErrorExecutionResult) executionResult);
        }
        if (executionResult instanceof InitializationErrorExecutionResult) {
            return new InitializationErrorExecutionResultDto();
        }
        if (executionResult instanceof UnknownErrorExecutionResult) {
            return new UnknownErrorExecutionResultDto();
        }
        throw new IllegalArgumentException("Unknown subtype");
    }
}
