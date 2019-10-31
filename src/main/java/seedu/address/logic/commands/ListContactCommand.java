package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.result.CommandResult;
import seedu.address.logic.commands.result.ResultInformation;
import seedu.address.logic.commands.result.UiFocus;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;

/**
 * Lists all contacts in the planner.
 */
public class ListContactCommand extends ListCommand {

    public static final String SECOND_COMMAND_WORD = "contact";

    public static final String MESSAGE_SUCCESS = "Listed all contacts";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Contact> lastShownList = model.getFilteredContactList();
        int contactListSize = lastShownList.size();
        ResultInformation[] resultInformation = new ResultInformation[contactListSize];
        for (int i = 0; i < contactListSize; i++) {
            resultInformation[i] = new ResultInformation(lastShownList.get(i), Index.fromZeroBased(i));
        }

        return new CommandResult(MESSAGE_SUCCESS, resultInformation, new UiFocus[]{UiFocus.CONTACT, UiFocus.INFO});
    }
}
