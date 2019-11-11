package seedu.tarence.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_FORMAT;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;
import java.util.Map;

import seedu.tarence.commons.core.Messages;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Assignment;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.storage.Storage;

/**
 * Displays the selected tutorial assignment
 */
public class DisplayAssignmentScoreCommand extends Command {
    public static final String COMMAND_WORD = "displayAssignmentScore";
    public static final String MESSAGE_SUCCESS = "Assignment is displayed!";
    public static final String[] TABLE_SYNONYMS = {"t", "table", "tab"};
    public static final String[] GRAPH_SYNONYMS = {"g", "graph"};

    private static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase(),
        "displayscore", "displayassignscore", "displayassign", "displayassignment"};

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays the tutorial assignment identified by the tutorial index, assignment name "
            + "and display format.\n"
            + "Parameters:\n"
            + PREFIX_INDEX + "TUTORIAL_INDEX "
            + PREFIX_NAME + "ASSIGNMENT_NAME "
            + PREFIX_FORMAT + "DISPLAY_FORMAT(t for table and g for graph)\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_NAME + "Lab 1 " + PREFIX_FORMAT + "t\n"
            + "Synonyms:\n"
            + String.join("\n", COMMAND_SYNONYMS);

    private Index tutorialIndex;
    private String assignmentName;
    private DisplayFormat displayFormat;

    /**
     * Constructor based on shortcut index format
     */
    public DisplayAssignmentScoreCommand(Index tutorialIndex, String assignmentName, DisplayFormat displayFormat) {
        requireAllNonNull(tutorialIndex, assignmentName, displayFormat);
        this.tutorialIndex = tutorialIndex;
        this.assignmentName = assignmentName;
        this.displayFormat = displayFormat;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Tutorial> lastShownTutorialList = model.getFilteredTutorialList();

        if (tutorialIndex.getZeroBased() >= lastShownTutorialList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX);
        }
        Tutorial tutorial = lastShownTutorialList.get(tutorialIndex.getZeroBased());
        Assignment assignment = getAssignment(tutorial);
        Map<Student, Integer> studentScores = tutorial.getAssignmentScores(assignment);

        return new CommandResult(String.format(MESSAGE_SUCCESS, tutorial), assignment, studentScores, displayFormat);
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        return execute(model);
    }

    private Assignment getAssignment(Tutorial tutorial) throws CommandException {
        List <Assignment> assignments = tutorial.getAssignments();
        for (Assignment assignment : assignments) {
            if (assignment.getAssignName().equals(assignmentName)) {
                return assignment;
            }
        }
        throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_IN_TUTORIAL);
    }

    /**
     * Returns true if user command matches command word or any defined synonyms, and false otherwise.
     *
     * @param userCommand command word from user.
     * @return whether user command matches specified command word or synonyms.
     */
    public static boolean isMatchingCommandWord(String userCommand) {
        for (String synonym : COMMAND_SYNONYMS) {
            if (synonym.equals(userCommand.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || ((other instanceof DisplayAssignmentScoreCommand // instanceof handles nulls
                && (displayFormat.equals(((DisplayAssignmentScoreCommand) other).displayFormat))
                && (tutorialIndex.equals(((DisplayAssignmentScoreCommand) other).tutorialIndex))
                && (assignmentName.equals(((DisplayAssignmentScoreCommand) other).assignmentName)))); // state check
    }
}
