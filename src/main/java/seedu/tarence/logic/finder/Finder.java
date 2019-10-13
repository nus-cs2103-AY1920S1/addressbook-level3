package seedu.tarence.logic.finder;

import static seedu.tarence.logic.commands.AddTutorialCommand.MESSAGE_INVALID_MODULE;

import java.util.ArrayList;
import java.util.List;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * Provides methods for searching for objects similar to a user's input when an exact match is not found.
 * Uses fuzzy string matching to search for names with a high degree of similarity.
 */
public class Finder {

    private Model model;

    // thresholds for similarity differ between parameters due to their varying expected lengths
    private int thresholdModCode = 80;
    private int thresholdTutName = 70;

    public Finder(Model model) {
        this.model = model;
    }

    /**
     * Searches for modules in the application with a {@code ModCode} similar to the given one.
     * @param modCode target module code to match against.
     * @return a {@code List} of similar {@code ModCode}s.
     */
    public List<ModCode> findSimilarModCodes (ModCode modCode) throws CommandException {
        List<ModCode> similarModCodes = new ArrayList<>();
        for (Module module : model.getFilteredModuleList()) {
            if (FuzzySearch.ratio(modCode.toString(), module.getModCode().toString()) > thresholdModCode) {
                similarModCodes.add(module.getModCode());
            }
        }
        if (similarModCodes.size() == 0) {
            throw new CommandException(MESSAGE_INVALID_MODULE);
        }
        return similarModCodes;
    }

    /**
     * Searches for tutorials in the application with a {@code TutName} similar to the given one.
     * @param tutName target tutorial name to match against.
     * @return a {@code List} of similar {@code TutName}s.
     */
    public List<TutName> findSimilarTutNames (TutName tutName) throws CommandException {
        List<TutName> similarTutNames = new ArrayList<>();
        for (Tutorial tutorial : model.getApplication().getTutorialList()) {
            if (FuzzySearch.ratio(tutName.toString(), tutorial.getTutName().toString()) > thresholdTutName) {
                similarTutNames.add(tutorial.getTutName());
            }
        }
        if (similarTutNames.size() == 0) {
            throw new CommandException(MESSAGE_INVALID_MODULE);
        }
        return similarTutNames;
    }

}
