package restrouting;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import routingapi.RoutingService;

@RestController
public class RoutingController {	
	
    @RequestMapping(value = "/v1/routing", method = RequestMethod.GET)
    @ApiOperation(value = "routing", nickname = "startRouting", produces = "application/json")
    public JSONObject routing(@RequestParam(value="startLat", defaultValue="0.0") Double startLat,
    		@RequestParam(value="startLon", defaultValue="0.0") Double startLon,
    		@RequestParam(value="endLat", defaultValue="0.0") Double endLat,
    		@RequestParam(value="endLon", defaultValue="0.0") Double endLon) {
        return new RoutingService().startRouting(startLat, startLon, endLat, endLon);
    }
    
    @ExceptionHandler(value = Exception.class)
    public String inputParameterError() {
      return "Your input parameters for the routing service are invalid!";
    }
    
}