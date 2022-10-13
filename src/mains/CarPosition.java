package mains;
import static java.lang.Math.*;

public class CarPosition {
    double lat, lon, distance;

    public CarPosition(Double lat, Double lon) {
        this.lat = toRadians(lat);
        this.lon = toRadians(lon);
    }

    public double distanceFrom(CarPosition nextPoint) {
        double dlat = nextPoint.lat - this.lat;
        double dlon = nextPoint.lon - this.lon;
        double a = pow((sin(dlat/2)), 2)+ pow((cos(this.lat) * cos(nextPoint.lat) * (sin(dlon/2))), 2);
        distance = 6373.0 * 2 * atan2(sqrt(a), sqrt(1-a));
        return distance;
    }
}