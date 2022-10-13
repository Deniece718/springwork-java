package test;
import static mains.CollectPoints.calculateCategories;
import static mains.CollectPoints.readJsonFileToJsonArray;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import mains.SinglePoint;

public class SinglePointTest {
    @Test
    void readJsonFileToJsonArrayTest() throws IOException, ParseException{
        String testJsonFile = "test1.json";
        JSONObject jsonObject= new JSONObject();
        jsonObject.put("timestamp", "2016-06-21T12:00:20.000Z");
        JSONObject position = new JSONObject();
        position.put("latitude", 59.3323);
        position.put("longitude", 18.0666);
        jsonObject.put("position", position);
        jsonObject.put("speed", 8.33);
        jsonObject.put("speed_limit", 8.33);
        JSONArray expectedResult = new JSONArray();
        expectedResult.add(jsonObject);
        assertEquals(expectedResult, readJsonFileToJsonArray(testJsonFile));
    }

    @Test
    void calculateCategoriesTestSpeeding() throws java.text.ParseException{
        SinglePoint testSinglePoint1 = new SinglePoint("2016-06-21T12:00:05.000Z", 59.3337, 18.0662, 9.4, 8.33);
        SinglePoint testSinglePoint2 = new SinglePoint("2016-06-21T12:00:10.000Z", 59.3331, 18.0664, 11.1, 8.33);
        ArrayList<SinglePoint> testArrayList = new ArrayList<SinglePoint>();
        testArrayList.add(testSinglePoint1);
        testArrayList.add(testSinglePoint2);
        HashMap<String, Double> categoryList = calculateCategories(testArrayList);
        assertEquals(5.0, categoryList.get("speedingDuration"));
        assertEquals(0.0643, categoryList.get("speedingDistance"));
        assertEquals(5.0, categoryList.get("totalDuration"));
        assertEquals(0.0643, categoryList.get("totalDistance"));
    }

    @Test
    void calculateCategoriesTestNotSpeeding() throws java.text.ParseException{
        SinglePoint testSinglePoint1 = new SinglePoint("2016-06-21T12:00:15.000Z", 59.3327, 18.0665, 8.32, 8.33);
        SinglePoint testSinglePoint2 = new SinglePoint("2016-06-21T12:00:20.000Z", 59.3323, 18.0666, 8.33, 8.33);
        ArrayList<SinglePoint> testArrayList = new ArrayList<SinglePoint>();
        testArrayList.add(testSinglePoint1);
        testArrayList.add(testSinglePoint2);
        HashMap<String, Double> categoryList = calculateCategories(testArrayList);
        assertEquals(0.0, categoryList.get("speedingDuration"));
        assertEquals(0.0, categoryList.get("speedingDistance"));
        assertEquals(5.0, categoryList.get("totalDuration"));
        assertEquals(0.0417, categoryList.get("totalDistance"));
    }
}

