package seedu.address.model.deadline;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import seedu.address.model.KeyboardFlashCards;
import seedu.address.model.Model;
import seedu.address.model.flashcard.Question;

/**
 * BadQuestions class.
 * Contains a Hashmap with questions that are rated as 'bad'
 * Each Question will be tagged to a specific DueDate, which will be referred to
 * in the list of Deadlines
 */
public class BadQuestions {

    private HashMap<DueDate, Question> badQuestions = new HashMap<DueDate, Question>();

    public BadQuestions() {
        //HashMap<DueDate, Question> badQuestions = new HashMap<DueDate, Question>();
    }

    public void addBadQuestion(DueDate d, Question q) {
        badQuestions.put(d, q);
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
}
