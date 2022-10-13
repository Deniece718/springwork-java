package mains;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static java.lang.Math.*;

public class SinglePoint {
    private double timestamp;
    private CarPosition position;
    private double CarSpeed, CarSpeedLimit;

    public SinglePoint(String time, Double longitude, Double latitude, Double speed, Double speedLimit) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date dateFormat= sdf.parse(time);
        this.timestamp = dateFormat.getTime();
        this.position = new CarPosition(latitude, longitude);
        this.CarSpeed = speed;
        this.CarSpeedLimit = speedLimit;
    }

    public double timeFrom(SinglePoint nextPoint){
        return abs(this.timestamp - nextPoint.timestamp) / 1000;
    }

    public double getTimestamp(){
        return this.timestamp;
    }

    public CarPosition getPosition(){
        return this.position;
    }

    public double getCarSpeed(){
        return this.CarSpeed;
    }

    public double getCarSpeedLimit(){
        return this.CarSpeedLimit;
    }
}
