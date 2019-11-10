package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_TYPE_AND_NUM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.util.ModuleEventMappingUtil.mapModuleToEvent;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import seedu.address.model.module.SemesterNo;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.EventClashException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.schedule.Event;

/**
 * Gets details about a module from NusMods
 */
public class AddNusModCommand extends Command {
    public static final String COMMAND_WORD = "addmod";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_NAME + "PERSON_NAME "
            + PREFIX_MODULE_CODE + "MODULE_CODE "
            + PREFIX_LESSON_TYPE_AND_NUM + "CLASS_TYPE_1:CLASS_NUMBER_1,CLASS_TYPE_2:CLASS_NUMBER_2,...\n";

    public static final String MESSAGE_SUCCESS = "Added module to person's schedule.";
    public static final String MESSAGE_FAILURE = "Unable to add module: %s";
    public static final String MESSAGE_PERSON_NOT_FOUND = "couldn't find person!";
    public static final String MESSAGE_MODULE_NOT_FOUND = "invalid module %s";
    public static final String MESSAGE_EVENTS_CLASH = "there is a timing clash between the module you're adding and"
            + " the events in the person's schedule!";
    public static final String MESSAGE_DUPLICATE_EVENT = "module already exists in the schedule";

    private final Name name;
    private final ModuleCode moduleCode;
    private final Map<LessonType, LessonNo> lessonTypeNumMap;

    public AddNusModCommand(Name name, ModuleCode moduleCode,
                            Map<LessonType, LessonNo> lessonTypeNumMap) {
        requireNonNull(moduleCode);
        requireNonNull(lessonTypeNumMap);
        this.name = name;
        this.moduleCode = moduleCode;
        this.lessonTypeNumMap = lessonTypeNumMap;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        AcadYear acadYear = model.getAcadYear();
        SemesterNo semesterNo = model.getSemesterNo();
        LocalDate startAcadSemDate = model.getAcadSemStartDate(acadYear, semesterNo);
        Holidays holidays = model.getHolidays();
        Event event;
        Module module;
        ModuleId moduleId = new ModuleId(acadYear, moduleCode);

        Person person;
        try {
            person = getPerson(name, model);
        } catch (PersonNotFoundException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_PERSON_NOT_FOUND)).build();
        }

        try {
            module = model.findModule(moduleId);
            event = mapModuleToEvent(module, startAcadSemDate, semesterNo, this.lessonTypeNumMap, holidays);
        } catch (ModuleNotFoundException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE,
                    String.format(MESSAGE_MODULE_NOT_FOUND, moduleCode))).build();
        } catch (ModuleToEventMappingException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE, e.getMessage())).build();
        }

        try {
            person.addEvent(event);
        } catch (EventClashException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_EVENTS_CLASH)).build();
        }

        // updates UI.
        if (name == null) {
            model.updateDisplayWithUser(LocalDateTime.now(), ScheduleState.PERSON);
            model.updateSidePanelDisplay(SidePanelDisplayType.PERSON);
        } else {
            try {
                model.updateDisplayWithPerson(name, LocalDateTime.now(), ScheduleState.PERSON);
            } catch (PersonNotFoundException e) {
                return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_PERSON_NOT_FOUND)).build();
            }
            model.updateSidePanelDisplay(SidePanelDisplayType.PERSON);
        }

        return new CommandResultBuilder(MESSAGE_SUCCESS).build();
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

    @Override
    public boolean equals(Command command) {
        return command == this // short circuit if same object
                || (command instanceof AddNusModCommand // instanceof handles nulls
                && name.equals(((AddNusModCommand) command).name)
                && moduleCode.equals(((AddNusModCommand) command).moduleCode)
                && lessonTypeNumMap.equals(((AddNusModCommand) command).lessonTypeNumMap));
    }
}
