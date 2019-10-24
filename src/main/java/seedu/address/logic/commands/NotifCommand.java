package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.entity.body.BodyStatus.CONTACT_POLICE;

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
import seedu.address.ui.NotifWindow;
import seedu.address.ui.NotificationButton;

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

    private Notif toAdd;
    private long period;
    private TimeUnit timeUnit;

    public NotifCommand(Notif notif, long period, TimeUnit timeUnit) {
        this.toAdd = notif;
        this.period = period;
        this.timeUnit = timeUnit;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasNotif(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_NOTIF);
        }

        model.addNotif(toAdd);

        startSesChangeBodyStatus();
        startSesChangeBodyStatusUi(model);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    //@@author ambervoong
    /**
     * Removes a notification from the model. Used to undo changes made in an AddCommand
     * @param model model of Mortago.
     */
    public void removeNotif(Model model) {
        requireNonNull(model);

        if (model.hasNotif(toAdd)) {
            model.deleteNotif(toAdd);
        }
    }
    //@@author

    /**
     * Updates the BodyStatus after a specified time.
     */
    public void startSesChangeBodyStatus() {
        ses.schedule(toAdd.getAlert(), period, timeUnit);
    }

    /**
     * Updates the UI to reflect the change in BodyStatus.
     * @param model refers to the ModelManager
     */
    public void startSesChangeBodyStatusUi(Model model) throws CommandException {
        Body body = toAdd.getBody();
        String notifContent = "Body Id: " + body.getIdNum()
                                + "\nName: " + body.getName()
                                + "\nNext of Kin has been uncontactable. Please contact the police";

        Runnable changeUi = () -> Platform.runLater(() -> {
            if (body.getBodyStatus().equals(Optional.of(CONTACT_POLICE))) {
                UpdateCommand up = new UpdateCommand(body.getIdNum(), new UpdateBodyDescriptor(body));
                up.setUpdateFromNotif(true);
                try {
                    up.execute(model);

                    NotifWindow notifWindow = new NotifWindow();
                    notifWindow.setTitle("Contact Police!");
                    notifWindow.setContent(notifContent);
                    notifWindow.display();
                    ses.shutdown();
                } catch (CommandException e) {
                    logger.info("Error updating the body and fridge ");
                }
            }
            NotificationButton.getInstanceOfNotifButton().setIconNumber(model.getNumberOfNotifs());
        });

        ses.schedule(changeUi, period, timeUnit);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NotifCommand
                && toAdd.equals(((NotifCommand) other).toAdd));
    }
}

//@@author
