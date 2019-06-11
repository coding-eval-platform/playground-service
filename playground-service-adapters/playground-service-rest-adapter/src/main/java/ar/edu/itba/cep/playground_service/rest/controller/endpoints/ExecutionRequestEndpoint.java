package ar.edu.itba.cep.playground_service.rest.controller.endpoints;

import ar.edu.itba.cep.playground_service.models.ExecutionRequest;
import ar.edu.itba.cep.playground_service.models.ExecutionResult;
import ar.edu.itba.cep.playground_service.rest.controller.dtos.ExecutionRequestUploadDto;
import ar.edu.itba.cep.playground_service.rest.controller.dtos.ExecutionResultDto;
import ar.edu.itba.cep.playground_service.rest.controller.dtos.PendingExecutionResultDto;
import ar.edu.itba.cep.playground_service.services.PlaygroundService;
import com.bellotapps.webapps_commons.config.JerseyController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Rest Adapter of {@link PlaygroundService},
 * encapsulating {@link ExecutionRequest} and {@link ExecutionResult} management.
 */
@Path("")
@Produces(MediaType.APPLICATION_JSON)
@JerseyController
public class ExecutionRequestEndpoint {

    /**
     * The {@link Logger}.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionRequestEndpoint.class);
    /**
     * The {@link PlaygroundService} that will be used to manage {@link ExecutionRequest}s and {@link ExecutionResult}s.
     */
    private final PlaygroundService playgroundService;


    /**
     * Constructor.
     *
     * @param playgroundService The {@link PlaygroundService} that will be used to manage
     *                          {@link ExecutionRequest}s and {@link ExecutionResult}s.
     */
    @Autowired
    public ExecutionRequestEndpoint(final PlaygroundService playgroundService) {
        this.playgroundService = playgroundService;
    }


    @POST
    @Path(Routes.EXECUTION_REQUESTS)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createExam(@Context final UriInfo uriInfo, @Valid final ExecutionRequestUploadDto dto) {
        LOGGER.debug("Creating new Execution Request");
        final var request = playgroundService.requestExecution(
                dto.getCode(),
                dto.getInputs(),
                dto.getTimeout(),
                dto.getLanguage()
        );
        final var location = uriInfo.getAbsolutePathBuilder()
                .path(Long.toString(request.getId()))
                .build();
        return Response.created(location).build();
    }

    @GET
    @Path(Routes.EXECUTION_RESULT)
    public Response getExecutionResult(@SuppressWarnings("RSReferenceInspection") @PathParam("id") final long id) {
        LOGGER.debug("Getting result for request with id {}", id);
        final var result = playgroundService.getResultFor(id)
                .map(ExecutionResultDto::createFor)
                .orElseGet(PendingExecutionResultDto::new);
        return Response.ok(result).build();
    }
}
