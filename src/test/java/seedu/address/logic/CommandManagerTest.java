package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_ADD_EVENT_DUPLICATE;
import static seedu.address.commons.core.Messages.MESSAGE_ADD_EVENT_SUCCESS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddEventCommand;

import seedu.address.model.ModelManager;

class CommandManagerTest {

    @Test
    void onCommandInput_addCommand_success() {
        CommandManager manager = new CommandManager();

        UserOutputListenerStub listener = new UserOutputListenerStub();
        manager.addUserOutputListener(listener);

        // Unknown command
        manager.onCommandInput("add_event test '11/11/1111 11:11'");
        assertEquals(MESSAGE_UNKNOWN_COMMAND, listener.getUserOutput().toString());

        // Register command
        manager.addCommand("add_event", () -> AddEventCommand.newBuilder(new ModelManager()));

        manager.onCommandInput("add_event test '11/11/1111 11:11'");
        assertEquals(String.format(MESSAGE_ADD_EVENT_SUCCESS, "test"), listener.getUserOutput().toString());
    }

    @Test
    void onCommandInput_updateUserOutputListeners_success() {
        ModelManager model = new ModelManager();
        CommandManager manager = new CommandManager();

        UserOutputListenerStub listener1 = new UserOutputListenerStub();
        UserOutputListenerStub listener2 = new UserOutputListenerStub();
        UserOutputListenerStub listener3 = new UserOutputListenerStub();

        manager.addUserOutputListener(listener1);
        manager.addUserOutputListener(listener2);
        manager.addUserOutputListener(listener3);

        // Update on parse exception
        manager.onCommandInput("");
        assertEquals(MESSAGE_INVALID_COMMAND_FORMAT, listener1.getUserOutput().toString());
        assertEquals(MESSAGE_INVALID_COMMAND_FORMAT, listener2.getUserOutput().toString());
        assertEquals(MESSAGE_INVALID_COMMAND_FORMAT, listener3.getUserOutput().toString());

        manager.addCommand("add_event", () -> AddEventCommand.newBuilder(model));

        // Update on success
        manager.onCommandInput("add_event test '11/11/1111 11:11'");
        assertEquals(String.format(MESSAGE_ADD_EVENT_SUCCESS, "test"), listener1.getUserOutput().toString());

        // Update on command execute exception
        manager.onCommandInput("add_event test '11/11/1111 11:11'");
        assertEquals(MESSAGE_ADD_EVENT_DUPLICATE, listener1.getUserOutput().toString());
    }
}
