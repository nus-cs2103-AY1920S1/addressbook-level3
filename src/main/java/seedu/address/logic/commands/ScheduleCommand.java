package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.schedule.Schedule;
import seedu.address.model.weekschedule.WeekSchedule;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUPNAME;

public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";
    public static final String MESSAGE_SUCCESS = "Schedule found: ";
    public static final String MESSAGE_FAILURE = "Unable to generate schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_GROUPNAME + " GROUPNAME";

    public final GroupName groupName;

    public ScheduleCommand(GroupName groupName) {
        this.groupName = groupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Group group = model.findGroup(groupName);
        if(group == null) {
            return new CommandResult(MESSAGE_FAILURE);
        }

        ArrayList<PersonId> personIds = model.findPersonsOfGroup(group.getGroupId());
        ArrayList<Person> persons = new ArrayList<>();

        for(int i = 0; i < personIds.size(); i++) {
            persons.add(model.findPerson(personIds.get(i)));
        }

        WeekSchedule schedule = model.getWeekSchedule(groupName.toString(), LocalDateTime.now(), persons);

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Command command) {
        if(command == null) {
            return false;
        } else if (!(command instanceof ScheduleCommand)) {
            return false;
        } else if (((ScheduleCommand) command).groupName.equals(this.groupName)) {
            return true;
        } else {
            return false;
        }
    }
}
