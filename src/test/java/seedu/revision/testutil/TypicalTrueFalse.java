package seedu.revision.testutil;


import static seedu.revision.logic.commands.CommandTestUtil.VALID_CATEGORY_GREENFIELD;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_CATEGORY_UML;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_DIFFICULTY_ALPHA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_DIFFICULTY_BETA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_QUESTION_ALPHA;
import static seedu.revision.logic.commands.CommandTestUtil.VALID_QUESTION_BETA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.revision.logic.commands.quiz.McqInputCommand;
import seedu.revision.logic.commands.quiz.TfInputCommand;
import seedu.revision.model.RevisionTool;
import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.testutil.builder.SaqBuilder;
import seedu.revision.testutil.builder.TrueFalseBuilder;

/**
 * A utility class containing a list of {@code Answerable} objects to be used in tests.
 */
public class TypicalTrueFalse {
    public static final Answer TF_ANSWER_TRUE = new Answer("True");
    public static final Answer TF_ANSWER_FALSE = new Answer("False");


    public static final ArrayList<Answer> TF_TRUE_ANSWER_LIST = new ArrayList<>(
            Arrays.asList(TF_ANSWER_TRUE));
    public static final ArrayList<Answer> TF_FALSE_ANSWER_LIST = new ArrayList<>(
            Arrays.asList(TF_ANSWER_FALSE));



    public static final Answerable A_ANSWERABLE = new TrueFalseBuilder().withQuestion(
            "Greenfield projects are harder than brownfield projects")
            .withCorrectAnswerList(TF_FALSE_ANSWER_LIST)
            .withDifficulty("1").withCategories("greenfield", "brownfield", "week 1").build();
    public static final Answerable B_ANSWERABLE = new TrueFalseBuilder()
            .withQuestion("As per the textbook, a log file is like the auto-pilot system of an airplane.?")
            .withCorrectAnswerList(TF_FALSE_ANSWER_LIST)
            .withDifficulty("1").withCategories("logging", "Week 5").build();
    public static final Answerable C_ANSWERABLE = new TrueFalseBuilder()
            .withQuestion("OODMs represents the class structure of the problem domain.")
            .withDifficulty("2")
            .withCorrectAnswerList(TF_TRUE_ANSWER_LIST)
            .build();
    public static final Answerable D_ANSWERABLE = new TrueFalseBuilder()
            .withQuestion("XP can be divided into twelve simple rules.").withDifficulty("3")
            .withCorrectAnswerList(TF_FALSE_ANSWER_LIST)
            .build();

    public static final TfInputCommand A_TF_COMMAND = new TfInputCommand("False", A_ANSWERABLE);


//    public static final Answerable K_ANSWERABLE = new SaqBuilder().build();
//    public static final TfInputCommand K_SAQ_COMMAND = new TfInputCommand(
//            "The statement is untrue", K_ANSWERABLE);


    private TypicalTrueFalse() {} // prevents instantiation

    /**
     * Returns an {@code RevisionTool} with all the typical answerables.
     */
    public static RevisionTool getTypicalRevisionTool() {
        RevisionTool ab = new RevisionTool();
        for (Answerable answerable : getTypicalAnswerables()) {
            ab.addAnswerable(answerable);
        }
        return ab;
    }

    public static List<Answerable> getTypicalAnswerables() {
        return new ArrayList<>(Arrays.asList(A_ANSWERABLE, B_ANSWERABLE, C_ANSWERABLE,
                D_ANSWERABLE));
    }
}
