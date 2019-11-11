package seedu.address.model.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalTutorAid.ADDER;
import static seedu.address.testutil.TypicalTutorAid.DELETER;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.commands.commandsexceptions.CommandNotFoundException;
import seedu.address.model.commands.commandsexceptions.DuplicateCommandException;
import seedu.address.testutil.CommandObjectBuilder;

public class UniqueCommandsListTest {

    private static final String VALID_ACTION_MULTIPLY = "multiply";
    private final UniqueCommandsList uniqueCommandsList = new UniqueCommandsList();

    @Test
    public void contains_nullCommand_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCommandsList.contains(null));
    }

    @Test
    public void contains_commandNotInList_returnsFalse() {
        assertFalse(uniqueCommandsList.contains(ADDER));
    }

    @Test
    public void contains_commandInList_returnsTrue() {
        uniqueCommandsList.add(DELETER);
        assertTrue(uniqueCommandsList.contains(DELETER));
    }

    @Test
    public void add_nullCommand_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCommandsList.add(null));
    }

    @Test
    public void add_duplicateCommand_throwsDuplicateCommandException() {
        uniqueCommandsList.add(ADDER);
        assertThrows(DuplicateCommandException.class, () -> uniqueCommandsList.add(ADDER));
    }

    @Test
    public void setCommand_nullTargetCommand_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCommandsList.setCommands(null, ADDER));
    }

    @Test
    public void setCommand_nullEditedCommand_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCommandsList.setCommands(ADDER, null));
    }

    @Test
    public void setCommand_targetCommandNotInList_throwsCommandNotFoundException() {
        assertThrows(CommandNotFoundException.class, () -> uniqueCommandsList.setCommands(ADDER, DELETER));
    }

    @Test
    public void setCommand_editedCommandIsSameCommand_success() {
        uniqueCommandsList.add(DELETER);
        uniqueCommandsList.setCommands(DELETER, DELETER);
        UniqueCommandsList expectedUniqueCommandsList = new UniqueCommandsList();
        expectedUniqueCommandsList.add(DELETER);
        assertEquals(expectedUniqueCommandsList, uniqueCommandsList);
    }

    @Test
    public void setCommand_editedCommandHasSameIdentity_success() {
        uniqueCommandsList.add(ADDER);
        CommandObject editedAdder = new CommandObjectBuilder(ADDER).withCommandAction(VALID_ACTION_MULTIPLY)
                .build();
        uniqueCommandsList.setCommands(ADDER, editedAdder);
        UniqueCommandsList expectedUniqueCommandsList = new UniqueCommandsList();
        expectedUniqueCommandsList.add(editedAdder);
        assertEquals(expectedUniqueCommandsList, uniqueCommandsList);
    }

    @Test
    public void setCommand_editedCommandHasDifferentIdentity_success() {
        uniqueCommandsList.add(ADDER);
        uniqueCommandsList.setCommands(ADDER, DELETER);
        UniqueCommandsList expectedUniqueCommandsList = new UniqueCommandsList();
        expectedUniqueCommandsList.add(DELETER);
        assertEquals(expectedUniqueCommandsList, uniqueCommandsList);
    }

    @Test
    public void setCommand_editedCommandHasNonUniqueIdentity_throwsDuplicateCommandException() {
        uniqueCommandsList.add(DELETER);
        uniqueCommandsList.add(ADDER);
        assertThrows(DuplicateCommandException.class, () -> uniqueCommandsList.setCommands(ADDER, DELETER));
    }

    @Test
    public void remove_nullCommand_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCommandsList.remove(null));
    }

    @Test
    public void remove_commandDoesNotExist_throwsCommandNotFoundException() {
        assertThrows(CommandNotFoundException.class, () -> uniqueCommandsList.remove(ADDER));
    }

    @Test
    public void remove_existingCommand_removesCommand() {
        uniqueCommandsList.add(ADDER);
        uniqueCommandsList.remove(ADDER);
        UniqueCommandsList expectedUniqueCommandsList = new UniqueCommandsList();
        assertEquals(expectedUniqueCommandsList, uniqueCommandsList);
    }

    @Test
    public void setCommand_nullUniqueCommandsList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCommandsList.setCommands((UniqueCommandsList) null));
    }

    @Test
    public void setCommand_uniqueCommandsList_replacesOwnListWithProvidedUniqueCommandsList() {
        uniqueCommandsList.add(ADDER);
        UniqueCommandsList expectedUniqueCommandList = new UniqueCommandsList();
        expectedUniqueCommandList.add(DELETER);
        uniqueCommandsList.setCommands(expectedUniqueCommandList);
        assertEquals(expectedUniqueCommandList, uniqueCommandsList);
    }

    @Test
    public void setCommands_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCommandsList.setCommands((List<CommandObject>) null));
    }

    @Test
    public void setCommand_list_replacesOwnListWithProvidedList() {
        uniqueCommandsList.add(DELETER);
        List<CommandObject> commandList = Collections.singletonList(ADDER);
        uniqueCommandsList.setCommands(commandList);
        UniqueCommandsList expectedUniqueCommandList = new UniqueCommandsList();
        expectedUniqueCommandList.add(ADDER);
        assertEquals(expectedUniqueCommandList, uniqueCommandsList);
    }

    @Test
    public void setCommand_listWithDuplicateCommand_throwsDuplicateCommandException() {
        List<CommandObject> listWithDuplicateCommand = Arrays.asList(ADDER, ADDER);
        assertThrows(DuplicateCommandException.class, () -> uniqueCommandsList.setCommands(listWithDuplicateCommand));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> uniqueCommandsList
                .asUnmodifiableObservableList().remove(0));
    }
}
