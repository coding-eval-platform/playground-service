package ar.edu.itba.cep.playground_service.models;

import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * An {@link ExecutionResult} that corresponds to a finished execution (i.e has exit code, stdout and stderr).
 */
@ToString(
        callSuper = true,
        doNotUseGetters = true
)
@Getter
public final class FinishedExecutionResult extends ExecutionResult {

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
     * Default constructor.
     */
    public FinishedExecutionResult() {
        this.exitCode = 0;
        this.stdout = new LinkedList<>();
        this.stderr = new LinkedList<>();
    }

    /**
     * Constructor.
     *
     * @param exitCode         The execution's exit code.
     * @param stdout           A {@link List} of {@link String}s
     *                         that were sent to standard output by the program being executed.
     *                         Each {@link String} in the {@link List} is a line that was printed in standard output.
     * @param stderr           A {@link List} of {@link String}s
     *                         that were sent to standard error output by the program being executed.
     *                         Each {@link String} in the {@link List}
     *                         is a line that was printed in standard error output.
     * @param executionRequest The {@link ExecutionRequest} to which this result belongs to.
     * @throws IllegalArgumentException If any argument is not valid.
     */
    public FinishedExecutionResult(
            final int exitCode,
            final List<String> stdout,
            final List<String> stderr,
            final ExecutionRequest executionRequest) throws IllegalArgumentException {
        super(executionRequest);
        assertStdOutList(stdout);
        assertStdErrList(stderr);
        this.exitCode = exitCode;
        this.stdout = stdout;
        this.stderr = stderr;
    }


    // ================================
    // Assertions
    // ================================

    /**
     * Asserts that the given {@code stdout} {@link List} is valid.
     *
     * @param stdout The {@link List} with standard output to be validated.
     * @throws IllegalArgumentException If the {@link List} is not valid.
     */
    private static void assertStdOutList(final List<String> stdout) throws IllegalArgumentException {
        Assert.notNull(stdout, "The stdout list must not be null");
        Assert.isTrue(stdout.stream().noneMatch(Objects::isNull), "The stdout list must not contain nulls.");
    }

    /**
     * Asserts that the given {@code stderr} {@link List} is valid.
     *
     * @param stderr The {@link List} with standard error output to be validated.
     * @throws IllegalArgumentException If the {@link List} is not valid.
     */
    private static void assertStdErrList(final List<String> stderr) throws IllegalArgumentException {
        Assert.notNull(stderr, "The stderr list must not be null");
        Assert.isTrue(stderr.stream().noneMatch(Objects::isNull), "The stderr list must not contain nulls.");
    }
}
