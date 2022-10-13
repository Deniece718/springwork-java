package test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import mains.CarPosition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarPositionTest {

    @Test
    void twoPoints(){
        CarPosition point1 = new CarPosition(59.3331, 18.0664);
        CarPosition point2 = new CarPosition(59.3327, 18.0665);
        double actualValue = new BigDecimal(point1.distanceFrom(point2)).setScale(4, RoundingMode.HALF_UP).doubleValue();
        assertEquals(0.0446, actualValue);
    }
}