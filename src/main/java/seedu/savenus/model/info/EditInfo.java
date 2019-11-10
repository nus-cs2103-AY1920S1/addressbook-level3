package seedu.savenus.model.info;

import seedu.savenus.logic.commands.EditCommand;

//@@author robytanama
/**
 * Contains information on Edit command.
 */
public class EditInfo {

    public static final String COMMAND_WORD = EditCommand.COMMAND_WORD;

    public static final String INFORMATION = "Edit command allows you to modify a particular "
            + "Food item's information.\n\n"
            + "The edited Food item will depend on the following factors:\n"
            + "Index (Compulsory)\n"
            + "Name\n"
            + "Price\n"
            + "Category\n"
            + "Location\n"
            + "Multiple tags\n"
            + "Opening hours\n"
            + "Restrictions\n\n";

    public static final String USAGE = "edit 3 n/Indomie t/Instant";

    public static final String OUTPUT = "Food item 3 will become Indomie with the tag Instant.";
}
