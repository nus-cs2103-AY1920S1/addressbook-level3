package seedu.tarence.logic.parser;

import java.util.List;

import seedu.tarence.logic.commands.ImportCommand;
import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.tutorial.Tutorial;

/**
 * Parses input arguments and creates a new ImportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     * @throws ParseException if the user input does not match the expected formats for the module code.
     */
    public ImportCommand parse(String args) throws ParseException {
        List<Tutorial> importedTutorials;
        if (!validateUrlFormat(args)) {
            throw new ParseException(ImportCommand.MESSAGE_USAGE);
        }
        try {
            importedTutorials = NusModsParser.urlToTutorials(args);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            throw new ParseException(ImportCommand.MESSAGE_IMPORT_FAILURE);
        }
        return new ImportCommand(importedTutorials);
    }

    /**
     * Checks the validity of the url. Returns true if the url is valid, otherwise false.
     */
    public static boolean validateUrlFormat(String args) {
        int len = "https://nusmods.com/timetable/sem-1/share?".length();
        String url = args.trim();
        if (url.length() < len) {
            return false;
        }
        return url.substring(0, len).equals("https://nusmods.com/timetable/sem-1/share?")
                || url.substring(0, len).equals("https://nusmods.com/timetable/sem-2/share?");
    }
}

