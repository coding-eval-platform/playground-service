package ar.edu.itba.cep.playground_service.commands.executor_service;

import ar.edu.itba.cep.executor.client.ExecutionResponseIdDataFactory;
import ar.edu.itba.cep.playground_service.commands.ExecutionRequestId;
import com.bellotapps.the_messenger.commons.Message;
import org.springframework.stereotype.Component;

/**
 * An {@link ExecutionResponseIdDataFactory} that builds a {@link ExecutionRequestId} instance from
 * a {@link Message}, taking data from its headers (i.e checks for the {@link ResponseIdDataHeaders#REQUEST_ID} header).
 */
@Component
public class SolutionAndTestCaseIdDataFactory implements ExecutionResponseIdDataFactory<ExecutionRequestId> {

    @Override
    public ExecutionRequestId buildFromMessage(final Message message) {
        final var requestId = message.headerValue(ResponseIdDataHeaders.REQUEST_ID)
                .map(Long::parseLong)
                .orElseThrow(() -> new IllegalArgumentException("Missing request id")); // TODO: throw?

        return ExecutionRequestId.create(requestId);
    }
}
