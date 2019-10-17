package seedu.jarvis.testutil.history;

import static seedu.jarvis.testutil.address.TypicalPersons.ALICE;
import static seedu.jarvis.testutil.address.TypicalPersons.BENSON;
import static seedu.jarvis.testutil.address.TypicalPersons.CARL;
import static seedu.jarvis.testutil.address.TypicalPersons.DANIEL;
import static seedu.jarvis.testutil.address.TypicalPersons.ELLE;
import static seedu.jarvis.testutil.address.TypicalPersons.FIONA;
import static seedu.jarvis.testutil.address.TypicalPersons.GEORGE;

import java.util.Arrays;
import java.util.List;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.address.AddAddressCommand;
import seedu.jarvis.logic.commands.address.ClearAddressCommand;
import seedu.jarvis.model.history.HistoryManager;

/**
 * A utility class containing {@code Command} objects to be used in tests.
 */
public class TypicalCommands {
    // executed commands to add typical persons to the application.
    public static final Command ADD_ALICE = new AddAddressCommand(ALICE);
    public static final Command ADD_BENSON = new AddAddressCommand(BENSON);
    public static final Command ADD_CARL = new AddAddressCommand(CARL);
    public static final Command ADD_DANIEL = new AddAddressCommand(DANIEL);
    public static final Command ADD_ELLE = new AddAddressCommand(ELLE);
    public static final Command ADD_FIONA = new AddAddressCommand(FIONA);
    public static final Command ADD_GEORGE = new AddAddressCommand(GEORGE);

    // inversely executed command to clear all typical persons to the application.
    public static final Command CLEAR_ALL = new ClearAddressCommand(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE,
            FIONA, GEORGE));

    private TypicalCommands() {} // prevents instantiation

    /**
     * Gets a typical {@code HistoryManager} which remembers the executed commands that populates the application
     * with typical persons and remembers a inversely executed command that clears all typical persons from the
     * application.
     *
     * @return Typical {@code HistoryManager}.
     */
    public static HistoryManager getTypicalHistoryManager() {
        HistoryManager historyManager = new HistoryManager();
        getTypicalExecutedCommands().forEach(historyManager::rememberExecutedCommand);
        getTypicalInverselyExecutedCommands().forEach(historyManager::rememberInverselyExecutedCommand);
        return historyManager;
    }

    /**
     * Gets a {@code List} of {@code Command} objects that will add the typical persons to the application.
     *
     * @return {@code List} of {@code Command} objects.
     */
    public static List<Command> getTypicalExecutedCommands() {
        return Arrays.asList(ADD_ALICE, ADD_BENSON, ADD_CARL, ADD_DANIEL, ADD_ELLE, ADD_FIONA, ADD_GEORGE);
    }

    /**
     * Gets a {@code List} of {@code Command} objects that will clear all typical persons from the application.
     *
     * @return {@code List} of {@code Command} objects.
     */
    public static List<Command> getTypicalInverselyExecutedCommands() {
        return Arrays.asList(CLEAR_ALL);
    }

}
