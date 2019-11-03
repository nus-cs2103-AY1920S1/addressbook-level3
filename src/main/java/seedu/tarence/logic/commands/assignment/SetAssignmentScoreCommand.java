package seedu.tarence.logic.commands.assignment;

import static java.util.Objects.requireNonNull;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_SCORE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_NAME;

import java.util.Date;
import java.util.List;
import java.util.Map;

import seedu.tarence.commons.core.Messages;
import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.CommandResult;
import seedu.tarence.logic.commands.DisplayFormat;
import seedu.tarence.logic.commands.exceptions.CommandException;
import seedu.tarence.model.Model;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.student.Student;
import seedu.tarence.model.tutorial.Assignment;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.exceptions.InvalidScoreException;
import seedu.tarence.storage.Storage;

/**
 * Sets Student's score for a particular Assignment.
 * Keyword matching is case insensitive.
 */
public class SetAssignmentScoreCommand extends AssignmentCommand {

    public static final String MESSAGE_SET_SCORE_SUCCESS = "%1$s's score is now %2$s ";
    public static final String COMMAND_WORD = "setAssignmentScore";
    private static final String[] COMMAND_SYNONYMS = {COMMAND_WORD.toLowerCase(), "sets", "setscore",
        "setsc", "setassignscore"};

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the score for student.\n"
            + "Parameters:\n"
            + PREFIX_MODULE + "MODULE CODE "
            + PREFIX_TUTORIAL_NAME + "TUTORIAL NAME "
            + PREFIX_INDEX + "ASSIGNMENT INDEX "
            + PREFIX_INDEX + "STUDENT INDEX "
            + PREFIX_SCORE + "SCORE\n"
            + PREFIX_INDEX + "TUTORIAL INDEX "
            + PREFIX_INDEX + "ASSIGNMENT INDEX "
            + PREFIX_INDEX + "STUDENT INDEX "
            + PREFIX_SCORE + "SCORE\n"
            + "Note:\n"
            + "Tutorial, assignment and student index have to be entered in the order specified.\n"
            + "Example:\n"
            + COMMAND_WORD + " "
            + PREFIX_TUTORIAL_NAME + "Lab 1 "
            + PREFIX_MODULE + "CS1010 "
            + PREFIX_INDEX + "1 "
            + PREFIX_INDEX + "1 "
            + PREFIX_SCORE + "10\n"
            + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_INDEX + "1 "
            + PREFIX_INDEX + "1 "
            + PREFIX_SCORE + "10\n"
            + "Synonyms:\n"
            + String.join("\n", COMMAND_SYNONYMS);

    public SetAssignmentScoreCommand(ModCode modCode, TutName tutName, Index tutIndex, Index assignIndex,
            Index studentIndex, Integer score) {
        super(modCode, tutName, tutIndex, assignIndex, studentIndex, null, score, null, null);
    }

    public SetAssignmentScoreCommand() {
        super();
    }

    @Override
    public AssignmentCommand build(ModCode modCode, TutName tutName, Index tutIndex, Index assignIndex,
            Index studentIndex, String assignName, Integer score, Date startDate, Date endDate) {
        return new SetAssignmentScoreCommand(modCode, tutName, tutIndex, assignIndex, studentIndex, score);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Tutorial> lastShownTutorialList = model.getFilteredTutorialList();
        List<Student> lastShownStudentList = model.getFilteredStudentList();

        Tutorial targetTutorial = null;
        Assignment targetAssignment = null;
        Student targetStudent = null;

        // TODO: Consider cases with multiple matching tutorials, students?
        if (targetModCode.isPresent() && targetTutName.isPresent()) {
            // format with modcode and tutname
            targetTutorial = lastShownTutorialList.stream()
                    .filter(tut -> tut.getTutName().equals(targetTutName.get())
                    && tut.getModCode().equals(targetModCode.get()))
                    .findFirst()
                    .orElse(null);
            if (targetTutorial == null) {
                return handleSuggestedCommands(model,
                        new SetAssignmentScoreCommand());
            }
        } else if (targetTutIndex.isPresent()) {
            // format with tutorial index
            try {
                targetTutorial = lastShownTutorialList.get(targetTutIndex.get().getZeroBased());
            } catch (IndexOutOfBoundsException e) {
                throw new CommandException(
                    String.format(Messages.MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX));
            }
        }

        try {
            targetAssignment = targetTutorial.getAssignment(targetAssignIndex.get());
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException(Messages.MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX);
        }

        if (targetStudentIndex.get().getZeroBased() >= lastShownStudentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        targetStudent = lastShownStudentList.get(targetStudentIndex.get().getZeroBased());
        try {
            targetTutorial.setScore(targetAssignment, targetStudent, score.get());
        } catch (InvalidScoreException e) {
            throw new CommandException(e.getMessage());
        }

        Map<Student, Integer> scores = targetTutorial.getAssignmentScores(targetAssignment);

        return new CommandResult(
                    String.format(MESSAGE_SET_SCORE_SUCCESS, targetStudent.getName(), score.get()), targetAssignment,
                scores, DisplayFormat.GRAPH);
    }

    @Override
    public CommandResult execute(Model model, Storage storage) throws CommandException {
        return execute(model);
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
                || (other instanceof SetAssignmentScoreCommand // instanceof handles nulls
                && super.equals(other)); // state check
    }

}
