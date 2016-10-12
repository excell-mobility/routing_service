package restrouting.extended_gh.mapmatching;



import java.util.List;

/**
 * Created by Manal on 07.10.2016.
 */
public class MatchResult {
    private List<EdgeMatch> edgeMatches;
    private double matchLength;
    private long matchMillis;
    private double gpxEntriesLength;
    private long gpxEntriesMillis;

    public MatchResult(List<EdgeMatch> edgeMatches) {
        setEdgeMatches(edgeMatches);
    }

    public void setEdgeMatches(List<EdgeMatch> edgeMatches) {
        if (edgeMatches == null) {
            throw new IllegalStateException("edgeMatches cannot be null");
        }

        this.edgeMatches = edgeMatches;
    }

    public void setGPXEntriesLength(double gpxEntriesLength) {
        this.gpxEntriesLength = gpxEntriesLength;
    }

    public void setGPXEntriesMillis(long gpxEntriesMillis) {
        this.gpxEntriesMillis = gpxEntriesMillis;
    }

    public void setMatchLength(double matchLength) {
        this.matchLength = matchLength;
    }

    public void setMatchMillis(long matchMillis) {
        this.matchMillis = matchMillis;
    }

    /**
     * All possible assigned edges.
     */
    public List<EdgeMatch> getEdgeMatches() {
        return edgeMatches;
    }

    /**
     * Length of the original GPX track in meters
     */
    public double getGpxEntriesLength() {
        return gpxEntriesLength;
    }

    /**
     * Length of the original GPX track in milliseconds
     */
    public long getGpxEntriesMillis() {
        return gpxEntriesMillis;
    }

    /**
     * Length of the map-matched road in meters
     */
    public double getMatchLength() {
        return matchLength;
    }

    /**
     * Length of the map-matched road in milliseconds
     */
    public long getMatchMillis() {
        return matchMillis;
    }

    @Override
    public String toString() {
        return "length:" + matchLength + ", seconds:" + matchMillis / 1000f + ", matches:" + edgeMatches.toString();
    }
}
