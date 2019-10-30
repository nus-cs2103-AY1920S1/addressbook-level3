package seedu.address.model.util;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.model.AppData;
import seedu.address.model.ReadOnlyAppData;
import seedu.address.model.note.Content;
import seedu.address.model.note.Note;
import seedu.address.model.note.Title;
import seedu.address.model.question.Answer;
import seedu.address.model.question.Difficulty;
import seedu.address.model.question.Question;
import seedu.address.model.question.QuestionBody;
import seedu.address.model.question.Subject;
import seedu.address.model.quiz.QuizResult;
import seedu.address.model.task.Heading;
import seedu.address.model.task.Task;

/**
 * Contains utility methods for populating {@code AppData} with sample data.
 */
public class SampleDataUtil {
    private static Note[] getSampleNotes() {
        return new Note[] {
            new Note(new Title("Magnus Carlsen"), new Content("World Chess Champion as of 2012")),
            new Note(new Title("Russell's paradox"), new Content("arises in naive set theory")),
            new Note(new Title("An AVL tree"), new Content("Self-balancing binary search tree. Needs rotations "
                    + "when adding or deleting. Suitable for the lazy variant of Dijkstra's algorithm"))
        };
    }

    private static Question[] getSampleQuestions() {
        return new Question[] {
            new Question(new QuestionBody("Which algorithm can be used to find an item in a sorted list?"),
                    new Answer("Binary search"), new Subject("CS2040"), new Difficulty("Easy")),
            new Question(new QuestionBody("What programming paradigm is Java?"),
                    new Answer("Object-oriented"), new Subject("CS2040"), new Difficulty("Easy")),
            new Question(new QuestionBody("What keyword is used to declare exception-handling code in Java?"),
                    new Answer("catch"), new Subject("CS2030"), new Difficulty("Easy")),
            new Question(new QuestionBody("What is the diamond operator used for?"),
                    new Answer("Generics"), new Subject("CS2030"), new Difficulty("Medium")),
            new Question(new QuestionBody("Timsort originated in what language?"),
                    new Answer("Python"), new Subject("CS2040"), new Difficulty("Medium")),
            new Question(new QuestionBody("What are the prerequisites for CS2103/T?"),
                    new Answer("CS2030, CS2040"), new Subject("CS2103"), new Difficulty("Hard"))
        };
    }

    private static Task[] getSampleTasks() {
        return new Task[] {
            new Task(new Heading("An AVL tree"), LocalDate.of(2019, 12, 12),
                    LocalTime.of(17, 0), false),
            new Task(new Heading("Magnus Carlsen"), LocalDate.of(2018, 11, 28),
                    LocalTime.of(12, 30), true),
        };
    }

    public static QuizResult[] getQuizResult() {
        return new QuizResult[] {
            new QuizResult(new Answer("Binary search"),
                    new QuestionBody("Which algorithm can be used to find an item in a sorted list?"),
                    new Subject("CS2040"), new Difficulty("Easy"), "2019/10/24 21:46:31", true),
            new QuizResult(new Answer("Object-oriented"),
                    new QuestionBody("What programming paradigm is Java?"),
                    new Subject("CS2040"), new Difficulty("Easy"), "2019/10/30 21:46:31", true),
            new QuizResult(new Answer("something"),
                    new QuestionBody("What keyword is used to declare exception-handling code in Java?"),
                    new Subject("CS2030"), new Difficulty("Easy"), "2019/10/03 21:46:31", false),
        };
    }

    public static ReadOnlyAppData getSampleAppData() {
        AppData sampleAb = new AppData();
        for (Note sampleNote: getSampleNotes()) {
            sampleAb.addNote(sampleNote);
        }
        for (Question sampleQuestion: getSampleQuestions()) {
            sampleAb.addQuestion(sampleQuestion);
        }
        for (Task sampleTask: getSampleTasks()) {
            sampleAb.addTask(sampleTask);
        }
        for (QuizResult sampleQuizResult : getQuizResult()) {
            sampleAb.addQuizResult(sampleQuizResult);
        }
        return sampleAb;
    }
}
