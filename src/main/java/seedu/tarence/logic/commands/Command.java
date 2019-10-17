package seedu.tarence.logic.commands;

import java.util.List;
import java.util.stream.Collectors;

import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.logic.finder.Finder;
import seedu.tarence.model.Model;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.tutorial.TutName;

/**
 * Represents a command with hidden internal logic and the ability to be executed.
 */
public abstract class Command {

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    public abstract CommandResult execute(Model model) throws CommandException;

    /**
     * Returns true if command requires prior user input, else false.
     */
    public abstract boolean needsInput();

    /**
     * Returns true if prior command is required for execution, else false.
     * Only needed for commands that require prior user input.
     */
    public abstract boolean needsCommand(Command command);

    /**
     * Returns a list of {@code ModCode}s similar to the given one, and corresponding to a module containing a
     * target tutorial. Used for generating suggested corrections to modcode input errors by the user.
     *
     * @param modCode The incorrectly entered modcode from the user.
     * @param tutName The target tutorial that must be present in the suggested modules.
     * @param model The model in which to search.
     * @return a list of modcodes fulfilling the above criteria.
     * @throws CommandException if no similar modules are found.
     */
    List<ModCode> getSimilarModCodesWithTutorial (ModCode modCode, TutName tutName, Model model)
            throws CommandException {
        return new Finder(model).findSimilarModCodes(modCode)
                .stream().filter(similarModCode -> model.hasTutorialInModule(similarModCode, tutName))
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of {@code TutName}s similar to the given one, and corresponding to a tutorial belonging to a
     * target module. Used for generating suggested corrections to modcode input errors by the user.
     *
     * @param modCode The target module that must be present in the suggested tutorials.
     * @param tutName The incorrectly entered tutorial name from the user.
     * @param model The model in which to search.
     * @return a list of tutnames fulfilling the above criteria.
     * @throws CommandException if no similar tutorial names are found.
     */
    List<TutName> getSimilarTutNamesWithModule (ModCode modCode, TutName tutName, Model model)
            throws CommandException {
        return new Finder(model).findSimilarTutNames(tutName)
                .stream().filter(similarTutName -> model.hasTutorialInModule(modCode, similarTutName))
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of {@code Name}s similar to the given one, and corresponding to a student in a tutorial/module
     * combination. Used for generating suggested corrections to student name input errors by the user.
     * @param modCode The target module the suggested student must be in.
     * @param tutName The target tutorial the suggested student must be in.
     * @param studName The incorrectly entered student name from the user.
     * @param model The model in which to search.
     * @return a list of similar student names fulfilling the above criteria.
     * @throws CommandException if no similar students are found.
     */
    List<Name> getSimilarStudNamesWithTutorialAndModule (ModCode modCode, TutName tutName, Name studName, Model model)
            throws CommandException {
        return new Finder(model).findSimilarNames(studName)
                .stream()
                .filter(similarStudName -> model.hasStudentInTutorialAndModule(similarStudName, tutName, modCode))
                .collect(Collectors.toList());
    }
}
