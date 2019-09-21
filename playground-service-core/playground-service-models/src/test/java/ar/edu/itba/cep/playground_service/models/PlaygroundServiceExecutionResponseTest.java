package ar.edu.itba.cep.playground_service.models;

import ar.edu.itba.cep.executor.models.ExecutionResponse;
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
class PlaygroundServiceExecutionResponseTest {

    // ================================================================================================================
    // Acceptable Arguments
    // ================================================================================================================

    /**
     * Tests that creating a {@link PlaygroundServiceExecutionResponse} with valid arguments works as expected.
     *
     * @param request  The {@link PlaygroundServiceExecutionRequest}
     *                 to which the {@link PlaygroundServiceExecutionResponse} belongs.
     * @param response The {@link ExecutionResponse} wrapped in the created {@link PlaygroundServiceExecutionResponse}.
     */
    @Test
    void testAcceptableArguments(
            @Mock(name = "request") final PlaygroundServiceExecutionRequest request,
            @Mock(name = "response") final ExecutionResponse response) {
        final var playgroundServiceExecutionResponse = PlaygroundServiceExecutionResponse.fromCommonsResponse(
                request,
                response
        );

        Assertions.assertAll(
                "Creating a playground service execution response does not work as expected.",
                () -> Assertions.assertEquals(
                        request,
                        playgroundServiceExecutionResponse.getPlaygroundServiceExecutionRequest(),
                        "There is a mismatch in the request"
                ),
                () -> Assertions.assertEquals(
                        response,
                        playgroundServiceExecutionResponse.getResponse(),
                        "There is a mismatch in the response"
                )
        );

        verifyZeroInteractions(request);
        verifyZeroInteractions(response);
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
    void testNullExecutionRequest(@Mock(name = "response") final ExecutionResponse response) {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> PlaygroundServiceExecutionResponse.fromCommonsResponse(
                        null,
                        response

                ),
                "Creating a playground service execution response with a null execution request" +
                        " is being allowed."
        );
        verifyZeroInteractions(response);
    }

    /**
     * Tests that creating a {@link PlaygroundServiceExecutionResponse} with a {@code null} {@link ExecutionResponse}
     * throws an {@link IllegalArgumentException}.
     */
    @Test
    void testNullExecutionResponse(@Mock(name = "request") final PlaygroundServiceExecutionRequest request) {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> PlaygroundServiceExecutionResponse.fromCommonsResponse(
                        request,
                        null

                ),
                "Creating a playground service execution response with a null execution response" +
                        " is being allowed."
        );
        verifyZeroInteractions(request);
    }
}
