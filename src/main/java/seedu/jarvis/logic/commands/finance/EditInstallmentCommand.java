package seedu.jarvis.logic.commands.finance;

import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.jarvis.logic.parser.CliSyntax.PREFIX_MONEY;

public class EditInstallmentCommand {

    public static final String COMMAND_WORD = "instal edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_MONEY + "MONEY] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DESCRIPTION + "Netflix subscription "
            + PREFIX_MONEY + "13.50";


}
