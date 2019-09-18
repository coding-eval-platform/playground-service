package ar.edu.itba.cep.playground_service.rest.controller.dtos;

import ar.edu.itba.cep.executor.models.ExecutionResponse;
import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

/**
 * Data transfer object for receiving {@link ExecutionResponse}s.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true)
public class CompletedExecutionResponseDto implements ExecutionResponseDto {

    /**
     * The result of the execution (e.g finished, timed-out, or with compilation errors).
     */
    private final ExecutionResponse.ExecutionResult result;
    /**
     * The execution's exit code.
     */
    private final int exitCode;
    /**
     * A {@link List} of {@link String}s that were sent to standard output by the program being executed.
     * Each {@link String} in the {@link List} is a line that was printed in standard output.
     */
    private final List<String> stdout;
    /**
     * A {@link List} of {@link String}s that were sent to standard error output by the program being executed.
     * Each {@link String} in the {@link List} is a line that was printed in standard error output.
     */
    private final List<String> stderr;


    /**
     * @return The execution's result.
     */
    @JsonProperty(value = "result", access = READ_ONLY)
    public ExecutionResponse.ExecutionResult getResult() {
        return result;
    }

    /**
     * @return The execution's exit code.
     */
    @JsonProperty(value = "exitCode", access = READ_ONLY)
    public int getExitCode() {
        return exitCode;
    }

    /**
     * @return A {@link List} of {@link String}s that were sent to standard output by the program being executed.
     * Each {@link String} in the {@link List} is a line that was printed in standard output.
     */
    @JsonProperty(value = "stdout", access = READ_ONLY)
    public List<String> getStdout() {
        return stdout;
    }

    /**
     * @return A {@link List} of {@link String}s that were sent to standard error output by the program being executed.
     * Each {@link String} in the {@link List} is a line that was printed in standard error output.
     */
    @JsonProperty(value = "stderr", access = READ_ONLY)
    public List<String> getStderr() {
        return stderr;
    }


    /**
     * Factory method to create an instance of {@link CompletedExecutionResponseDto}
     * from a given {@link PlaygroundServiceExecutionResponse}.
     *
     * @param playgroundServiceExecutionResponse The {@link PlaygroundServiceExecutionResponse}
     *                 from which the {@link CompletedExecutionResponseDto} will be built.
     * @return The created {@link CompletedExecutionResponseDto}.
     */
    public static CompletedExecutionResponseDto createFor(
            final PlaygroundServiceExecutionResponse playgroundServiceExecutionResponse) {
        final var response = playgroundServiceExecutionResponse.getResponse();
        return new CompletedExecutionResponseDto(
                response.getResult(),
                response.getExitCode(),
                response.getStdout(),
                response.getStderr()
        );
    }
}
