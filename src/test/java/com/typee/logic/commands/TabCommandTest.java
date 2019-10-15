package com.typee.logic.commands;

import static com.typee.logic.commands.CommandTestUtil.assertCommandSuccess;
import static com.typee.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import com.typee.model.Model;
import com.typee.model.ModelManager;
import com.typee.model.UserPrefs;
import com.typee.ui.Tab;

/**
 * Unit Test for {@code TabCommand}
 */
public class TabCommandTest {

    @Test
    public void execute_valid_tabCommand() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        TabCommand tabCommand = new TabCommand(new Tab("Generate Report"));
        assertCommandSuccess(tabCommand, model, TabCommand.MESSAGE_SUCCESS + "Generate Report", model);

        TabCommand tabCommandGame = new TabCommand(new Tab("Typing Game"));
        assertCommandSuccess(tabCommandGame, model,
                TabCommand.MESSAGE_SUCCESS + "Typing Game", model);

        TabCommand tabCommandAppointment = new TabCommand(new Tab("Calendar View"));
        assertCommandSuccess(tabCommandAppointment, model,
                TabCommand.MESSAGE_SUCCESS + "Calendar View", model);

        TabCommand tabCommandCalendar = new TabCommand(new Tab("Add Engagement"));
        assertCommandSuccess(tabCommandCalendar, model,
                TabCommand.MESSAGE_SUCCESS + "Add Engagement", model);
    }
}
