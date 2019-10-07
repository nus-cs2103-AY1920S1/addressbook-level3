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
        String gmapsResponse =
                "{ \n"
                + "   \"destination_addresses\":[ \n"
                + "      \"10 Lower Kent Ridge Rd, National University of Singapore, Singapore\",\n"
                + "      \"Lower Kent Ridge Rd, Singapore\",\n"
                + "      \"11 Computing Dr, Singapore 117416\"\n"
                + "   ],\n"
                + "   \"rows\":[ \n"
                + "      { \n"
                + "         \"elements\":[ \n"
                + "            { \n"
                + "               \"duration\":{ \n"
                + "                  \"text\":\"1 min\",\n"
                + "                  \"value\":0\n"
                + "               },\n"
                + "               \"distance\":{ \n"
                + "                  \"text\":\"1 ft\",\n"
                + "                  \"value\":0\n"
                + "               },\n"
                + "               \"status\":\"OK\"\n"
                + "            },\n"
                + "            { \n"
                + "               \"duration\":{ \n"
                + "                  \"text\":\"3 mins\",\n"
                + "                  \"value\":157\n"
                + "               },\n"
                + "               \"distance\":{ \n"
                + "                  \"text\":\"0.3 mi\",\n"
                + "                  \"value\":466\n"
                + "               },\n"
                + "               \"status\":\"OK\"\n"
                + "            },\n"
                + "            { \n"
                + "               \"duration\":{ \n"
                + "                  \"text\":\"8 mins\",\n"
                + "                  \"value\":455\n"
                + "               },\n"
                + "               \"distance\":{ \n"
                + "                  \"text\":\"1.4 mi\",\n"
                + "                  \"value\":2293\n"
                + "               },\n"
                + "               \"status\":\"OK\"\n"
                + "            }\n"
                + "         ]\n"
                + "      },\n"
                + "      { \n"
                + "         \"elements\":[ \n"
                + "           { \n"
                + "               \"duration\":{ \n"
                + "                  \"text\":\"2 mins\",\n"
                + "                  \"value\":145\n"
                + "               },\n"
                + "               \"distance\":{ \n"
                + "                  \"text\":\"0.3 mi\",\n"
                + "                  \"value\":466\n"
                + "               },\n"
                + "               \"status\":\"OK\"\n"
                + "            },\n"
                + "            { \n"
                + "               \"duration\":{ \n"
                + "                  \"text\":\"1 min\",\n"
                + "                  \"value\":0\n"
                + "               },\n"
                + "               \"distance\":{ \n"
                + "                  \"text\":\"1 ft\",\n"
                + "                  \"value\":0\n"
                + "               },\n"
                + "               \"status\":\"OK\"\n"
                + "            },\n"
                + "            { \n"
                + "               \"duration\":{ \n"
                + "                  \"text\":\"6 mins\",\n"
                + "                  \"value\":348\n"
                + "               },\n"
                + "               \"distance\":{ \n"
                + "                  \"text\":\"1.2 mi\",\n"
                + "                  \"value\":1964\n"
                + "               },\n"
                + "               \"status\":\"OK\"\n"
                + "            }\n"
                + "         ]\n"
                + "      },\n"
                + "      { \n"
                + "         \"elements\":[ \n"
                + "            { \n"
                + "               \"duration\":{ \n"
                + "                  \"text\":\"9 mins\",\n"
                + "                  \"value\":526\n"
                + "               },\n"
                + "               \"distance\":{ \n"
                + "                  \"text\":\"1.6 mi\",\n"
                + "                  \"value\":2534\n"
                + "               },\n"
                + "               \"status\":\"OK\"\n"
                + "            },\n"
                + "            { \n"
                + "               \"duration\":{ \n"
                + "                  \"text\":\"7 mins\",\n"
                + "                  \"value\":434\n"
                + "               },\n"
                + "               \"distance\":{ \n"
                + "                  \"text\":\"1.4 mi\",\n"
                + "                  \"value\":2205\n"
                + "               },\n"
                + "               \"status\":\"OK\"\n"
                + "            },\n"
                + "            { \n"
                + "               \"duration\":{ \n"
                + "                  \"text\":\"1 min\",\n"
                + "                  \"value\":0\n"
                + "               },\n"
                + "               \"distance\":{ \n"
                + "                  \"text\":\"1 ft\",\n"
                + "                  \"value\":0\n"
                + "               },\n"
                + "               \"status\":\"OK\"\n"
                + "            }\n"
                + "         ]\n"
                + "      }\n"
                + "   ],\n"
                + "   \"origin_addresses\":[ \n"
                + "      \"10 Lower Kent Ridge Rd, National University of Singapore, Singapore\",\n"
                + "      \"Lower Kent Ridge Rd, Singapore\",\n"
                + "      \"11 Computing Dr, Singapore 117416\"\n"
                + "   ],\n"
                + "   \"status\":\"OK\"\n"
                + "}\n";
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
