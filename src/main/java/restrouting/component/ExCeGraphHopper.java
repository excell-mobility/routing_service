package restrouting.component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.graphhopper.GraphHopper;
import com.graphhopper.routing.util.EdgeFilter;
import com.graphhopper.routing.util.FlagEncoder;
import com.graphhopper.routing.util.Weighting;
import com.graphhopper.routing.util.WeightingMap;
import com.graphhopper.storage.index.LocationIndex;
import com.graphhopper.storage.index.QueryResult;
import com.graphhopper.util.EdgeIteratorState;
import com.graphhopper.util.PointList;

public class ExCeGraphHopper extends GraphHopper
{

  
  private Set<Integer> forbiddenEdges = new HashSet<Integer>();
  
  public ExCeGraphHopper()
  {
    super();
  }

  public List<Double[]> block(double lat, double lon)
  {
    LocationIndex index = this.getLocationIndex();
    QueryResult result = index.findClosest(lat,lon, EdgeFilter.ALL_EDGES);
    EdgeIteratorState edge = result.getClosestEdge();
    forbiddenEdges.add(edge.getEdge());
    PointList pointList = edge.fetchWayGeometry(2);
    return pointList.toGeoJson();
    //System.out.println("Block Edge: "+edge.getEdge()+" at ["+lat+" "+lon+"]");
  }

  
  public List<Double[]> unblock(double lat, double lon)
  {    
    LocationIndex index = this.getLocationIndex();
    QueryResult result = index.findClosest(lat,lon, EdgeFilter.ALL_EDGES);
    EdgeIteratorState edge = result.getClosestEdge();
    forbiddenEdges.remove(edge.getEdge());
    PointList pointList = edge.fetchWayGeometry(2);
    return pointList.toGeoJson();
    //System.out.println("Unblock Edge: "+edge.getEdge()+" at ["+lat+" "+lon+"]");
  }
  
  public void unblockAll()
  {    
    if (!forbiddenEdges.isEmpty())
	  forbiddenEdges.clear();
  }

  
  @Override
  public Weighting createWeighting(WeightingMap weightingMap, FlagEncoder encoder)
  {
    return new ExCeWeighting(encoder, forbiddenEdges);
    // else return super.createWeighting(weighting, encoder);
  }
}