package seedu.address.logic.commands.switches;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CARDS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ModeEnum;
import seedu.address.logic.commands.SwitchCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wordbanklist.WordBankList;

/**
 * Terminates the program.
 */
public class BankCommand extends SwitchCommand {

    public static final String COMMAND_WORD = "bank";

    public static final String MESSAGE_LIST_ACKNOWLEDGEMENT = "Displaying available wordbanks\n Choose one";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Loads the bank identified by the index number.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private static String name;
//    private final Index targetIndex;
//
//    public BankCommand(Index targetIndex) {
//        this.targetIndex = targetIndex;
//    }

    public BankCommand(String name) {
        this.name = name;
    }

    @Override
    public ModeEnum check(Model model, ModeEnum mode) throws CommandException {
        if (mode != ModeEnum.LOAD) {
            throw new CommandException("Load word bank first!");
        }
        return ModeEnum.APP;
    }

    @Override
    public CommandResult execute(Model model) {
        WordBankList temp = model.getWordBankList();
        System.out.println("~~~~~~~" +this.name);
        model.setWordBank(temp.getWordBank(this.name));
        return new CommandResult(MESSAGE_LIST_ACKNOWLEDGEMENT , false, false);
    }

}
