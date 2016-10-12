package restrouting.extended_gh.importer.models;

/**
 * Created by Manal on 16.09.2016.
 */

public class Connection {
    private Long relationshipId;
    private Base start;
    private Base end;

    public Connection(Base start, Base end) {
        this.start = start;
        this.end = end;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append(this.start.toString());
        sb.append(" -> ");
        sb.append(this.end.toString());

        return sb.toString();
    }
}
