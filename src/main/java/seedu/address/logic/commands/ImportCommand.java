//@@author LeowWB

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPORT_PATH;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.export.ExportPath;
import seedu.address.model.flashcard.FlashCard;

/**
 * Imports all {@code FlashCard}s from a specified file.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports your FlashCards from a file.\n"
            + "Parameters: "
            + PREFIX_EXPORT_PATH + "FILE_PATH\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EXPORT_PATH + "C:\\Users\\damithc\\Documents\\CS2105_Cheat_Sheet.docx";

    public static final String MESSAGE_IMPORT_EMPTY_OPTIONAL =
            "Could not find any FlashCards to import. Are you sure you got the path correct?";
    public static final String MESSAGE_IMPORT_DATA_CONVERSION_EXCEPTION =
            "There was an error in reading from the file. Perhaps it was corrupted?";
    public static final String MESSAGE_IMPORT_SUCCESS =
            "Import was successful! Number of FlashCards imported: %d\n";
    public static final String MESSAGE_IMPORT_DUPLICATES =
            "%d duplicate FlashCard(s) were not imported.";
    public static final String MESSAGE_IMPORT_ALL_DUPLICATES =
            "There are no new FlashCards to import from that file.";

    private final ExportPath exportPath;

    /**
     * Creates a new ImportCommand with the given ExportPath.
     * @param exportPath The ExportPath from which the FlashCards will be imported.
     */
    public ImportCommand(ExportPath exportPath) {
        this.exportPath = exportPath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            return applyImport(
                    model,
                    exportPath.importFrom()
            );
        } catch (DataConversionException e) {
            throw new CommandException(MESSAGE_IMPORT_DATA_CONVERSION_EXCEPTION);
        } catch (UnsupportedOperationException e) {
            throw new CommandException(e.getMessage());
        } catch (CommandException e) {
            throw e;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && exportPath.equals(((ImportCommand) other).exportPath)); // state check
    }

    /**
     * Helper function to add an imported List of {@code FlashCard}s to a {@code Model}. This imported List comes in an
     * Optional. A CommandException will be thrown if the Optional is empty.
     *
     * @param model The Model to be updated
     * @param optionalList An Optional List of {@code FlashCard}s to add to the Model.
     * @return CommandResult representing the operation's success.
     * @throws CommandException If the Optional List turns out to be empty.
     */
    private CommandResult applyImport(Model model, Optional<List<FlashCard>> optionalList) throws CommandException {
        verifyOptionalFlashCardListPresent(optionalList, MESSAGE_IMPORT_EMPTY_OPTIONAL);
        requireNonNull(model);

        int successCount = 0;
        int duplicateCount = 0;
        List<FlashCard> list = optionalList.get();

        for (FlashCard flashCard : list) {
            if (model.hasFlashcard(flashCard)) {
                duplicateCount++;
            } else {
                model.addFlashCard(flashCard);
                successCount++;
            }
        }

        return new CommandResult(
                formatCommandResultString(successCount, duplicateCount)
        );
    }

    /**
     * Verifies that an Optional List of {@code FlashCard}s is present. Throws a CommandException otherwise.
     * @param optionalList Optional List of {@code FlashCard}s
     * @param message Message to be thrown with the CommandException if the Optional turns out to be empty
     * @throws CommandException If the Optional is empty
     */
    private void verifyOptionalFlashCardListPresent(Optional<List<FlashCard>> optionalList, String message)
            throws CommandException {
        if (optionalList == null || optionalList.isEmpty()) {
            throw new CommandException(
                message
            );
        }
    }

    /**
     * Helper function to format the String that will be returned in the CommandResult of an ImportCommand.
     *
     * @param successCount Number of {@code FlashCard}s that were successfully imported
     * @param duplicateCount Number of {@code FlashCard}s that were found to be duplicates (already present in Model)
     * @return String representing the formatted success message
     */
    private String formatCommandResultString(int successCount, int duplicateCount) {
        assert successCount >= 0;
        assert duplicateCount >= 0;

        if (successCount == 0) {
            return MESSAGE_IMPORT_ALL_DUPLICATES;
        }

        String returnValue = String.format(
                MESSAGE_IMPORT_SUCCESS,
                successCount
        );

        if (duplicateCount != 0) {
            returnValue += String.format(
                    MESSAGE_IMPORT_DUPLICATES,
                    duplicateCount
            );
        }

        return returnValue;
    }
}
