package seedu.planner.logic.commands.schedulecommand;

import static java.util.Objects.requireNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.planner.model.Model.PREDICATE_SHOW_ALL_DAYS;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.planner.commons.core.Messages;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.autocomplete.CommandInformation;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.logic.commands.util.HelpExplanation;
import seedu.planner.model.Model;
import seedu.planner.model.ModelManager;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.activity.ActivityWithCount;
import seedu.planner.model.day.ActivityWithTime;
import seedu.planner.model.day.Day;
import seedu.planner.model.field.Address;
import seedu.planner.model.field.NameOrTagWithTime;

//@@author oscarsu97

/**
 * Generates a schedule for specified day(s).
 */
public class AutoScheduleCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "autoschedule";
    public static final String MESSAGE_INVALID_SCHEDULE = "Unable to generate a schedule"
            + " with no overlapping";
    public static final String MESSAGE_SUCCESS = "Schedule for the day(s) generated!";
    public static final String TIME_FORMAT = "HHmm";
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);
    public static final LocalTime DEFAULT_START_TIME = LocalTime.parse("0900", TIME_FORMATTER);
    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD,
            "Generates a schedule based on the following.",
            COMMAND_WORD + " (" + PREFIX_TAG + "TAG [START_TIME] || "
                    + PREFIX_NAME + "ACTIVITY_NAME [START_TIME])... "
                    + "[" + PREFIX_ADDRESS + "LOCATION] "
                    + "[" + PREFIX_DAY + "DAY_INDEX...]",
            COMMAND_WORD + " " + PREFIX_TAG + "Sports " + PREFIX_NAME + "Disneyland 1400 "
                    + PREFIX_ADDRESS + "Tokyo " + PREFIX_DAY + "1 4"
    );

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(
            COMMAND_WORD,
            new ArrayList<>(),
            Arrays.asList(PREFIX_TAG.toString(), PREFIX_NAME.toString()),
            Arrays.asList(PREFIX_DAY.toString(), PREFIX_ADDRESS.toString()),
            new ArrayList<>()
    );

    private List<NameOrTagWithTime> schedulePlan;
    private Optional<Address> address;
    private List<Index> days;
    private final boolean isUndoRedo;

    public AutoScheduleCommand(List<NameOrTagWithTime> schedulePlan, Optional<Address> address, List<Index> days,
                               boolean isUndoRedo) {
        requireNonNull(schedulePlan);
        this.schedulePlan = schedulePlan;
        this.address = address;
        this.days = days;
        this.isUndoRedo = isUndoRedo;
    }

    public List<NameOrTagWithTime> getDraftSchedule() {
        return schedulePlan;
    }

    public Optional<Address> getAddress() {
        return address;
    }

    public List<Index> getDays() {
        return days;
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Day> editDays = new ArrayList<>(model.getFilteredItinerary());
        List<Activity> activityListByLocation = new ArrayList<>(filterByLocation(model.getFilteredActivityList()));
        List<Optional<LocalTime>> timeSchedule = fillTimeSchedule(schedulePlan);
        Collections.sort(activityListByLocation);

        if (editDays.size() == 0) {
            throw new CommandException(Messages.MESSAGE_NO_DAYS_AVAILABLE);
        }
        if (days.size() == 0) {
            days = scheduleForAllDays(editDays.size());
        }

        Model initialModel = new ModelManager(model.getAccommodations(), model.getActivities(), model.getContacts(),
                model.getItinerary(), model.getUserPrefs());

        // Generate schedule for specified day(s)
        for (Index dayIndex : days) {
            List<ActivityWithTime> activitiesForTheDay = new ArrayList<>();
            if (dayIndex.getZeroBased() >= editDays.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_DAY_DISPLAYED_INDEX);
            }
            for (int i = 0; i < schedulePlan.size(); i++) {
                List<Activity> similarActivities = getSimilarActivities(activityListByLocation, schedulePlan.get(i));
                List<ActivityWithCount> activitiesWithCount = updateCount(similarActivities, editDays,
                        activitiesForTheDay, dayIndex);
                int nextIndex = i + 1;
                OptionalInt nextTimingIndex = getNextTimingIndex(i, timeSchedule);
                LocalTime currentTime = timeSchedule.get(i).get();

                // Check if there exists an activity that fits the schedule
                Optional<Activity> activity = getSuitableActivity(activitiesWithCount, i, timeSchedule);
                if (activity.isPresent()) {
                    activitiesForTheDay.add(toSchedule(timeSchedule.get(i).get()
                            .atDate(model.getStartDate().plusDays(dayIndex.getZeroBased())), activity.get()));
                } else {
                    throw new CommandException(MESSAGE_INVALID_SCHEDULE);
                }

                // Sets the next timing as the end time of chosen activity if next timing in schedule is unavailable
                int duration = activity.get().getDuration().value;
                if ((nextTimingIndex.isEmpty() || (nextTimingIndex.getAsInt() != nextIndex
                        && nextTimingIndex.getAsInt() != schedulePlan.size()))) {
                    timeSchedule.set(nextIndex, Optional.ofNullable(currentTime.plusMinutes(duration)));
                }
            }

            Day editedDay = new Day(activitiesForTheDay);
            editDays.set(dayIndex.getZeroBased(), editedDay);
            model.setDays(editDays);
            model.updateFilteredItinerary(PREDICATE_SHOW_ALL_DAYS);
        }
        if (!isUndoRedo) {
            // Not due to redo method of AutoScheduleEvent
            updateEventStack(this, initialModel);
        }
        return new CommandResult(Messages.MESSAGE_SCHEDULE_ACTIVITY_SUCCESS, new UiFocus[]{UiFocus.AGENDA});
    }

    private Optional<Activity> getSuitableActivity(List<ActivityWithCount> activitiesWithCount, int currentIndex,
                                                   List<Optional<LocalTime>> timeSchedule) throws CommandException {
        for (ActivityWithCount activityWithCount : activitiesWithCount) {
            int duration = activityWithCount.getActivity().getDuration().value;
            LocalTime currentTiming = timeSchedule.get(currentIndex).get();
            LocalTime currentActivityEndTime = currentTiming.plusMinutes(duration);

            // Check if it is the last activity in the schedulePlan to schedule
            if (currentIndex == schedulePlan.size() - 1) {
                if (currentActivityEndTime.isBefore(currentTiming)) {
                    continue;
                }
                return Optional.of(activityWithCount.getActivity());
            }

            // Check if activity chosen overlap next timing in timeSchedule
            OptionalInt nextTimingIndex = getNextTimingIndex(currentIndex, timeSchedule);
            if (nextTimingIndex.isEmpty()) {
                return Optional.of(activityWithCount.getActivity());
            } else {
                LocalTime startTimeOfNextActivity = timeSchedule.get(nextTimingIndex.getAsInt()).get();
                if (startTimeOfNextActivity.compareTo(currentActivityEndTime) >= 0) {
                    return Optional.of(activityWithCount.getActivity());
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Gets the index of the next timing in the schedule if any.
     */
    private OptionalInt getNextTimingIndex(int currentIndex, List<Optional<LocalTime>> timeSchedule) {
        if (currentIndex + 1 == timeSchedule.size()) {
            return OptionalInt.of(timeSchedule.size());
        }
        return IntStream.range(currentIndex + 1, timeSchedule.size())
                .filter(k -> timeSchedule.get(k).isPresent())
                .findFirst();
    }

    /**
     * Creates an ActivityWithTime to be scheduled.
     */
    private ActivityWithTime toSchedule(LocalDateTime currentDateTime, Activity activity) {
        return new ActivityWithTime(activity, currentDateTime);
    }

    /**
     * Generates a list that track the number of time each activity appears in the timetable.
     *
     * @param activitiesForTheDay list of activities that are generated by auto-scheduling
     */
    private List<ActivityWithCount> updateCount(List<Activity> similarActivities, List<Day> lastShownDays,
                                                List<ActivityWithTime> activitiesForTheDay, Index dayToEdit) {
        List<ActivityWithCount> activityCounts = new ArrayList<>();
        for (Activity similarActivity : similarActivities) {
            long count = 0;
            for (int i = 0; i < lastShownDays.size(); i++) {
                int index = days.indexOf(Index.fromZeroBased(i));
                if ((index == -1) || index < dayToEdit.getZeroBased()) {
                    count += lastShownDays.get(i).getListOfActivityWithTime()
                            .stream()
                            .filter(activityWithTime -> activityWithTime.getActivity().equals(similarActivity))
                            .count();
                }
            }
            count += (int) activitiesForTheDay
                    .stream()
                    .filter(activityWithTime -> activityWithTime.getActivity().equals(similarActivity))
                    .count();
            activityCounts.add(new ActivityWithCount(similarActivity, (int) count));
        }
        Collections.sort(activityCounts);
        return activityCounts;
    }

    /**
     * Creates a list to track the time of each activity specified by user to be carried out.
     */
    private List<Optional<LocalTime>> fillTimeSchedule(List<NameOrTagWithTime> schedulePlan) {
        List<Optional<LocalTime>> timeSchedule = new ArrayList<>();
        for (NameOrTagWithTime nameAndTagWithTime : schedulePlan) {
            timeSchedule.add(nameAndTagWithTime.getTime());
        }
        if (timeSchedule.get(0).isEmpty()) {
            timeSchedule.set(0, Optional.of(DEFAULT_START_TIME));
        }
        return timeSchedule;
    }

    private List<Activity> getSimilarActivities(List<Activity> filteredActivitiesByLocation,
                                                NameOrTagWithTime nameAndTagWithTime) throws CommandException {
        List<Activity> filteredList = filteredActivitiesByLocation
                .stream()
                .filter(activity -> (nameAndTagWithTime.getTag().isPresent()
                        && activity.getTags().contains(nameAndTagWithTime.getTag().get()))
                        ||
                        nameAndTagWithTime.getName().isPresent()
                                && activity.getName().equals(nameAndTagWithTime.getName().get()))
                .collect(Collectors.toList());
        if (filteredList.isEmpty()) {
            if (nameAndTagWithTime.getName().isPresent()) {
                throw new CommandException(String.format(Messages.MESSAGE_ACTIVITY_NAME_NOT_FOUND,
                        nameAndTagWithTime.getName().get()));
            } else {
                throw new CommandException(String.format(Messages.MESSAGE_ACTIVITY_TAG_NOT_FOUND,
                        nameAndTagWithTime.getTag().get()));
            }
        }
        return filteredList;
    }

    /**
     * Returns a list of all the day indexes available such that all days in the itinerary will be planned to schedule.
     */
    private List<Index> scheduleForAllDays(int size) {
        return IntStream.rangeClosed(1, size)
                .mapToObj(x -> Index.fromOneBased(x))
                .collect(Collectors.toList());
    }

    /**
     * @return list of activities that has the same location specified.
     */
    private List<Activity> filterByLocation(List<Activity> lastShownActivities) throws CommandException {
        List<Activity> filteredList = lastShownActivities;
        if (address.isPresent()) {
            filteredList = lastShownActivities
                    .stream()
                    .filter(activity -> activity.getAddress().equals(address.get()))
                    .collect(Collectors.toList());
            if (filteredList.size() == 0) {
                throw new CommandException(String.format(Messages.MESSAGE_ADDRESS_NOT_FOUND, address.get()));
            }
        }
        return filteredList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AutoScheduleCommand
                && schedulePlan.equals(((AutoScheduleCommand) other).schedulePlan))
                && address.equals(((AutoScheduleCommand) other).address)
                && days.equals((((AutoScheduleCommand) other).days));
    }
}
