package seedu.address.storage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import seedu.address.model.deadline.BadQuestions;
import seedu.address.model.deadline.DueDate;
import seedu.address.model.flashcard.Question;

/**
 * The type Json store bad deadlines.
 */
public class JsonBadDeadlines {

    private HashMap<String, String> badQuestions;

    /**
     * Instantiates a new JsonBadDeadlines.
     * Stores all the "Bad" flashcards into a Hashmap in json
     */
    public JsonBadDeadlines(BadQuestions badQuestions) {
        HashMap<String, String> badQuestionList = badQuestions.getBadQuestionsList();

        Gson gson = new Gson();

        // convert java object to JSON format,
        // and returned as JSON formatted string
        String json = gson.toJson(badQuestionList);
        try {
            //write converted json data to a file named "CountryGSON.json"
            FileWriter writer = new FileWriter("data/test.json");
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//        ObjectMapper mapper = new ObjectMapper();
//
//        /**
//         * Convert Map to JSON and write to a file
//         */
//        try {
//            mapper.writeValue(new File("data/BadDeadlines.json"), badQuestionList);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * Load json bad deadlines.
     * Load all the "Bad" flashcards into a Hashmap from a json
     */
    public HashMap<String, String> loadJsonBadDeadlines() throws FileNotFoundException {

            BadQuestions badQuestions = new BadQuestions();
            Gson gson = new Gson();
            BufferedReader br = new BufferedReader(new FileReader("data/test.json"));

            //convert the json string back to object

            HashMap<String, String> mapObj = new Gson().fromJson(
                    br, new TypeToken<HashMap<String, String>>() {}.getType()
            );

    //        HashMap map = gson.fromJson(br, badQuestions.getBadQuestionsList().getClass());
            System.out.println(mapObj);
            return mapObj;


//        ObjectMapper mapper = new ObjectMapper();
//        /**
//         * Read JSON from a file into a HashMap
//         */
//        try {
//            HashMap<String, String> map = mapper.readValue(new File(
//                    "data/BadDeadlines.json"), new TypeReference<HashMap<String, String>>() {});
//            System.out.println("data:" + map);
//
////            badQuestions = map;
//            return map;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
    }
}
