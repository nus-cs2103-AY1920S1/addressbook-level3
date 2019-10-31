package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DAYS;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.result.CommandResult;
import seedu.address.logic.commands.util.HelpExplanation;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.NameWithTime;
import seedu.address.model.day.ActivityWithTime;
import seedu.address.model.day.Day;
import seedu.address.model.field.Address;
import seedu.address.model.tag.TagWithTime;

/**
 * Generates a schedule for specified day(s).
 */
public class AutoScheduleCommand extends Command {

    public static final String COMMAND_WORD = "autoSchedule";

    public static final String TIME_FORMAT = "HHmm";
    public static final String MESSAGE_INVALID_SCHEDULE = "Unnable to generate a schedule based on the requirements";
    public static final String MESSAGE_SCHEDULE_ACTIVITY_SUCCESS = "Activities successfully scheduled.";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
                COMMAND_WORD,
        "Generates a list of activities for a specified day based on tag order given by user.",
        COMMAND_WORD + " ([" + PREFIX_TAG + "TAG START_TIME] || "
                + PREFIX_NAME + "ACTIVITY_NAME START_TIME])... "
                + PREFIX_ADDRESS + "ADDRESS_TO_PRIORITISE "
                + PREFIX_DAY + "[DAY_INDEX]...",
        COMMAND_WORD + " " + PREFIX_TAG + "Dining 1000 " + PREFIX_TAG + "Attraction 1200 "
                + PREFIX_TAG + "Dining " + PREFIX_NAME + "Disneyland 1400 "
                + PREFIX_ADDRESS + "Tokyo " + PREFIX_DAY + "1 4 5"
    );

    public static final String MESSAGE_SUCCESS = "Schedule for the day generated!";
    public static final Integer DEFAULT_START_TIME = 900;
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    private List<Object> draftSchedule;
    private Address address;
    private List<Index> days;

    public AutoScheduleCommand(List<Object> draftSchedule, Address address, List<Index> days) {
        this.draftSchedule = draftSchedule;
        this.address = address;
        this.days = days;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Day> lastShownDays = model.getFilteredItinerary();

        //If user did not indicate which day(s) to auto schedule, it is assumed to schedule for all days
        if (days.size() == 0) {
            days = daysToSchedule(lastShownDays.size());
        }
        for (Index dayIndex : days) {
            List<Activity> lastShownActivities = model.getFilteredActivityList();
            List<Activity> filteredActivitiesByLocation = lastShownActivities;
            List<Integer> timeSchedule = fillTimeSchedule(draftSchedule);
            List<ActivityWithTime> activitiesForTheDay = new ArrayList<>();
            // Gets the list of activities that has the location specified by user
            if (address != null) {
                filteredActivitiesByLocation = filterActivitiesByLocation(lastShownActivities, address.toString());
            }
            // sort activities by priority
            List<Activity> newActivityListByLocation = new ArrayList<>(filteredActivitiesByLocation);
            Collections.sort(newActivityListByLocation);
            //draftSchedule contains TagWithTime and NameWithTime in the same order given by user
            //Eg. t/Activity 1000  n/DisneyLand 1200  t/Activity   t/Dining 1800
            for (int i = 0; i < draftSchedule.size(); i++) {
                List<Activity> similarActivities = new ArrayList<>();
                boolean isScheduled = false;

                //Gets all activities that has the same tag
                if (draftSchedule.get(i) instanceof TagWithTime) {
                    similarActivities =
                            getActivitiesWithSameTag(newActivityListByLocation, (TagWithTime) draftSchedule.get(i));
                }
                //Gets all activities that has the same name
                if (draftSchedule.get(i) instanceof NameWithTime) {
                    similarActivities =
                            getActivitiesWithSameName(newActivityListByLocation, (NameWithTime) draftSchedule.get(i));
                }
                //ActivityCount represents an activity and the number of times it appears in the timetable
                //Eg. Ski -> 1, Gundam Museum -> 1, Shop At daiso -> 2
                List<ActivityWithCount> activitiesWithCount =
                        updateActivitiesCount(similarActivities, lastShownDays, activitiesForTheDay, dayIndex);

                //Activities with the lowest count are the first to be added
                //For those with same count, if the activity has a priority that is higher, it will be added first.
                Collections.sort(activitiesWithCount);

                //Main entry of execution
                //Checks if duration exceed the next timing if any
                for (ActivityWithCount activityWithCount : activitiesWithCount) {
                    int duration = activityWithCount.getActivity().getDuration().value;
                    int hour = (duration / 60) * 100;
                    int min = duration - 60 * (duration / 60);
                    //The last activity do not have to worry about overlap with another activity
                    if (i == draftSchedule.size() - 1) {
                        isScheduled = true;
                        activitiesForTheDay.add(activityToSchedule(timeSchedule.get(i), hour, min,
                                activityWithCount.getActivity()));
                        break;
                    }
                    //find the next timing if any given by user
                    int nextTimingIndex = -1;
                    int currentTiming = timeSchedule.get(i);
                    for (int k = i + 1; k < timeSchedule.size(); k++) {
                        if (timeSchedule.get(k) != null) {
                            nextTimingIndex = k;
                        }
                    }
                    // No timing is given by user for the entire autoschedule command
                    if (nextTimingIndex == -1) {
                        isScheduled = true;
                        activitiesForTheDay.add(activityToSchedule(timeSchedule.get(i),
                                hour, min, activityWithCount.getActivity()));
                        timeSchedule.set(i + 1, currentTiming + hour + min);
                        break;
                        //check next timing does not overlap
                    } else {
                        int startTimeOfNextActivity = timeSchedule.get(nextTimingIndex);
                        int currentActivityEndTime = currentTiming + hour + min;
                        if (startTimeOfNextActivity - currentActivityEndTime >= 0) {
                            isScheduled = true;
                            activitiesForTheDay.add(activityToSchedule(timeSchedule.get(i),
                                    hour, min, activityWithCount.getActivity()));
                            //the next activity will be given a start time,
                            // if the timing is not the next in line
                            //Eg. 1000 null 1300 -> becomes 1000 1000+30min  1300
                            if (nextTimingIndex != i + 1) {
                                timeSchedule.set(i + 1, currentActivityEndTime);
                            }
                            break;
                        }
                    }
                }
                if (!isScheduled) {
                    throw new CommandException(MESSAGE_INVALID_SCHEDULE);
                }
            }
            Day editedDay = new Day(activitiesForTheDay);
            List<Day> editedDays = new ArrayList<>(lastShownDays);
            editedDays.set(dayIndex.getZeroBased(), editedDay);
            model.setDays(editedDays);
            model.updateFilteredItinerary(PREDICATE_SHOW_ALL_DAYS);
        }
        return new CommandResult(String.format(MESSAGE_SCHEDULE_ACTIVITY_SUCCESS));
    }

    /**
     * Creates an ActivityWithTime to be scheduled.
     *
     * @param activity activity to be scheduled
     */
    private ActivityWithTime activityToSchedule(Integer currentTiming, int hour, int min, Activity activity) {
        LocalTime startTime = LocalTime.parse(String.format("%04d", currentTiming), TIME_FORMATTER);
        LocalTime endTime = LocalTime.parse(String.format("%04d", currentTiming + hour + min), TIME_FORMATTER);
        return new ActivityWithTime(activity, startTime, endTime);
    }

    /**
     * Generates a list containing all activities with counts representing the number of the same activity that exist
     * in the itinerary.
     *
     * @param activitiesForTheDay list of activities that are generated by auto-scheduling
     */
    private List<ActivityWithCount> updateActivitiesCount(List<Activity> similarActivities, List<Day> lastShownDays,
                                                          List<ActivityWithTime> activitiesForTheDay, Index dayToEdit) {
        List<ActivityWithCount> activityCounts = new ArrayList<>();
        for (Activity similarActivity : similarActivities) {
            int count = 0;
            //number of times the activities appear in other days
            for (int i = 0; i < lastShownDays.size(); i++) {
                if (i == dayToEdit.getZeroBased()) {
                    continue;
                }
                List<ActivityWithTime> activities = lastShownDays.get(i).getListOfActivityWithTime();
                for (ActivityWithTime activityWithTime : activities) {
                    if (activityWithTime.getActivity().equals(similarActivity)) {
                        count += 1;
                    }
                }
            }
            //number of times the activity appear in our current list of activities to schedule
            for (ActivityWithTime activityWithTime : activitiesForTheDay) {
                if (activityWithTime.getActivity().equals(similarActivity)) {
                    count += 1;
                }
            }
            activityCounts.add(new ActivityWithCount(similarActivity, count));
        }
        return activityCounts;
    }

    /**
     * Creates a list to track the time of each activity to be carried out.
     *
     * @param draftSchedule The order in with the type of activity to be carried out
     */
    private List<Integer> fillTimeSchedule(List<Object> draftSchedule) {
        List<Integer> timeSchedule = new ArrayList<>();
        for (int i = 0; i < draftSchedule.size(); i++) {
            Integer time = null;
            if (draftSchedule.get(i) instanceof TagWithTime) {
                time = ((TagWithTime) draftSchedule.get(i)).getTime();
            }
            if (draftSchedule.get(i) instanceof NameWithTime) {
                time = ((NameWithTime) draftSchedule.get(i)).getTime();
            }
            if (time == null && i == 0) {
                timeSchedule.add(DEFAULT_START_TIME);
            } else {
                timeSchedule.add(time);
            }
        }
        return timeSchedule;
    }

    /**
     * @return List of activities that has the same name specified
     */
    private List<Activity> getActivitiesWithSameName(List<Activity> filteredActivitiesByLocation, NameWithTime name) {
        List<Activity> similarActivities = new ArrayList<>();
        for (Activity activity : filteredActivitiesByLocation) {
            if (activity.getName().equals(name.getName())) {
                similarActivities.add(activity);
            }
        }
        return similarActivities;
    }

    /**
     * @return list of activities that has the same tag specified.
     */
    private List<Activity> getActivitiesWithSameTag(List<Activity> filteredActivitiesByLocation, TagWithTime tag) {
        List<Activity> similarActivities = new ArrayList<>();
        for (Activity activity : filteredActivitiesByLocation) {
            if (activity.getTags().contains(tag.getTag())) {
                similarActivities.add(activity);
            }
        }
        return similarActivities;
    }

    /**
     * @return list of all days to genaerate a schedule for
     */
    private List<Index> daysToSchedule(int size) {
        List<Index> dayIndexes = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            dayIndexes.add(Index.fromOneBased(i));
        }
        return dayIndexes;
    }

    /**
     * @return list of activities that has the same location specified.
     */
    private List<Activity> filterActivitiesByLocation(List<Activity> lastShownActivities, String address) {
        List<Activity> filteredList = new ArrayList<>();
        for (Activity activity : lastShownActivities) {
            if (activity.getAddress().toString().contains(address)) {
                filteredList.add(activity);
            }
        }
        return filteredList;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AutoScheduleCommand
                && draftSchedule.equals(((AutoScheduleCommand) other).draftSchedule))
                && address.equals(((AutoScheduleCommand) other).address)
                && days.equals((((AutoScheduleCommand) other).days));
    }

    /**
     * Represents the number of times the Activity appears in the Timetable.
     */
    private static class ActivityWithCount implements Comparable<ActivityWithCount> {
        private Activity activity;
        private int count;

        ActivityWithCount(Activity activity, int count) {
            this.activity = activity;
            this.count = count;
        }

        public Activity getActivity() {
            return activity;
        }

        public int getCount() {
            return count;
        }

        @Override
        public int compareTo(ActivityWithCount o) {
            return count - o.count;
        }
    }

}
