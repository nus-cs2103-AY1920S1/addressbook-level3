package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

    public NotifCommand(Notif notif) {
        this.toAdd = notif;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasNotif(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_NOTIF);
        }

        model.addNotif(toAdd);

        ses.schedule(toAdd.getAlert(), 5, TimeUnit.SECONDS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NotifCommand
                && toAdd.equals(((NotifCommand) other).toAdd));
    }
}
