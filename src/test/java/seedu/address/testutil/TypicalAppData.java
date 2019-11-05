package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.CORRECT_ANSWER_CONCEPT;
import static seedu.address.logic.commands.CommandTestUtil.CORRECT_RESULT;
import static seedu.address.logic.commands.CommandTestUtil.FINISH_TIME_CONCEPT;
import static seedu.address.logic.commands.CommandTestUtil.INCORRECT_ANSWER_CONCEPT;
import static seedu.address.logic.commands.CommandTestUtil.INCORRECT_RESULT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_ALGEBRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ANSWER_CONCEPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CONTENT_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_ALGEBRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DIFFICULTY_CONCEPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_BODY_ALGEBRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_QUESTION_BODY_CONCEPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_ALGEBRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_CONCEPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AppData;
import seedu.address.model.note.Note;
import seedu.address.model.question.Question;
import seedu.address.model.quiz.QuizResult;
import seedu.address.model.task.Task;

/**
 * A utility class containing a list of {@code Note}, {@code Question} and other objects to be used in tests.
 */
public class TypicalAppData {
    // notes
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

    // questions
    public static final Question MVC_QUESTION = new QuestionBuilder()
            .withQuestionBody("MVC design pattern refers to (  ).")
            .withAnswer("Model View Controller")
            .withSubject("CS2103T")
            .withDifficulty("medium")
            .build();
    public static final Question TCP_QUESTION = new QuestionBuilder()
            .withQuestionBody("A port number in TCP is (  ) bytes long.")
            .withAnswer("2")
            .withSubject("CS2105")
            .withDifficulty("hard")
            .build();
    public static final Question UDP_QUESTION = new QuestionBuilder()
            .withQuestionBody("UDP uses (  ) to dispatch incoming packets to different processes in the same host.")
            .withAnswer("de-multiplexing")
            .withSubject("CS2105")
            .withDifficulty("hard")
            .build();
    public static final Question CAMERA_QUESTION = new QuestionBuilder()
            .withQuestionBody("The camera is positioned in the ()")
            .withAnswer("world coordinates")
            .withSubject("CS3241")
            .withDifficulty("easy")
            .build();

    // quiz results
    public static final QuizResult MVC_RESULT = new QuizResultBuilder()
            .withAnswer("Model View Controller")
            .withQuestionBody("MVC design pattern refers to (  ).")
            .withSubject("CS2103T")
            .withDifficulty("medium")
            .withQuizTime("2019/10/10 12:00")
            .withResult("true")
            .build();
    public static final QuizResult TCP_RESULT = new QuizResultBuilder()
            .withAnswer("4")
            .withQuestionBody("A port number in TCP is (  ) bytes long.")
            .withSubject("CS2105")
            .withDifficulty("hard")
            .withQuizTime("2019/10/20 13:00")
            .withResult("false")
            .build();
    public static final QuizResult UDP_RESULT = new QuizResultBuilder()
            .withAnswer("de-multiplexing")
            .withQuestionBody("UDP uses (  ) to dispatch incoming packets to different processes in the same host.")
            .withSubject("CS2105")
            .withDifficulty("hard")
            .withQuizTime("2019/10/30 1530")
            .withResult("true")
            .build();

    // Manually added notes
    public static final Note HOON = new NoteBuilder().withTitle("Hoon Meier")
            .withContent("little india").build();
    public static final Note IDA = new NoteBuilder().withTitle("Ida Mueller")
            .withContent("chicago ave").build();

    // Manually added - Note's details found in {@code CommandTestUtil}
    public static final Note AMY = new NoteBuilder().withTitle(VALID_TITLE_AMY)
            .withContent(VALID_CONTENT_AMY).build();
    public static final Note BOB = new NoteBuilder().withTitle(VALID_TITLE_BOB)
            .withContent(VALID_CONTENT_BOB).build();

    // Manually added - Question's details found in {@code CommandTestUtil}
    public static final Question CONCEPT_QUESTION = new QuestionBuilder()
            .withQuestionBody(VALID_QUESTION_BODY_CONCEPT)
            .withAnswer(VALID_ANSWER_CONCEPT)
            .withSubject(VALID_SUBJECT_CONCEPT)
            .withDifficulty(VALID_DIFFICULTY_CONCEPT)
            .build();
    public static final Question ALGEBRA_QUESTION = new QuestionBuilder()
            .withQuestionBody(VALID_QUESTION_BODY_ALGEBRA)
            .withAnswer(VALID_ANSWER_ALGEBRA)
            .withSubject(VALID_SUBJECT_ALGEBRA)
            .withDifficulty(VALID_DIFFICULTY_ALGEBRA)
            .build();

    // Manually added - Quiz result's details found in {@code CommandTestUtil}
    public static final QuizResult CORRECT_CONCEPT_RESULT = new QuizResultBuilder()
            .withAnswer(CORRECT_ANSWER_CONCEPT)
            .withQuestionBody(VALID_QUESTION_BODY_CONCEPT)
            .withSubject(VALID_SUBJECT_CONCEPT)
            .withDifficulty(VALID_DIFFICULTY_CONCEPT)
            .withQuizTime(FINISH_TIME_CONCEPT)
            .withResult(CORRECT_RESULT)
            .build();
    public static final QuizResult INCORRECT_CONCEPT_RESULT = new QuizResultBuilder()
            .withAnswer(INCORRECT_ANSWER_CONCEPT)
            .withQuestionBody(VALID_QUESTION_BODY_CONCEPT)
            .withSubject(VALID_SUBJECT_CONCEPT)
            .withDifficulty(VALID_DIFFICULTY_CONCEPT)
            .withQuizTime(FINISH_TIME_CONCEPT)
            .withResult(INCORRECT_RESULT)
            .build();

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

    public static final QuizResult CONAN_RESULT = new QuizResultBuilder()
            .withAnswer("Haibara Ai")
            .withQuestionBody("Who is your favourite female character in Detective Conan?")
            .withSubject("Anime")
            .withDifficulty("easy")
            .withQuizTime("2019/10/29 2000")
            .withResult("true")
            .build();

    public static final Task CONAN_TASK_DEFAULT = new TaskBuilder().build();
    public static final Task CONAN_TASK_FOR_NOTE = new TaskBuilder().withNote(CONAN).build();
    public static final Task CONAN_TASK_FOR_QUESTION = new TaskBuilder().withQuestion(CONAN_QUESTION).build();
    public static final Task CONAN_TASK_MODIFIED_DATE = new TaskBuilder()
            .withNote(CONAN).withDate("06/08/2019").build();
    public static final Task CONAN_TASK_MODIFIED_TIME = new TaskBuilder().withNote(CONAN).withTime("1400").build();
    public static final Task CONAN_TASK_DONE = new TaskBuilder()
            .withNote(CONAN).withDate("01/10/2019").withStatus(true).build();

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

        for (QuizResult quizResult : getTypicalQuizResults()) {
            ab.addQuizResult(quizResult);
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
        return new ArrayList<>(Arrays.asList(MVC_QUESTION, TCP_QUESTION, UDP_QUESTION, CONAN_QUESTION,
                CAMERA_QUESTION));
    }

    public static List<QuizResult> getTypicalQuizResults() {
        return new ArrayList<>(Arrays.asList(MVC_RESULT, TCP_RESULT, UDP_RESULT, CONAN_RESULT));
    }

}
