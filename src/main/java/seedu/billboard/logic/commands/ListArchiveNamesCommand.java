package seedu.billboard.logic.commands;

import seedu.billboard.logic.commands.exceptions.CommandException;
import seedu.billboard.model.Model;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Lists all existing archive names to the user.
 */
public class ListArchiveNamesCommand extends Command{

    public static final String COMMAND_WORD = "list-arc-names";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<String> archiveNames = model.getArchiveNames();
        String feedback;
        if (archiveNames.size() == 0) {
            feedback = "There are no existing archives";
        } else {
            feedback = "Here are the existing archive(s):\n";
            for(int i = 0; i < archiveNames.size(); i++) {
                feedback += "[" + archiveNames.get(i) + "]";
                if (i != archiveNames.size() - 1) {
                    feedback += ",\n";
                }
            }
        }
        return new CommandResult(feedback);
    }
}
