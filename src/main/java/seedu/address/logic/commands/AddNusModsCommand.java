package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LINK;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.LessonNo;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.NusModsShareLink;
import seedu.address.model.module.Semester;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.schedule.Event;
import seedu.address.websocket.NusModApi;

/**
 * Add an NusMods timetable to a person's schedule.
 */
public class AddNusModsCommand extends Command {

    public static final String COMMAND_WORD = "addmods";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_LINK + "NUSMODS_SHARE_LINK\n"
            + "Example Link: " + NusModsShareLink.EXAMPLE;

    public static final String MESSAGE_SUCCESS = "Added NUS modules to person's schedule: \n\n";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Unable to find person";
    public static final String MESSAGE_MODULE_NOT_FOUND = "Unable to get all module details";

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

        // find person with name
        Person person = model.findPerson(name);
        if (person == null) {
            return new CommandResult(MESSAGE_PERSON_NOT_FOUND);
        }

        // translate module to event
        ArrayList<Event> eventsToAdd = new ArrayList<>();
        for (Map.Entry<ModuleCode, List<LessonNo>> entry : link.moduleLessonsMap.entrySet()) {
            ModuleCode moduleCode = entry.getKey();
            Module module = model.getModuleList().findModule(moduleCode);

            // call api if module not in internal storage
            if (module == null) {
                NusModApi api = new NusModApi();
                JSONObject obj = api.getModule(moduleCode);
                if (obj == null) {
                    return new CommandResult(MESSAGE_MODULE_NOT_FOUND);
                } else {
                    module = new Module(obj);
                }
            }

            List<LessonNo> lessonNos = entry.getValue();

            eventsToAdd.add(module.toEvent(link.semesterNo, lessonNos));
        }

        for (Event event : eventsToAdd) {
            person.addEvent(event);
        }

        return new CommandResult(MESSAGE_SUCCESS + person.getSchedule());
    }

    @Override
    public boolean equals(Command command) {
        return false;
    }
}
