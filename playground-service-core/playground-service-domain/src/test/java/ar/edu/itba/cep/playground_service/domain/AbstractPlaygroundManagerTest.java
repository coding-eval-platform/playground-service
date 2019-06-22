package ar.edu.itba.cep.playground_service.domain;


import ar.edu.itba.cep.playground_service.commands.ExecutorServiceCommandMessageProxy;
import ar.edu.itba.cep.playground_service.repositories.ExecutionRequestRepository;
import ar.edu.itba.cep.playground_service.repositories.ExecutionResultRepository;

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
}
