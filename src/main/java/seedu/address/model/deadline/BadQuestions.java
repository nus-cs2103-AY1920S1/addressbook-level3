//@@author dalsontws

package seedu.address.model.deadline;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import seedu.address.model.Model;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.exceptions.DuplicateFlashCardException;
import seedu.address.model.flashcard.exceptions.NoBadFlashCardException;

/**
 * BadQuestions class.
 * Contains a Hashmap with questions that are rated as 'bad'
 * Each Question will be tagged to a specific DueDate, which will be referred to
 * in the list of Deadlines
 */
public class BadQuestions {

    private static HashMap<String, Set<FlashCard>> internalMap;
    //private JsonBadDeadlines jsonBadDeadlines;

    public BadQuestions() {
        internalMap = loadFromJson();
        if (internalMap.isEmpty()) {
            HashMap<String, Set<FlashCard>> map = new HashMap<>();
            internalMap = map;
        }
    }

    public HashMap<String, Set<FlashCard>> getBadQuestionsList() {
        return internalMap;
    }

    /**
     *
     */
    public String showBadQuestionsList(DueDate d) {
        Set<FlashCard> set = internalMap.get(d.toString());
        try {
            Iterator<FlashCard> itr = set.iterator();

            StringBuilder sb = new StringBuilder();
            sb.append("For Deadline: " + d.toString() + "\n");
            while (itr.hasNext()) {
                sb.append(itr.next().getQuestion().toString() + "\n");
            }
            return sb.toString();
        } catch (NullPointerException e) {
            throw new NoBadFlashCardException();
        }
    }

    /**
     * Add bad rated flashcards into set.
     * For each due date, there will be a set of bad flashcards
     *
     * @param d the duedate of bad rated flashcards
     * @param f the flashcard that is rated bad
     */
    public void addBadQuestion(DueDate d, FlashCard f) {
        String dateStr = d.toString();
        Set<FlashCard> set = internalMap.get(dateStr);
        if (set == null) {
            set = new HashSet<>();
        }
        if (set.contains(f)) {
            throw new DuplicateFlashCardException();
        }
        set.add(f);
        internalMap.put(d.toString(), set);
    }

    //TODO: remove bad question
//    /**
//     * Add bad rated flashcards into set.
//     * For each due date, there will be a set of bad flashcards
//     *
//     * @param d the duedate of bad rated flashcards
//     * @param f the flashcard that is rated bad
//     */
//    public void removeBadQuestion(DueDate d, FlashCard f) {
//        String dateStr = d.toString();
//        Set<FlashCard> set = internalMap.get(dateStr);
//        if (set == null) {
//            set = new HashSet<>();
//        }
//        if (set.contains(f)) {
//            throw new DuplicateFlashCardException();
//        }
//        set.add(f);
//        internalMap.put(d.toString(), set);
//    }

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
     * Using Google's Gson library, save HashMap as a JSON Object and store it
     * in BadQuestions.json. This can later be used to fetch the 'bad' questions
     * to test in a future date.
     */
    public void saveAsJson(BadQuestions badQuestions) {
        Gson gson = new Gson();
        String json = gson.toJson(badQuestions.getBadQuestionsList());
        try {
            //TODO: fix load and save json, json save replace file instead of appending
            FileWriter writer = new FileWriter("data/BadFlashCards.json");
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load HashMap from json file that contains all flashcards that are rated 'bad'.
     *
     * @throws FileNotFoundException the file not found exception
     */
    public HashMap<String, Set<FlashCard>> loadFromJson() {
        Gson gson = new Gson();
        try {
            Type type = new TypeToken<HashMap<String, Set<FlashCard>>>() {
            }.getType();
            JsonReader reader = new JsonReader(new FileReader("data/BadFlashCards.json"));
            HashMap<String, Set<FlashCard>> data = gson.fromJson(reader, type);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String, Set<FlashCard>> map = new HashMap<>();
        return map;
    }
}
