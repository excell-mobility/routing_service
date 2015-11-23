package restrouting;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import routingapi.RoutingService;

@RestController
public class RoutingController {	
	
    @RequestMapping("/routing")
    public JSONObject routing(@RequestParam(value="startLat", defaultValue="0.0") Double startLat,
    		@RequestParam(value="startLon", defaultValue="0.0") Double startLon,
    		@RequestParam(value="endLat", defaultValue="0.0") Double endLat,
    		@RequestParam(value="endLon", defaultValue="0.0") Double endLon) {
        return new RoutingService().startRouting(startLat, startLon, endLat, endLon);
    }
}