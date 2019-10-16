//@@author LeowWB

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE_PATH;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.util.ExportUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.category.Category;
import seedu.address.model.category.CategoryContainsAnyKeywordsPredicate;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.util.FilePath;

/**
 * Finds and lists all flashcards in address book whose question contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports your flashcards into a document file, "
            + "for easy sharing or use as a cheat sheet.\n"
            + "Parameters: "
            + PREFIX_CATEGORY + "CATEGORY "
            + PREFIX_FILE_PATH + "FILE_PATH\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CATEGORY + "CS2105 "
            + PREFIX_FILE_PATH + "C:\\Users\\damithc\\Documents\\CS2105_Cheat_Sheet.docx";

    private final Category category;
    private final FilePath filePath;

    public ExportCommand(Category category, FilePath filePath) {
        this.category = category;
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            ExportUtil.exportFlashCards(
                    getFlashCardsByCategory(model, category),
                    this.filePath
            );
        } catch (IOException e) {
            throw new CommandException(Messages.MESSAGE_EXPORT_IO_EXCEPTION);
        }

        return new CommandResult("Export was successful! You can find your file at "
                + "the following path:\n"
                + this.filePath);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && category.equals(((ExportCommand) other).category)
                && filePath.equals(((ExportCommand) other).filePath)); // state check
    }

    public static ObservableList<FlashCard> getFlashCardsByCategory(Model model, Category category) {
        model.updateFilteredFlashCardList(
                categoryToPredicate(category)
        );
        return model.getFilteredFlashCardList();
    }

    public static CategoryContainsAnyKeywordsPredicate categoryToPredicate(Category category) {
        return new CategoryContainsAnyKeywordsPredicate(
                categoryToKeywordList(
                        category
                )
        );
    }

    public static List<String> categoryToKeywordList(Category category) {
        String categoryName = category.categoryName;
        List<String> categoryKeywordList = Collections.singletonList(categoryName);
        return categoryKeywordList;
    }
}
