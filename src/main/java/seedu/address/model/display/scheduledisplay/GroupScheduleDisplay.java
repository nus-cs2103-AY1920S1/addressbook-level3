package seedu.address.model.display.scheduledisplay;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.model.display.sidepanel.GroupDisplay;
import seedu.address.model.display.timeslots.FreeSchedule;
import seedu.address.model.display.timeslots.FreeTimeslot;
import seedu.address.model.display.timeslots.PersonSchedule;
import seedu.address.model.person.Name;
import seedu.address.model.person.exceptions.InvalidTimeslotException;

/**
 * A Schedule of a Group.
 */
public class GroupScheduleDisplay extends ScheduleDisplay {

    private static final ScheduleState state = ScheduleState.GROUP;

    private GroupDisplay groupDisplay;
    private ArrayList<FreeSchedule> freeSchedules;

    private Optional<List<Name>> filteredNames = Optional.empty();

    public GroupScheduleDisplay(ArrayList<PersonSchedule> personSchedules,
                                ArrayList<FreeSchedule> freeSchedules,
                                GroupDisplay groupDisplay) {
        super(personSchedules);
        this.freeSchedules = freeSchedules;
        this.groupDisplay = groupDisplay;
    }

    public GroupDisplay getGroupDisplay() {
        return groupDisplay;
    }

    public ArrayList<FreeSchedule> getFreeSchedule() {
        return freeSchedules;
    }

    public FreeTimeslot getFreeTimeslot(int week, int id) throws InvalidTimeslotException {
        return freeSchedules.get(week).getFreeTimeslot(id);
    }

    public void setFilteredNames(List<Name> filteredNames) {
        ArrayList<PersonSchedule> personSchedules = super.getPersonSchedules();
        List<Name> presentNames = personSchedules.stream()
                .map(personSchedule -> personSchedule.getPersonDisplay().getName())
                .filter(filteredNames::contains)
                .collect(Collectors.toCollection(ArrayList::new));
        this.filteredNames = Optional.of(presentNames);
    }

    public Optional<List<Name>> getFilteredNames() {
        return filteredNames;
    }

    @Override
    public ScheduleState getState() {
        return state;
    }

}
