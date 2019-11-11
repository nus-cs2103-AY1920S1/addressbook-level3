package seedu.address.logic.commands.datamanagement;

import seedu.address.model.module.Module;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Shows all core modules.
 */
public class ShowCoreCommand extends ShowCommand {

    public static final String COMMAND_WORD = "showcore";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Showing all core modules";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Shows all core modules. ";

    public static final String MESSAGE_SUCCESS = "All core modules are shown.";

    public ShowCoreCommand() {
        this.messageSuccess = MESSAGE_SUCCESS;
    }

    @Override
    protected boolean checkModule(Module currentModule) {
        UniqueTagList tags = currentModule.getTags();
        for (Tag tag : tags) {
            boolean match = (tag.getTagName().compareToIgnoreCase("core") == 0);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
