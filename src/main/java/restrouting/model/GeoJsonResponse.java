package restrouting.model;

import java.util.List;

public class GeoJsonResponse
{
  public String type;
  public List<Double[]> coordinates;
  
  public GeoJsonResponse(List<Double[]> coordinates)
  {
    type = "LineString";
    this.coordinates = coordinates; 
  }
}
