package restrouting.extended_gh.mapmatching;


import com.graphhopper.util.EdgeIteratorState;

import java.util.List;

/**
 * Created by Manal on 07.10.2016.
 */
public class EdgeMatch {
    private final EdgeIteratorState edgeState;
    private final List<GPXExtension> gpxExtensions;

    public EdgeMatch(EdgeIteratorState edgeState, List<GPXExtension> gpxExtension) {
        this.edgeState = edgeState;

        if (edgeState == null) {
            throw new IllegalStateException("Cannot fetch null EdgeState");
        }

        this.gpxExtensions = gpxExtension;
        if (this.gpxExtensions == null) {
            throw new IllegalStateException("extension list cannot be null");
        }
    }

    public boolean isEmpty() {
        return gpxExtensions.isEmpty();
    }

    public EdgeIteratorState getEdgeState() {
        return edgeState;
    }

    public List<GPXExtension> getGpxExtensions() {
        return gpxExtensions;
    }

    public double getMinDistance() {
        if (isEmpty()) {
            throw new IllegalStateException("No minimal distance for " + edgeState);
        }

        double min = Double.MAX_VALUE;
        for (GPXExtension gpxExt : gpxExtensions) {
            if (gpxExt.queryResult.getQueryDistance() < min) {
                min = gpxExt.queryResult.getQueryDistance();
            }
        }
        return min;
    }

    @Override
    public String toString() {
        return "edge:" + edgeState + ", extensions:" + gpxExtensions;
    }

}
