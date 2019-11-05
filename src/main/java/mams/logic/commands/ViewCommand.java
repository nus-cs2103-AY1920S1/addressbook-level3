package mams.logic.commands;

import static java.util.Objects.requireNonNull;

import static mams.commons.core.Messages.MESSAGE_INVALID_APPEAL_DISPLAYED_INDEX;
import static mams.commons.core.Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX;
import static mams.commons.core.Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX;
import static mams.commons.util.CollectionUtil.isAnyNonNull;
import static mams.commons.util.CollectionUtil.requireAllNonNull;
import static mams.logic.parser.CliSyntax.PREFIX_APPEAL;
import static mams.logic.parser.CliSyntax.PREFIX_MODULE;
import static mams.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.List;
import java.util.Optional;

import mams.commons.core.index.Index;
import mams.logic.commands.exceptions.CommandException;
import mams.logic.history.FilterOnlyCommandHistory;
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
            + "[" + PREFIX_APPEAL + "INDEX" + "] "
            + "[" + PREFIX_MODULE + "INDEX" + "] "
            + "[" + PREFIX_STUDENT + "INDEX" + "]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_APPEAL + "1 "
            + PREFIX_MODULE + "11 "
            + PREFIX_STUDENT + "10";

    public static final String MESSAGE_VIEW_SUCCESS = "Displayed items with ID: %1$s";

    public static final String MESSAGE_APPEAL_ALREADY_EXPANDED = "There is already an appeal in expanded form";
    public static final String MESSAGE_MODULE_ALREADY_EXPANDED = "There is already a module in expanded form";
    public static final String MESSAGE_STUDENT_ALREADY_EXPANDED = "There is already a student in expanded form";

    public static final String MESSAGE_NO_APPEALS_TO_EXPAND = "There are no displayed appeals to expand";
    public static final String MESSAGE_NO_MODULES_TO_EXPAND = "There are no displayed modules to expand";
    public static final String MESSAGE_NO_STUDENTS_TO_EXPAND = "There are no displayed students to expand";

    public static final String MESSAGE_TIP_ON_FAILURE = "Tip: You may use the list or find commands to retrieve valid"
            + "lists of items to expand";

    private ViewCommandParameters params;

    public ViewCommand(ViewCommandParameters params) {
        requireNonNull(params);
        this.params = params;
    }

    /**
     * Execute ViewCommand and displays the expanded view of items in the specified indexes.
     *
     * In the case of multiple specified indexes:
     * If at least one of the parameters are out of bounds, then no lists will be updated.
     * That is to say, even if the appealIndex was valid, but the supplied studentIndex in
     * ViewCommandParameters was out of bounds, then appeal list will not be updated and an
     * error will be thrown. This is by design - if appeal list had been updated, then the user
     * will have to go back and delete the appeal list argument from the previous command
     * before he can reuse the command. (Remember that if ViewCommand is performed successfully on an appeal,
     * the new displayed appeal list now only has one item with an Index of 1, so the old Index is no
     * longer valid, and old command is hence no longer reusable)
     */
    @Override
    public CommandResult execute(Model model, FilterOnlyCommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        // Defensive check: ViewCommandParser should have ensured at least one parameter is present
        if (!params.isAtLeastOneParameterPresent()) {
            throw new CommandException(MESSAGE_USAGE);
        }

        StringBuilder displayedFeedback = new StringBuilder();

        List<Appeal> lastShownAppealList = model.getFilteredAppealList();
        List<Module> lastShownModuleList = model.getFilteredModuleList();
        List<Student> lastShownStudentList = model.getFilteredStudentList();

        verifyAllTargetedListsExpandable(lastShownAppealList, lastShownModuleList, lastShownStudentList);
        verifyAllSpecifiedIndexesWithinBounds(lastShownAppealList, lastShownModuleList, lastShownStudentList);

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
    protected void verifyAllSpecifiedIndexesWithinBounds(List<Appeal> lastShownAppealList,
                                                         List<Module> lastShownModuleList,
                                                         List<Student> lastShownStudentList) throws CommandException {
        requireAllNonNull(lastShownAppealList, lastShownModuleList, lastShownStudentList);
        StringBuilder errorMessage = new StringBuilder();
        boolean isIndexOutOfBounds = false;

        if (params.getAppealIndex().isPresent()
                && params.getAppealIndex().get().getZeroBased() >= lastShownAppealList.size()) {
            errorMessage.append(MESSAGE_INVALID_APPEAL_DISPLAYED_INDEX);
            errorMessage.append("\n");
            isIndexOutOfBounds = true;
        }
        if (params.getModuleIndex().isPresent()
                && params.getModuleIndex().get().getZeroBased() >= lastShownModuleList.size()) {
            errorMessage.append(MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
            errorMessage.append("\n");
            isIndexOutOfBounds = true;
        }
        if (params.getStudentIndex().isPresent()
                && params.getStudentIndex().get().getZeroBased() >= lastShownStudentList.size()) {
            errorMessage.append(MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
            errorMessage.append("\n");
            isIndexOutOfBounds = true;
        }

        if (isIndexOutOfBounds) {
            throw new CommandException(errorMessage.toString().trim());
        }
    }

    /**
     * Verify that the lists the current {@code ViewCommand} object is targeting are expandable.
     * In other words, the targeted lists should have target-able non-expanded items for expanding.
     *
     * If there is already an item in expanded form on the GUI display,
     * and the ViewCommandParameters object has an Index that meant for
     * expanding an item on that particular list, this
     * method should throw a exception. Note that as of v1.4, this is as
     * simple as checking whether the lists contain only one item.
     *
     * If there are no items on the list to be expanded, this method should also throw an exception.
     * This is as simple as checking if the last shown list is of size 0.
     *
     * @param lastShownAppealList appeal list to check for expand-ability
     * @param lastShownModuleList module list to check for expand-ability
     * @param lastShownStudentList student list to check for expand-ability
     * @throws CommandException if any of the lists targeted by the ViewCommand object is not expandable.
     */
    protected void verifyAllTargetedListsExpandable(List<Appeal> lastShownAppealList,
                                                    List<Module> lastShownModuleList,
                                                    List<Student> lastShownStudentList) throws CommandException {
        requireAllNonNull(lastShownAppealList, lastShownModuleList, lastShownStudentList);
        StringBuilder errorMessage = new StringBuilder();
        boolean isNotExpandable = false;

        int appealListSize = lastShownAppealList.size();
        int moduleListSize = lastShownModuleList.size();
        int studentListSize = lastShownStudentList.size();

        if (params.getAppealIndex().isPresent() && (appealListSize == 0 || appealListSize == 1)) {
            errorMessage.append((appealListSize == 0)
                    ? MESSAGE_NO_APPEALS_TO_EXPAND
                    : MESSAGE_APPEAL_ALREADY_EXPANDED);
            errorMessage.append("\n");
            isNotExpandable = true;
        }
        if (params.getModuleIndex().isPresent() && (moduleListSize == 0 || moduleListSize == 1)) {
            errorMessage.append((moduleListSize == 0)
                    ? MESSAGE_NO_MODULES_TO_EXPAND
                    : MESSAGE_MODULE_ALREADY_EXPANDED);
            errorMessage.append("\n");
            isNotExpandable = true;
        }
        if (params.getStudentIndex().isPresent() && (studentListSize == 0 || studentListSize == 1)) {
            errorMessage.append((studentListSize == 0)
                    ? MESSAGE_NO_STUDENTS_TO_EXPAND
                    : MESSAGE_STUDENT_ALREADY_EXPANDED);
            errorMessage.append("\n");
            isNotExpandable = true;
        }

        if (isNotExpandable) {
            throw new CommandException(errorMessage.toString().trim());
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
     * caused by the optional/combinatorial nature of the parameters passed to ViewCommand.
     * In future iterations (v1.5 & above) ViewCommand will accept both indexes and IDs, and this
     * will avoid having too many constructors and null-value handling in ViewCommand, where
     * it might obfuscate functional code.
     */
    public static class ViewCommandParameters {
        private Index appealIndex;
        private Index moduleIndex;
        private Index studentIndex;

        public void setAppealIndex(Index appealIndex) {
            requireNonNull(appealIndex);
            this.appealIndex = appealIndex;
        }

        public void setModuleIndex(Index moduleIndex) {
            requireNonNull(moduleIndex);
            this.moduleIndex = moduleIndex;
        }

        public void setStudentIndex(Index studentIndex) {
            requireNonNull(studentIndex);
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
