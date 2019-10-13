package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.notif.Notif;

public class NotifCommand {
    private static ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
    public static final String MESSAGE_DUPLICATE_NOTIF = "This notif already exists in the address book";
    public static final String MESSAGE_SUCCESS = "New notif added: %1$s";

    private Notif toAdd;

    public NotifCommand(Notif notif) {
        this.toAdd = notif;
    }

    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasNotif(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_NOTIF);
        }

        model.addNotif(toAdd);

        ses.schedule(toAdd.getAlert(), 5, TimeUnit.SECONDS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }
}
