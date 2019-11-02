//@@author LeowWB

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPORT_PATH;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.export.ExportPath;
import seedu.address.model.export.JsonExportPath;
import seedu.address.model.export.JsonImportUtil;
import seedu.address.model.flashcard.FlashCard;

/**
 * Imports all {@code FlashCard}s from a specified file.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports your flashcards from a file.\n"
            + "Parameters: "
            + PREFIX_EXPORT_PATH + "FILE_PATH\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_EXPORT_PATH + "C:\\Users\\damithc\\Documents\\CS2105_Cheat_Sheet.docx";

    public static final String MESSAGE_IMPORT_SUCCESS = "Import was successful!";

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
            //TODO TODO oughta delegate to the exportpath.
            if (exportPath instanceof JsonExportPath) {
                return importFromJsonPath(
                        (JsonExportPath) exportPath,
                        model
                );
            } else {
                throw new CommandException("need json path");
            }
        } catch (IOException e) {
            throw new CommandException(e.getMessage());
        } catch (DataConversionException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && exportPath.equals(((ImportCommand) other).exportPath)); // state check
    }

    private CommandResult importFromJsonPath(JsonExportPath jsonExportPath, Model model)
            throws IOException, DataConversionException {
        Optional<List<FlashCard>> optionalList = JsonImportUtil.importFlashCardsFromJson(
                jsonExportPath
        );

        if (optionalList.isEmpty()) {
            return new CommandResult(
                    "cannot find eh"
            );
        } else {
            List<FlashCard> list = optionalList.get();

            for (FlashCard flashCard : list) {
                model.addFlashCard(flashCard);
            }

            return new CommandResult(
                    MESSAGE_IMPORT_SUCCESS
            );
        }
    }
}
