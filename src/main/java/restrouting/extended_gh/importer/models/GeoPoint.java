package restrouting.extended_gh.importer.models;

import java.sql.Timestamp;

/**
 * Created by Manal on 16.09.2016.
 */

public class GeoPoint extends Base {

	private float latitude;
	private float longitude;
    private Timestamp timestamp;

    public GeoPoint(Long id, float latitude, float longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void addConnection(Base other) {}

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    public static String getCsvHeader() {
        StringBuffer sb = new StringBuffer();

        sb.append("geoId");
        sb.append("\t");
        sb.append("label");
        sb.append("\t");
        sb.append("latitude");
        sb.append("\t");
        sb.append("longitutde");
        sb.append("\t");
        sb.append("timpestamp");
        sb.append("\t");

        return sb.toString();
    }

    public String getCsvLine() {
        StringBuffer sb = new StringBuffer();

        sb.append(this.id);
        sb.append("\t");
        sb.append("Geopoint");
        sb.append("\t");
        sb.append(this.latitude);
        sb.append("\t");
        sb.append(this.longitude);
        sb.append("\t");
        sb.append(this.timestamp);

        return sb.toString();
    }
    
    public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
		return String.format("GeoPoint [latitude=%10.15f, longitude=%10.15f]", latitude, longitude);
	}
}
