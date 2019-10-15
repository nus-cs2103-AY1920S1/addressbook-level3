package seedu.address.logic.commands.questioncommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.question.Question;

/**
 * Adds a question to NUStudy.
 */
public class AddQuestionCommand extends Command {
    public static final String COMMAND_WORD = "addq";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a question to NUStudy. "
            + "Parameters: "
            + PREFIX_QUESTION + "QUESTION "
            + PREFIX_ANSWER + "ANSWER "
            + PREFIX_SUBJECT + "SUBJECT "
            + PREFIX_DIFFICULTY + "DIFFICULTY \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_QUESTION + "MSS stands for? "
            + PREFIX_ANSWER + "Main Success Scenario "
            + PREFIX_SUBJECT + "CS2103T "
            + PREFIX_DIFFICULTY + "Easy ";

    public static final String MESSAGE_SUCCESS = "New question added: %1$s";
    public static final String MESSAGE_DUPLICATE_QUESTION = "This question already exists in NUStudy";

    private final Question toAdd;

    /**
     * Creates an AddQuestionCommand to add the specified {@code Question}
     */
    public AddQuestionCommand(Question question) {
        requireNonNull(question);
        toAdd = question;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasQuestion(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_QUESTION);
        }

        model.addQuestion(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddQuestionCommand // instanceof handles nulls
                && toAdd.equals(((AddQuestionCommand) other).toAdd));
    }
}
