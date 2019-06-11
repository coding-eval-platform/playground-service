package ar.edu.itba.cep.playground_service.commands.executor_service.dto;


import ar.edu.itba.cep.playground_service.models.FinishedExecutionResult;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

/**
 * An {@link ExecutionResultDto} for a {@link FinishedExecutionResult}.
 */
@Getter
public class FinishedExecutionResultDto implements ExecutionResultDto {

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
     * Constructor.
     *
     * @param exitCode The execution's exit code.
     * @param stdout   A {@link List} of {@link String}s
     *                 that were sent to standard output by the program being executed.
     *                 Each {@link String} in the {@link List} is a line that was printed in standard output.
     * @param stderr   A {@link List} of {@link String}s
     *                 that were sent to standard error output by the program being executed.
     *                 Each {@link String} in the {@link List}
     *                 is a line that was printed in standard error output.
     * @throws IllegalArgumentException If any argument is not valid.
     */
    @JsonCreator
    public FinishedExecutionResultDto(
            @JsonProperty(value = "exitCode", access = JsonProperty.Access.WRITE_ONLY) final int exitCode,
            @JsonProperty(value = "stdout", access = JsonProperty.Access.WRITE_ONLY) final List<String> stdout,
            @JsonProperty(value = "stderr", access = JsonProperty.Access.WRITE_ONLY) final List<String> stderr)
            throws IllegalArgumentException {
        this.exitCode = exitCode;
        this.stdout = stdout;
        this.stderr = stderr;
    }
}
