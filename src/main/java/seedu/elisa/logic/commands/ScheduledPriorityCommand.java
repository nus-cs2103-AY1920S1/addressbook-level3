package seedu.elisa.logic.commands;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import seedu.elisa.model.ItemModel;

/**
 * Create a priority command that will turn off after a certain time.
 */
public class ScheduledPriorityCommand extends PriorityCommand {
    public static final String COMMAND_WORD = "priority";
    private LocalDateTime ldt;

    /**
     * Constructor to create the command
     * @param ldt the time at which the priority mode will end.
     */
    public ScheduledPriorityCommand(LocalDateTime ldt, boolean focusMode) {
        super(focusMode);
        ZonedDateTime zdt = ldt.atZone(ZoneId.systemDefault());
        this.ldt = ldt;
    }

    @Override
    public CommandResult execute(ItemModel model) {
        model.scheduleOffPriorityMode(ldt);
        return super.execute(model);
    }

}
