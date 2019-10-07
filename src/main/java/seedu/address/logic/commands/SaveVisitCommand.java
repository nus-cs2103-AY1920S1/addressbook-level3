package seedu.address.logic.commands;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VISIT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.VisitReport;
import seedu.address.ui.VisitRecordWindow;
public class SaveVisitCommand extends Command {
    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added visit to Person: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed visit from Person: %1$s";

    private final Index index;
    private final VisitReport visitReport;

    private String medication;
    private String diagnosis;
    private String remarks;

    public SaveVisitCommand(int index, String date, String meds, String dg, String rmk) {
        requireAllNonNull(index, date);
        System.out.println("INDEX = " + index + "\n");
        this.index = Index.fromOneBased(index);
        this.visitReport = new VisitReport(date);
        this.medication = meds;
        this.diagnosis = dg;
        this.remarks = rmk;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        visitReport.setName(personToEdit.getName());
        visitReport.setDetails(medication, diagnosis, remarks);
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(),personToEdit.getVisitList().addRecord(visitReport) , personToEdit.getTags());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson), false, true, false);
    }

    /**
     * Generates a command execution success message based on whether the visitList is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = !visitReport.value.isEmpty() ? MESSAGE_ADD_REMARK_SUCCESS : MESSAGE_DELETE_REMARK_SUCCESS;
        return String.format(message, personToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SaveVisitCommand)) {
            return false;
        }

        // state check
        SaveVisitCommand e = (SaveVisitCommand) other;
        return index.equals(e.index)
                && visitReport.equals(e.visitReport);
    }

}
