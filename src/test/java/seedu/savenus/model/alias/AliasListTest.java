package seedu.savenus.model.alias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.savenus.logic.commands.FindCommand;
import seedu.savenus.logic.commands.SortCommand;

public class AliasListTest {

    private AliasList aliasList;

    @BeforeEach
    public void setUp() {
        aliasList = new AliasList();
    }

    @Test
    public void setUpTests() {
        aliasList.setAliasPairList(aliasList.getList());
        assertEquals(new AliasList(), aliasList);
    }

    @Test
    public void getCommandWordTests() {
        String aliasWord = "xdxd";
        String commandWord = SortCommand.COMMAND_WORD;
        aliasList.changeAliasWord(commandWord, aliasWord);

        assertEquals(commandWord, aliasList.getCommandWord(aliasWord));
        assertEquals(commandWord, aliasList.getCommandWord(commandWord));
    }

    @Test
    public void duplicateAliasWordTests() {
        String aliasWord = "xdxd";
        String commandWord = SortCommand.COMMAND_WORD;
        aliasList.changeAliasWord(commandWord, aliasWord);

        assertTrue(aliasList.hasAliasWord(FindCommand.COMMAND_WORD, aliasWord));
        assertFalse(aliasList.hasAliasWord(FindCommand.COMMAND_WORD, "Empty"));
    }

    @Test
    public void commandWordTests() {
        String commandWord = SortCommand.COMMAND_WORD;

        assertTrue(aliasList.isCommandWord(commandWord));
        assertTrue(aliasList.doesCommandWordExist(commandWord));
    }

    @Test
    public void changeAliasWordTests() {
        String aliasWord = "xdxd";
        String commandWord = SortCommand.COMMAND_WORD;
        aliasList.changeAliasWord(commandWord, aliasWord);

        AliasList other = new AliasList();
        other.changeAliasWord(commandWord, aliasWord);

        assertEquals(aliasList, other);
    }

    @Test
    public void equalsTests() {
        assertFalse(aliasList.equals(new Object()));

        assertTrue(aliasList.equals(aliasList));
        assertTrue(aliasList.equals(new AliasList()));
    }
}
