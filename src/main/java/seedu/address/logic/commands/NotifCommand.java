package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.entity.body.BodyStatus.ARRIVED;
import static seedu.address.model.entity.body.BodyStatus.CONTACT_POLICE;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javafx.application.Platform;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.utility.UpdateBodyDescriptor;
import seedu.address.model.Model;
import seedu.address.model.entity.body.Body;
import seedu.address.model.notif.Notif;
import seedu.address.storage.Storage;
import seedu.address.ui.NotifWindow;

//@@author arjavibahety

/**
 * Notifies a user when there is an automatic change in BodyStatus.
 */
public class NotifCommand extends Command {
    public static final String MESSAGE_DUPLICATE_NOTIF = "This notif already exists in the address book";
    public static final String MESSAGE_BODY_STATUS_CHANGE_FAILURE = "There was an error in updating the body status";
    public static final String MESSAGE_SUCCESS = "New notif added: %1$s";
    private static final Logger logger = LogsCenter.getLogger(NotifCommand.class);

    private static ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
    private static Storage storageManager;

    private Notif notif;
    private long period;
    private TimeUnit timeUnit;

    public NotifCommand(Notif notif, long period, TimeUnit timeUnit) {
        this.notif = notif;
        this.period = period;
        this.timeUnit = timeUnit;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        startSesChangeBodyStatus();
        startSesChangeBodyStatusUi(model);

        return new CommandResult(String.format(MESSAGE_SUCCESS, notif));
    }

    //@@author ambervoong
    /**
     * Removes a notification from the model. Used to undo changes made in an AddCommand.
     *
     * @param model model of Mortago.
     */
    public void removeNotif(Model model) {
        requireNonNull(model);

        if (model.hasNotif(notif)) {
            model.deleteNotif(notif);
        }
    }

    /**
     * Adds a notification back into the model. Used to redo changes made in an AddCommand.
     * @param model model of Mortago.
     * @throws CommandException if this notification already exists in the model.
     */
    public void addNotif(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasNotif(notif)) {
            throw new CommandException(MESSAGE_DUPLICATE_NOTIF);
        } else {
            model.addNotif(notif);
        }
    }
    //@@author

    /**
     * Updates the BodyStatus after a specified time.
     */
    public void startSesChangeBodyStatus() {
        ses.schedule(notif.getAlert(), period, timeUnit);
    }

    /**
     * Updates the UI to reflect the change in BodyStatus.
     *
     * @param model refers to the ModelManager
     */
    public void startSesChangeBodyStatusUi(Model model) {

        Body body = notif.getBody();
        String notifContent = "Body Id: " + body.getIdNum()
                + "\nName: " + body.getName()
                + "\nNext of Kin has been uncontactable. Please contact the police";

        Runnable changeUi = () -> Platform.runLater(() -> {
            if (body.getBodyStatus().equals(Optional.of(CONTACT_POLICE))) {
                UpdateCommand up = new UpdateCommand(body.getIdNum(), new UpdateBodyDescriptor(body));
                // This is so that when undone, the status goes back to ARRIVED.
                body.setBodyStatus(ARRIVED);
                try {
                    up.execute(model);
                    NotifWindow notifWindow = new NotifWindow();
                    notifWindow.setTitle("Contact Police!");
                    notifWindow.setContent(notifContent);
                    notifWindow.display();
                    if (!model.hasNotif(notif)) {
                        // throw new CommandException(MESSAGE_DUPLICATE_NOTIF);
                        model.addNotif(notif);
                    }
                    storageManager.saveAddressBook(model.getAddressBook());

                } catch (CommandException | IOException e) {
                    logger.info("Error updating the body and fridge ");
                }
            }
        });
        ses.schedule(changeUi, period, timeUnit);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NotifCommand
                && notif.equals(((NotifCommand) other).notif));
    }

    public ScheduledExecutorService getSes() {
        return ses;
    }

    public static void setStorage(Storage storage) {
        storageManager = storage;
    }
}

//@@author
