package restrouting.component;

import com.graphhopper.GraphHopper;
import com.graphhopper.reader.DataReader;
import com.graphhopper.reader.osm.OSMReader;
import com.graphhopper.routing.util.FlagEncoder;
import com.graphhopper.routing.util.HintsMap;
import com.graphhopper.routing.weighting.Weighting;
import com.graphhopper.storage.GraphHopperStorage;

public class TravelTimeGraphHopper extends GraphHopper {
	
	@Override
	public Weighting createWeighting(HintsMap hintsMap, FlagEncoder encoder, com.graphhopper.storage.Graph graph) {
		return new TravelTimeWeighting(encoder);
	};
	
    @Override
    protected DataReader createReader(GraphHopperStorage ghStorage) {
        return initDataReader(new OSMReader(ghStorage));
    }

    public String getOSMFile() {
        return getDataReaderFile();
    }

    /**
     * This file can be an osm xml (.osm), a compressed xml (.osm.zip or .osm.gz) or a protobuf file
     * (.pbf).
     */
    public TravelTimeGraphHopper setOSMFile(String osmFileStr) {
        super.setDataReaderFile(osmFileStr);
        return this;
    }
	
}