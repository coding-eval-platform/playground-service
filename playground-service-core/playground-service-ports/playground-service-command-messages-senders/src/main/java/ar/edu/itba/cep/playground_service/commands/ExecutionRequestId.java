package ar.edu.itba.cep.playground_service.commands;

import ar.edu.itba.cep.executor.api.ExecutionResponseIdData;
import lombok.Data;

/**
 * Class containing data to handle an {@link ar.edu.itba.cep.executor.models.ExecutionResponse}.
 */
@Data(staticConstructor = "create")
public class ExecutionRequestId implements ExecutionResponseIdData {

    /**
     * The id of the {@link ar.edu.itba.cep.executor.models.ExecutionRequest}.
     */
    private final long requestId;
}
