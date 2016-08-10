package extended_GH;

import org.junit.Test;
import restrouting.model.SensorData;
import restrouting.sensorflag.SensorDataMapper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * Created by Manal on 10.08.2016.
 */
public class SensorDataMapperTest {

    @Test
    public void mappingTest(){
        long initialDelay = 10;
        long period = 10;
        TimeUnit unit = MINUTES;
        SensorDataMapper sensorScheduler = new SensorDataMapper(createSensorData());
        sensorScheduler.sensorMappingScheduler(initialDelay, period, unit, sensorScheduler.getSensorsList());
    }

    private List<SensorData> createSensorData(){
        //TODO create a list with sensors Data from JSON file
        return null;
    }
}
