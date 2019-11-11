package seedu.jarvis.logic.commands.course;

import static java.util.Objects.requireNonNull;

import seedu.jarvis.logic.commands.Command;
import seedu.jarvis.logic.commands.CommandResult;
import seedu.jarvis.logic.commands.exceptions.CommandException;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.viewstatus.ViewType;
import seedu.jarvis.storage.history.commands.JsonAdaptedCommand;
import seedu.jarvis.storage.history.commands.exceptions.InvalidCommandToJsonException;

/**
 * Helps the user in the Course Planner.
 */
public class ShowCourseHelpCommand extends Command {
    public static final String COMMAND_WORD = "help-course";

    public static final String MESSAGE_HELP =
        "Here are the early core modules for students of undergraduate NUS Computer Science:\n\n"
        + "CS1101S: Programming Methodology\n"
        + "CS2030:  Programming Methodology II\n"
        + "CS2040S: Data Structures and Algorithms\n"
        + "CS2100:  Computer Organisation\n"
        + "CS203T:  Software Engineering\n"
        + "CS2105:  Introduction to Computer Networks\n"
        + "CS2106:  Introduction to Operating Systems\n"
        + "CS3230:  Design and Analysis of Algorithms\n"
        + "IS1103:  Ethics in Computing\n"
        + "CS2101:  Effective Communication for Computing Professionals\n"
        + "ES2660:  Communicating in the Modern Age\n"
        + "MA1521:  Calculus for Computing\n"
        + "MA1101R: Linear Algebra\n"
        + "ST2334:  Probability and Statistics\n\n"
        + "Complete at least 8 MCs of Computer Systems Team Project courses:\n\n"
        + "CS3230:  Software Engineering Project, or\n\n"
        + "CS3216:  Software Product Engineering for Digital Markets\n"
        + "CS3217:  Software Engineering on Modern Application Platforms, or\n\n"
        + "CS3281:  Thematic Systems Project I\n"
        + "CS3282:  Thematic Systems Project II\n\n"
        + "Plus 12 MCs of Industrial Experience Requirement and 24 MCs of CS modules, where:\n"
        + "\t1. 12 MCs are Level 4000 and above\n"
        + "\t2. Satisfies at least one CS Focus Area";

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
        model.setViewStatus(ViewType.LIST_COURSE);
        return new CommandResult(MESSAGE_SUCCESS, true);
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
