package restrouting.extended_gh.importer.models;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Manal on 16.09.2016.
 */

public abstract class Base {
    protected Long id;
	protected String sid;
    protected int l;
    protected int sperr;
    protected int t;
    protected int tRout;

    public abstract void addConnection(Base other);

    protected String[] relations(List<Base> list, String type) {
        List<String> items = new ArrayList<String>();

        for (Base b : list) {
            StringBuffer sb = new StringBuffer();

            sb.append(this.id);
            sb.append("\t");
            sb.append(b.id);
            sb.append("\t");
            sb.append(type);

            items.add(sb.toString());
        }

        return items.toArray(new String[items.size()]);
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getId() {
		return id;
	}

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setL(int l) {
        this.l = l;
    }

    public void setSperr(int sperr) {
        this.sperr = sperr;
    }

    public void setT(int t) {
        this.t = t;
    }

    public void setTRout(int tRout) {
        this.tRout = tRout;
    }
    
    @Override
	public String toString() {
		return "Base [id=" + id + ", sid=" + sid + ", l=" + l + ", sperr="
				+ sperr + ", t=" + t + ", tRout=" + tRout + "]";
	}
}
