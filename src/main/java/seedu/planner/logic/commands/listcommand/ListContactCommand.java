package seedu.planner.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.autocomplete.CommandInformation;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.ResultInformation;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.model.Model;
import seedu.planner.model.contact.Contact;

/**
 * Lists all contacts in the planner.
 * @author 1nefootstep
 */
public class ListContactCommand extends ListCommand {

    public static final String SECOND_COMMAND_WORD = "contact";

    public static final String MESSAGE_SUCCESS = "Listed all contacts";

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(COMMAND_WORD + " "
            + SECOND_COMMAND_WORD);

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
