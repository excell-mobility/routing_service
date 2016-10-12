package restrouting.extended_gh.importer.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manal on 16.09.2016.
 */

public class Section extends Base {

    private List<Base> geoPoints = new ArrayList<Base>();
    private List<Base> intersections = new ArrayList<Base>();

    public void addGeoPoint(GeoPoint gp) {
        this.geoPoints.add(gp);
    }

    public List<Base> getGeoPoints() {
        return  geoPoints;
    }

    public void addConnection(Base other) {
        this.intersections.add(other);
    }

    public static String getCsvHeader() {
        StringBuffer sb = new StringBuffer();

        sb.append("id");
        sb.append("\t");
        sb.append("sid_sections");
        sb.append("\t");
        sb.append("label");
        sb.append("\t");
        sb.append("l");
        sb.append("\t");
        sb.append("sperr");
        sb.append("\t");
        sb.append("t");
        sb.append("\t");
        sb.append("t_rout");

        return sb.toString();
    }

    public String getCsvLine() {
        StringBuffer sb = new StringBuffer();

        sb.append(this.id);
        sb.append("\t");
        sb.append(this.sid);
        sb.append("\t");
        sb.append("Section");
        sb.append("\t");
        sb.append(this.l);
        sb.append("\t");
        sb.append(this.sperr);
        sb.append("\t");
        sb.append(this.t);
        sb.append("\t");
        sb.append(this.tRout);

        return sb.toString();
    }

    public String[] relationsGridPoints() {
        return this.relations(this.geoPoints, "GEOPOINT");
    }

    public String[] relationsIntersections() {
        return this.relations(this.intersections, "CONNECTION");
    }
}
