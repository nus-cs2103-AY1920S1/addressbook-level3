package seedu.elisa.logic.commands;

import static seedu.elisa.logic.parser.CliSyntax.PREFIX_AUTO_RESCHEDULE;
import static seedu.elisa.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.elisa.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.elisa.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.elisa.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.elisa.commons.core.item.Item;

/**
 * Adds an Event to the item model.
 */
public class AddEventCommand extends AddCommand {

    public static final String SHOW_EVENT_VIEW = "E";
    public static final String COMMAND_WORD = "event";
    public static final String MESSAGE_SUCCESS = "Oh great, new Event added: %1$s \nDon't forget about it!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an Event to the Event list. \n"
            + "Parameters: \n"
            + "<Compulsory> " + "description \n"
            + "<Compulsory> " + PREFIX_DATETIME + " Event time \n"
            + "<Optional> " + PREFIX_REMINDER + " Reminder \n"
            + "<Optional> " + PREFIX_PRIORITY + " Priority \n"
            + "<Optional> " + PREFIX_TAG + " Tag \n"
            + "<Optional> " + PREFIX_AUTO_RESCHEDULE + " Period \n";

    public AddEventCommand(Item item) {
        super(item);
    }

    @Override
    public String getListView() {
        return SHOW_EVENT_VIEW;
    }

    @Override
    public String getMessageSuccess() {
        return MESSAGE_SUCCESS;
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
