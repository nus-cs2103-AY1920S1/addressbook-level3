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
import seedu.address.model.display.schedulewindow.ScheduleWindowDisplayType;
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
            + "[" + PREFIX_LESSON_TYPE_AND_NUM + "CLASS_TYPE_1:CLASS_NUMBER_1,CLASS_TYPE_2:CLASS_NUMBER_2,]...\n";

    public static final String MESSAGE_SUCCESS = "Added module to person's schedule: \n\n";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Unable to find person";
    public static final String MESSAGE_MODULE_NOT_FOUND = "Unable to find module";
    public static final String MESSAGE_EVENTS_CLASH = "Unable to add module - there is a timing clash "
            + "between the module you're adding and the events in the person's schedule!";

    private final Name name;
    private final ModuleCode moduleCode;
    private final Map<LessonType, LessonNo> lessonTypeNumMap;

    public AddNusModCommand(Name name, ModuleCode moduleCode,
                            Map<LessonType, LessonNo> lessonTypeNumMap) {
        this.name = name;
        this.moduleCode = moduleCode;
        this.lessonTypeNumMap = lessonTypeNumMap;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person person;
        try {
            person = model.findPerson(name);
        } catch (PersonNotFoundException pnfe) {
            return new CommandResult(MESSAGE_PERSON_NOT_FOUND);
        }

        AcadYear acadYear = model.getAcadYear();
        SemesterNo semesterNo = model.getSemesterNo();
        LocalDate startAcadSemDate = model.getAcadSemStartDate(acadYear, semesterNo);
        Holidays holidays = model.getHolidays();
        Event event;
        Module module;
        ModuleId moduleId = new ModuleId(acadYear, moduleCode);

        try {
            module = model.findModule(moduleId);
            event = mapModuleToEvent(module, startAcadSemDate, semesterNo,
                    this.lessonTypeNumMap, holidays);
        } catch (ModuleNotFoundException e) {
            return new CommandResult(MESSAGE_MODULE_NOT_FOUND);
        } catch (ModuleToEventMappingException e) {
            return new CommandResult("Unable to add module: " + e.getMessage());
        }

        try {
            person.addEvent(event);
        } catch (EventClashException e) {
            return new CommandResult(MESSAGE_EVENTS_CLASH);
        }
        // updates UI
        model.updateScheduleWindowDisplay(name, LocalDateTime.now(), ScheduleWindowDisplayType.PERSON);
        model.updateSidePanelDisplay(SidePanelDisplayType.PERSON);

        return new CommandResult(MESSAGE_SUCCESS + person.getSchedule());
    }

    @Override
    public boolean equals(Command command) {
        if (command == null) {
            return false;
        } else if (!(command instanceof AddNusModCommand)) {
            return false;
        } else if (((AddNusModCommand) command).moduleCode.equals(this.moduleCode)) {
            return true;
        } else {
            return false;
        }
    }

}
