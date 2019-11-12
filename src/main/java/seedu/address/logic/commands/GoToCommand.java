package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FILES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PASSWORDS;

import seedu.address.commons.core.Mode;
import seedu.address.model.Model;


/**
 * Terminates the program.
 */
public class GoToCommand extends Command {

    public static final String COMMAND_WORD = "goto";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches to a specific mode. Parameters: MODE\n"
            + "SecureIT currently supports four modes: password, file, note, and card.";

    public static final String MESSAGE_GOTO_ACKNOWLEDGEMENT = "Switched to %s mode.";

    private final Mode mode;

    public GoToCommand(Mode mode) {
        this.mode = mode;
    }

    @Override
    public CommandResult execute(Model model) {
        model.updateFilteredPasswordList(PREDICATE_SHOW_ALL_PASSWORDS);
        model.updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);
        model.updateFilteredFileList(PREDICATE_SHOW_ALL_FILES);
        return CommandResult.builder(String.format(MESSAGE_GOTO_ACKNOWLEDGEMENT, mode))
                .isGoTo()
                .setMode(mode)
                .build();
    }

}
