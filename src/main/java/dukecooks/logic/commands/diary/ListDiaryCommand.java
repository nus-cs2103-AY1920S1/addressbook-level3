package dukecooks.logic.commands.diary;

import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.ListCommand;
import dukecooks.model.Model;

import static java.util.Objects.requireNonNull;

/**
 * Lists all recipes in the Duke Cooks to the user.
 */
public class ListDiaryCommand extends ListCommand {

    public static final String VARIANT_WORD = "diary";

    public static final String MESSAGE_SUCCESS = "Listed all diaries";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredDiaryList(Model.PREDICATE_SHOW_ALL_DIARIES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
