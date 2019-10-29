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
import seedu.address.model.display.detailwindow.DetailWindowDisplayType;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.module.AcadYear;
import seedu.address.model.module.Holidays;
import seedu.address.model.module.LessonNo;
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
            + "Example Link: " + NusModsShareLink.EXAMPLE;

    public static final String MESSAGE_SUCCESS = "Added NUS modules to person's schedule: \n\n";
    public static final String MESSAGE_FAILURE = "Unable to add modules";
    public static final String MESSAGE_PERSON_NOT_FOUND = MESSAGE_FAILURE + ": unable to find person";
    public static final String MESSAGE_MODULE_NOT_FOUND = MESSAGE_FAILURE + ": unable to get all module details";
    public static final String MESSAGE_MODULES_CLASH = MESSAGE_FAILURE + ": there's a timing clash "
            + "between the modules you're adding!";
    public static final String MESSAGE_EVENTS_CLASH = MESSAGE_FAILURE + ": there's a timing clash"
            + "between the modules you're adding and some event in the person's schedule!";

    private final Name name;
    private final NusModsShareLink link;

    public AddNusModsCommand(Name name, NusModsShareLink link) {
        requireNonNull(name);
        requireNonNull(link);

        this.name = name;
        this.link = link;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        AcadYear acadYear = model.getAcadYear();

        Person person = null;
        try {
            person = model.findPerson(name);
        } catch (PersonNotFoundException e) {
            return new CommandResult(MESSAGE_PERSON_NOT_FOUND);
        }

        LocalDate startAcadSemDate = model.getAcadSemStartDate(acadYear, link.semesterNo);
        Holidays holidays = model.getHolidays();

        // translate module to event
        ArrayList<Event> eventsToAdd = new ArrayList<>();
        for (Map.Entry<ModuleCode, List<LessonNo>> entry : link.moduleLessonsMap.entrySet()) {
            ModuleCode moduleCode = entry.getKey();
            ModuleId moduleId = new ModuleId(acadYear, moduleCode);
            try {
                Module module = model.findModule(moduleId);
                Event e = mapModuleToEvent(module, startAcadSemDate, link.semesterNo,
                        entry.getValue(), holidays);
                eventsToAdd.add(e);
            } catch (ModuleNotFoundException e) {
                return new CommandResult(MESSAGE_MODULE_NOT_FOUND);
            } catch (ModuleToEventMappingException e) {
                return new CommandResult("Unable to add modules: " + e.getMessage());
            }
        }

        if (checkClashingModuleEvents(eventsToAdd)) {
            return new CommandResult(MESSAGE_MODULES_CLASH);
        }
        for (Event event : eventsToAdd) {
            try {
                if (model.isEventClash(name, event)) {
                    return new CommandResult(MESSAGE_EVENTS_CLASH);
                }
            } catch (PersonNotFoundException e) {
                return new CommandResult(MESSAGE_PERSON_NOT_FOUND);
            }

        }
        for (Event event : eventsToAdd) {
            try {
                model.addEvent(name, event);

            } catch (PersonNotFoundException e) {
                return new CommandResult(MESSAGE_PERSON_NOT_FOUND);
            } catch (EventClashException e) {
                return new CommandResult(MESSAGE_EVENTS_CLASH);
            }
        }

        // updates UI
        model.updateDetailWindowDisplay(name, LocalDateTime.now(), DetailWindowDisplayType.PERSON);
        model.updateSidePanelDisplay(SidePanelDisplayType.PERSONS);

        return new CommandResult(MESSAGE_SUCCESS + person.getSchedule());
    }

    /**
     * Checks if modules have clashing timeslots between one another.
     */

    private boolean checkClashingModuleEvents(ArrayList<Event> eventsToAdd) {
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
        return false;
    }
}
