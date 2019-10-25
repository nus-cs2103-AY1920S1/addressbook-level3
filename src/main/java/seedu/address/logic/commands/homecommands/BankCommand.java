package seedu.address.logic.commands.homecommands;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.HomeCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wordbanklist.WordBankList;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;
import seedu.address.statistics.WordBankStatistics;

/**
 * Selects a word bank.
 */
public class BankCommand extends HomeCommand {

    public static final String COMMAND_WORD = "bank";

    private final String MESSAGE_LIST_ACKNOWLEDGEMENT = "Selected a word bank.\n"
            + "Type start to start the game\n"
            + "Or type open to edit word bank.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " WORDBANK \n"
            + "Eg: " + COMMAND_WORD + " sample";

    private String name = "";

    public BankCommand(String name) {
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        WordBankList wbl = model.getWordBankList();
        if (wbl.getWordBankFromName(this.name) == null) {
            throw new CommandException("Work bank does not exist");
        }
        model.setWordBank(wbl.getWordBankFromName(name).get());

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
