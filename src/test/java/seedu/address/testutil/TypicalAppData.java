package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOB;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AppData;
import seedu.address.model.note.Note;
import seedu.address.model.question.Question;
import seedu.address.model.quiz.QuizResult;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskForNote;

/**
 * A utility class containing a list of {@code Note}, {@code Question} and other objects to be used in tests.
 */
public class TypicalAppData {
    public static final Note ALICE = new NoteBuilder().withTitle("Alice Pauline")
            .withContent("123, Jurong West Ave 6, #08-111").build();
    public static final Note BENSON = new NoteBuilder().withTitle("Benson Meier")
            .withContent("311, Clementi Ave 2, #02-25").build();
    public static final Note CARL = new NoteBuilder().withTitle("Carl Kurz")
            .withContent("wall street").build();
    public static final Note DANIEL = new NoteBuilder().withTitle("Daniel Meier")
            .withContent("10th street").build();
    public static final Note ELLE = new NoteBuilder().withTitle("Elle Meyer")
            .withContent("michegan ave").build();
    public static final Note FIONA = new NoteBuilder().withTitle("Fiona Kunz")
            .withContent("little tokyo").build();
    public static final Note GEORGE = new NoteBuilder().withTitle("George Best")
            .withContent("4th street").build();

    public static final Question ALICE_QUESTION = new QuestionBuilder().withQuestionBody("(  ) is a measure of the "
            + "degree of dependence between components, classes, methods, etc.").withAnswer("Coupling")
            .withSubject("CS2103T").withDifficulty("easy").build();

    public static final QuizResult ALICE_RESULT = new QuizResultBuilder().withAnswer("Coupling")
            .withQuestionBody("(  ) is a measure of the degree of dependence between components, classes, methods, "
                    + "etc.").withQuizTime("2019/10/10 12:00").withResult("true").build();

    // Manually added
    public static final Note HOON = new NoteBuilder().withTitle("Hoon Meier")
            .withContent("little india").build();
    public static final Note IDA = new NoteBuilder().withTitle("Ida Mueller")
            .withContent("chicago ave").build();

    // Manually added - Note's details found in {@code CommandTestUtil}
    public static final Note AMY = new NoteBuilder().withTitle(VALID_TITLE_AMY)
            .withContent(VALID_CONTENT_AMY).build();
    public static final Note BOB = new NoteBuilder().withTitle(VALID_TITLE_BOB)
            .withContent(VALID_CONTENT_BOB).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    public static final Note CONAN = new NoteBuilder()
            .withTitle("Edogawa Conan")
            .withContent("There is always only one truth")
            .build();

    public static final Question CONAN_QUESTION = new QuestionBuilder()
            .withQuestionBody("Who is your favourite female character in Detective Conan?")
            .withAnswer("Haibara Ai")
            .withSubject("Anime")
            .withDifficulty("easy")
            .build();

    public static final Task CONAN_TASK_DEFAULT = new TaskBuilder().build();
    public static final Task CONAN_TASK_FOR_NOTE = new TaskBuilder().withNote(CONAN).build();
    public static final Task CONAN_TASK_FOR_QUESTION = new TaskBuilder().withQuestion(CONAN_QUESTION).build();
    public static final Task CONAN_TASK_MODIFIED_DATE = new TaskBuilder().withDate("06/08/2019").build();
    public static final Task CONAN_TASK_MODIFIED_TIME = new TaskBuilder().withTime("1400").build();
    public static final Task CONAN_TASK_DONE = new TaskBuilder().withStatus(true).build();

    private TypicalAppData() {} // prevents instantiation

    /**
     * Returns an {@code AppData} with all the typical data.
     */
    public static AppData getTypicalAppData() {
        AppData ab = new AppData();
        for (Note note : getTypicalNotes()) {
            ab.addNote(note);
        }

        for (Task task : getTypicalTasks()) {
            ab.addTask(task);
        }

        for (Question question : getTypicalQuestions()) {
            ab.addQuestion(question);
        }
        return ab;
    }

    public static List<Note> getTypicalNotes() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<Task> getTypicalTasks() {
        return new ArrayList<>(Arrays.asList(CONAN_TASK_DEFAULT, CONAN_TASK_FOR_NOTE, CONAN_TASK_FOR_QUESTION,
                CONAN_TASK_MODIFIED_DATE, CONAN_TASK_MODIFIED_TIME, CONAN_TASK_DONE));
    }

    public static List<Question> getTypicalQuestions() {
        return new ArrayList<>(Arrays.asList(ALICE_QUESTION));
    }

}
