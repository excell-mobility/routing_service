package restrouting.component;

import com.graphhopper.routing.util.FlagEncoder;
import com.graphhopper.routing.util.Weighting;
import com.graphhopper.util.EdgeIteratorState;

import java.util.HashSet;
import java.util.Set;

public class ExCeWeighting implements Weighting
{
  /**
   * Converting to seconds is not necessary but makes adding other penalities easier (e.g. turn costs or traffic light costs etc)
   */
  protected final static double SPEED_CONV = 3.6;
  final static double DEFAULT_HEADING_PENALTY = 300; // [s]
  private final double heading_penalty;
  protected final FlagEncoder flagEncoder;
  private final double maxSpeed;

  Set<Integer> forbiddenEdges;

  public ExCeWeighting(FlagEncoder encoder, Set<Integer> forbiddenEdges)
  {
    if (!encoder.isRegistered()) throw new IllegalStateException("Make sure you add the FlagEncoder " + encoder + " to an EncodingManager before using it elsewhere");

    this.forbiddenEdges = forbiddenEdges;

    this.flagEncoder = encoder;
    heading_penalty = DEFAULT_HEADING_PENALTY;
    maxSpeed = encoder.getMaxSpeed() / SPEED_CONV;
  }

  public ExCeWeighting(FlagEncoder encoder)
  {
    this(encoder, new HashSet<Integer>());
  }

  @Override
  public double getMinWeight(double distance)
  {
    return distance / maxSpeed;
  }

  @Override
  public double calcWeight(EdgeIteratorState edge, boolean reverse, int prevOrNextEdgeId)
  {

    if (forbiddenEdges.contains(edge.getEdge())) return Double.POSITIVE_INFINITY;

    double speed = reverse ? flagEncoder.getReverseSpeed(edge.getFlags()) : flagEncoder.getSpeed(edge.getFlags());
    if (speed == 0) return Double.POSITIVE_INFINITY;

    double time = edge.getDistance() / speed * SPEED_CONV;

    // add direction penalties at start/stop/via points
    boolean penalizeEdge = edge.getBoolean(EdgeIteratorState.K_UNFAVORED_EDGE, reverse, false);
    if (penalizeEdge) time += heading_penalty;

    return time * 2; // TODO Hack
  }

  @Override
  public FlagEncoder getFlagEncoder()
  {
    return flagEncoder;
  }

  @Override
  public int hashCode()
  {
    int hash = 7;
    hash = 71 * hash + toString().hashCode();
    return hash;
  }

  @Override
  public boolean equals(Object obj)
  {
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    final ExCeWeighting other = (ExCeWeighting) obj;
    return toString().equals(other.toString());
  }

  @Override
  public String toString()
  {
    return "FASTEST|" + flagEncoder;
  }
}