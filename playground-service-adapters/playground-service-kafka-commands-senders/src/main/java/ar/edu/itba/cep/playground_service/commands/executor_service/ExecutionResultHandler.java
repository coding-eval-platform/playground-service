package ar.edu.itba.cep.playground_service.commands.executor_service;

import ar.edu.itba.cep.playground_service.commands.ExecutionResultProcessor;
import ar.edu.itba.cep.playground_service.commands.executor_service.dto.*;
import ar.edu.itba.cep.playground_service.models.CompileErrorExecutionResult;
import ar.edu.itba.cep.playground_service.models.InitializationErrorExecutionResult;
import ar.edu.itba.cep.playground_service.models.UnknownErrorExecutionResult;
import com.bellotapps.the_messenger.commons.Message;
import com.bellotapps.the_messenger.commons.payload.PayloadDeserializer;
import com.bellotapps.the_messenger.consumer.DeserializerMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Adapts an {@link ExecutionResultProcessor} into the Kafka Command senders infrastructure,
 * waiting for command results.
 * Is implemented as a {@link DeserializerMessageHandler} of {@link ExecutionResultDto} that takes data from the
 * request dto (checking its type) and calls the appropriate method of {@link ExecutionResultProcessor}
 * (i.e {@link ExecutionResultProcessor#receiveTimedOut(long)} or
 * {@link ExecutionResultProcessor#receiveFinished(int, List, List, long)}, according to the received result type).
 */
@Component
public class ExecutionResultHandler extends DeserializerMessageHandler<ExecutionResultDto> {

    /**
     * The {@link ExecutionResultProcessor} that is in charge of processing an execution result.
     */
    private final ExecutionResultProcessor executionResultProcessor;


    /**
     * Constructor.
     *
     * @param executionRequestDtoDeserializer A {@link PayloadDeserializer} of {@link ExecutionResultDto}.
     * @param executionResultProcessor        The {@link ExecutionResultProcessor}
     *                                        that is in charge of processing an execution result.
     */
    @Autowired
    public ExecutionResultHandler(
            final PayloadDeserializer<ExecutionResultDto> executionRequestDtoDeserializer,
            final ExecutionResultProcessor executionResultProcessor) {
        super(executionRequestDtoDeserializer);
        this.executionResultProcessor = executionResultProcessor;
    }


    @Override
    protected void andThen(final ExecutionResultDto executionResultDto, final Message message) {
        final var requestId = message.headerValue(Constants.REQUEST_ID)
                .map(Long::parseLong)
                .orElseThrow(() -> new IllegalArgumentException("Missing request id")); // TODO: throw?
        processResultType(requestId, executionResultDto);
    }

    /**
     * Performs the {@link ExecutionResultDto} type processing.
     *
     * @param requestId          The id of the execution request to which the received result belongs to.
     * @param executionResultDto The {@link ExecutionResultDto} to be processed.
     */
    private void processResultType(final long requestId, final ExecutionResultDto executionResultDto) {
        if (executionResultDto instanceof FinishedExecutionResultDto) {
            final var finishedExecutionResultDto = (FinishedExecutionResultDto) executionResultDto;
            executionResultProcessor.receiveFinished(
                    finishedExecutionResultDto.getExitCode(),
                    finishedExecutionResultDto.getStdout(),
                    finishedExecutionResultDto.getStderr(),
                    requestId
            );
            return;
        }
        if (executionResultDto instanceof TimedOutExecutionResultDto) {
            executionResultProcessor.receiveTimedOut(requestId);
            return;
        }
        if (executionResultDto instanceof CompileErrorExecutionResultDto) {
            final var compiledErrorExecutionResultDto = (CompileErrorExecutionResultDto) executionResultDto;
            executionResultProcessor.receiveCompileError(
                    compiledErrorExecutionResultDto.getCompilerErrors(),
                    requestId
            );
            return;
        }
        if (executionResultDto instanceof InitializationErrorExecutionResultDto) {
            executionResultProcessor.receiveInitializationError(requestId);
            return;
        }
        if (executionResultDto instanceof UnknownErrorExecutionResultDto) {
            executionResultProcessor.receiveUnknownError(requestId);
            return;
        }
        throw new IllegalArgumentException("Unknown subtype");
    }
}
