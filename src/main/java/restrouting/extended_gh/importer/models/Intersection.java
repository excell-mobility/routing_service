package restrouting.extended_gh.importer.models;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manal on 16.09.2016.
 */

public class Intersection extends Base {
    private List<Base> sections = new ArrayList<Base>();

    public String[] relationsSections() {
        return this.relations(this.sections, "CONNECTION");
    }

    public void addConnection(Base other) {
        this.sections.add(other);
    }

    public static String getCsvHeader() {
        StringBuffer sb = new StringBuffer();

        sb.append("id");
        sb.append("\t");
        sb.append("sid_intersections");
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
        sb.append("Intersection");
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
}
