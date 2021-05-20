package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static seedu.address.testutil.CommandItemBuilder.DEFAULT_COMMANDTASK;
import static seedu.address.testutil.CommandItemBuilder.DEFAULT_COMMANDWORD;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.address.model.FinSec;
import seedu.address.model.ModelStub;
import seedu.address.model.ReadOnlyFinSec;
import seedu.address.model.commanditem.CommandItem;
import seedu.address.testutil.CommandItemBuilder;

public class CreateShortCutCommandTest {

    @Test
    public void execute_shortCutCreated() {
        ModelStubAcceptingCommandAdded modelStub = new ModelStubAcceptingCommandAdded();
        CommandItem validCommandItem = new CommandItemBuilder().build();

        CreateShortCutCommand command = new CreateShortCutCommand(DEFAULT_COMMANDWORD, DEFAULT_COMMANDTASK);

        CommandResult commandResult = command.execute(modelStub);
        assertEquals(String.format(CreateShortCutCommand.SHOWING_NEW_SHORTCUT_MESSAGE + command.getPrevInput() + " to "
                        + command.getCurrentInput(), validCommandItem),
                commandResult.getFeedbackToUser());

    }

    @Test
    public void equals() {
        CommandItem addShortcut = new CommandItemBuilder().withCommandWord("add")
                .withCommandTask("add_contact").build();
        CommandItem deleteShortcut = new CommandItemBuilder().withCommandWord("delete").build();
        CreateShortCutCommand addShortcutCommand = new CreateShortCutCommand("add_contact",
                "add");
        CreateShortCutCommand addDeleteCommand = new CreateShortCutCommand("delete_contact",
                "delete");

        // same object -> returns true
        assertEquals(addShortcutCommand, addShortcutCommand);

        // same values -> returns true
        CreateShortCutCommand copy = new CreateShortCutCommand("add_contact", "add");
        assertEquals(addShortcutCommand, copy);

        // different types -> returns false
        assertNotEquals(1, addDeleteCommand);

        // null -> returns false
        assertNotEquals(null, addDeleteCommand);

        // different commandItems -> returns false
        assertNotEquals(addShortcut, deleteShortcut);
    }


    /**
     * A Model stub that contains a single shortcut.
     */
    private class ModelStubWithCommandItem extends ModelStub {
        private final CommandItem commandItem;

        ModelStubWithCommandItem(CommandItem commandItem) {
            requireNonNull(commandItem);
            this.commandItem = commandItem;
        }

        @Override
        public boolean hasCommand(CommandItem commandItem) {
            requireNonNull(commandItem);
            return this.commandItem.isSameCommand(commandItem);
        }

    }

    /**
     * A Model stub that always accept the income being added.
     */
    private class ModelStubAcceptingCommandAdded extends ModelStub {
        final ArrayList<CommandItem> commandsAdded = new ArrayList<>();

        @Override
        public boolean hasCommand(CommandItem command) {
            requireNonNull(command);
            return commandsAdded.stream().anyMatch(command::isSameCommand);
        }

        @Override
        public void addCommand(CommandItem commandItem) {
            requireNonNull(commandItem);
            commandsAdded.add(commandItem);
        }

        @Override
        public ReadOnlyFinSec getFinSec() {
            return new FinSec();
        }

    }

}
