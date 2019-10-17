package seedu.address.logic.commands.findcommand;

import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.PrefixType;

/**
 * Abstract class for the find commands.
 */
public abstract class FindCommand extends Command {
    public abstract CommandResult execute(Model model);

    /**
     * Lists the results of the found values.
     *
     * @param results
     * @param type
     */
    public void listResults(List<? extends Entity> results, PrefixType type) {
        String header;
        switch(type) {
        case P:
            header = "Found Participants";
            break;
        case T:
            header = "Found Teams";
            break;
        case M:
            header = "Found Mentors";
            break;
        default:
            header = "";
        }

        header += String.format(" (%d results found)", results.size());
        System.out.println(header);
        for (int i = 0; i < results.size(); i++) {
            Entity result = results.get(i);
            System.out.println(
                    String.format("%d. Name: %s, ID: %s", i, result.getName(), result.getId()));
        }
    }
}
