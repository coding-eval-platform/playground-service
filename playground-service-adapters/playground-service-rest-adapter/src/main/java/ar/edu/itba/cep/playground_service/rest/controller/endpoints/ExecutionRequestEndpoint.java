package ar.edu.itba.cep.playground_service.rest.controller.endpoints;

import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionRequest;
import ar.edu.itba.cep.playground_service.models.PlaygroundServiceExecutionResponse;
import ar.edu.itba.cep.playground_service.rest.controller.dtos.CompletedExecutionResponseDto;
import ar.edu.itba.cep.playground_service.rest.controller.dtos.ExecutionRequestDto;
import ar.edu.itba.cep.playground_service.rest.controller.dtos.ExecutionResponseDto;
import ar.edu.itba.cep.playground_service.rest.controller.dtos.PendingExecutionResponseDto;
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
 * encapsulating {@link PlaygroundServiceExecutionRequest} and {@link PlaygroundServiceExecutionResponse} management.
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
     * The {@link PlaygroundService} that will be used to manage {@link PlaygroundServiceExecutionRequest}s and {@link PlaygroundServiceExecutionResponse}s.
     */
    private final PlaygroundService playgroundService;


    /**
     * Constructor.
     *
     * @param playgroundService The {@link PlaygroundService} that will be used to manage
     *                          {@link PlaygroundServiceExecutionRequest}s and {@link PlaygroundServiceExecutionResponse}s.
     */
    @Autowired
    public ExecutionRequestEndpoint(final PlaygroundService playgroundService) {
        this.playgroundService = playgroundService;
    }


    @POST
    @Path(Routes.EXECUTION_REQUESTS)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createExam(@Context final UriInfo uriInfo, @Valid final ExecutionRequestDto dto) {
        LOGGER.debug("Creating new Execution Request");
        final var request = playgroundService.requestExecution(
                dto.getCode(),
                dto.getProgramArguments(),
                dto.getStdin(),
                dto.getCompilerFlags(),
                dto.getTimeout(),
                dto.getLanguage()
        );
        final var location = uriInfo.getAbsolutePathBuilder()
                .path(Long.toString(request.getId()))
                .build();
        return Response.created(location).build();
    }

    @GET
    @Path(Routes.EXECUTION_RESPONSE)
    public Response getExecutionResponse(@PathParam("id") final long id) {
        LOGGER.debug("Getting response for request with id {}", id);
        final var response = playgroundService.getResponseFor(id)
                .<ExecutionResponseDto>map(CompletedExecutionResponseDto::createFor)
                .orElseGet(PendingExecutionResponseDto::getInstance);
        return Response.ok(response).build();
    }
}
