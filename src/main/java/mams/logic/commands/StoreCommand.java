package mams.logic.commands;

import static mams.logic.parser.CliSyntax.PREFIX_TAG;


/**
 * Abstract class for AddModCommand and RemoveModCommand
 */
public abstract class StoreCommand extends Command {

    public static final String COMMAND_STORE = "save";
    public static final String COMMAND_UNDO = "undo";
    public static final String COMMAND_REDO = "redo";
    public static final String COMMAND_BACKUP = "backup";
    public static final String MESSAGE_USAGE_STORE = COMMAND_STORE + ": Stores current state in memory for undo/redo ";

    public static final String MESSAGE_USAGE_BACKUP = COMMAND_BACKUP
            + ": Creates a backup with specified tag "
            + "use a number for the backup tag"
            + "Example: " + COMMAND_BACKUP
            + PREFIX_TAG + "TAG NUMBER";
    public static final String MESSAGE_USAGE_UNDO = COMMAND_UNDO
            + ": Undo last action";
    public static final String MESSAGE_USAGE_REDO = COMMAND_REDO
            + "Undo last undo";

    public static final String MESSAGE_MISSING_BACKUP_TAG = "Please enter 1 valid tag number";


}
