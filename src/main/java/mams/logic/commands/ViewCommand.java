package mams.logic.commands;

import static mams.logic.parser.ViewCommandParser.PREFIX_APPEALS;
import static mams.logic.parser.ViewCommandParser.PREFIX_MODS;
import static mams.logic.parser.ViewCommandParser.PREFIX_STUDENTS;

public class ViewCommand {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays expanded view of a single item "
            + "specified by its index on the displayed list, or its unique ID. You can display up to three items\n"
            + "(one from each type) in a single " + COMMAND_WORD + " command.\n"
            + "Parameters: KEYWORD "
            + "[" + PREFIX_APPEALS + "INDEX_OR_APPEAL_ID" + "] "
            + "[" + PREFIX_MODS + "INDEX_OR_MODULE_ID" + "] "
            + "[" + PREFIX_STUDENTS + "INDEX_OR_STUDENT_ID" + "]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_APPEALS + "1"
            + PREFIX_MODS + "CS2103"
            + PREFIX_STUDENTS + "10\n";
}
