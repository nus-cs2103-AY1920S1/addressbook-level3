//package seedu.address.storage;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import seedu.address.model.deadline.DueDate;
//import seedu.address.model.flashcard.Question;
//import seedu.address.model.deadline.BadQuestions;
//
//public class JsonLoadBadDeadlines {
////    private static HashMap<String, String> badQuestions;
//
//    public static void main(String[] args) throws FileNotFoundException {
//        BadQuestions badQuestions = new BadQuestions();
//        Gson gson = new Gson();
//        BufferedReader br = new BufferedReader(new FileReader("data/test.json"));
//
//        //convert the json string back to object
//
//        HashMap<String, String> mapObj = new Gson().fromJson(
//                br, new TypeToken<HashMap<String, Object>>() {}.getType()
//        );
//
//        HashMap map = gson.fromJson(br, badQuestions.getBadQuestionsList().getClass());
//        System.out.println(mapObj);
////        return mapObj;
//
//    }
//}
