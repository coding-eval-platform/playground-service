package ar.edu.itba.cep.playground_service.commands.executor_service;

/**
 * Class with several constants to be used across the Kafka Command Senders' Executor Service proxy module.
 */
/* package */ class ResponseIdDataHeaders {

    // ================================================================================================================
    // Headers
    // ================================================================================================================

    /**
     * The Request Id header key. Used to match a response with a request.
     */
    /* package */ static final String REQUEST_ID = "Request-Id";
}
