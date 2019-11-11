package seedu.address.logic.commands.datamanagement;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.module.Module;
import seedu.address.model.tag.DefaultTagType;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Shows all focus area primary modules.
 */
public class ShowFocusCommand extends ShowCommand {

    public static final String COMMAND_WORD = "showfocus";
    public static final String HELP_MESSAGE = COMMAND_WORD + ": Showing all focus area primary modules";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " : Shows all focus area primary modules. ";

    public static final String MESSAGE_SUCCESS = "All focus area primary modules are shown.";

    public ShowFocusCommand() {
        this.messageSuccess = MESSAGE_SUCCESS;
    }

    @Override
    protected boolean checkModule(Module currentModule) {
        UniqueTagList tags = currentModule.getTags();
        List<String> matchTagStrings = Arrays.asList(DefaultTagType.class.getEnumConstants())
                .stream()
                .map(x -> x.getDefaultTagTypeName())
                .filter(x -> x.endsWith(":P"))
                .collect(Collectors.toList());
        for (Tag tag : tags) {
            for (String matchTagString : matchTagStrings) {
                boolean match = (tag.getTagName().compareToIgnoreCase(matchTagString) == 0);
                if (match) {
                    return true;
                }
            }
        }
        return false;
    }
}
