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
import seedu.address.model.cap.person.*;
import seedu.address.model.common.Module;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_MODULE_CODE + "MODULE CODE] "
            + "[" + PREFIX_TITLE + "TITLE] "
            + "[" + PREFIX_CREDIT + "CREDIT] "
            + "[" + PREFIX_FACULTY + "FACULTY] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION]...\n";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Module> lastShownList = model.getFilteredModuleList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Module moduleToEdit = lastShownList.get(index.getZeroBased());
        Module editedModule= createEditedModule(moduleToEdit, editPersonDescriptor);

        if (!moduleToEdit.isSameModule(editedModule) && model.hasModule(editedModule)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.setModule(moduleToEdit, editedModule);
        model.updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedModule));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Module createEditedModule(Module moduleToEdit, EditPersonDescriptor editModuleDescriptor) {
        assert moduleToEdit != null;

        ModuleCode updatedModuleCode = editModuleDescriptor.getModuleCode().orElse(moduleToEdit.getModuleCode());
        Title updatedTitle = editModuleDescriptor.getTitle().orElse(moduleToEdit.getTitle());
        Credit updatedCredit = editModuleDescriptor.getCredit().orElse(moduleToEdit.getCredit());
        Description updatedDescription = editModuleDescriptor.getDescription().orElse(moduleToEdit.getDescription());
        Faculty updatedFaculty = editModuleDescriptor.getFaculty().orElse(moduleToEdit.getFaculty());

        return new Module(updatedModuleCode, updatedTitle, updatedDescription, updatedCredit, updatedFaculty);
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
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private ModuleCode moduleCode;
        private Title title;
        private Credit credit;
        private Faculty faculty;
        private Description description;


        public EditPersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            setModuleCode(toCopy.moduleCode);
            setTitle(toCopy.title);
            setCredit(toCopy.credit);
            setFaculty(toCopy.faculty);
            setDescription(toCopy.description);
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

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getModuleCode().equals(e.getModuleCode())
                    && getTitle().equals(e.getTitle())
                    && getCredit().equals(e.getCredit())
                    && getDescription().equals(e.getDescription())
                    && getFaculty().equals(e.getFaculty());
        }
    }
}
