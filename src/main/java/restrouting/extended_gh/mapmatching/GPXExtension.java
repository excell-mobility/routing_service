package restrouting.extended_gh.mapmatching;

import com.graphhopper.storage.index.QueryResult;
import com.graphhopper.util.GPXEntry;

/**
 * Created by Manal on 07.10.2016.
 */
public class GPXExtension {
    final GPXEntry entry;
    final QueryResult queryResult;
    final int gpxListIndex;

    public GPXExtension( GPXEntry entry, QueryResult queryResult, int gpxListIndex )
    {
        this.entry = entry;
        this.queryResult = queryResult;
        this.gpxListIndex = gpxListIndex;
    }

    @Override
    public String toString()
    {
        return "entry:" + entry
                + ", query distance:" + queryResult.getQueryDistance()
                + ", gpxListIndex:" + gpxListIndex;
    }

    public QueryResult getQueryResult() {
        return this.queryResult;
    }

    public GPXEntry getEntry() {
        return entry;
    }
}
