package restrouting.model;

import java.util.List;

public class GeoJsonResopnse
{
  public String type;
  public List<Double[]> coordinates;
  
  public GeoJsonResopnse(List<Double[]> coordinates)
  {
    type = "LineString";
    this.coordinates = coordinates; 
  }
}
