package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXPORT_ICS_SUCCESS;

import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.ics.IcsException;
import seedu.address.ics.IcsExporter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.ui.UserOutput;

//@@author marcusteh1238
/**
 * Represents a Command which exports Events stored in Horo into an Ics file.
 */
public class ExportIcsCommand extends Command {
    private static final Path DEFAULT_DIRECTORY = Paths.get("");

    private final String filepath;
    private final ModelManager model;

    //@@author marcusteh1238
    ExportIcsCommand(ExportIcsCommandBuilder builder) {
        this.model = builder.getModel();
        this.filepath = getFilePath(builder);
    }

    //@@author marcusteh1238
    private String getFilePath(ExportIcsCommandBuilder builder) {
        String directory = builder.getOptionDirectory();
        String filename = IcsExporter.getExportFileName();
        if (directory != null) {
            return Paths.get(directory).resolve(filename).toString();
        } else {
            return DEFAULT_DIRECTORY.resolve(filename).toString();
        }
    }

    //@@author marcusteh1238
    public static CommandBuilder newBuilder(ModelManager model) {
        return new ExportIcsCommandBuilder(model).init();
    }

    //@@author marcusteh1238
    @Override
    public UserOutput execute() throws CommandException {
        try {
            IcsExporter icsExporter = new IcsExporter(model);
            icsExporter.export(filepath);
            return new UserOutput(String.format(MESSAGE_EXPORT_ICS_SUCCESS, filepath));
        } catch (IcsException e) {
            throw new CommandException(e.getMessage());
        }
    }
}
