package seedu.billboard.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.billboard.model.Model;

//@@author waifonglee
/**
 *
 * Displays the list of unique tag names.
 *
 */
public class ListTagCommand extends TagCommand {
    public static final String COMMAND_WORD = "list";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<String> tagNames = model.getTagNames();
        StringBuilder sb = new StringBuilder();
        if (tagNames.size() == 0) {
            sb.append("There is no existing tag");
        } else {
            sb.append("Here are the existing tags(s):\n");
            for (int i = 0; i < tagNames.size(); i++) {
                sb.append("[").append(tagNames.get(i)).append("]");
                if (i != tagNames.size() - 1) {
                    sb.append(",\n");
                }
            }
        }
        return new CommandResult(sb.toString());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListTagCommand); // instanceof handles nulls
    }
}
