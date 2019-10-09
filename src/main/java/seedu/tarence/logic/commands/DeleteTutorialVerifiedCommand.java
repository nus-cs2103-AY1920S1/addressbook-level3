package seedu.tarence.logic.commands;

import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * Represents a followup to {@code DeletedTutorialCommand} where the {@code Tutorial} to be deleted has been verified as
 * a valid one in the application.
 */
public class DeleteTutorialVerifiedCommand extends Command {

    private Tutorial tutorialToDelete;

    DeleteTutorialVerifiedCommand(Tutorial tutorialToDelete) {
        this.tutorialToDelete = tutorialToDelete;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.deleteStudentsFromTutorial(tutorialToDelete);
        model.deleteTutorial(tutorialToDelete);
        return new CommandResult(String.format(DeleteTutorialCommand.MESSAGE_DELETE_TUTORIAL_SUCCESS,
                tutorialToDelete));
    }
}
