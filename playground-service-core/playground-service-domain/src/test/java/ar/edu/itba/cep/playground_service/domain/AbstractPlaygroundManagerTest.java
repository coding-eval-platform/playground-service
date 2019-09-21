package ar.edu.itba.cep.playground_service.domain;


import ar.edu.itba.cep.playground_service.commands.ExecutorServiceCommandMessageProxy;
import ar.edu.itba.cep.playground_service.repositories.ExecutionRequestRepository;
import ar.edu.itba.cep.playground_service.repositories.ExecutionResponseRepository;

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
     * An {@link ExecutionResponseRepository} that is injected to the {@link PlaygroundManager}.
     * This reference is saved in order to configure its behaviour in each test.
     */
    /* package */ final ExecutionResponseRepository executionResponseRepository;

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
     * @param executionRequestRepository  An {@link ExecutionRequestRepository}
     *                                    that is injected to the {@link PlaygroundManager}.
     * @param executionResponseRepository An {@link ExecutionResponseRepository}
     *                                    that is injected to the {@link PlaygroundManager}.
     * @param executorServiceProxy        An {@link ExecutorServiceCommandMessageProxy}
     *                                    that is injected to the {@link PlaygroundManager}.
     */
    AbstractPlaygroundManagerTest(
            final ExecutionRequestRepository executionRequestRepository,
            final ExecutionResponseRepository executionResponseRepository,
            final ExecutorServiceCommandMessageProxy executorServiceProxy) {
        this.executionRequestRepository = executionRequestRepository;
        this.executionResponseRepository = executionResponseRepository;
        this.executorServiceProxy = executorServiceProxy;
        this.playgroundManager = new PlaygroundManager(
                executionRequestRepository,
                executionResponseRepository,
                executorServiceProxy
        );
    }
}
