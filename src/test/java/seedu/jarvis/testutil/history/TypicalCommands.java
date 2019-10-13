package seedu.jarvis.testutil.history;

import static seedu.jarvis.testutil.address.TypicalPersons.AMY;
import static seedu.jarvis.testutil.address.TypicalPersons.BENSON;
import static seedu.jarvis.testutil.address.TypicalPersons.CARL;
import static seedu.jarvis.testutil.address.TypicalPersons.DANIEL;
import static seedu.jarvis.testutil.address.TypicalPersons.ELLE;
import static seedu.jarvis.testutil.address.TypicalPersons.FIONA;
import static seedu.jarvis.testutil.address.TypicalPersons.GEORGE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.address.AddAddressCommand;
import seedu.jarvis.model.history.HistoryManager;

/**
 * A utility class containing {@code Command} objects to be used in tests.
 */
public class TypicalCommands {
    public static final Command ADD_AMY = new AddAddressCommand(AMY);
    public static final Command ADD_BENSON = new AddAddressCommand(BENSON);
    public static final Command ADD_CARL = new AddAddressCommand(CARL);
    public static final Command ADD_DANIEL = new AddAddressCommand(DANIEL);
    public static final Command ADD_ELLE = new AddAddressCommand(ELLE);
    public static final Command ADD_FIONA = new AddAddressCommand(FIONA);
    public static final Command ADD_GEORGE = new AddAddressCommand(GEORGE);

    private TypicalCommands() {} // prevents instantiation


    /**
     * Returns a {@code HistoryManager} with the history of commands to add persons to the typical AddressBook.
     */
    public static HistoryManager getTypicalHistoryManager() {
        HistoryManager historyManager = new HistoryManager();
        getTypicalCommands().forEach(historyManager::rememberExecutedCommand);
        return historyManager;
    }

    public static List<Command> getTypicalCommands() {
        return new ArrayList<>(Arrays.asList(ADD_AMY, ADD_BENSON, ADD_CARL, ADD_DANIEL, ADD_ELLE, ADD_FIONA,
                ADD_GEORGE));
    }

}
