package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.AcadCalendar;
import seedu.address.model.module.Holidays;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleCode;
import seedu.address.websocket.NusModApi;

/**
 * Import all NusMods data which Timebook requires to storage for offline compatibility.
 */
public class ImportNusModsCommand extends Command {
    public static final String COMMAND_WORD = "importmods";

    public static final String MESSAGE_USAGE = COMMAND_WORD;

    public static final String MESSAGE_COMPLETE = "Import complete: \n\n";

    public ImportNusModsCommand() {
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        NusModApi api = new NusModApi(model.getAppSettings().getAcadYear());

        // import academic calendar and holidays
        JSONObject academicCalendarObj = api.getAcademicCalendar();
        AcadCalendar acadCalendar = AcadCalendar.parseFromJson(academicCalendarObj);
        model.setAcademicCalendar(acadCalendar);;

        JSONArray holidaysArr = api.getHolidays();
        Holidays holidays = Holidays.parseFromJson(holidaysArr);
        model.setHolidays(holidays);

        // import list of detailed module timetable data
        JSONArray moduleListJsonArr = api.getModuleList();

        int numMods = moduleListJsonArr.size();
        int numModsInLocalStorage = 0;
        int numModsImportedFromApi = 0;
        int numModsFailed = 0;

        for (int i = 0; i < moduleListJsonArr.size(); i++) {
            JSONObject item = (JSONObject) moduleListJsonArr.get(i);
            ModuleCode moduleCode = new ModuleCode(item.get("moduleCode").toString());
            Module module = model.findModule(moduleCode);

            if (module == null) {
                JSONObject obj = api.getModule(moduleCode);
                if (obj == null) {
                    numModsFailed += 1;
                    continue;
                }
                module = new Module(obj);
                model.addModule(module);
                numModsImportedFromApi += 1;
            } else {
                numModsInLocalStorage += 1;
            }
        }

        return new CommandResult(MESSAGE_COMPLETE
                + "Total number of modules: " + numMods + "\n"
                + "Modules in local storage: " + numModsInLocalStorage + "\n"
                + "Modules imported from NUSMods API: " + numModsImportedFromApi + "\n"
                + "Modules failed to retrieve info of: " + numModsFailed);
    }

    @Override
    public boolean equals(Command command) {
        return false;
    }
}
