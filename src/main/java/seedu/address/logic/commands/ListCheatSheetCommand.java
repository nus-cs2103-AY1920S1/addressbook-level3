package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CHEATSHEETS;

import seedu.address.model.Model;

public class ListCheatSheetCommand extends Command{
    public static final String COMMAND_WORD = "listcs";

    public static final String MESSAGE_SUCCESS = "Listed all cheatsheets";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCheatSheetList(PREDICATE_SHOW_ALL_CHEATSHEETS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
