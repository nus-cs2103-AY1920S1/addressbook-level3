package seedu.address.logic.commands.finance;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.employee.Employee;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDateTimeMap;

import java.util.List;

import static java.util.Objects.requireNonNull;


public class PaySlip extends Command {

    public static final String COMMAND_WORD = "payslip";

    public static String MESSAGE_SUCCESS = "pay pay";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        List<Event> eventlist = model.getEventBook().getEventList();
        double totalH = 0.0;
        for (Event e : eventlist) {
            totalH = totalH + e.getEventDateTimeMap().totalHours();
        }
        double amount = totalH * 10;
        MESSAGE_SUCCESS = "Total Payment for all events: $" + amount + "\nBreakdown: " + totalH + " hours, $10.0/h";
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
