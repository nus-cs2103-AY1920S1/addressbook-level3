package seedu.address.model.deadline;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import com.google.gson.Gson;

import seedu.address.model.Model;
import seedu.address.model.flashcard.Question;

/**
 * BadQuestions class.
 * Contains a Hashmap with questions that are rated as 'bad'
 * Each Question will be tagged to a specific DueDate, which will be referred to
 * in the list of Deadlines
 */
public class BadQuestions {

    private HashMap<String, String> internalMap = new HashMap<String, String>();
    //private JsonBadDeadlines jsonBadDeadlines;

    public BadQuestions() {
    //TODO: add initialisation of bad deadline list - load json
    }

    public HashMap<String, String> getBadQuestionsList() {
        return internalMap;
    }

    public void setBadQuestionsList(HashMap<String, String> map) {
        internalMap = map;
    }

    public void addBadQuestion(DueDate d, Question q) {
        internalMap.put(d.toString(), q.toString());
    }

    public void loadBadQuestions() throws FileNotFoundException {
        //internalMap = jsonBadDeadlines.loadJsonBadDeadlines();
    }

    public static DueDate getBadDeadline() {
        LocalDate today = LocalDate.now();
        LocalDate due = today.plusDays(3);
        String formattedDate = due.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        DueDate d = new DueDate(formattedDate);
        return d;
    }

    public void setBadQuestionsAsDeadline(Model model) {
        Task task = new Task("Bad Questions");
        Deadline deadline = new Deadline(task, getBadDeadline());
        model.addDeadline(deadline);
    }

    /**
     * Save as json.
     */
    public void saveAsJson(BadQuestions badQuestions) {
        Gson gson = new Gson();

        // convert java object to JSON format,
        // and returned as JSON formatted string
        String json = gson.toJson(badQuestions.getBadQuestionsList());
        try {
            //write converted json data to a file
            //TODO: fix load and save json, json save replace file instead of appending
            FileWriter writer = new FileWriter("data/BadDeadlines.json", true);
            writer.write(json);
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
