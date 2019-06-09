package ar.edu.itba.cep.playground_service.commands.executor_service;

import ar.edu.itba.cep.playground_service.commands.ExecutorServiceCommandMessageProxy;
import ar.edu.itba.cep.playground_service.models.ExecutionRequest;
import com.bellotapps.webapps_commons.exceptions.NotImplementedException;
import org.springframework.stereotype.Component;

/**
 * A mocked {@link ExecutorServiceCommandMessageProxy} used to boot the application.
 */
@Component
public class MockedExecutorServiceKafkaCommandMessageProxy implements ExecutorServiceCommandMessageProxy {

    @Override
    public void requestExecution(final ExecutionRequest executionRequest) {
        throw new NotImplementedException("Not implemented yet!");
    }
}
