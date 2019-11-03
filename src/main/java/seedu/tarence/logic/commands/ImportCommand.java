package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.storage.Storage;

/**
 * Marks attendance of student in a specified tutorial.
 * Keyword matching is case insensitive.
 */
public class ImportCommand extends Command {
    public static final String MESSAGE_IMPORT_SUCCESS = "Import success";
    public static final String MESSAGE_IMPORT_FAILURE = "Import failed: Check if the url is formatted correctly."
            + "Otherwise, the tutorials cannot be found.";
    public static final String COMMAND_WORD = "import";

    private static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase(), "importtutorials",
        "importmods", "importtutorial", "importtuts"};

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports tutorials via an NUSMods shared url.\n"
            + "Note:\n"
            + "Expect a slow response the first time this command is inputted.\n"
            + "Parameters:\n"
            + "NUSMODS URL\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + "https://nusmods.com/timetable/sem-1/share?CS1231=TUT:08,SEC:2&IS1103=TUT:19,SEC:1\n"
            + "Synonyms:\n"
            + String.join("\n", COMMAND_SYNONYMS);

    private List<Tutorial> importedTutorials;

    public ImportCommand(List<Tutorial> importedTutorials) {
        this.importedTutorials = importedTutorials;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        for (Tutorial tutorial : importedTutorials) {
            if (model.hasTutorial(tutorial)) {
                continue;
            }
            model.addTutorial(tutorial);
            if (!model.hasModuleOfCode(tutorial.getModCode())) {
                model.addModule(new Module(tutorial.getModCode(), new ArrayList<>()));
            }
            model.addTutorialToModule(tutorial);
        }
        return new CommandResult(String.format(MESSAGE_IMPORT_SUCCESS));
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        return execute(model);
    }

    /**
     * Returns true if user command matches command word or any defined synonyms, and false otherwise.
     *
     * @param userCommand command word from user.
     * @return whether user command matches specified command word or synonyms.
     */
    public static boolean isMatchingCommandWord(String userCommand) {
        for (String synonym : COMMAND_SYNONYMS) {
            if (synonym.equals(userCommand.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && importedTutorials.equals(((ImportCommand) other).importedTutorials)); // state check
    }
}
