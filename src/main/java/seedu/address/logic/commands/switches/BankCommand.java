package seedu.address.logic.commands.switches;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.util.ModeEnum;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.ModeSwitchException;
import seedu.address.model.Model;
import seedu.address.model.wordbanklist.WordBankList;
import seedu.address.model.wordbankstatslist.WordBankStatisticsList;
import seedu.address.statistics.WordBankStatistics;

/**
 * Selects Bank and swtiches to APP mode if successful
 */
public class BankCommand extends SwitchCommand {

    public static final String COMMAND_WORD = "bank";

    public static final String MESSAGE_LIST_ACKNOWLEDGEMENT = "Displaying available wordbanks\n Choose one";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Loads the bank identified by the name.\n"
            + "Parameters: NAME\n"
            + "Example: " + COMMAND_WORD + " addressbook";

    private final String name;

    public BankCommand(String name) {
        this.name = name;
    }



    @Override
    public CommandResult execute(Model model) throws CommandException {
        WordBankList temp = model.getWordBankList();
        WordBankStatisticsList wbStatsList = model.getWordBankStatisticsList();
        if (temp.getWordBank(this.name) == null) {
            throw new CommandException("Workbank does not exist");
        }
        model.setWordBank(temp.getWordBank(name));
        WordBankStatistics wbStats = wbStatsList.getWordBankStatistics(name);
        if (wbStats == null) {
            model.setWordBankStatistics(WordBankStatistics.getEmpty(name));
        } else {
            model.setWordBankStatistics(wbStats);
        }
        return new CommandResult(MESSAGE_LIST_ACKNOWLEDGEMENT , false, false);
    }

    public ModeEnum getNewMode(ModeEnum old) throws ModeSwitchException {
        return ModeEnum.APP;
    }

}
