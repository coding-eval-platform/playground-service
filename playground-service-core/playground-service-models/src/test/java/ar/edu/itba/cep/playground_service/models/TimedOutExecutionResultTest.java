package ar.edu.itba.cep.playground_service.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test class for {@link TimedOutExecutionResult}
 */
@ExtendWith(MockitoExtension.class)
class TimedOutExecutionResultTest {

    // ================================================================================================================
    // Acceptable arguments
    // ================================================================================================================

    /**
     * Tests that the constructor does not fail when invoking it with valid arguments.
     *
     * @param mockedExecutionRequest A mocked {@link ExecutionRequest} to be passed to the
     *                               {@link TimedOutExecutionResult#TimedOutExecutionResult(ExecutionRequest)}
     *                               constructor.
     */
    @Test
    void testConstructorWithValidArguments(@Mock final ExecutionRequest mockedExecutionRequest) {
        Assertions.assertDoesNotThrow(
                () -> new TimedOutExecutionResult(mockedExecutionRequest),
                "A timed-out execution result is not being created with acceptable arguments."
        );
        Mockito.verifyZeroInteractions(mockedExecutionRequest);
    }


    // ================================================================================================================
    // Constraint testing
    // ================================================================================================================

    /**
     * Tests that creating a {@link TimedOutExecutionResult} with a {@code null} {@link ExecutionRequest}
     * throws an {@link IllegalArgumentException}.
     */
    @Test
    void testNullExecutionRequest() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new TimedOutExecutionResult(null),
                "Creating a timed-out execution result with a null execution request is being allowed."
        );
    }
}
