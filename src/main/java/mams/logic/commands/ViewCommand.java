package mams.logic.commands;

import static java.util.Objects.requireNonNull;

import static mams.commons.core.Messages.MESSAGE_INVALID_APPEAL_DISPLAYED_INDEX;
import static mams.commons.core.Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX;
import static mams.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static mams.commons.util.CollectionUtil.isAnyNonNull;
import static mams.logic.parser.CliSyntax.PREFIX_APPEAL;
import static mams.logic.parser.CliSyntax.PREFIX_MODULE;
import static mams.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.List;

import mams.commons.core.index.Index;
import mams.logic.commands.exceptions.CommandException;
import mams.model.Model;
import mams.model.appeal.Appeal;
import mams.model.module.Module;
import mams.model.student.Student;

/**
 * Displays expanded view of items based on its index on the displayed list. Able to specify up to three MAMS items
 * (one from each type) to be displayed in a single command.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays expanded view of items "
            + "based on their indexes on the displayed lists. Specify up to three items "
            + "(one from each type) in a single " + COMMAND_WORD + " command.\n"
            + "Parameters: KEYWORD "
            + " [" + PREFIX_APPEAL + "INDEX" + "] "
            + " [" + PREFIX_MODULE + "INDEX" + "] "
            + "  [" + PREFIX_STUDENT + "INDEX" + "]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_APPEAL + "1 "
            + PREFIX_MODULE + "11 "
            + PREFIX_STUDENT + "10\n";

    public static final String MESSAGE_VIEW_SUCCESS = "Displayed items with ID: %1$s";

    private Index appealIndex;
    private Index moduleIndex;
    private Index studentIndex;

    /**
     * @param appealIndex Index of appeal on the displayed list to be expanded and viewed
     * @param moduleIndex Index of module on the displayed list to be expanded and viewed
     * @param studentIndex Index of student on the displayed list to be expanded and viewed
     */
    public ViewCommand(Index appealIndex, Index moduleIndex, Index studentIndex) {
        this.appealIndex = appealIndex;
        this.moduleIndex = moduleIndex;
        this.studentIndex = studentIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // ViewCommandParser should ensure that they're not all null
        assert isAnyNonNull(appealIndex, moduleIndex, studentIndex) : "ViewCommandParser should ensure "
                + "at least one is non-null";
        requireNonNull(model);
        StringBuilder displayedFeedback = new StringBuilder();

        List<Appeal> lastShownAppealList = model.getFilteredAppealList();
        List<Module> lastShownModuleList = model.getFilteredModuleList();
        List<Student> lastShownStudentList = model.getFilteredStudentList();

        verifyAllIndexesWithinBounds(lastShownAppealList, lastShownModuleList, lastShownStudentList);

        if (appealIndex != null) {
            Appeal appeal = lastShownAppealList.get(appealIndex.getZeroBased());
            model.updateFilteredAppealList(appeal::equals);
            displayedFeedback.append(appeal.getAppealId());
            displayedFeedback.append(" ");
        }
        if (moduleIndex != null) {
            Module module = lastShownModuleList.get(moduleIndex.getZeroBased());
            model.updateFilteredModuleList(module::equals);
            displayedFeedback.append(module.getModuleCode());
            displayedFeedback.append(" ");
        }
        if (studentIndex != null) {
            Student student = lastShownStudentList.get(studentIndex.getZeroBased());
            model.updateFilteredStudentList(student::equals);
            displayedFeedback.append(student.getMatricId());
            displayedFeedback.append(" ");
        }

        return new CommandResult(String.format(MESSAGE_VIEW_SUCCESS, displayedFeedback.toString().trim()));
    }

    /**
     * Verify that the supplied {@code Index} for appeal, module, and student in ViewCommand are all
     * within bounds of the currently displayed list on MAMS.
     *
     * @param lastShownAppealList appeal list to check {@code appealIndex} against
     * @param lastShownModuleList module list to check {@code moduleIndex} against
     * @param lastShownStudentList student list to check {@code studentIndex} against
     * @throws CommandException if any of the indexes are out of bounds within their specific lists
     */
    private void verifyAllIndexesWithinBounds(List<Appeal> lastShownAppealList,
                                              List<Module> lastShownModuleList,
                                              List<Student> lastShownStudentList) throws CommandException {
        StringBuilder errorMessage = new StringBuilder();
        boolean hasError = false;

        if (appealIndex != null && appealIndex.getZeroBased() >= lastShownAppealList.size()) {
            errorMessage.append(MESSAGE_INVALID_APPEAL_DISPLAYED_INDEX);
            errorMessage.append("\n");
            hasError = true;
        }
        if (moduleIndex != null && moduleIndex.getZeroBased() >= lastShownModuleList.size()) {
            errorMessage.append(MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
            errorMessage.append("\n");
            hasError = true;
        }
        if (studentIndex != null && studentIndex.getZeroBased() >= lastShownStudentList.size()) {
            errorMessage.append(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
            errorMessage.append("\n");
            hasError = true;
        }

        if (hasError) {
            throw new CommandException(errorMessage.toString());
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }

        // state check
        ViewCommand v = (ViewCommand) other;
        return appealIndex.equals(v.appealIndex)
                && moduleIndex.equals(v.moduleIndex)
                && studentIndex.equals(v.studentIndex);
    }
}
