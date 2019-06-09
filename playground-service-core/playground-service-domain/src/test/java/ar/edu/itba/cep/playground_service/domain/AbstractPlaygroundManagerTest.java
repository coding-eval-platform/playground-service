package ar.edu.itba.cep.playground_service.domain;


import ar.edu.itba.cep.playground_service.commands.ExecutorServiceCommandMessageProxy;
import ar.edu.itba.cep.playground_service.repositories.ExecutionRequestRepository;
import ar.edu.itba.cep.playground_service.repositories.ExecutionResultRepository;
import org.mockito.Mockito;

/**
 * A base Test class for {@link PlaygroundManager}.
 */
abstract class AbstractPlaygroundManagerTest {

    // ================================================================================================================
    // Mocks
    // ================================================================================================================

    /**
     * An {@link ExecutionRequestRepository} that is injected to the {@link PlaygroundManager}.
     * This reference is saved in order to configure its behaviour in each test.
     */
    /* package */ final ExecutionRequestRepository executionRequestRepository;
    /**
     * An {@link ExecutionResultRepository} that is injected to the {@link PlaygroundManager}.
     * This reference is saved in order to configure its behaviour in each test.
     */
    /* package */ final ExecutionResultRepository executionResultRepository;

    /**
     * An {@link ExecutorServiceCommandMessageProxy} that is injected to the {@link PlaygroundManager}.
     * This reference is saved in order to configure its behaviour in each test.
     */
    /* package */ final ExecutorServiceCommandMessageProxy executorServiceProxy;


    // ================================================================================================================
    // Playground Manager
    // ================================================================================================================

    /**
     * The {@link PlaygroundManager} being tested.
     */
    /* package */ final PlaygroundManager playgroundManager;


    // ================================================================================================================
    // Constructor
    // ================================================================================================================

    /**
     * Constructor.
     *
     * @param executionRequestRepository An {@link ExecutionRequestRepository}
     *                                   that is injected to the {@link PlaygroundManager}.
     * @param executionResultRepository  An {@link ExecutionResultRepository}
     *                                   that is injected to the {@link PlaygroundManager}.
     * @param executorServiceProxy       An {@link ExecutorServiceCommandMessageProxy}
     *                                   that is injected to the {@link PlaygroundManager}.
     */
    AbstractPlaygroundManagerTest(
            final ExecutionRequestRepository executionRequestRepository,
            final ExecutionResultRepository executionResultRepository,
            final ExecutorServiceCommandMessageProxy executorServiceProxy) {
        this.executionRequestRepository = executionRequestRepository;
        this.executionResultRepository = executionResultRepository;
        this.executorServiceProxy = executorServiceProxy;
        this.playgroundManager = new PlaygroundManager(
                executionRequestRepository,
                executionResultRepository,
                executorServiceProxy
        );
    }


    /**
     * Verifies that there were no interactions with any repository.
     */
    /* package */ void verifyNoInteractionWithAnyMockedRepository() {
        Mockito.verifyZeroInteractions(executionRequestRepository);
        Mockito.verifyZeroInteractions(executionResultRepository);
    }
//
//    /**
//     * Verifies that interactions with repositories only implies searching for an {@link ExecutionRequest}.
//     *
//     * @param examId The id of the {@link ExecutionRequest} being searched.
//     */
//    /* package */ void verifyOnlyExecutionRequestSearch(final long examId) {
//        Mockito.verify(executionRequestRepository, Mockito.only()).findById(examId);
//        Mockito.verifyZeroInteractions(executionResultRepository);
//    }
//
//    /**
//     * Verifies that interactions with repositories only implies searching for an {@link ExecutionResult}.
//     *
//     * @param exerciseId The id of the {@link ExecutionResult} being searched.
//     */
//    /* package */ void verifyOnlyExecutionResultSearch(final long exerciseId) {
//        Mockito.verifyZeroInteractions(executionRequestRepository);
//        Mockito.verify(executionResultRepository, Mockito.only()).findById(exerciseId);
//    }
}
