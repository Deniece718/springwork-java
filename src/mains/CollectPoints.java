package mains;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CollectPoints {
    public static void main(String[] args) throws IOException, ParseException, java.text.ParseException{
        String jsonFile= "waypoints.json";
        JSONArray pointList = readJsonFileToJsonArray(jsonFile);
        ArrayList<SinglePoint> points = addEachPointToSinglePoint(pointList);
        HashMap<String, Double> categoryList = calculateCategories(points);

        System.out.println("Hi Peter, below is your waypoints for this trip:");
        System.out.printf("Distance Speeding: %.4fkm", categoryList.get("speedingDistance"));
        System.out.println("\nDuration Speeding (sec): " + categoryList.get("speedingDuration") + "s");
        System.out.printf("Total Distance: %.4fkm", categoryList.get("totalDistance"));
        System.out.println("\nTotal Duration: " + categoryList.get("totalDuration") + "s");
    }

    public static JSONArray readJsonFileToJsonArray(String jsonFile) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        FileReader file = new FileReader(jsonFile);
        Object rows = jsonParser.parse(file);
        return (JSONArray) rows;
    }

    public static ArrayList<SinglePoint> addEachPointToSinglePoint(JSONArray pointList) throws java.text.ParseException{
        ArrayList<SinglePoint> points = new ArrayList<>();
        for (int i = 0; i < (pointList.size()); i++ ){
            JSONObject pointInfo = (JSONObject) pointList.get(i);
            String timestamp = (String) pointInfo.get("timestamp");
            Double speed = (Double) pointInfo.get("speed");
            Double speedLimit = (Double) pointInfo.get("speed_limit");
            JSONObject position = (JSONObject) pointInfo.get("position");
            Double latitude = (Double) position.get("latitude");
            Double longitude = (Double) position.get("longitude");
            points.add(new SinglePoint(timestamp, latitude, longitude, speed, speedLimit));
        }
        return points;
    }

    public static HashMap<String, Double> calculateCategories(ArrayList<SinglePoint> points){
        double speedingDuration = 0.0;
        double totalDuration = 0.0;
        double speedingDistance = 0.0;
        double totalDistance = 0.0;
        HashMap<String, Double> categoryList = new HashMap<>();
        for (int singlePoint = 0; singlePoint < (points.size() - 1); singlePoint++){
            SinglePoint firstPoint = points.get(singlePoint);
            SinglePoint secondPoint = points.get(singlePoint+1);

            if (firstPoint.getCarSpeed() > firstPoint.getCarSpeedLimit()){
                speedingDuration += firstPoint.timeFrom(secondPoint);
                speedingDistance += firstPoint.getPosition().distanceFrom(secondPoint.getPosition());
            }
            totalDuration += firstPoint.timeFrom(secondPoint);
            totalDistance += firstPoint.getPosition().distanceFrom(secondPoint.getPosition());
        }
        categoryList.put("speedingDuration", new BigDecimal(speedingDuration).setScale(4, RoundingMode.HALF_UP).doubleValue());
        categoryList.put("speedingDistance", new BigDecimal(speedingDistance).setScale(4, RoundingMode.HALF_UP).doubleValue());
        categoryList.put("totalDuration", new BigDecimal(totalDuration).setScale(4, RoundingMode.HALF_UP).doubleValue());
        categoryList.put("totalDistance", new BigDecimal(totalDistance).setScale(4, RoundingMode.HALF_UP).doubleValue());
        return categoryList;
    }
}
