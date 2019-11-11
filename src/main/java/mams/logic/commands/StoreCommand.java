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
    public static final String COMMAND_RESTORE = "restore";

    public static final String MESSAGE_USAGE_BACKUP = COMMAND_BACKUP
            + ": Creates a backup with specified tag \n"
            + "Use an alphanumeric (excluding \"undo\" and \"redo\") for the backup tag\n"
            + "The file will be prepended with \"mamshistory_\"\n"
            + "If no tags are present, a timestamp will be used instead\n"
            + "Example: " + COMMAND_BACKUP
            + " " + PREFIX_TAG + "TAG NUMBER";

    public static final String MESSAGE_USAGE_UNDO = COMMAND_UNDO
            + ": Undo last action";
    public static final String MESSAGE_USAGE_REDO = COMMAND_REDO
            + "Undo last undo";

    public static final String MESSAGE_USAGE_RESTORE = COMMAND_RESTORE
            + ": Restores backup with given tag if it exists\n"
            + "Use an existing tag (excluding \"undo\" and \"redo\")\n"
            + "Example: " + COMMAND_RESTORE
            + " " + PREFIX_TAG + "TAG NUMBER";

    public static final String MESSAGE_MISSING_BACKUP_TAG = "Please enter 1 valid tag number";


}
