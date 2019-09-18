package ar.edu.itba.cep.playground_service.commands.executor_service;

import ar.edu.itba.cep.executor.dtos.ExecutionResponseDto;
import ar.edu.itba.cep.executor.models.ExecutionResponse;
import ar.edu.itba.cep.playground_service.commands.ExecutionResponseProcessor;
import com.bellotapps.the_messenger.commons.Message;
import com.bellotapps.the_messenger.commons.payload.PayloadDeserializer;
import com.bellotapps.the_messenger.consumer.DeserializerMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Adapts an {@link ExecutionResponseProcessor} into the Kafka Command senders infrastructure,
 * waiting for command responses.
 * Is implemented as a {@link DeserializerMessageHandler} of {@link ExecutionResponseDto} that takes data from the
 * request dto and calls the {@link ExecutionResponseProcessor#processResponse(long, ExecutionResponse)} method.
 */
@Component
public class ExecutionResponseHandler extends DeserializerMessageHandler<ExecutionResponseDto> {

    /**
     * The {@link ExecutionResponseProcessor} that is in charge of processing an execution response.
     */
    private final ExecutionResponseProcessor executionResponseProcessor;


    /**
     * Constructor.
     *
     * @param executionRequestDtoDeserializer A {@link PayloadDeserializer} of {@link ExecutionResponseDto}.
     * @param executionResponseProcessor      The {@link ExecutionResponseProcessor}
     *                                        that is in charge of processing an execution response.
     */
    @Autowired
    public ExecutionResponseHandler(
            final PayloadDeserializer<ExecutionResponseDto> executionRequestDtoDeserializer,
            final ExecutionResponseProcessor executionResponseProcessor) {
        super(executionRequestDtoDeserializer);
        this.executionResponseProcessor = executionResponseProcessor;
    }


    @Override
    protected void andThen(final ExecutionResponseDto executionResponseDto, final Message message) {
        final var requestId = message.headerValue(Constants.REQUEST_ID)
                .map(Long::parseLong)
                .orElseThrow(() -> new IllegalArgumentException("Missing request id")); // TODO: throw?
        executionResponseProcessor.processResponse(requestId, executionResponseDto.getExecutionResponse());
    }
}
