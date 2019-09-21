package ar.edu.itba.cep.playground_service.models;

import ar.edu.itba.cep.executor.models.ExecutionRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Test class for {@link PlaygroundServiceExecutionResponse} (i.e tests passing {@code null} to its constructor).
 */
@ExtendWith(MockitoExtension.class)
class PlaygroundServiceExecutionRequestTest {

    // ================================================================================================================
    // Acceptable Arguments
    // ================================================================================================================

    /**
     * Tests that creating a {@link PlaygroundServiceExecutionRequest} with valid arguments works as expected.
     *
     * @param request The {@link ExecutionRequest} wrapped in the created {@link PlaygroundServiceExecutionRequest}.
     */
    @Test
    void testAcceptableArguments(@Mock(name = "request") final ExecutionRequest request) {
        final var playgroundServiceExecutionResponse = PlaygroundServiceExecutionRequest.fromCommonsRequest(request);

        Assertions.assertAll(
                "Creating a playground service execution response does not work as expected.",
                () -> Assertions.assertEquals(
                        request,
                        playgroundServiceExecutionResponse.getRequest(),
                        "There is a mismatch in the request"
                )
        );

        verifyZeroInteractions(request);
    }


    // ================================================================================================================
    // Constraint testing
    // ================================================================================================================

    /**
     * Tests that creating a {@link PlaygroundServiceExecutionResponse} with a {@code null}
     * {@link PlaygroundServiceExecutionRequest}
     * throws an {@link IllegalArgumentException}.
     */
    @Test
    void testNullExecutionRequest() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> PlaygroundServiceExecutionRequest.fromCommonsRequest(null),
                "Creating a playground service execution request with a null execution request" +
                        " is being allowed."
        );
    }
}
