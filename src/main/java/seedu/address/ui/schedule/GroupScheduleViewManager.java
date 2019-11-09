package seedu.address.ui.schedule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.display.detailwindow.PersonSchedule;
import seedu.address.model.display.schedulewindow.FreeSchedule;
import seedu.address.model.display.schedulewindow.MonthSchedule;
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;
import seedu.address.model.display.schedulewindow.WeekSchedule;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Name;

/**
 * Class to handle schedule view of groups
 */
public class GroupScheduleViewManager implements ScheduleViewManager {

    private List<PersonSchedule> originalPersonSchedules;
    private List<MonthSchedule> filteredMonthSchedules;
    private ArrayList<String> originalColors;
    private ArrayList<String> filteredColors;
    private GroupName groupName;
    private ArrayList<FreeSchedule> freeSchedules;
    private int weekNumber;
    private ScheduleView scheduleView;
    private LocalDate currentDate;

    public GroupScheduleViewManager(List<PersonSchedule> originalPersonSchedules,
                                    ArrayList<String> originalColors,
                                    GroupName groupName, ArrayList<FreeSchedule> freeSchedules) {
        this.originalPersonSchedules = originalPersonSchedules;
        this.filteredMonthSchedules = originalPersonSchedules.stream()
                .map(PersonSchedule::getScheduleDisplay)
                .collect(Collectors.toCollection(ArrayList::new));
        this.originalColors = originalColors;
        this.filteredColors = new ArrayList<>(originalColors);
        this.groupName = groupName;
        this.freeSchedules = freeSchedules;
        this.weekNumber = 0;
        this.currentDate = LocalDate.now();
        initScheduleView();
    }

    /**
     * Method to initialise or reinitialise group ScheduleView object to be displayed in the UI.
     * Group schedules show free time.
     */
    private void initScheduleView() {
        LocalDate dateToShow = currentDate.plusDays(7 * weekNumber);
        this.scheduleView = new ScheduleView(MonthSchedule.getWeekSchedulesOf(filteredMonthSchedules, weekNumber),
                filteredColors, groupName.toString(), dateToShow);
        //Required to set the free time schedule first before generating the schedule.
        this.scheduleView.setFreeTime(freeSchedules.get(weekNumber));
        this.scheduleView.generateSchedule();
    }

    /**
     *
     */
    private void filterPerson(List<Name> filteredList) {
        filteredMonthSchedules = new ArrayList<>();
        filteredColors = new ArrayList<>();
        ArrayList<Name> filteredListCopy = new ArrayList<>(filteredList);
        for (PersonSchedule personSchedule : originalPersonSchedules) {
            if (filteredListCopy.contains(personSchedule.getPersonDisplay().getName())) {
                //Person is found in the filtered list.
                int index = originalPersonSchedules.indexOf(personSchedule);
                filteredMonthSchedules.add(personSchedule.getScheduleDisplay());
                filteredColors.add(originalColors.get(index));
                filteredListCopy.remove(personSchedule.getPersonDisplay().getName());
            }
        }
        //Must remove everyone from filteredList
        assert filteredListCopy.size() == 0;
    }

    @Override
    public void filterPersonsFromSchedule(List<Name> namesToFilter) {
        filterPerson(namesToFilter);
        initScheduleView();
    }

    @Override
    public void toggleNext() {
        this.weekNumber = (weekNumber + 1) % 4;
        initScheduleView();
    }

    @Override
    public List<String> getOriginalColors() {
        return originalColors;
    }

    @Override
    public ScheduleWindowDisplayType getScheduleWindowDisplayType() {
        return ScheduleWindowDisplayType.GROUP;
    }

    @Override
    public ScheduleView getScheduleViewCopy() {
        List<WeekSchedule> weekToBeExported = MonthSchedule.getWeekSchedulesOf(originalPersonSchedules
                .stream().map(PersonSchedule::getScheduleDisplay)
                .collect(Collectors.toCollection(ArrayList::new)), weekNumber);
        ScheduleView copy = new ScheduleView(weekToBeExported,
                originalColors, groupName.toString(), currentDate.plusDays(7 * weekNumber));
        copy.setFreeTime(freeSchedules.get(weekNumber));
        copy.generateSchedule();
        return copy;
    }

    @Override
    public ScheduleView getScheduleView() {
        return this.scheduleView;
    }

    @Override
    public void scrollNext() {
        this.scheduleView.scrollNext();
    }
}
