package seedu.revision.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.revision.logic.commands.quiz.TfInputCommand;
import seedu.revision.model.RevisionTool;
import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;
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



    public static final Answerable TF_A = new TrueFalseBuilder()
            .withQuestion("OODMs represents the class structure of the problem domain.")
            .withDifficulty("2")
            .withCorrectAnswerList(TF_TRUE_ANSWER_LIST)
            .build();
    public static final Answerable TF_B = new TrueFalseBuilder()
            .withQuestion("As per the textbook, a log file is like the auto-pilot system of an airplane.?")
            .withCorrectAnswerList(TF_FALSE_ANSWER_LIST)
            .withDifficulty("1").withCategories("logging", "Week 5").build();
    public static final Answerable TF_C = new TrueFalseBuilder().withQuestion(
            "Greenfield projects are harder than brownfield projects")
            .withCorrectAnswerList(TF_FALSE_ANSWER_LIST)
            .withDifficulty("1").withCategories("greenfield", "brownfield", "week 1").build();
    public static final Answerable TF_D = new TrueFalseBuilder()
            .withQuestion("XP can be divided into twelve simple rules.").withDifficulty("3")
            .withCorrectAnswerList(TF_FALSE_ANSWER_LIST)
            .build();

    public static final TfInputCommand TF_A_COMMAND = new TfInputCommand("True", TF_A);


    private TypicalTrueFalse() {} // prevents instantiation

    /**
     * Returns an {@code RevisionTool} with all the typical answerables.
     */
    public static RevisionTool getTrueFalseRevisionTool() {
        RevisionTool trueFalseRevisionTool = new RevisionTool();
        for (Answerable answerable : getTypicalTrueFalse()) {
            trueFalseRevisionTool.addAnswerable(answerable);
        }
        return trueFalseRevisionTool;
    }

    public static List<Answerable> getTypicalTrueFalse() {
        return new ArrayList<>(Arrays.asList(TF_A, TF_B, TF_C,
                TF_D));
    }
}
