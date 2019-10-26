package seedu.address.logic.commands.findcommand;

import java.util.List;

import seedu.address.commons.util.PrefixUtil;
import seedu.address.logic.commands.Command;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.PrefixType;

/**
 * Abstract class for the find commands.
 */
public abstract class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";
    public static final String MESSAGE_USAGE = ""; // TODO: implement

    public static final String HEADER_MENTOR = "Found Mentors";
    public static final String HEADER_PARTICIPANT = "Found Participants";
    public static final String HEADER_TEAM = "Found Teams";

    /**
     * Lists the results of the found values.
     *
     * @param results
     * @param type
     */
    public void listResults(List<? extends Entity> results, PrefixType type) {
        String header = PrefixUtil.getStringBasedOnPrefixType(type, HEADER_MENTOR, HEADER_PARTICIPANT, HEADER_TEAM);

        header += String.format(" (%d results found)", results.size());
        System.out.println(header);
        for (int i = 0; i < results.size(); i++) {
            Entity result = results.get(i);
            System.out.println(
                    String.format("%d. Name: %s, ID: %s", i, result.getName(), result.getId()));
        }
    }

}
