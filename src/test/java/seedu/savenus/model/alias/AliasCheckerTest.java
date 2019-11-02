package seedu.savenus.model.alias;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.FindCommand;
import seedu.savenus.logic.commands.SortCommand;

public class AliasCheckerTest {
    @Test
    public void checkDuplicateAliasWord() {
        AliasList aliasList = new AliasList();
        aliasList.changeAliasWord(SortCommand.COMMAND_WORD, "wash");

        assertFalse(AliasChecker.isThereDuplicateAliasWord(aliasList.getList(),
                SortCommand.COMMAND_WORD, ""));
        assertFalse(AliasChecker.isThereDuplicateAliasWord(aliasList.getList(),
                SortCommand.COMMAND_WORD, "wish"));
        assertFalse(AliasChecker.isThereDuplicateAliasWord(aliasList.getList(),
                SortCommand.COMMAND_WORD, "wash"));
        assertTrue(AliasChecker.isThereDuplicateAliasWord(aliasList.getList(),
                FindCommand.COMMAND_WORD, "wash"));
    }

    @Test
    public void checkCommandWords() {
        AliasList aliasList = new AliasList();

        assertTrue(AliasChecker.isCommandWord(aliasList.getList(), SortCommand.COMMAND_WORD));

        assertFalse(AliasChecker.isCommandWord(aliasList.getList(), ""));
        assertFalse(AliasChecker.isCommandWord(aliasList.getList(), "Invalid command word."));
        assertFalse(AliasChecker.isCommandWord(aliasList.getList(), "Bogus"));
    }

    @Test
    public void doesCOmmandWordExistsTests() {
        AliasList aliasList = new AliasList();

        assertTrue(AliasChecker.doesCommandWordExist(aliasList.getList(), SortCommand.COMMAND_WORD));

        assertFalse(AliasChecker.doesCommandWordExist(aliasList.getList(), ""));
        assertFalse(AliasChecker.doesCommandWordExist(aliasList.getList(), "Invalid command word."));
        assertFalse(AliasChecker.doesCommandWordExist(aliasList.getList(), "Bogus"));
    }
}
