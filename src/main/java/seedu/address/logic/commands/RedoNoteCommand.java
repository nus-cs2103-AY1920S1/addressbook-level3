package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.note.Note;
import seedu.address.model.note.exceptions.InvalidRedoException;

/**
 * Terminates the program.
 */
public class RedoNoteCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_REDO_ACKNOWLEDGEMENT = "Successfully redone command: %1$s";
    public static final String MESSAGE_REDO_FAILED = "No commands to redo!";

    @Override
    public CommandResult execute(Model model) {
        try {
            String redoneCommand = model.redoNote();
            if (isOpenCommand(redoneCommand)) {
                return redoOpenNoteCommand(redoneCommand, model);
            } else {
                return new CommandResult(String.format(MESSAGE_REDO_ACKNOWLEDGEMENT, redoneCommand));
            }
        } catch (InvalidRedoException | ParseException e) {
            return new CommandResult(MESSAGE_REDO_FAILED);
        }
    }

    /**
     * Checks if command is Open command.
     * @return true if command is Open command, else return false.
     */
    private boolean isOpenCommand(String command) {
        if (command.split(" ")[0].equals("open")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Recreates the openNoteCommand when redo-ing an open note command.
     */
    private CommandResult redoOpenNoteCommand(String redoneCommand, Model model) throws ParseException {
        Index index = ParserUtil.parseIndex(redoneCommand.split(" ")[1]);
        List<Note> lastShownList = model.getFilteredNoteList();
        Note noteToRead = lastShownList.get(index.getZeroBased());
        return CommandResult.builder(String.format(MESSAGE_REDO_ACKNOWLEDGEMENT, redoneCommand))
                .setIndex(index)
                .setObject(noteToRead)
                .read()
                .build();
    }

}
