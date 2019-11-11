package seedu.revision.testutil;

import static seedu.revision.logic.commands.CommandTestUtil.VALID_CATEGORY_GREENFIELD;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_CATEGORY_UML;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_DIFFICULTY_ALPHA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BETA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_MCQ_QUESTION_1;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_MCQ_QUESTION_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.revision.logic.commands.quiz.McqInputCommand;
import seedu.revision.model.RevisionTool;
import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;


/**
 * A utility class containing a list of {@code Answerable} objects to be used in tests.
 */
public class TypicalMcqs {
    public static final Answer MCQ_CORRECT_ANSWER_A = new Answer("Brownfield");
    public static final Answer MCQ_CORRECT_ANSWER_B = new Answer("Greyfield");
    public static final Answer MCQ_WRONG_ANSWER_A = new Answer("Greenfield");
    public static final Answer MCQ_WRONG_ANSWER_B = new Answer("Blackfield");
    public static final Answer MCQ_WRONG_ANSWER_C = new Answer("Whitefield");

    public static final ArrayList<Answer> MCQ_VALID_CORRECT_ANSWER_LIST = new ArrayList<>(
            Arrays.asList(MCQ_CORRECT_ANSWER_A));
    public static final ArrayList<Answer> MCQ_INVALID_CORRECT_ANSWER_LIST = new ArrayList<>(
            Arrays.asList(MCQ_CORRECT_ANSWER_A, MCQ_CORRECT_ANSWER_B));
    public static final ArrayList<Answer> MCQ_VALID_WRONG_ANSWER_LIST = new ArrayList<>(
            Arrays.asList(MCQ_WRONG_ANSWER_A, MCQ_WRONG_ANSWER_B, MCQ_WRONG_ANSWER_C));


    // Manually added
    public static final Answerable H_ANSWERABLE = new McqBuilder().withQuestion("Hoon Meier")
            .withDifficulty("1").withCorrectAnswerList(MCQ_VALID_CORRECT_ANSWER_LIST)
            .withWrongAnswerList(MCQ_VALID_WRONG_ANSWER_LIST).build();
    public static final Answerable I_ANSWERABLE = new McqBuilder().withQuestion("Ida Mueller")
            .withDifficulty("1").withCorrectAnswerList(MCQ_VALID_CORRECT_ANSWER_LIST)
            .withWrongAnswerList(MCQ_VALID_WRONG_ANSWER_LIST).build();


    public static final Answerable MCQ_A = new McqBuilder().withQuestion(VALID_MCQ_QUESTION_1)
            .withDifficulty(VALID_DIFFICULTY_ALPHA).withCorrectAnswerList(MCQ_VALID_CORRECT_ANSWER_LIST)
            .withWrongAnswerList(MCQ_VALID_WRONG_ANSWER_LIST).withCategories(VALID_CATEGORY_GREENFIELD).build();

    public static final Answerable MCQ_B = new McqBuilder().withQuestion(VALID_MCQ_QUESTION_2)
            .withDifficulty(VALID_DIFFICULTY_BETA).withCorrectAnswerList(MCQ_VALID_CORRECT_ANSWER_LIST)
            .withWrongAnswerList(MCQ_VALID_WRONG_ANSWER_LIST)
            .withCategories(VALID_CATEGORY_GREENFIELD, VALID_CATEGORY_UML).build();

    public static final Answerable MCQ_C = new McqBuilder().withQuestion("What type of project is AB3?")
            .withCorrectAnswerList(MCQ_VALID_CORRECT_ANSWER_LIST).withWrongAnswerList(MCQ_VALID_WRONG_ANSWER_LIST)
            .withDifficulty("1").withCategories("LSP", "SOLID", "Week 9").build();
    public static final Answerable MCQ_D = new McqBuilder().withQuestion("Brownfield or Greenfield?")
            .withCorrectAnswerList(MCQ_VALID_CORRECT_ANSWER_LIST).withWrongAnswerList(MCQ_VALID_WRONG_ANSWER_LIST)
            .withDifficulty("1").withCategories("field", "introduction").build();
    public static final Answerable MCQ_E = new McqBuilder().withQuestion("Carl Kurz").withDifficulty("1")
            .withCorrectAnswerList(MCQ_VALID_CORRECT_ANSWER_LIST).withWrongAnswerList(MCQ_VALID_WRONG_ANSWER_LIST)
            .build();
    public static final Answerable MCQ_F = new McqBuilder().withQuestion("Elle Meyer").withDifficulty("1")
            .withCorrectAnswerList(MCQ_VALID_CORRECT_ANSWER_LIST).withWrongAnswerList(MCQ_VALID_WRONG_ANSWER_LIST)
            .build();
    public static final Answerable MCQ_G = new McqBuilder().withQuestion("Fiona Kunz").withDifficulty("1")
            .withCorrectAnswerList(MCQ_VALID_CORRECT_ANSWER_LIST).withWrongAnswerList(MCQ_VALID_WRONG_ANSWER_LIST)
            .build();

    public static final McqInputCommand MCQ_A_COMMAND = new McqInputCommand("a", MCQ_A);



    private TypicalMcqs() {} // prevents instantiation

    /**
     * Returns an {@code RevisionTool} with all the typical answerables.
     */
    public static RevisionTool getTypicalMcqs() {
        RevisionTool mcqRevisionTool = new RevisionTool();
        for (Answerable answerable : getTypicalMcqsList()) {
            mcqRevisionTool.addAnswerable(answerable);
        }
        return mcqRevisionTool;
    }

    private static List<Answerable> getTypicalMcqsList() {
        return new ArrayList<>(Arrays.asList(MCQ_C, MCQ_D, MCQ_E,
                MCQ_F, MCQ_G));
    }
}
