package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.tarence.commons.core.Messages;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.tutorial.Assignment;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.storage.Storage;

/**
 * Displays the selected assignment.
 */
public class DisplayAssignmentListCommand extends Command {
    public static final String COMMAND_WORD = "displayAssignments";
    public static final String MESSAGE_SUCCESS = "Assignments are displayed!";

    private static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase(), "lista",
        "listassignments"};

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the tutorial assignments identified by the tutorial index.\n"
            + "Parameters:\n"
            + "TUTORIAL_INDEX\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + "1\n"
            + "Synonyms:\n"
            + String.join("\n", COMMAND_SYNONYMS);

    private Index tutorialIndex;

    /**
     * Constructor based on shortcut index format
     */
    public DisplayAssignmentListCommand(Index tutorialIndex) {
        requireNonNull(tutorialIndex);
        this.tutorialIndex = tutorialIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Tutorial> lastShownTutorialList = model.getFilteredTutorialList();
        if (tutorialIndex.getZeroBased() >= lastShownTutorialList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX);
        }
        Tutorial tutorial = lastShownTutorialList.get(tutorialIndex.getZeroBased());
        List<Assignment> assignments = tutorial.getAssignments();

        return new CommandResult(String.format(MESSAGE_SUCCESS, tutorial), assignments);
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        return execute(model);
    }

    /**
     * Returns true if user command matches command word or any defined synonyms, and false otherwise.
     *
     * @param userCommand command word from user.
     * @return whether user command matches specified command word or synonyms.
     */
    public static boolean isMatchingCommandWord(String userCommand) {
        for (String synonym : COMMAND_SYNONYMS) {
            if (synonym.equals(userCommand.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || ((other instanceof DisplayAssignmentListCommand // instanceof handles nulls
                && (tutorialIndex.equals(((DisplayAssignmentListCommand) other).tutorialIndex)))); // state check
    }
}
