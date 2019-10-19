package seedu.billboard.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.billboard.model.Model;

/**
 * Lists all existing archive names to the user.
 */
public class ListArchiveNamesCommand extends ArchiveCommand {

    public static final String COMMAND_WORD = "listall";

    public static final String MESSAGE_NO_ARCHIVES = "There are no existing archives";

    public static final String MESSAGE_EXISTING_ARCHIVES = "Here are the existing archive(s):\n";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<String> archiveNames = model.getArchiveNames();
        String feedback;
        if (archiveNames.size() == 0) {
            feedback = MESSAGE_NO_ARCHIVES;
        } else {
            feedback = MESSAGE_EXISTING_ARCHIVES;
            for (int i = 0; i < archiveNames.size(); i++) {
                feedback += "[" + archiveNames.get(i) + "]";
                if (i != archiveNames.size() - 1) {
                    feedback += ",\n";
                }
            }
        }
        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListArchiveNamesCommand); // instanceof handles nulls
    }
}
