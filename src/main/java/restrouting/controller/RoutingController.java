package restrouting.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import restrouting.component.RoutingService;
import restrouting.exceptions.InputParameterErrorException;
import restrouting.exceptions.RoutingNotFoundException;
import restrouting.model.ErrorResponse;
import restrouting.model.RoutingResponse;

@RestController
@Api(value="/v1/routing")
public class RoutingController {

	@Autowired
	RoutingService routingService;

	@RequestMapping(value = "/v1/routing", method = RequestMethod.GET)
    @ApiOperation(
    		value = "Find fastest route", 
    		response=RoutingResponse.class,
    		produces = "application/json")

    @ResponseBody
    public RoutingResponse routing(
    		@ApiParam(name="startLat", value="Latitude for start coordinate", defaultValue = "51.04") 
    		@RequestParam(value="startLat", defaultValue = "0.0") Double startLat,
    		
    		@ApiParam(name="startLon", value="Longitude for start coordinate", defaultValue = "13.73") 
    		@RequestParam(value="startLon", defaultValue = "0.0") Double startLon,
    		
    		@ApiParam(name="endLat", value="Latitude for end coordinate", defaultValue = "51.029") 
    		@RequestParam(value="endLat", defaultValue = "0.0") Double endLat,
    		
    		@ApiParam(name="endLon", value="Longitude for end coordinate", defaultValue = "13.736") 
    		@RequestParam(value="endLon", defaultValue = "0.0") Double endLon) throws RoutingNotFoundException, InputParameterErrorException {
    	
		return routingService.startRouting(startLat, startLon, endLat, endLon);
    }
	
    @ExceptionHandler(InputParameterErrorException.class)
    public final ResponseEntity<ErrorResponse> handleParameterError(InputParameterErrorException ex, WebRequest request) {
    	ErrorResponse errorDetails = new ErrorResponse(new Date(), ex.getMessage(), request.getDescription(false));
    	return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(RoutingNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleRoutingNotFoundException(RoutingNotFoundException ex, WebRequest request) {
    	ErrorResponse errorDetails = new ErrorResponse(new Date(), ex.getMessage(), request.getDescription(false));
    	return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}