package seedu.address.logic.internal.nusmods;

import java.util.Optional;
import java.util.logging.Logger;

import org.json.simple.JSONArray;

import seedu.address.commons.core.AppSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleSummary;
import seedu.address.model.module.ModuleSummaryList;
import seedu.address.websocket.Cache;
import seedu.address.websocket.NusModsApi;
import seedu.address.websocket.NusModsParser;

/**
 * Internal class to be executed as a standalone program to import all NUSMods detailed module data.
 */
public class ImportMods {
    private static final Logger logger = LogsCenter.getLogger(Cache.class);

    /**
     * Main driver.
     */
    public static void main(String[] args) {
        importMods();
    }

    /**
     * Method to import detailed data of all nus modules for the default academic year.
     */
    public static void importMods() {
        NusModsApi api = new NusModsApi(AppSettings.DEFAULT_ACAD_YEAR);
        Optional<JSONArray> moduleSummaryJsonOptional = api.getModuleList();

        if (!moduleSummaryJsonOptional.isPresent()) {
            logger.severe("No module summaries, can't scrape all detailed modules.");
            return;
        }

        ModuleSummaryList moduleSummaries = NusModsParser.parseModuleSummaryList(
                moduleSummaryJsonOptional.get(), api.getAcadYear());

        // loop through every mod in moduleSummaries, attempt to load from Cache, which will call api and save to file
        // if module is missing.
        int total = moduleSummaries.getModuleSummaries().size();
        int found = 0;
        int failed = 0;
        int curr = 0;
        for (ModuleSummary modSummary : moduleSummaries.getModuleSummaries()) {
            curr += 1;
            Optional<Module> moduleOptional = Cache.loadModule(modSummary.getModuleId());

            if (!moduleOptional.isPresent()) {
                failed += 1;
                logger.severe("[" + curr + "/" + total + "] Hmm could not get detailed data for this module: "
                        + modSummary);
                break;
            } else {
                found += 1;
                logger.info("[" + curr + "/" + total + "] Found this module: " + modSummary);
            }
        }
        logger.info("Modules found/failed/total: [" + found + "/" + failed + "/" + total + "]");
    }
}
