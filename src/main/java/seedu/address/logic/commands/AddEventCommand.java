package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENTNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMING;

import java.time.LocalDateTime;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.detailwindow.DetailWindowDisplayType;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.person.Name;
import seedu.address.model.person.schedule.Event;


/**
 * Adds an Event to the schedule of a person.
 */
public class AddEventCommand extends Command {
    public static final String COMMAND_WORD = "addevent";
    public static final String MESSAGE_SUCCESS = "New event added: ";
    public static final String MESSAGE_FAILURE = "Unable to add event: ";
    public static final String MESSAGE_FAILURE_WRONG_TIMINGS = "Invalid timing arguments\n"
            + "Time format: ddMMyyyy:HHmm";
    public static final String MESSAGE_FAILURE_UNABLE_TO_FIND_PERSON = "Unable to find person";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_NAME + " NAME "
            + PREFIX_EVENTNAME + " EVENTNAME "
            + PREFIX_TIMING + " STARTTIME-ENDTIME-LOCATION"
            + "\n"
            + "Time format: ddMMyyyy:HHmm";

    public final Event event;
    public final Name name;

    public AddEventCommand(Name name, Event event) {
        requireNonNull(name);

        this.event = event;
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (event == null) {
            return new CommandResult(MESSAGE_FAILURE + MESSAGE_FAILURE_WRONG_TIMINGS);
        } else if (model.addEvent(name, event)) {

            // updates main window
            model.updateDetailWindowDisplay(name, LocalDateTime.now(), DetailWindowDisplayType.EMPTY);

            // updates side panel
            model.updateSidePanelDisplay(SidePanelDisplayType.PERSONS);

            return new CommandResult(MESSAGE_SUCCESS + event.toString());
        } else {
            return new CommandResult(MESSAGE_FAILURE + MESSAGE_FAILURE_UNABLE_TO_FIND_PERSON);
        }
    }

    @Override
    public boolean equals(Command command) {
        if (command == null) {
            return false;
        } else if (!(command instanceof AddEventCommand)) {
            return false;
        } else if (((AddEventCommand) command).event.equals(this.event)
                && ((AddEventCommand) command).name.equals(this.name)) {
            return true;
        } else {
            return false;
        }
    }
}
