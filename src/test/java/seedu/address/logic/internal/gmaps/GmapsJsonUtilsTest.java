package seedu.address.logic.internal.gmaps;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

public class GmapsJsonUtilsTest {

    @Test
    public void contructs_nxn_matrix() {
        String gmapsResponse = "{\"destination_addresses\":[\"10 Lower Kent Ridge Rd, National University of Singapore, Singapore\",\"Lower Kent Ridge Rd, Singapore\",\"11 Computing Dr, Singapore 117416\"],\"rows\":[{\"elements\":[{\"duration\":{\"text\":\"1 min\",\"value\":0},\"distance\":{\"text\":\"1 ft\",\"value\":0},\"status\":\"OK\"},{\"duration\":{\"text\":\"3 mins\",\"value\":157},\"distance\":{\"text\":\"0.3 mi\",\"value\":466},\"status\":\"OK\"},{\"duration\":{\"text\":\"8 mins\",\"value\":455},\"distance\":{\"text\":\"1.4 mi\",\"value\":2293},\"status\":\"OK\"}]},{\"elements\":[{\"duration\":{\"text\":\"2 mins\",\"value\":145},\"distance\":{\"text\":\"0.3 mi\",\"value\":466},\"status\":\"OK\"},{\"duration\":{\"text\":\"1 min\",\"value\":0},\"distance\":{\"text\":\"1 ft\",\"value\":0},\"status\":\"OK\"},{\"duration\":{\"text\":\"6 mins\",\"value\":348},\"distance\":{\"text\":\"1.2 mi\",\"value\":1964},\"status\":\"OK\"}]},{\"elements\":[{\"duration\":{\"text\":\"9 mins\",\"value\":526},\"distance\":{\"text\":\"1.6 mi\",\"value\":2534},\"status\":\"OK\"},{\"duration\":{\"text\":\"7 mins\",\"value\":434},\"distance\":{\"text\":\"1.4 mi\",\"value\":2205},\"status\":\"OK\"},{\"duration\":{\"text\":\"1 min\",\"value\":0},\"distance\":{\"text\":\"1 ft\",\"value\":0},\"status\":\"OK\"}]}],\"origin_addresses\":[\"10 Lower Kent Ridge Rd, National University of Singapore, Singapore\",\"Lower Kent Ridge Rd, Singapore\",\"11 Computing Dr, Singapore 117416\"],\"status\":\"OK\"}\n";
        JSONParser parser = new JSONParser();
        ArrayList<Long> expectedRow1 = new ArrayList<>();
        expectedRow1.add((long) 0);
        expectedRow1.add((long) 157);
        expectedRow1.add((long) 455);
        ArrayList<Long> expectedRow2 = new ArrayList<>();
        expectedRow2.add((long) 145);
        expectedRow2.add((long) 0);
        expectedRow2.add((long) 348);
        ArrayList<Long> expectedRow3 = new ArrayList<>();
        expectedRow3.add((long) 526);
        expectedRow3.add((long) 434);
        expectedRow3.add((long) 0);
        ArrayList<ArrayList<Long>> expectedMatrix = new ArrayList<>();
        expectedMatrix.add(expectedRow1);
        expectedMatrix.add(expectedRow2);
        expectedMatrix.add(expectedRow3);
        try {
            JSONObject response = (JSONObject) parser.parse(gmapsResponse);
            ArrayList<ArrayList<Long>> matrix = GmapsJsonUtils.getArrayListMatrix(response);
            assertTrue(matrix.equals(expectedMatrix));
        } catch (ParseException pe) {
            System.out.println("Failed to parse JSON object");
        }
    }
}
