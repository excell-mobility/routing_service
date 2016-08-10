package restrouting.model;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Manal on 05.08.2016.
 */
public class SensorData implements Comparable<SensorData> {


    private final long id;
    private final long latitude;
    private final long longitude;
    private long speed;
    private Timestamp timestamp;
    private boolean junction;

    protected SensorData(long id, long latitude, long longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected SensorData(long id, long latitude, long longtude, long speed, Timestamp timestamp, boolean junction) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longtude;
        this.speed = speed;
        this.timestamp = timestamp;
        this.junction = junction;
    }
    public long getId()
    {
        return id;
    }

    public long getLatitude() {
        return latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public long getSpeed() {
        return speed;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public boolean isJunction(){
        return junction;
    }

    public boolean isTimestampChanged(Timestamp timeBefore, Timestamp timeNow){
        // Indicates whether this Timestamp object is later than the given Timestamp
        // object Indicates whether this Timestamp object is later than the given Timestamp object
        return timeBefore.after(timeNow);
    }

    public void updateSensorSpeed(SensorData sensor){
        if(this.compareTo(sensor) == 0) {
            Timestamp timeNow = sensor.getTimestamp();
            if (timeNow.after(timestamp)) {
                this.speed = sensor.getSpeed();
                this.timestamp = sensor.getTimestamp();
            }
        }else System.out.println("Error: sensor with id :" + sensor.getId() + " is not found !!");
    }

    @Override
    public int compareTo(SensorData sensor) {
        if(id != sensor.getId())
            return latitude == sensor.getLongitude() && longitude == sensor.longitude ? 0: -1;
        return 0;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", speed=" + speed +
                ", timestamp=" + timestamp +
                '}';
    }

}
