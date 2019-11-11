package seedu.revision.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.revision.logic.commands.quiz.SaqInputCommand;
import seedu.revision.model.RevisionTool;
import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.testutil.builder.SaqBuilder;

/**
 * A utility class containing a list of {@code Answerable} objects to be used in tests.
 */
public class TypicalSaqs {
    public static final Answer SAQ_CORRECT_ANSWER_A = new Answer("Unit Testing");
    public static final Answer SAQ_CORRECT_ANSWER_B = new Answer("Integration Testing");
    public static final Answer SAQ_CORRECT_ANSWER_C = new Answer("System Testing");
    public static final Answer SAQ_CORRECT_ANSWER_D = new Answer("Interface Testing");
    public static final Answer SAQ_CORRECT_ANSWER_E = new Answer("Acceptance Testing");
    public static final Answer SAQ_CORRECT_ANSWER_F = new Answer("UML Diagram");


    public static final ArrayList<Answer> SAQ_CORRECT_ANSWER_LIST_A = new ArrayList<>(
            Arrays.asList(SAQ_CORRECT_ANSWER_A, SAQ_CORRECT_ANSWER_B,
                    SAQ_CORRECT_ANSWER_C, SAQ_CORRECT_ANSWER_D, SAQ_CORRECT_ANSWER_E));
    public static final ArrayList<Answer> SAQ_CORRECT_ANSWER_LIST_B = new ArrayList<>(
            Arrays.asList(SAQ_CORRECT_ANSWER_F));



    public static final Answerable SAQ_A = new SaqBuilder()
            .withQuestion("Name one functional testing used in software engineering.")
            .withDifficulty("2")
            .withCorrectAnswerList(SAQ_CORRECT_ANSWER_LIST_A)
            .build();

    public static final Answerable SAQ_B = new SaqBuilder()
            .withQuestion("What diagram is used to represent a software system?")
            .withCorrectAnswerList(SAQ_CORRECT_ANSWER_LIST_B)
            .withDifficulty("1").withCategories("diagrams").build();

    public static final SaqInputCommand SAQ_B_COMMAND = new SaqInputCommand("UML Diagram", SAQ_B);

    // prevents instantiation
    private TypicalSaqs() {}

    /**
     * Returns an {@code RevisionTool} with all the typical answerables.
     */
    public static RevisionTool getSaqRevisionTool() {
        RevisionTool saqRevisionTool = new RevisionTool();
        for (Answerable answerable : getTypicalSaq()) {
            saqRevisionTool.addAnswerable(answerable);
        }
        return saqRevisionTool;
    }

    public static List<Answerable> getTypicalSaq() {
        return new ArrayList<>(Arrays.asList(SAQ_A, SAQ_B));
    }
}
