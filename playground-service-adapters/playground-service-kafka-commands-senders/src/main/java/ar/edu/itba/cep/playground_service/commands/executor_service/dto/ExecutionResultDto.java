package ar.edu.itba.cep.playground_service.commands.executor_service.dto;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Interface that marks an object as an execution result data transfer object.
 * Includes subtyping metadata that indicates how to deserialize JSONs into objects that implement this interface.
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
        )
})
public interface ExecutionResultDto {

    /**
     * Value that marks a JSON to be deserialized into a {@link FinishedExecutionResultDto}.
     */
    /* package */ String FINISHED_STRING_VALUE = "FINISHED";
    /**
     * Value that marks a JSON to be deserialized into a {@link TimedOutExecutionResultDto}.
     */
    /* package */ String TIMED_OUT_STRING_VALUE = "TIMED_OUT";
}
