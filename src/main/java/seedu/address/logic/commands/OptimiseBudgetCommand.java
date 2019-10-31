package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.result.CommandResult;
import seedu.address.logic.commands.result.UiFocus;
import seedu.address.logic.commands.util.HelpExplanation;
import seedu.address.model.Model;
import seedu.address.model.day.ActivityWithTime;
import seedu.address.model.day.Day;
import seedu.address.model.field.Cost;

/**
 * Optimises a Day's activities.
 */
public class OptimiseBudgetCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "optimise";
    public static final String MESSAGE_SUCCESS = "Day has been optimised!";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD,
            "Optimises the day based on the total cost of activities in a day.",
            COMMAND_WORD + " DAY_INDEX (must be a positive integer)",
            COMMAND_WORD + " 1"
    );

    private final Index dayIndex;
    private TreeMap<ActivityWithTime, List<ActivityWithTime>> adjList;
    private List<Path> paths;

    /**
     * Creates an OptimiseBudgetCommand to optimise a day's activities.
     */
    public OptimiseBudgetCommand(Index dayIndex) {
        requireNonNull(dayIndex);
        this.dayIndex = dayIndex;
    }

    public Index getDayIndex() {
        return dayIndex;
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Day> listOfDays = model.getFilteredItinerary();

        if (dayIndex.getZeroBased() >= listOfDays.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DAY_DISPLAYED_INDEX);
        }

        Day dayToOptimise = listOfDays.get(dayIndex.getZeroBased());
        adjList = makeAdjList(dayToOptimise);
        paths = new ArrayList<>();

        List<ActivityWithTime> startPoints = dayToOptimise.findAllOverlap(adjList.firstKey());
        for (ActivityWithTime startPoint : startPoints) {
            Path path = new Path();
            path.add(startPoint);
            tracePaths(startPoint, path);
        }

        Collections.sort(paths);
        model.setDay(dayToOptimise, new Day (paths.get(0).path));
        return new CommandResult(MESSAGE_SUCCESS, new UiFocus[]{UiFocus.AGENDA});
    }

    /**
     * Creates an adjacency list of all the activities
     */
    private TreeMap<ActivityWithTime, List<ActivityWithTime>> makeAdjList(Day day) {
        TreeMap<ActivityWithTime, List<ActivityWithTime>> adjList = new TreeMap<>();
        List<ActivityWithTime> activities = day.getListOfActivityWithTime();

        for (int i = 0; i < activities.size(); i++) {
            ActivityWithTime currAct = activities.get(i);
            Optional<ActivityWithTime> nextAct = day.findNextActNoOverlap(Index.fromZeroBased(i));
            nextAct.ifPresentOrElse(x -> {
                List<ActivityWithTime> neighbours = findNeighbours(day, x);
                adjList.put(currAct, neighbours);
            }, () -> adjList.put(currAct, new ArrayList<>()));
        }
        return adjList;
    }

    /**
     * Finds a list of possible activities that do not clash with the current activity and comes after the current
     * activity.
     */
    private List<ActivityWithTime> findNeighbours(Day day, ActivityWithTime activity) {
        List<ActivityWithTime> neighbours = new ArrayList<>();
        neighbours.addAll(day.findAllOverlap(activity));
        return neighbours;
    }

    /**
     * Creates a list of possible combinations of all valid schedules such that the activities do not overlap with each
     * other.
     */
    private void tracePaths(ActivityWithTime currNode, Path path) {
        if (adjList.get(currNode).isEmpty()) {
            paths.add(path);
        } else {
            List<ActivityWithTime> neighbours = adjList.get(currNode);
            for (ActivityWithTime neighbour : neighbours) {
                Path splitPath = path.clone();
                splitPath.add(neighbour);
                tracePaths(neighbour, splitPath);
            }
        }
    }

    /**
     * Represents a schedule without conflict
     */
    private class Path implements Comparable<Path> {
        private double cost;
        private List<ActivityWithTime> path;

        public Path() {
            cost = 0.00;
            path = new ArrayList<>();
        }

        public Path(double cost, List<ActivityWithTime> path) {
            this.cost = cost;
            this.path = path;
        }

        /**
         * Adds an activity that does not conflict to the schedule.
         */
        public void add(ActivityWithTime activity) {
            Optional<Cost> costOfActivity = activity.getActivity().getCost();
            cost += costOfActivity.isPresent()
                    ? costOfActivity.get().getCost()
                    : 0;
            path.add(activity);
        }

        public Path clone() {
            List<ActivityWithTime> copiedPath = path.stream().collect(Collectors.toList());
            return new Path(this.cost, copiedPath);
        }

        @Override
        public int compareTo(Path that) {
            if (this.cost == that.cost) {
                return that.path.size() - this.path.size();
            } else if (that.cost < this.cost) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
