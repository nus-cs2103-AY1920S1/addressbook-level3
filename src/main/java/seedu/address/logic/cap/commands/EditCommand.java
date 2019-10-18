package seedu.address.logic.cap.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.cap.parser.CliSyntax.PREFIX_CREDIT;
import static seedu.address.logic.cap.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.cap.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.address.logic.cap.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.address.logic.cap.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.model.cap.Model.PREDICATE_SHOW_ALL_MODULES;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.cap.commands.exceptions.CommandException;
import seedu.address.model.cap.Model;
import seedu.address.model.cap.person.Credit;
import seedu.address.model.cap.person.Description;
import seedu.address.model.cap.person.Faculty;
import seedu.address.model.cap.person.Grade;
import seedu.address.model.cap.person.ModuleCode;
import seedu.address.model.cap.person.Title;
import seedu.address.model.common.Module;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the details of the module identified "
            + "by the index number used in the displayed module list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_MODULE_CODE + "MODULE CODE] "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_CREDIT + "CREDIT] "
            + "[" + PREFIX_FACULTY + "FACULTY] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION]...\n";

    public static final String MESSAGE_EDIT_MODULE_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists in the cap log.";

    private final Index index;
    private final EditModuleDescriptor editModuleDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editModuleDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditModuleDescriptor editModuleDescriptor) {
        requireNonNull(index);
        requireNonNull(editModuleDescriptor);

        this.index = index;
        this.editModuleDescriptor = new EditModuleDescriptor(editModuleDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Module> lastShownList = model.getFilteredModuleList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
        }

        Module moduleToEdit = lastShownList.get(index.getZeroBased());
        Module editedModule = createEditedModule(moduleToEdit, editModuleDescriptor);

        if (!moduleToEdit.isSameModule(editedModule) && model.hasModule(editedModule)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }

        model.setModule(moduleToEdit, editedModule);
        model.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        return new CommandResult(String.format(MESSAGE_EDIT_MODULE_SUCCESS, editedModule));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Module createEditedModule(Module moduleToEdit, EditModuleDescriptor editModuleDescriptor) {
        assert moduleToEdit != null;

        ModuleCode updatedModuleCode = editModuleDescriptor.getModuleCode().orElse(moduleToEdit.getModuleCode());
        Title updatedTitle = editModuleDescriptor.getTitle().orElse(moduleToEdit.getTitle());
        Credit updatedCredit = editModuleDescriptor.getCredit().orElse(moduleToEdit.getCredit());
        Description updatedDescription = editModuleDescriptor.getDescription().orElse(moduleToEdit.getDescription());
        Faculty updatedFaculty = editModuleDescriptor.getFaculty().orElse(moduleToEdit.getFaculty());
        Grade updatedGrade = editModuleDescriptor.getGrade().orElse(moduleToEdit.getGrade());

        return new Module(updatedModuleCode, updatedTitle, updatedDescription,
            updatedCredit, updatedFaculty, updatedGrade);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editModuleDescriptor.equals(e.editModuleDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditModuleDescriptor {
        private ModuleCode moduleCode;
        private Title title;
        private Credit credit;
        private Faculty faculty;
        private Description description;
        private Grade grade;


        public EditModuleDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditModuleDescriptor(EditModuleDescriptor toCopy) {
            setModuleCode(toCopy.moduleCode);
            setTitle(toCopy.title);
            setCredit(toCopy.credit);
            setFaculty(toCopy.faculty);
            setDescription(toCopy.description);
            setGrade(toCopy.grade);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(moduleCode, title, credit, faculty, description);
        }

        public void setModuleCode(ModuleCode moduleCode) {
            this.moduleCode = moduleCode;
        }

        public Optional<ModuleCode> getModuleCode() {
            return Optional.ofNullable(moduleCode);
        }

        public void setCredit(Credit credit) {
            this.credit = credit;
        }

        public Optional<Credit> getCredit() {
            return Optional.ofNullable(credit);
        }

        public void setTitle(Title title) {
            this.title = title;
        }

        public Optional<Title> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setFaculty(Faculty faculty) {
            this.faculty = faculty;
        }

        public Optional<Faculty> getFaculty() {
            return Optional.ofNullable(faculty);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        private void setGrade(Grade grade) {
            this.grade = grade;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public Optional<Grade> getGrade() {
            return Optional.ofNullable(grade);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditModuleDescriptor)) {
                return false;
            }

            // state check
            EditModuleDescriptor e = (EditModuleDescriptor) other;

            return getModuleCode().equals(e.getModuleCode())
                    && getTitle().equals(e.getTitle())
                    && getCredit().equals(e.getCredit())
                    && getDescription().equals(e.getDescription())
                    && getFaculty().equals(e.getFaculty());
        }
    }
}
