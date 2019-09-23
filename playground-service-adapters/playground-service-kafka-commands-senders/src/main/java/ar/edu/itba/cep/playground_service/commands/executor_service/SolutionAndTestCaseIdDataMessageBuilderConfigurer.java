package ar.edu.itba.cep.playground_service.commands.executor_service;

import ar.edu.itba.cep.executor.client.ExecutionResponseIdDataMessageBuilderConfigurer;
import ar.edu.itba.cep.playground_service.commands.ExecutionRequestId;
import com.bellotapps.the_messenger.producer.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * An {@link ExecutionResponseIdDataMessageBuilderConfigurer} that configures the builder in order
 * to set the {@link ResponseIdDataHeaders#REQUEST_ID} header using data in a {@link ExecutionRequestId} instance.
 */
@Component
public class SolutionAndTestCaseIdDataMessageBuilderConfigurer
        implements ExecutionResponseIdDataMessageBuilderConfigurer<ExecutionRequestId> {

    @Override
    public void configureMessageBuilder(final MessageBuilder builder, final ExecutionRequestId idData) {
        builder.copyHeaders(ResponseIdDataHeaders.REQUEST_ID)
                .withHeader(ResponseIdDataHeaders.REQUEST_ID, Long.toString(idData.getRequestId()));
    }
}
