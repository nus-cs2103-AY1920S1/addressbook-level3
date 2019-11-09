//@@author dalsontws

package seedu.address.model.deadline;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import seedu.address.model.Model;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.exceptions.DuplicateFlashCardAndDeadlineException;
import seedu.address.model.flashcard.exceptions.DuplicateFlashCardException;
import seedu.address.model.flashcard.exceptions.NoBadFlashCardException;

/**
 * BadQuestions class.
 * Contains a Hashmap with questions that are rated as 'bad'
 * Each Question will be tagged to a specific DueDate, which will be referred to
 * in the list of Deadlines
 */
public class BadQuestions {

    private static HashMap<String, ArrayList<FlashCard>> internalMap;

    public BadQuestions() {
        internalMap = loadFromJson();
        if (internalMap == null) {
            HashMap<String, ArrayList<FlashCard>> map = new HashMap<>();
            internalMap = map;
        }
    }

    public HashMap<String, ArrayList<FlashCard>> getBadQuestionsList() {
        return internalMap;
    }

    /**
     *
     */
    public String showBadQuestionsList(DueDate d) {
        try {
            ArrayList<FlashCard> list = internalMap.get(d.toString());
            StringBuilder sb = new StringBuilder();
            sb.append("For Deadline: " + d.toString() + "\n");
            int index = 1;
            for (FlashCard f : list) {
                sb.append(index + ". " + f.getQuestion().toString() + "\n");
                index++;
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
    public void addBadQuestion(DueDate d, FlashCard f, boolean duplicateDeadline) {
        String dateStr = d.toString();
        ArrayList<FlashCard> list = internalMap.get(dateStr);
        if (list == null) {
            list = new ArrayList<>();
        }
        if (list.contains(f) && duplicateDeadline) {
            throw new DuplicateFlashCardAndDeadlineException();
        } else if (list.contains(f)) {
            throw new DuplicateFlashCardException();
        }
        list.add(f);
        internalMap.put(d.toString(), list);
    }

    //TODO: remove bad question

    /**
     * Remove bad rated flashcards from the set.
     * For each due date, there will be a set of bad flashcards
     * Removing a bad flashcard can be done by using the index
     *
     * @param d the duedate of bad rated flashcards
     * @param f the flashcard that is rated bad
     */
//    public void removeBadQuestion(DueDate d, int index) {
//        String dateStr = d.toString();
//        Set<FlashCard> set = internalMap.get(dateStr);
//        try {
//
//            if (set.contains(f)) {
//                throw new DuplicateFlashCardException();
//            }
//            set.add(f);
//            internalMap.put(d.toString(), set);
//        } catch (NullPointerException e) {
//            throw new NoBadFlashCardException();
//        }
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
    public HashMap<String, ArrayList<FlashCard>> loadFromJson() {
        Gson gson = new Gson();
        try {
            Type type = new TypeToken<HashMap<String, ArrayList<FlashCard>>>() {
            }.getType();
            JsonReader reader = new JsonReader(new FileReader("data/BadFlashCards.json"));
            HashMap<String, ArrayList<FlashCard>> data = gson.fromJson(reader, type);
            System.out.println(data);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String, ArrayList<FlashCard>> map = new HashMap<>();
        return map;
    }
}
