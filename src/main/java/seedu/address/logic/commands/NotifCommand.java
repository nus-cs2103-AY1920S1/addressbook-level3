package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_BODY_COULD_NOT_BE_UPDATED;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_NOTIF;
import static seedu.address.commons.core.Messages.MESSSAGE_NOTIF_DOES_NOT_EXIST;
import static seedu.address.model.entity.body.BodyStatus.ARRIVED;
import static seedu.address.model.entity.body.BodyStatus.CONTACT_POLICE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import javafx.application.Platform;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.utility.UpdateBodyDescriptor;
import seedu.address.model.Model;
import seedu.address.model.entity.body.Body;
import seedu.address.model.notif.Notif;
import seedu.address.model.notif.exceptions.DuplicateNotifException;
import seedu.address.storage.Storage;
import seedu.address.ui.NotifWindow;

//@@author arjavibahety

/**
 * Notifies a user when there is an automatic change in BodyStatus.
 */
public class NotifCommand extends Command {
    public static final String MESSAGE_SUCCESS = "New notif added: %1$s";
    private static final Logger logger = LogsCenter.getLogger(NotifCommand.class);

    private static ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
    private static ArrayList<NotifCommand> notifCommands = new ArrayList<>();
    private static Storage storageManager;

    private Notif notif;
    private long period;
    private TimeUnit timeUnit;
    private ScheduledFuture changeUiEvent;
    private ScheduledFuture changeBodyStatusEvent;

    public NotifCommand(Notif notif, long period, TimeUnit timeUnit) {
        this.notif = notif;
        this.period = period;
        this.timeUnit = timeUnit;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        notifCommands.add(this);
        if (!model.hasNotif(notif)) {
            try {
                model.addNotif(notif);
            } catch (DuplicateNotifException exp) {
                throw new CommandException(MESSAGE_DUPLICATE_NOTIF);
            }
        }
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
    private void startSesChangeBodyStatus() {
        changeBodyStatusEvent = ses.schedule(notif.getAlert(), period, timeUnit);
    }

    /**
     * Updates the UI to reflect the change in BodyStatus.
     *
     * @param model refers to the ModelManager
     */
    private void startSesChangeBodyStatusUi(Model model) {

        Runnable changeUi = new Runnable() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    Body body = notif.getBody();
                    String notifContent = "Body Id: " + body.getIdNum()
                            + "\nName: " + body.getName()
                            + "\nNext of Kin has been uncontactable. Please contact the police";
                    if (body.getBodyStatus().equals(Optional.of(CONTACT_POLICE))) {
                        UpdateCommand up = new UpdateCommand(body.getIdNum(), new UpdateBodyDescriptor(body));
                        up.setUpdateFromNotif(true);
                        body.setBodyStatus(ARRIVED);

                        if (model.hasNotif(notif)) {
                            try {
                                model.deleteNotif(notif);
                            } catch (NullPointerException exp) {
                                logger.info(MESSSAGE_NOTIF_DOES_NOT_EXIST);
                            }
                        }

                        Platform.runLater(() -> {
                            if (!model.hasNotif(notif)) {
                                try {
                                    model.addNotif(notif);
                                } catch (DuplicateNotifException exp) {
                                    logger.info(MESSAGE_DUPLICATE_NOTIF);
                                }
                            }
                        });

                        try {
                            up.execute(model);
                            NotifWindow notifWindow = new NotifWindow();
                            notifWindow.setTitle("Contact Police!");
                            notifWindow.setContent(notifContent);
                            notifWindow.display();
                            storageManager.saveAddressBook(model.getAddressBook());

                        } catch (CommandException | IOException e) {
                            logger.info(MESSAGE_BODY_COULD_NOT_BE_UPDATED);
                        }
                    }
                });
            }
        };

        changeUiEvent = ses.schedule(changeUi, period, timeUnit);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof NotifCommand) {
            NotifCommand otherNotifCmd = (NotifCommand) other;
            return other == this // short circuit if same object
                    || (notif.equals(otherNotifCmd.notif))
                    && period == otherNotifCmd.period
                    && timeUnit.equals(otherNotifCmd.timeUnit);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(notif, period, timeUnit);
    }

    public static void setStorage(Storage storage) {
        storageManager = storage;
    }

    public ScheduledFuture getChangeUiEvent() {
        return changeUiEvent;
    }

    public ScheduledFuture getChangeBodyStatusEvent() {
        return changeBodyStatusEvent;
    }

    public Notif getNotif() {
        return notif;
    }

    public static ArrayList<NotifCommand> getNotifCommands() {
        return notifCommands;
    }
}

//@@author
