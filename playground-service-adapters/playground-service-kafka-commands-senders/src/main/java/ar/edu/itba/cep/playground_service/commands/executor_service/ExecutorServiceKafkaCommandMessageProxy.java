package ar.edu.itba.cep.playground_service.commands.executor_service;

import ar.edu.itba.cep.executor.dtos.ExecutionRequestDto;
import ar.edu.itba.cep.playground_service.commands.ExecutorServiceCommandMessageProxy;
import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionRequest;
import com.bellotapps.the_messenger.commons.Message;
import com.bellotapps.the_messenger.producer.MessageBuilderFactory;
import com.bellotapps.the_messenger.producer.MessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Concrete implementation of {@link ExecutorServiceCommandMessageProxy}, using Kafka.
 */
@Component
public class ExecutorServiceKafkaCommandMessageProxy implements ExecutorServiceCommandMessageProxy {

    /**
     * The {@link MessageProducer} in charge of sending the {@link Message}.
     */
    private final MessageProducer messageProducer;

    /**
     * A {@link MessageBuilderFactory} of {@link ExecutionRequestDto} that creates the
     * {@link com.bellotapps.the_messenger.producer.MessageBuilder} that can create the request {@link Message}s.
     */
    private final MessageBuilderFactory<ExecutionRequestDto> executionRequestDtoMessageBuilderFactory;


    /**
     * Constructor.
     *
     * @param messageProducer                          The {@link MessageProducer}
     *                                                 in charge of sending the {@link Message}.
     * @param executionRequestDtoMessageBuilderFactory A {@link MessageBuilderFactory} of {@link ExecutionRequestDto}
     *                                                 that creates the
     *                                                 {@link com.bellotapps.the_messenger.producer.MessageBuilder}
     *                                                 that can create the request {@link Message}s.
     */
    @Autowired
    public ExecutorServiceKafkaCommandMessageProxy(
            final MessageProducer messageProducer,
            final MessageBuilderFactory<ExecutionRequestDto> executionRequestDtoMessageBuilderFactory) {
        this.messageProducer = messageProducer;
        this.executionRequestDtoMessageBuilderFactory = executionRequestDtoMessageBuilderFactory;
    }


    @Override
    public void requestExecution(final PlaygroundServiceExecutionRequest playgroundServiceExecutionRequest) {
        final var message = executionRequestDtoMessageBuilderFactory.commandMessage("requestExecution")
                .withHeader(Constants.REQUEST_ID, Long.toString(playgroundServiceExecutionRequest.getId()))
                .copyHeaders(Constants.REQUEST_ID)
                .withPayload(ExecutionRequestDto.buildFromRequest(playgroundServiceExecutionRequest.getRequest()))
                .build();
        messageProducer.send(message, Constants.EXECUTOR_SERVICE_COMMANDS_CHANNEL);
    }
}
