package ar.edu.itba.cep.playground_service.domain;

import ar.edu.itba.cep.playground_service.commands.ExecutorServiceCommandMessageProxy;
import ar.edu.itba.cep.playground_service.models.ExecutionRequest;
import ar.edu.itba.cep.playground_service.repositories.ExecutionRequestRepository;
import ar.edu.itba.cep.playground_service.repositories.ExecutionResultRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

/**
 * Test class for {@link PlaygroundManager}, containing tests for the illegal arguments situations
 * (i.e how the manager behaves when operating with invalid values).
 */
@ExtendWith(MockitoExtension.class)
class PlaygroundManagerIllegalArgumentsTest extends AbstractPlaygroundManagerTest {

    /**
     * Constructor.
     *
     * @param executionRequestRepository A mocked {@link ExecutionRequestRepository} passed to super class.
     * @param executionResultRepository  A mocked {@link ExecutionResultRepository} passed to super class.
     * @param executorServiceProxy       A mocked {@link ExecutorServiceCommandMessageProxy} passed to super class.
     */
    PlaygroundManagerIllegalArgumentsTest(
            @Mock(name = "requestRepository") final ExecutionRequestRepository executionRequestRepository,
            @Mock(name = "resultRepository") final ExecutionResultRepository executionResultRepository,
            @Mock(name = "executorServiceProxy") final ExecutorServiceCommandMessageProxy executorServiceProxy) {
        super(executionRequestRepository,
                executionResultRepository,
                executorServiceProxy);
    }


    /**
     * Tests that an {@link ExecutionRequest} is not created (i.e is not saved) when arguments are not valid.
     */
    @Test
    void testExecutionRequestIsNotCreatedUsingInvalidArguments() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> playgroundManager.requestExecution(
                        TestHelper.invalidCode(),
                        TestHelper.invalidInputOutputList(),
                        TestHelper.nonPositiveTimeout(),
                        TestHelper.invalidLanguage()

                ),
                "Using invalid arguments when creating an Execution Request" +
                        " did not throw an IllegalArgumentException."
        );
        Mockito.verifyZeroInteractions(executionRequestRepository);
        Mockito.verifyZeroInteractions(executionResultRepository);
        Mockito.verifyZeroInteractions(executorServiceProxy);
    }

    // Not testing PlaygroundManager#receiveTimedOut(long) because there are no possible invalid arguments.

    @Test
    void testFinishedExecutionResultReceptionFailsWithInvalidArguments(
            @Mock(name = "request") final ExecutionRequest mockedRequest) {
        final var requestId = TestHelper.validExecutionRequestId();
        Mockito.when(executionRequestRepository.findById(requestId)).thenReturn(Optional.of(mockedRequest));
        Mockito.when(executionResultRepository.existsFor(mockedRequest)).thenReturn(false);
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> playgroundManager.receiveFinished(
                        TestHelper.validExitCode(), // No invalid value for this
                        TestHelper.invalidInputOutputList(),
                        TestHelper.invalidInputOutputList(),
                        requestId
                ),
                "Using invalid arguments when creating a Finished Execution Result" +
                        " did not throw an IllegalArgumentException."
        );
        Mockito.verify(executionRequestRepository, Mockito.only()).findById(requestId);
        Mockito.verify(executionResultRepository, Mockito.times(1)).existsFor(mockedRequest);
        Mockito.verifyNoMoreInteractions(executionResultRepository);
        Mockito.verifyZeroInteractions(executorServiceProxy);
    }
}
