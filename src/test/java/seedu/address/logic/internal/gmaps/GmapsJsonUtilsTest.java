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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GmapsJsonUtilsTest {

    private JSONObject apiResponse;

    @BeforeEach
    void setUp() {
        try {
            JSONParser parser = new JSONParser();
            apiResponse = (JSONObject) parser.parse(
                    new FileReader("src/test/data/ApiStubsTest/GmapsDistanceMatrix3x3SuccessStubTest.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getArrayListMatrix() {
        ArrayList<Long> row1 = new ArrayList<Long>(Arrays.asList((long) 0, (long) 395, (long) 455));
        ArrayList<Long> row2 = new ArrayList<Long>(Arrays.asList((long) 187, (long) 0, (long) 98));
        ArrayList<Long> row3 = new ArrayList<Long>(Arrays.asList((long) 526, (long) 181, (long) 0));
        ArrayList<ArrayList<Long>> expectedResult = new ArrayList<ArrayList<Long>>(Arrays.asList(row1, row2, row3));
        assertEquals(GmapsJsonUtils.getArrayListMatrix(apiResponse), expectedResult);
    }

    @Test
    void getStatus() {
        assertEquals(GmapsJsonUtils.getStatus(apiResponse), "OK");
    }
}

