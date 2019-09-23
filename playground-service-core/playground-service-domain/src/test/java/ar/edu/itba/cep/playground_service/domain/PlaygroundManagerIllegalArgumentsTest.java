package ar.edu.itba.cep.playground_service.domain;

import ar.edu.itba.cep.executor.api.ExecutionRequestSender;
import ar.edu.itba.cep.executor.models.ExecutionResponse;
import ar.edu.itba.cep.playground_service.commands.ExecutionRequestId;
import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionRequest;
import ar.edu.itba.cep.playground_service.repositories.ExecutionRequestRepository;
import ar.edu.itba.cep.playground_service.repositories.ExecutionResponseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Test class for {@link PlaygroundManager}, containing tests for the illegal arguments situations
 * (i.e how the manager behaves when operating with invalid values).
 */
@ExtendWith(MockitoExtension.class)
class PlaygroundManagerIllegalArgumentsTest extends AbstractPlaygroundManagerTest {

    /**
     * Constructor.
     *
     * @param executionRequestRepository  A mocked {@link ExecutionRequestRepository} passed to super class.
     * @param executionResponseRepository A mocked {@link ExecutionResponseRepository} passed to super class.
     * @param executorServiceProxy        A mocked {@link ExecutionRequestSender} passed to super class.
     */
    PlaygroundManagerIllegalArgumentsTest(
            @Mock(name = "requestRepository") final ExecutionRequestRepository executionRequestRepository,
            @Mock(name = "responseRepository") final ExecutionResponseRepository executionResponseRepository,
            @Mock(name = "executorServiceProxy") final ExecutionRequestSender<ExecutionRequestId> executorServiceProxy) {
        super(executionRequestRepository,
                executionResponseRepository,
                executorServiceProxy);
    }


    /**
     * Tests that a {@link PlaygroundServiceExecutionRequest} is not created (i.e is not saved)
     * when arguments are not valid.
     */
    @Test
    void testExecutionRequestIsNotCreatedUsingInvalidArguments() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> playgroundManager.requestExecution(
                        TestHelper.invalidCode(),
                        TestHelper.invalidInputOutputList(),
                        TestHelper.invalidInputOutputList(),
                        TestHelper.validCompilerFlags(),
                        TestHelper.nonPositiveTimeout(),
                        TestHelper.invalidLanguage()

                ),
                "Using invalid arguments when creating an Execution Request" +
                        " did not throw an IllegalArgumentException."
        );
        verifyZeroInteractions(executionRequestRepository);
        verifyZeroInteractions(executionResponseRepository);
        verifyZeroInteractions(executorServiceProxy);
    }

    /**
     * Tests that an {@link IllegalArgumentException} is thrown when invoking the
     * {@link PlaygroundManager#processExecutionResponse(ExecutionResponse, ExecutionRequestId)} with invalid arguments.
     */
    @Test
    void testProcessResponseWithNullResponse() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> playgroundManager.processExecutionResponse(
                        null,
                        ExecutionRequestId.create(TestHelper.validExecutionRequestId())
                ),
                "Using invalid arguments when invoking the process response method" +
                        " did not throw an IllegalArgumentException."
        );
        verifyZeroInteractions(executionRequestRepository);
        verifyZeroInteractions(executionResponseRepository);
        verifyZeroInteractions(executorServiceProxy);
    }
}
