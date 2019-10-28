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
import java.util.Optional;

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

    private ViewCommandParameters params;

    public ViewCommand(ViewCommandParameters params) {
        this.params = params;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Defensive check: ViewCommandParser should have ensured at least one parameter is present
        if (!params.isAtLeastOneParameterPresent()) {
            throw new CommandException(MESSAGE_USAGE);
        }

        requireNonNull(model);
        StringBuilder displayedFeedback = new StringBuilder();

        List<Appeal> lastShownAppealList = model.getFilteredAppealList();
        List<Module> lastShownModuleList = model.getFilteredModuleList();
        List<Student> lastShownStudentList = model.getFilteredStudentList();

        verifyAllIndexesWithinBounds(lastShownAppealList, lastShownModuleList, lastShownStudentList);

        if (params.getAppealIndex().isPresent()) {
            Appeal appeal = lastShownAppealList.get(params.getAppealIndex().get().getZeroBased());
            model.updateFilteredAppealList(appeal::equals);
            displayedFeedback.append(appeal.getAppealId());
            displayedFeedback.append(" ");
        }
        if (params.getModuleIndex().isPresent()) {
            Module module = lastShownModuleList.get(params.getModuleIndex().get().getZeroBased());
            model.updateFilteredModuleList(module::equals);
            displayedFeedback.append(module.getModuleCode());
            displayedFeedback.append(" ");
        }
        if (params.getStudentIndex().isPresent()) {
            Student student = lastShownStudentList.get(params.getStudentIndex().get().getZeroBased());
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
        boolean indexOutOfBounds = false;

        if (params.getAppealIndex().isPresent()
                && params.getAppealIndex().get().getZeroBased() >= lastShownAppealList.size()) {
            errorMessage.append(MESSAGE_INVALID_APPEAL_DISPLAYED_INDEX);
            errorMessage.append("\n");
            indexOutOfBounds = true;
        }
        if (params.getModuleIndex().isPresent()
                && params.getModuleIndex().get().getZeroBased() >= lastShownModuleList.size()) {
            errorMessage.append(MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
            errorMessage.append("\n");
            indexOutOfBounds = true;
        }
        if (params.getStudentIndex().isPresent()
                && params.getStudentIndex().get().getZeroBased() >= lastShownStudentList.size()) {
            errorMessage.append(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
            errorMessage.append("\n");
            indexOutOfBounds = true;
        }

        if (indexOutOfBounds) {
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
        return params.equals(v.params);
    }

    /**
     * Stores the details of the parsed parameters that a {@code ViewCommand} will operate on.
     * This helps to avoid having too many unnecessary constructors (or passing of null-values)
     * caused by the optional/combinatory nature of the parameters passed to ViewCommand.
     */
    public static class ViewCommandParameters {
        private Index appealIndex;
        private Index moduleIndex;
        private Index studentIndex;

        public void setAppealIndex(Index appealIndex) {
            this.appealIndex = appealIndex;
        }

        public void setModuleIndex(Index moduleIndex) {
            this.moduleIndex = moduleIndex;
        }

        public void setStudentIndex(Index studentIndex) {
            this.studentIndex = studentIndex;
        }

        public Optional<Index> getAppealIndex() {
            return Optional.ofNullable(appealIndex);
        }

        public Optional<Index> getModuleIndex() {
            return Optional.ofNullable(moduleIndex);
        }

        public Optional<Index> getStudentIndex() {
            return Optional.ofNullable(studentIndex);
        }

        public boolean isAtLeastOneParameterPresent() {
            return isAnyNonNull(appealIndex, moduleIndex, studentIndex);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof ViewCommandParameters)) {
                return false;
            }

            // state check
            ViewCommandParameters vp = (ViewCommandParameters) other;

            return getAppealIndex().equals(vp.getAppealIndex())
                    && getModuleIndex().equals(vp.getModuleIndex())
                    && getStudentIndex().equals(vp.getStudentIndex());
        }
    }
}
