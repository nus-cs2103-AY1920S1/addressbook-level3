package seedu.savenus.model.alias;

import java.util.ArrayList;
import java.util.List;

import seedu.savenus.logic.commands.AddCommand;
import seedu.savenus.logic.commands.AliasCommand;
import seedu.savenus.logic.commands.AutoSortCommand;
import seedu.savenus.logic.commands.BudgetCommand;
import seedu.savenus.logic.commands.BuyCommand;
import seedu.savenus.logic.commands.ClearCommand;
import seedu.savenus.logic.commands.CustomSortCommand;
import seedu.savenus.logic.commands.DefaultCommand;
import seedu.savenus.logic.commands.DeleteCommand;
import seedu.savenus.logic.commands.DislikeCommand;
import seedu.savenus.logic.commands.EditCommand;
import seedu.savenus.logic.commands.ExitCommand;
import seedu.savenus.logic.commands.FilterCommand;
import seedu.savenus.logic.commands.FindCommand;
import seedu.savenus.logic.commands.HelpCommand;
import seedu.savenus.logic.commands.HistoryCommand;
import seedu.savenus.logic.commands.InfoCommand;
import seedu.savenus.logic.commands.LikeCommand;
import seedu.savenus.logic.commands.ListCommand;
import seedu.savenus.logic.commands.MakeSortCommand;
import seedu.savenus.logic.commands.RecommendCommand;
import seedu.savenus.logic.commands.RemoveDislikeCommand;
import seedu.savenus.logic.commands.RemoveLikeCommand;
import seedu.savenus.logic.commands.SaveCommand;
import seedu.savenus.logic.commands.ShowCommand;
import seedu.savenus.logic.commands.SortCommand;
import seedu.savenus.logic.commands.ThemeCommand;
import seedu.savenus.logic.commands.TopUpCommand;
import seedu.savenus.logic.commands.ViewSortCommand;
import seedu.savenus.logic.commands.WithdrawCommand;

//@@author seanlowjk
/**
 * Represents an adder which adds all command words in $aveNUS to the list of AliasPairs.
 */
public class CommandWordAdder {

    /**
     * Main method to add all command words to the list of AliasPairs.
     * @return the list of AliasPairs with all $aveNUS command words.
     */
    public List<AliasPair> addCommandWords() {
        String emptyAliasWord = "";
        List<AliasPair> aliasPairList = new ArrayList<>();
        aliasPairList.add(new AliasPair(AddCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(AliasCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(AutoSortCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(BudgetCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(BuyCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(ClearCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(CustomSortCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(DefaultCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(DeleteCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(DislikeCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(EditCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(ExitCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(FilterCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(FindCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(HelpCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(HistoryCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(InfoCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(LikeCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(ListCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(MakeSortCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(RecommendCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(RemoveDislikeCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(RemoveLikeCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(SortCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(SaveCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(ThemeCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(TopUpCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(ViewSortCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(WithdrawCommand.COMMAND_WORD, emptyAliasWord));
        aliasPairList.add(new AliasPair(ShowCommand.COMMAND_WORD, emptyAliasWord));

        return aliasPairList;
    }
}
