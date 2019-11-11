package seedu.address.logic.internal.nusmods;

import java.util.Optional;
import java.util.logging.Logger;

import org.json.simple.JSONArray;

import seedu.address.commons.core.AppSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.AcadYear;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleList;
import seedu.address.model.module.ModuleSummary;
import seedu.address.model.module.ModuleSummaryList;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.websocket.Cache;
import seedu.address.websocket.NusModsApi;
import seedu.address.websocket.NusModsParser;

/**
 * Internal class to be executed as a standalone program to import all NUSMods detailed module data.
 */
public class ImportMods {
    private static final Logger logger = LogsCenter.getLogger(Cache.class);
    private static boolean isSilent = false; //if true, don't log, else log.

    /**
     * Main driver.
     */
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("silent")) {
            isSilent = true;
        }
        importMods(AppSettings.DEFAULT_ACAD_YEAR);
    }

    /**
     * Imports detailed data of all nus modules for the given academic year.
     * Incrementally caches each module into the detailed modules file.
     * To re-import all modules, delete the existing detailed modules file before executing this method.
     */
    private static void importMods(AcadYear year) {
        NusModsApi api = new NusModsApi(year);

        Optional<ModuleSummaryList> moduleSummariesOptional = getModuleSummariesFromApiThenCache(api, year);
        ModuleSummaryList moduleSummaries;
        if (moduleSummariesOptional.isPresent()) {
            moduleSummaries = moduleSummariesOptional.get();
        } else {
            return;
        }

        ModuleList moduleList = new ModuleList();
        Optional<ModuleList> moduleListOptional = Cache.loadModuleList();
        if (moduleListOptional.isPresent()) {
            moduleList = moduleListOptional.get();
        }

        importMods(moduleSummaries, moduleList);

    }

    /**
     * Imports detailed data of all modules in moduleSummaries. If a module already exists in moduleList, then ignore.
     * @param moduleSummaries ModuleSummaryList.
     * @param moduleList ModuleList.
     */
    private static void importMods(ModuleSummaryList moduleSummaries, ModuleList moduleList) {
        int foundFromFile = 0;
        int foundFromApi = 0;
        int failed = 0;
        int curr = 0;
        int total = moduleSummaries.getModuleSummaries().size();
        for (ModuleSummary modSummary : moduleSummaries.getModuleSummaries()) {
            curr += 1;
            try {
                moduleList.findModule(modSummary.getModuleId());
                foundFromFile += 1;
                if (!isSilent) {
                    logger.info("[" + curr + "/" + total + "] Found in file: " + modSummary);
                }
            } catch (ModuleNotFoundException e) {
                // Cache module if missing from moduleList
                Optional<Module> moduleOptional = Cache.loadModule(modSummary.getModuleId());
                if (!moduleOptional.isPresent()) {
                    failed += 1;
                    if (!isSilent) {
                        logger.severe("[" + curr + "/" + total + "] Hmm could not get detailed data for this module: "
                                + modSummary);
                    }
                    break;
                } else {
                    foundFromApi += 1;
                    if (!isSilent) {
                        logger.info("[" + curr + "/" + total + "] Found from API: " + modSummary);
                    }
                }
            }

        }
        if (!isSilent) {
            logger.info("Modules foundFromFile/foundFromApi/failed/total: [" + foundFromFile + "/"
                    + foundFromApi + "/" + failed + "/" + total + "]");
        }
    }

    /**
     * Gets a ModuleSummaryList from API, if fails then go to cache.
     * @param api NusModsApi instance.
     * @param year AcadYear.
     * @return Optional of ModuleSummaryList or empty.
     */
    private static Optional<ModuleSummaryList> getModuleSummariesFromApiThenCache(NusModsApi api, AcadYear year) {
        Optional<JSONArray> moduleSummaryJsonOptional = api.getModuleList();
        Optional<ModuleSummaryList> moduleSummaryListOptional = Optional.empty();
        if (moduleSummaryJsonOptional.isPresent()) {
            try {
                moduleSummaryListOptional = Optional.of(NusModsParser.parseModuleSummaryList(
                        moduleSummaryJsonOptional.get(), year));
            } catch (ParseException e) {
                if (!isSilent) {
                    logger.info("Failed to parse module summaries: " + e.getMessage());
                }
            }
        } else {
            moduleSummaryListOptional = Cache.loadModuleSummaryList();
            if (!moduleSummaryListOptional.isPresent()) {
                if (!isSilent) {
                    logger.severe("No module summaries, can't scrape all detailed modules.");
                }
            }
        }
        return moduleSummaryListOptional;
    }
}
