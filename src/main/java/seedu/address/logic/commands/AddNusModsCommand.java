package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.util.ModuleEventMappingUtil.mapModuleToEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.exceptions.ModuleToEventMappingException;
import seedu.address.model.Model;
import seedu.address.model.display.scheduledisplay.ScheduleState;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.module.AcadYear;
import seedu.address.model.module.Holidays;
import seedu.address.model.module.LessonNo;
import seedu.address.model.module.LessonType;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.ModuleId;
import seedu.address.model.module.NusModsShareLink;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.EventClashException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.schedule.Event;

/**
 * Add an an NUSMods timetable to a person's schedule.
 */
public class AddNusModsCommand extends Command {

    public static final String COMMAND_WORD = "addmods";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_NAME + "PERSON_NAME "
            + PREFIX_LINK + "NUSMODS_SHARE_LINK\n"
            + "Example Link: " + NusModsShareLink.VALID_EXAMPLE_STRING;

    public static final String MESSAGE_SUCCESS = "Added NUS modules to person's schedule.";
    public static final String MESSAGE_FAILURE = "Unable to add modules: %s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "couldn't find person!";
    public static final String MESSAGE_MODULE_NOT_FOUND = "invalid module %s in link";
    public static final String MESSAGE_EVENTS_CLASH = "there's a timing clash somewhere "
            + "between the modules you're adding and the person's schedule!";

    private final Name name;
    private final NusModsShareLink link;

    public AddNusModsCommand(Name name, NusModsShareLink link) {
        requireNonNull(link);

        this.name = name;
        this.link = link;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        AcadYear acadYear = model.getAcadYear();
        LocalDate startAcadSemDate = model.getAcadSemStartDate(acadYear, link.semesterNo);
        Holidays holidays = model.getHolidays();

        Person person;
        try {
            person = getPerson(name, model);
        } catch (PersonNotFoundException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_PERSON_NOT_FOUND)).build();
        }

        List<Event> eventsToAdd;
        try {
            eventsToAdd = mapModulesToEvents(model, acadYear, startAcadSemDate, holidays);
        } catch (ModuleNotFoundException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE,
                    String.format(MESSAGE_MODULE_NOT_FOUND, e.getMessage()))).build();
        } catch (ModuleToEventMappingException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE, e.getMessage())).build();
        }

        try {
            addEventsToPerson(person, eventsToAdd);
        } catch (EventClashException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_EVENTS_CLASH)).build();
        }

        // updates UI.
        if (name == null) {
            model.updateScheduleWithUser(LocalDateTime.now(), ScheduleState.PERSON);
            model.updateSidePanelDisplay(SidePanelDisplayType.PERSON);
        } else {
            try {
                model.updateScheduleWithPerson(name, LocalDateTime.now(), ScheduleState.PERSON);
            } catch (PersonNotFoundException e) {
                return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_PERSON_NOT_FOUND)).build();
            }
            model.updateSidePanelDisplay(SidePanelDisplayType.PERSON);
        }

        return new CommandResultBuilder(MESSAGE_SUCCESS).build();
    }

    /**
     * Maps modules to events
     * @param model model object.
     * @param acadYear academic year.
     * @param startAcadSemDate start date of academic semester.
     * @param holidays Holidays object, containing holiday dates.
     * @return list mapped events.
     */
    private List<Event> mapModulesToEvents(Model model, AcadYear acadYear, LocalDate startAcadSemDate,
                                           Holidays holidays) {
        ArrayList<Event> eventsToAdd = new ArrayList<>();
        for (Map.Entry<ModuleCode, Map<LessonType, LessonNo>> entry : link.moduleLessonsMap.entrySet()) {
            ModuleCode moduleCode = entry.getKey();
            ModuleId moduleId = new ModuleId(acadYear, moduleCode);
            Module module = model.findModule(moduleId);
            Event e = mapModuleToEvent(module, startAcadSemDate, link.semesterNo,
                    entry.getValue(), holidays);
            eventsToAdd.add(e);
        }
        return eventsToAdd;
    }

    /**
     * Add events to a person's schedule.
     * @param person person to add events to.
     * @param eventsToAdd events to add to schedule.
     * @throws EventClashException if there is a clash in event to add and person's schedule.
     */
    private void addEventsToPerson(Person person, List<Event> eventsToAdd)
            throws EventClashException {
        if (checkClashingModuleEvents(eventsToAdd)) {
            throw new EventClashException();
        }

        for (Event event: eventsToAdd) {
            if (person.getSchedule().isClash(event)) {
                throw new EventClashException(event);
            }
        }

        for (Event event : eventsToAdd) {
            person.addEvent(event);
        }
    }

    private Person getPerson(Name name, Model model) throws PersonNotFoundException {
        Person person;
        if (name == null) {
            person = model.getUser();
        } else {
            person = model.findPerson(name);
        }
        return person;
    }

    /**
     * Checks if the modules to add have clashing timeslots between one another.
     */
    private boolean checkClashingModuleEvents(List<Event> eventsToAdd) {
        for (int i = 0; i < eventsToAdd.size() - 1; i++) {
            Event event = eventsToAdd.get(i);
            for (int j = i + 1; j < eventsToAdd.size(); j++) {
                Event otherEvent = eventsToAdd.get(j);
                if (event.isClash(otherEvent)) {
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public boolean equals(Command command) {
        return command == this // short circuit if same object
                || (command instanceof AddNusModsCommand // instanceof handles nulls
                && name.equals(((AddNusModsCommand) command).name)
                && link.equals(((AddNusModsCommand) command).link));
    }
}
