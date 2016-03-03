package restrouting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import restrouting.component.RoutingService;
import restrouting.model.GeoJsonResponse;
import restrouting.model.RoutingResponse;

@RestController
@Api(value = "/v1/routing")
public class RoutingController
{

  @Autowired
  RoutingService routingService;

  @RequestMapping(value = "/v1/routing", method = RequestMethod.GET)
  @ApiOperation(value = "Find fastest route", response = RoutingResponse.class, produces = "application/json")
  @ResponseBody
  public RoutingResponse routing(@ApiParam(name = "startLat", value = "Latitude for start coordinate", defaultValue = "51.04") @RequestParam(value = "startLat", defaultValue = "51.04") Double startLat,

      @ApiParam(name = "startLon", value = "Longitude for start coordinate", defaultValue = "13.73") @RequestParam(value = "startLon", defaultValue = "13.73") Double startLon,

      @ApiParam(name = "endLat", value = "Latitude for end coordinate", defaultValue = "51.029") @RequestParam(value = "endLat", defaultValue = "51.029") Double endLat,

      @ApiParam(name = "endLon", value = "Longitude for end coordinate", defaultValue = "13.736") @RequestParam(value = "endLon", defaultValue = "13.736") Double endLon)
  {

    return routingService.startRouting(startLat, startLon, endLat, endLon);
  }

  @RequestMapping(value = "/v1/routingGeoJson", method = RequestMethod.GET)
  @ApiOperation(value = "The fastest route in GeoJSON", response = GeoJsonResponse.class, produces = "application/json")
  public GeoJsonResponse routingGeoJson(@ApiParam(defaultValue = "51.02929000") double startLat, @ApiParam(defaultValue = "13.70708000") double startLon, @ApiParam(defaultValue = "=51.07149000") double endLat, @ApiParam(defaultValue = "13.73086000") double endLon)
  {
    RoutingResponse r = routingService.startRouting(startLat, startLon, endLat, endLon);
    return new GeoJsonResponse(r.getPointList());
  }

  @RequestMapping(value = "/v1/block", method = RequestMethod.GET)
  @ApiOperation(value = "Blocks edge in the near of [lat,lon]")
  public void block(@ApiParam(defaultValue = "51.0607071105") double lat, @ApiParam(defaultValue = "13.7316311527") double lon)
  {
    routingService.getHopper().block(lat, lon);
  }

  @RequestMapping(value = "/v1/unblock", method = RequestMethod.GET)
  public void unblock(@ApiParam(defaultValue = "51.0607071105") double lat, @ApiParam(defaultValue = "13.7316311527") double lon)
  {
    routingService.getHopper().unblock(lat, lon);
  }

  @ExceptionHandler(value = Exception.class)
  public String inputParameterError(Exception e)
  {
    e.printStackTrace();
    return "Your input parameters for the routing service are invalid!";
  }

}