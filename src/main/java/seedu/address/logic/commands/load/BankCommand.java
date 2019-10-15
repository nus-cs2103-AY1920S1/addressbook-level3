package seedu.address.logic.commands.load;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CARDS;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.LoadCommand;
import seedu.address.model.Model;
import seedu.address.model.wordbanklist.WordBankList;

/**
 * Terminates the program.
 */
public class BankCommand extends LoadCommand {

    public static final String COMMAND_WORD = "banks";

    public static final String MESSAGE_LIST_ACKNOWLEDGEMENT = "Displaying available wordbanks\n Choose 1";

    @Override
    public CommandResult execute(Model model) {
        WordBankList temp = new WordBankList();
        model.updateFilteredCardList(PREDICATE_SHOW_ALL_CARDS);
        return new CommandResult(MESSAGE_LIST_ACKNOWLEDGEMENT + temp.size(), false, false);
    }

}
