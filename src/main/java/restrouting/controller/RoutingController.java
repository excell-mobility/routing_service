package restrouting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import exceptions.InputParameterErrorException;
import exceptions.RoutingNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import restrouting.component.RoutingService;
import restrouting.model.RoutingResponse;

@RestController
@Api(value="routing")
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
    		@ApiParam(name="startLat", value="Latitude for start coordinate", defaultValue = "0.0") 
    		@RequestParam(value="startLat", defaultValue = "0.0") Double startLat,
    		
    		@ApiParam(name="startLon", value="Longitude for start coordinate", defaultValue = "0.0") 
    		@RequestParam(value="startLon", defaultValue = "0.0") Double startLon,
    		
    		@ApiParam(name="endLat", value="Latitude for end coordinate", defaultValue = "0.0") 
    		@RequestParam(value="endLat", defaultValue = "0.0") Double endLat,
    		
    		@ApiParam(name="endLon", value="Longitude for end coordinate", defaultValue = "0.0") 
    		@RequestParam(value="endLon", defaultValue = "0.0") Double endLon) throws RoutingNotFoundException, InputParameterErrorException {
    	
		return routingService.startRouting(startLat, startLon, endLat, endLon);
    }
    
	@RequestMapping(value = "/api/v1/routing", method = RequestMethod.GET)
    @ApiOperation(
    		value = "Find fastest route", 
    		response=RoutingResponse.class,
    		produces = "application/json")
    @ResponseBody
    public RoutingResponse routing1(
    		@ApiParam(name="startLat", value="Latitude for start coordinate", defaultValue = "0.0") 
    		@RequestParam(value="startLat", defaultValue = "0.0") Double startLat,
    		
    		@ApiParam(name="startLon", value="Longitude for start coordinate", defaultValue = "0.0") 
    		@RequestParam(value="startLon", defaultValue = "0.0") Double startLon,
    		
    		@ApiParam(name="endLat", value="Latitude for end coordinate", defaultValue = "0.0") 
    		@RequestParam(value="endLat", defaultValue = "0.0") Double endLat,
    		
    		@ApiParam(name="endLon", value="Longitude for end coordinate", defaultValue = "0.0") 
    		@RequestParam(value="endLon", defaultValue = "0.0") Double endLon) throws RoutingNotFoundException, InputParameterErrorException {
    	
		return routingService.startRouting(startLat, startLon, endLat, endLon);
    }
	
    @ExceptionHandler(value = InputParameterErrorException.class)
    public BodyBuilder routingParameterError() {
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(value = RoutingNotFoundException.class)
    public BodyBuilder routingError() {
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}