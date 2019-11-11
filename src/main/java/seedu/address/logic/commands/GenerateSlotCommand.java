package seedu.address.logic.commands;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.project.Project;
import seedu.address.model.timetable.TimeRange;
import seedu.address.model.timetable.TimeSlotGenerator;
import seedu.address.model.timetable.TimeSlotsAvailable;
import seedu.address.model.timetable.Timetable;

import java.util.List;
import java.util.stream.Collectors;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROJECTS;

public class GenerateSlotCommand extends Command {

    public static final Prefix PREFIX_DURATION = new Prefix("d/");
    public static final Prefix PREFIX_TIMERANGE = new Prefix("r/");
    public static final String COMMAND_WORD = "generate";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Generate timeslot from requested meeting duration and time range\n"
            + "Parameters: d/DURATION(must be positive integer less than 25) "
            + "[r/TIMERANGE (DAY TIME DAY TIME)]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DURATION + "2 "
            + PREFIX_TIMERANGE + "MONDAY 0800 MONDAY 1700";
    public static final String MESSAGE_NOT_CHECKED_OUT = "A project must be checked out first before running this command";
    public static final String MESSAGE_UNKNOWN_ERROR = "Unknown error occured in generation algorithm. Please contact the dev team to report on this bug";
    public static final String MESSAGE_SUCCESS = "Generation success! Possible timeslots are shown below.\n%s";
    public static final String MESSAGE_SUCCESS_WITH_MEMBERS_UNAVAILABLE = "Unable to find timeslot with all members available. However, we managed to find timeslots with most members available!\n"
            + "Members included: %s\n%s";
    public static final String MESSAGE_GENERATION_FAILED_NO_SLOT_AVAILABLE = "Unable to find any timeslots";
    public static final String DURATION_VALIDATION_REGEX = "\\d{1,2}";

    private int durationInHours;
    private TimeRange timeRange;


    public GenerateSlotCommand(int durationInHours, TimeRange timeRange) {
        requireAllNonNull(durationInHours, timeRange);

        this.durationInHours = durationInHours;
        this.timeRange = timeRange;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!model.isCheckedOut()) {
            throw new CommandException(MESSAGE_NOT_CHECKED_OUT);
        }
        if (model.getWorkingProject().isEmpty()) {
            throw new CommandException(MESSAGE_UNKNOWN_ERROR);
        }
        List<Timetable> timetables = model.getMembers().stream().map(Person::getTimetable).collect(Collectors.toList());
        try {
            boolean allMembersAvailable = true;
            Timetable generatedTimetable = new Timetable(new TimeSlotGenerator(timetables, durationInHours, timeRange).generate());
            List<Person> members = null;
            if (generatedTimetable.getTimeRanges().isEmpty()) {
                allMembersAvailable = false;
                TimeSlotsAvailable timeSlotsAvailable = new TimeSlotGenerator(timetables, durationInHours, timeRange).generateWithMostPeople();
                if (timeSlotsAvailable.isEmpty()) {
                    return new CommandResult(MESSAGE_GENERATION_FAILED_NO_SLOT_AVAILABLE, COMMAND_WORD);
                }
                generatedTimetable = new Timetable(timeSlotsAvailable.getTimeRanges());
                members = model.getMembers().stream().filter(x -> timeSlotsAvailable.getTimetables().contains(x.getTimetable())).collect(Collectors.toList());
            }
            Project currWorkingProject = model.getWorkingProject().get();
            Project editedProject = new Project(currWorkingProject.getTitle(),
                    currWorkingProject.getDescription(), currWorkingProject.getMemberNames(),
                    currWorkingProject.getTasks(), currWorkingProject.getFinance(), generatedTimetable);
            editedProject.setListOfMeeting(currWorkingProject.getListOfMeeting());
            model.setWorkingProject(editedProject);
            model.setProject(currWorkingProject, editedProject);
            model.updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);

            String formattedResult = generatedTimetable.getTimeRanges().stream().map(TimeRange::toString).collect(Collectors.joining("\n"));
            if (allMembersAvailable) {
                return new CommandResult(String.format(MESSAGE_SUCCESS, formattedResult), COMMAND_WORD);
            } else {
                return new CommandResult(String.format(MESSAGE_SUCCESS_WITH_MEMBERS_UNAVAILABLE, members.stream().map(x -> x.getName().toString()).collect(Collectors.joining(" ")), formattedResult), COMMAND_WORD);
            }
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_UNKNOWN_ERROR);
        }
    }

}
