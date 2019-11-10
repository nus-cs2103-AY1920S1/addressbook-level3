package seedu.address.ui.schedule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.display.schedulewindow.FreeSchedule;
import seedu.address.model.display.schedulewindow.MonthSchedule;
import seedu.address.model.display.schedulewindow.PersonSchedule;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;
import seedu.address.model.display.schedulewindow.WeekSchedule;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Name;

/**
 * Class to handle schedule view of groups
 */
public class GroupScheduleViewManager extends ScheduleViewManager {

    private List<PersonSchedule> originalPersonSchedules;
    private List<PersonSchedule> filteredPersonSchedules;
    private GroupName groupName;
    private ArrayList<FreeSchedule> freeSchedules;

    public GroupScheduleViewManager(List<PersonSchedule> originalPersonSchedules,
                                    GroupName groupName, ArrayList<FreeSchedule> freeSchedules) {
        this.originalPersonSchedules = originalPersonSchedules;
        this.filteredPersonSchedules = originalPersonSchedules;
        this.groupName = groupName;
        this.freeSchedules = freeSchedules;
        super.weekNumber = 0;
        super.currentDate = LocalDate.now();
        super.type = ScheduleWindowDisplayType.GROUP;
        super.LOGGER.info("Generating schedule view for " + groupName.toString() + ".");
    }

    /**
     * Method to initialise or reinitialise group ScheduleView object to be displayed in the UI.
     * Group schedules show free time.
     */
    private void update() {
        LocalDate dateToShow = currentDate.plusDays(7 * weekNumber);
        super.scheduleView = new ScheduleView(MonthSchedule.getWeekSchedulesOf(filteredPersonSchedules.stream()
                .map(PersonSchedule::getScheduleDisplay).collect(Collectors.toCollection(ArrayList::new)), weekNumber),
                groupName.toString(), dateToShow);
        //Required to set the free time schedule first before generating the schedule.
        super.scheduleView.setFreeTime(freeSchedules.get(weekNumber));
        super.scheduleView.generateSchedule();
    }

    /**
     *
     */
    private void filterPerson(List<Name> filteredList) {
        filteredPersonSchedules = new ArrayList<>();
        ArrayList<Name> filteredListCopy = new ArrayList<>(filteredList);
        for (PersonSchedule personSchedule : originalPersonSchedules) {
            if (filteredListCopy.contains(personSchedule.getPersonDisplay().getName())) {
                //Person is found in the filtered list.
                int index = originalPersonSchedules.indexOf(personSchedule);
                filteredPersonSchedules.add(personSchedule);
                filteredListCopy.remove(personSchedule.getPersonDisplay().getName());
            }
        }
        //Must remove everyone from filteredList
        assert filteredListCopy.size() == 0;
    }

    @Override
    public void filterPersonsFromSchedule(List<Name> namesToFilter) {
        filterPerson(namesToFilter);
    }

    @Override
    public void toggleNext() {
        super.weekNumber = (weekNumber + 1) % 4;
    }

    @Override
    public ScheduleView getScheduleViewCopy() {
        List<WeekSchedule> weekToBeExported = MonthSchedule.getWeekSchedulesOf(originalPersonSchedules
                .stream().map(PersonSchedule::getScheduleDisplay)
                .collect(Collectors.toCollection(ArrayList::new)), weekNumber);
        ScheduleView copy = new ScheduleView(weekToBeExported,
                groupName.toString(), currentDate.plusDays(7 * weekNumber));
        copy.setFreeTime(freeSchedules.get(weekNumber));
        copy.generateSchedule();
        return copy;
    }

    @Override
    public ScheduleView getScheduleView() {
        update();
        return super.scheduleView;
    }

    @Override
    public void scrollNext() {
        super.scheduleView.scrollNext();
    }
}
