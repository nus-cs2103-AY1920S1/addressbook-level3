package seedu.address.logic.commands;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ItemModel;

/**
 * Create a priority command that will turn off after a certain time.
 */
public class ScheduledPriorityCommand extends PriorityCommand {
    public static final String COMMAND_WORD = "priority";
    private Date date;
    private Timer timer;

    /**
     * Constructor to create the command
     * @param ldt the time at which the priority mode will end.
     */
    public ScheduledPriorityCommand(LocalDateTime ldt) {
        ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
        this.date = Date.from(zdt.toInstant());
        this.timer = new Timer();
    }

    @Override
    public CommandResult execute(ItemModel model) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                model.toggleOffPriorityMode();
                timer.cancel();
            }
        }, date);
        return super.execute(model);
    }

    @Override
    public void reverse(ItemModel model) throws CommandException {
        timer.cancel();
        super.reverse(model);
    }
}
