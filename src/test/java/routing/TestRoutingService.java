package routing;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import restrouting.exceptions.InputParameterErrorException;
import restrouting.exceptions.RoutingNotFoundException;
import restrouting.component.RoutingService;
import restrouting.model.RoutingResponse;

public class TestRoutingService {
	
	@Autowired
	private RoutingService routingService;
	private RoutingResponse response;

	@Before
	public void initialize() {
		routingService = new RoutingService(
				"src/test/resources/Dresden.osm.pbf",
				"src/test/resources/graphhopper");
	}
	
	@Test
	public void testRoutingService() throws RoutingNotFoundException, InputParameterErrorException {
		
		response = routingService.startRouting(51.048480, 13.729409, 51.049660, 13.74);
		assertTrue(response.getDistance() == 980.0225623571259);
		assertTrue(response.getTimeInMs() == 154945);
		assertTrue(response.getPointList().size() == 19);
		
		response = routingService.startRouting(60.048480, 21.729409, 51.049660, 13.74);
		assertTrue(response == null);	
	}

}