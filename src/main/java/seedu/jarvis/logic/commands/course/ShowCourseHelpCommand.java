package seedu.jarvis.logic.commands.course;

import static java.util.Objects.requireNonNull;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;

/**
 * Helps the user in the Course Planner.
 */
public class ShowCourseHelpCommand extends Command {
    public static final String COMMAND_WORD = "help-course";

    public static final String MESSAGE_HELP =
        "Welcome to the Course Planner!\n"
            + "\n"
            + "If you wish to bring up this help message again, type:\n"
            + "\n"
            + "\thelp-course\n"
            + "\n"
            + "Otherwise, here are some commands you can try:\n"
            + "\n"
            + "\tadd-course c/CS2040\n"
            + "\tadd-course c/CS2100 c/CS2030\n"
            + "\tdelete-course 1\n"
            + "\tdelete-course c/GER1000\n"
            + "\tlookup c/CS3230\n"
            + "\tcheck c/CS2040\n";

    public static final String MESSAGE_SUCCESS = "Help for Course Planner displayed.";
    public static final String MESSAGE_NO_INVERSE = "The command " + COMMAND_WORD + " cannot be undone";
    public static final boolean HAS_INVERSE = false;

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public boolean hasInverseExecution() {
        return HAS_INVERSE;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.checkCourse(MESSAGE_HELP);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public CommandResult executeInverse(Model model) throws CommandException {
        throw new CommandException(MESSAGE_NO_INVERSE);
    }

    @Override
    public JsonAdaptedCommand adaptToJsonAdaptedCommand() throws InvalidCommandToJsonException {
        throw new InvalidCommandToJsonException();
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof ShowCourseHelpCommand;
    }
}
