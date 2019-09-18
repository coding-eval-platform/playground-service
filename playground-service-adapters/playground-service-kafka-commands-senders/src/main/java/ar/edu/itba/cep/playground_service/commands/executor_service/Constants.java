package ar.edu.itba.cep.playground_service.commands.executor_service;

/**
 * Class with several constants to be used across the Kafka Command Senders' Executor Service proxy module.
 */
/* package */ class Constants {

    // ================================================================================================================
    // Headers
    // ================================================================================================================

    /**
     * The Request Id header key. Used to match a response with a request.
     */
    /* package */ static final String REQUEST_ID = "Request-Id";
    /**
     * The Reply-Channel header key. Asked by the Executor Service in order to receive the response from it.
     */
    /* package */ static final String REPLY_CHANNEL_HEADER = "Reply-Channel";


    // ================================================================================================================
    // Kafka Listening Channels
    // ================================================================================================================

    /**
     * Topic in which the command replies are received.
     */
    /* package */ static final String REPLY_CHANNEL = "PlaygroundService-Command-Replies";


    // ================================================================================================================
    // Kafka Sending Channels
    // ================================================================================================================

    /**
     * Topic to which execution requests are sent to the Executor Service.
     */
    /* package */ static final String EXECUTOR_SERVICE_COMMANDS_CHANNEL = "ExecutorService-Commands";
}
