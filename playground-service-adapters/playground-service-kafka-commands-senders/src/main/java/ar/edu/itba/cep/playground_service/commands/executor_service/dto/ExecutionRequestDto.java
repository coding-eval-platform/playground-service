package ar.edu.itba.cep.playground_service.commands.executor_service.dto;

import ar.edu.itba.cep.playground_service.models.ExecutionRequest;
import ar.edu.itba.cep.playground_service.models.Language;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Data transfer object for sending execution requests.
 */
public class ExecutionRequestDto {

    /**
     * The {@link ExecutionRequest} being wrapped.
     */
    private final ExecutionRequest executionRequest;


    /**
     * Constructor.
     *
     * @param executionRequest The {@link ExecutionRequest} being wrapped.
     * @throws IllegalArgumentException If any argument is not valid.
     */
    public ExecutionRequestDto(final ExecutionRequest executionRequest) {
        this.executionRequest = executionRequest;
    }


    /**
     * @return The code to be run.
     */
    @JsonProperty(value = "code", access = JsonProperty.Access.READ_ONLY)
    public String getCode() {
        return executionRequest.getCode();
    }

    /**
     * @return The input arguments to be passed to the execution.
     */
    @JsonProperty(value = "inputs", access = JsonProperty.Access.READ_ONLY)
    public List<String> getInputs() {
        return executionRequest.getInputs();
    }

    /**
     * @return The time given to execute, in milliseconds.
     */
    @JsonProperty(value = "timeout", access = JsonProperty.Access.READ_ONLY)
    public Long getTimeout() {
        return executionRequest.getTimeout();
    }

    /**
     * @return The programming language in which the {@link #getCode()} is written.
     */
    @JsonProperty(value = "language", access = JsonProperty.Access.READ_ONLY)
    public Language getLanguage() {
        return executionRequest.getLanguage();
    }
}
