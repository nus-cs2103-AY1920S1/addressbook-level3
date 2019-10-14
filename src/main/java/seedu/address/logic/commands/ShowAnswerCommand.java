package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

//@@author keiteo
/**
 * Shows the answer to a flashcard during the test.
 */
public class ShowAnswerCommand extends Command {

    public static final String COMMAND_WORD = "ans";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String answer = model.getTestAnswer();
        return new CommandResult(answer);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand); // instanceof handles nulls
    }

    @Override
    public String toString() {
        return "placeholder";
    }

}
