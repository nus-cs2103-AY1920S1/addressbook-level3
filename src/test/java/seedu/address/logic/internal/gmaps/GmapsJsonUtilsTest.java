package seedu.address.logic.internal.gmaps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

class GmapsJsonUtilsTest {

    private JSONObject apiResponse;



    @Test
    void getPlaceId() {
        try {
            JSONParser parser = new JSONParser();
            apiResponse = (JSONObject) parser.parse(
                    new FileReader("src/test/data/ApiStubsTest/GmapsPlacesOK.json"));
            assertEquals(GmapsJsonUtils.getPlaceId(apiResponse), "ChIJMW2gnpUb2jERlUYTYSaawlc");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getLat() {
        try {
            JSONParser parser = new JSONParser();
            apiResponse = (JSONObject) parser.parse(
                    new FileReader("src/test/data/ApiStubsTest/GmapsPlaceDetailsOK.json"));
            assertEquals(GmapsJsonUtils.getLat(apiResponse), 1.2976883);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getLng() {
        try {
            JSONParser parser = new JSONParser();
            apiResponse = (JSONObject) parser.parse(
                    new FileReader("src/test/data/ApiStubsTest/GmapsPlaceDetailsOK.json"));
            assertEquals(GmapsJsonUtils.getLng(apiResponse), 103.7804811);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getAlias() {
        try {
            JSONParser parser = new JSONParser();
            apiResponse = (JSONObject) parser.parse(
                    new FileReader("src/test/data/ApiStubsTest/GmapsPlaceDetailsOK.json"));
            assertEquals(GmapsJsonUtils.getAlias(apiResponse), "NUS S17");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getArrayListMatrixHappyFlow() {
        try {
            JSONParser parser = new JSONParser();
            apiResponse = (JSONObject) parser.parse(
                    new FileReader("src/test/data/ApiStubsTest/GmapsDistanceMatrix3x3SuccessStubTest.json"));
            ArrayList<Long> row1 = new ArrayList<Long>(Arrays.asList((long) 0, (long) 2268, (long) 2293));
            ArrayList<Long> row2 = new ArrayList<Long>(Arrays.asList((long) 821, (long) 0, (long) 498));
            ArrayList<Long> row3 = new ArrayList<Long>(Arrays.asList((long) 2534, (long) 674, (long) 0));
            ArrayList<ArrayList<Long>> expectedResult = new ArrayList<ArrayList<Long>>(Arrays.asList(row1, row2, row3));
            assertEquals(GmapsJsonUtils.getArrayListMatrix(apiResponse), expectedResult);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getArrayListMatrixSadFlow() {
        try {
            JSONObject apiResponse;
            JSONParser parser = new JSONParser();
            apiResponse = (JSONObject) parser.parse(
                    new FileReader("src/test/data/ApiStubsTest/GmapsDistanceMatrixNOTOK.json"));
            ArrayList<Long> row1 = new ArrayList<Long>(Arrays.asList(null, null, null));
            ArrayList<Long> row2 = new ArrayList<Long>(Arrays.asList(null, null, null));
            ArrayList<Long> row3 = new ArrayList<Long>(Arrays.asList(null, null, null));
            ArrayList<ArrayList<Long>> expectedResult = new ArrayList<ArrayList<Long>>(Arrays.asList(row1, row2, row3));
            assertEquals(GmapsJsonUtils.getArrayListMatrix(apiResponse), expectedResult);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getLatPlacesApi() {
        try {
            JSONParser parser = new JSONParser();
            apiResponse = (JSONObject) parser.parse(
                    new FileReader("src/test/data/ApiStubsTest/GmapsPlacesOK.json"));
            assertEquals(1.2966686, GmapsJsonUtils.getLatPlacesApi(apiResponse));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getLngPlacesApi() {
        try {
            JSONParser parser = new JSONParser();
            apiResponse = (JSONObject) parser.parse(
                    new FileReader("src/test/data/ApiStubsTest/GmapsPlacesOK.json"));
            assertEquals(103.7788514, GmapsJsonUtils.getLngPlacesApi(apiResponse));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getStatus() {
        try {
            JSONParser parser = new JSONParser();
            apiResponse = (JSONObject) parser.parse(
                    new FileReader("src/test/data/ApiStubsTest/GmapsPlacesOK.json"));
            assertEquals(GmapsJsonUtils.getStatus(apiResponse), "OK");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

