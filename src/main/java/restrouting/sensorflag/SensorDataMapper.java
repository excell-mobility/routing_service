package restrouting.sensorflag;

import restrouting.model.SensorData;

import java.sql.Timestamp;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Manal on 10.08.2016.
 */
public class SensorDataMapper {

    private final ScheduledExecutorService sensorScheduler;
    private List<SensorData> sensorsList;

    public SensorDataMapper(List<SensorData> sensorsList) {
        sensorScheduler = Executors.newScheduledThreadPool(1);
        this.sensorsList = sensorsList;
    }

    public List<SensorData> getSensorsList() {
        return sensorsList;
    }
    /**
     *
     * @param currentSensorsList
     */
    public void updateSensorSpeed(List<SensorData> currentSensorsList){
        for(SensorData sensor : sensorsList){
            for(SensorData currentSensor : currentSensorsList)
                if(sensor.compareTo(currentSensor)== 0) {
                    sensor.updateSensorSpeed(currentSensor);
                    break;
                }
        }
    }

    /**
     * sets up a ScheduledExecutorService to execute sensors data mapping e.g. every ten minutes
     * @param initialDelay
     * @param period
     * @param unit
     * @param currentSensorsList
     */
    public void sensorMappingScheduler(long initialDelay, long period, TimeUnit unit, List<SensorData> currentSensorsList) {
        final Runnable task = new Runnable() {
            public void run() {
                System.out.println("start mapping process");
                updateSensorSpeed(currentSensorsList);
            }
        };
        final ScheduledFuture<?> sensorHandle =
                sensorScheduler.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

}
