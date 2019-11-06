//@@author LeowWB

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPORT_PATH;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.category.Category;
import seedu.address.model.category.CategoryContainsAnyKeywordsPredicate;
import seedu.address.model.export.ExportPath;
import seedu.address.model.flashcard.FlashCard;

/**
 * Exports all {@code FlashCard}s whose category matches the supplied argument keyword. Keyword matching is case
 * insensitive. FlashCards will have their questions and answers copied to a specified file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports your FlashCards into a file, "
            + "for easy sharing or use as a cheat sheet.\n"
            + "Parameters: "
            + PREFIX_CATEGORY + "CATEGORY "
            + PREFIX_EXPORT_PATH + "FILE_PATH\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CATEGORY + "CS2105 "
            + PREFIX_EXPORT_PATH + "C:\\Users\\damithc\\Documents\\CS2105_Cheat_Sheet.docx";

    public static final String MESSAGE_EXPORT_SUCCESS = "Export was successful! You can find your file at "
            + "the following path:\n%s";

    private final Category category;
    private final ExportPath exportPath;

    /**
     * Creates a new ExportCommand with the given Category and ExportPath.
     * @param category The Category from which the FlashCards will be exported
     * @param exportPath The ExportPath to which the FlashCards will be exported
     */
    public ExportCommand(Category category, ExportPath exportPath) {
        this.category = category;
        this.exportPath = exportPath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            this.exportPath.export(
                    getFlashCardsByCategory(model, category)
            );
        } catch (IOException e) {
            throw new CommandException(Messages.MESSAGE_EXPORT_IO_EXCEPTION);
        }

        return new CommandResult(
                String.format(
                        MESSAGE_EXPORT_SUCCESS,
                        exportPath.toAbsolutePathString()
                )
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && category.equals(((ExportCommand) other).category)
                && exportPath.equals(((ExportCommand) other).exportPath)); // state check
    }

    /**
     * Gets a list of FlashCards by category. Does not result in any change to model. Helper method for execute().
     * @param model The model from which the FlashCards will be obtained
     * @param category The desired category
     * @return ObservableList of the FlashCards in the model that match the Category.
     */
    public static ObservableList<FlashCard> getFlashCardsByCategory(Model model, Category category) {
        return model.getFilteredFlashCardListNoCommit(
                categoryToPredicate(
                        category
                )
        );
    }

    /**
     * Converts a given Category to a CategoryContainsAnyKeywordsPredicate.
     *
     * @param category The Category to convert.
     * @return CategoryContainsAnyKeywordsPredicate representing the Category.
     */
    public static CategoryContainsAnyKeywordsPredicate categoryToPredicate(Category category) {
        return new CategoryContainsAnyKeywordsPredicate(
                categoryToKeywordList(
                        category
                )
        );
    }

    /**
     * Converts a given Category to a singleton Category List.
     *
     * @param category The Category to convert into a List.
     * @return Singleton List containing the given Category.
     */
    public static List<String> categoryToKeywordList(Category category) {
        String categoryName = category.categoryName;
        List<String> categoryKeywordList = Collections.singletonList(categoryName);
        return categoryKeywordList;
    }
}
