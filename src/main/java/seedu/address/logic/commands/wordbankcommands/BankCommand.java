package seedu.address.logic.commands.wordbankcommands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_WORD_BANK_NAME;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wordbankstats.WordBankStatistics;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;


/**
 * Selects a word bank.
 */
public class BankCommand extends WordBankCommand {

    public static final String COMMAND_WORD = "bank";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " WORDBANK \n"
            + "Eg: " + COMMAND_WORD + " sample";

    private static final String MESSAGE_LIST_ACKNOWLEDGEMENT = "Selected a word bank.\n"
            + "Type start to start the game\n"
            + "Or type open to edit word bank.";

    private String name;

    public BankCommand(String name) {
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.hasWordBank(name)) {
            throw new CommandException(MESSAGE_INVALID_WORD_BANK_NAME);
        }
        model.setCurrentWordBank(model.getWordBankFromName(name));

        WordBankStatisticsList wbStatsList = model.getWordBankStatisticsList();
        WordBankStatistics wbStats = wbStatsList.getWordBankStatistics(name);
        if (wbStats == null) {
            WordBankStatistics newWbStats = WordBankStatistics.getEmpty(name);
            model.setWordBankStatistics(newWbStats);
            wbStatsList.addWordBanksStatistics(newWbStats);
        } else {
            model.setWordBankStatistics(wbStats);
        }

        return new CommandResult(MESSAGE_LIST_ACKNOWLEDGEMENT, false, false);
    }
}
