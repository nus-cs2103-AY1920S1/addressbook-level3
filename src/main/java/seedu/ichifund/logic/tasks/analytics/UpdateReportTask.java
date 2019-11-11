package seedu.ichifund.logic.tasks.analytics;

import java.util.Optional;

import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.logic.tasks.Task;
import seedu.ichifund.model.Model;

/**
 * Update the generated report.
 */
public class UpdateReportTask extends Task {

    @Override
    public void execute(Model model) {
        Optional<Command> command = model.getCommand();
        if (command.isPresent()) {
            try {
                command.get().execute(model);
            } catch (CommandException ignored) {
                // The command will always be valid as it was executed once before.
            }
        }
    }
}
