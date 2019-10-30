package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.address.logic.commands.questioncommands.AddQuestionCommand;
import seedu.address.logic.commands.questioncommands.EditQuestionCommand;
import seedu.address.model.question.Question;

/**
 * A utility class for Question.
 */
public class QuestionUtil {

    /**
     * Returns an add command string for adding the {@code question}.
     */
    public static String getAddCommand(Question question) {
        return AddQuestionCommand.COMMAND_WORD + " " + getQuestionDetails(question);
    }

    /**
     * Returns the part of command string for the given {@code question}'s details.
     */
    public static String getQuestionDetails(Question question) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_QUESTION + question.getQuestionBody().body + " ");
        sb.append(PREFIX_ANSWER + question.getAnswer().answer + " ");
        sb.append(PREFIX_SUBJECT + question.getSubject().subject + " ");
        sb.append(PREFIX_DIFFICULTY + question.getDifficulty().difficulty + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditQuestionDescriptor}'s details.
     */
    public static String getEditQuestionDescriptorDetails(EditQuestionCommand.EditQuestionDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getQuestionBody().ifPresent(qb -> sb.append(PREFIX_QUESTION).append(qb.body).append(" "));
        descriptor.getAnswer().ifPresent(ans -> sb.append(PREFIX_ANSWER).append(ans.answer).append(" "));
        descriptor.getSubject().ifPresent(sub -> sb.append(PREFIX_SUBJECT).append(sub.subject).append(" "));
        descriptor.getDifficulty().ifPresent(dif -> sb.append(PREFIX_DIFFICULTY).append(dif.difficulty).append(" "));
        return sb.toString();
    }
}
