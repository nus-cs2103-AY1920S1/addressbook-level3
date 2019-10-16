package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_BODIES;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.notif.Notif;

/**
 * Notifes a user when there is an automatic change in BodyStatus.
 */
public class NotifCommand extends Command {
    public static final String MESSAGE_DUPLICATE_NOTIF = "This notif already exists in the address book";
    public static final String MESSAGE_SUCCESS = "New notif added: %1$s";

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
        // stopSes();

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

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
    public void startSesChangeBodyStatusUi(Model model) {
        Runnable changeUi = () -> Platform.runLater(() ->
                model.updateFilteredBodyList(PREDICATE_SHOW_ALL_BODIES));

        ses.schedule(changeUi, period, timeUnit);
    }

    /*
    public void stopSes() {
        ses.shutdown();
    }

     */

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NotifCommand
                && toAdd.equals(((NotifCommand) other).toAdd));
    }
}
