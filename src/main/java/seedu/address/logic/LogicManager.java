package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javafx.beans.property.ListPropertyBase;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.item.Item;
import seedu.address.commons.core.item.Reminder;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ItemModel;
import seedu.address.model.ItemStorage;
import seedu.address.model.item.VisualizeList;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final ItemModel model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;
    private final ScheduledThreadPoolExecutor checker;

    public LogicManager(ItemModel model, Storage storage) {
        this.storage = storage;
        this.model = model;
        addressBookParser = new AddressBookParser();

        //Create new thread class to check
        /*
        class CheckerThread extends Thread {

            ScheduledExecutorService
            //Schedule a check on future list every 5 seconds
            //If future.first.time <= now
            // A new collection <- future.remove until future.first.time > now
            // ActiveRemindersListProperty.add(A new collection)
            //
        }
        */
        //Spawn new thread
        //new CheckerThread().start();
        //Run checker
        Runnable checkTask = new Runnable() {
            public void run() {
                System.out.println("running checkTask");
                Reminder reminder;
                ArrayList<Item> reminders = model.getFutureRemindersList();
                ArrayList<Item> activeReminders = new ArrayList<Item> (0);
                if (reminders.size() > 0) {
                    System.out.println("There are pending reminders: " + reminders.toString());
                    //TODO: Check if Optional is present before .get()
                    reminder = reminders.get(0).getReminder().get();

                    while (reminder != null && reminder.getDateTime().isBefore(LocalDateTime.now())) {
                        System.out.println("Adding a new task to be reminded.");
                        Item item = reminders.remove(0);
                        activeReminders.add(item);
                        if (reminders.size() > 0) {
                            reminder = reminders.get(0).getReminder().get();
                        } else {
                            reminder = null;
                        }
                    }
                    System.out.println("Adding in model");
                    model.getActiveReminderListProperty().addReminders(activeReminders);
                }
            }
        };

        checker = new ScheduledThreadPoolExecutor(1);
        //TODO: Think about initial delay in relation to time for app to start up
        checker.scheduleAtFixedRate(checkTask, 0, 15, TimeUnit.SECONDS);
    }

    /*
     * Bryan Reminder
     */
    public final ListPropertyBase<Item> getActiveRemindersListProperty() {
        return model.getActiveReminderListProperty();
    }

    public final void shutdown() {
        checker.shutdown();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {

        //Logging
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        //Parse user input from String to a Command
        Command command = addressBookParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveItemStorage(model.getItemStorage());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ItemStorage getItemStorage() {
        return model.getItemStorage();
    }

    /*
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getVisualList();
<<<<<<< HEAD
    }

     */
    @Override
    public VisualizeList getVisualList() {
        return model.getVisualList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getItemStorageFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
