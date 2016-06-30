package restrouting.extended_gh.util;

import com.graphhopper.routing.util.FlagEncoder;
import com.graphhopper.util.*;
import com.graphhopper.util.EdgeIteratorState;

/**
 * Created by Manal on 30.06.2016.
 */
public class GHUtility {

    public static EdgeIteratorState createMockedEdgeIteratorState(final double distance, final long flags) {
        return new GHUtility.DisabledEdgeIterator() {
            @Override
            public double getDistance() {
                return distance;
            }

            @Override
            public long getFlags() {
                return flags;
            }

            // @Override
            public boolean getBool(int key, boolean _default) {
                return _default;
            }
        };
    }

    /**
     * This edge iterator can be used in tests to mock specific iterator behaviour via overloading
     * certain methods.
     */
    public static class DisabledEdgeIterator implements CHEdgeIterator {
        @Override
        public EdgeIterator detach(boolean reverse) {
            throw new UnsupportedOperationException("Not supported. Edge is empty.");
        }

        @Override
        public EdgeIteratorState setDistance(double dist) {
            throw new UnsupportedOperationException("Not supported. Edge is empty.");
        }

        @Override
        public EdgeIteratorState setFlags(long flags) {
            throw new UnsupportedOperationException("Not supported. Edge is empty.");
        }

        @Override
        public boolean next() {
            throw new UnsupportedOperationException("Not supported. Edge is empty.");
        }

        @Override
        public int getEdge() {
            throw new UnsupportedOperationException("Not supported. Edge is empty.");
        }

        @Override
        public int getBaseNode() {
            throw new UnsupportedOperationException("Not supported. Edge is empty.");
        }

        @Override
        public int getAdjNode() {
            throw new UnsupportedOperationException("Not supported. Edge is empty.");
        }

        @Override
        public double getDistance() {
            throw new UnsupportedOperationException("Not supported. Edge is empty.");
        }

        @Override
        public long getFlags() {
            throw new UnsupportedOperationException("Not supported. Edge is empty.");
        }

        @Override
        public PointList fetchWayGeometry(int type) {
            throw new UnsupportedOperationException("Not supported. Edge is empty.");
        }

        @Override
        public EdgeIteratorState setWayGeometry(PointList list) {
            throw new UnsupportedOperationException("Not supported. Edge is empty.");
        }

        @Override
        public String getName() {
            throw new UnsupportedOperationException("Not supported. Edge is empty.");
        }

        @Override
        public EdgeIteratorState setName(String name) {
            throw new UnsupportedOperationException("Not supported. Edge is empty.");
        }

//        @Override
//        public boolean getBool( int key, boolean _default )
//        {
//            throw new UnsupportedOperationException("Not supported. Edge is empty.");
//        }

        @Override
        public boolean isBackward(FlagEncoder encoder) {
            throw new UnsupportedOperationException("Not supported. Edge is empty.");
        }

        @Override
        public boolean getBoolean(int i, boolean b, boolean b1) {
            return false;
        }

        @Override
        public boolean isForward(FlagEncoder encoder) {
            throw new UnsupportedOperationException("Not supported. Edge is empty.");
        }

        @Override
        public int getAdditionalField() {
            throw new UnsupportedOperationException("Not supported. Edge is empty.");
        }

        @Override
        public EdgeIteratorState setAdditionalField(int value) {
            throw new UnsupportedOperationException("Not supported. Edge is empty.");
        }

        @Override
        public EdgeIteratorState copyPropertiesTo(EdgeIteratorState edge) {
            throw new UnsupportedOperationException("Not supported. Edge is empty.");
        }

        @Override
        public boolean isShortcut() {
            return false;
        }

        @Override
        public int getSkippedEdge1() {
            throw new UnsupportedOperationException("Not supported. Edge is empty.");
        }

        @Override
        public int getSkippedEdge2() {
            throw new UnsupportedOperationException("Not supported. Edge is empty.");
        }

        @Override
        public void setSkippedEdges(int edge1, int edge2) {
            throw new UnsupportedOperationException("Not supported. Edge is empty.");
        }

        @Override
        public double getWeight() {
            throw new UnsupportedOperationException("Not supported. Edge is empty.");
        }

        @Override
        public CHEdgeIteratorState setWeight(double weight) {
            throw new UnsupportedOperationException("Not supported. Edge is empty.");
        }

        @Override
        public boolean canBeOverwritten(long flags) {
            throw new UnsupportedOperationException("Not supported. Edge is empty.");
        }
    }

    ;
}


