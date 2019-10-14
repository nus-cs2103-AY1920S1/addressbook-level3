package seedu.billboard.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.billboard.commons.exceptions.IllegalValueException;
import seedu.billboard.model.archive.Archive;
import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.expense.ExpenseList;

import java.util.ArrayList;
import java.util.List;

/**
 * Jackson-friendly version of an archive
 */
class JsonAdaptedArchive {

    private final String archiveName;

    private final List<JsonAdaptedExpense> archiveExpenses = new ArrayList<>();

    @JsonCreator
    public JsonAdaptedArchive(@JsonProperty("archive name") String archiveName,
                              @JsonProperty("archive expenses") List<JsonAdaptedExpense> archiveExpenses) {
        this.archiveName = archiveName;
        this.archiveExpenses.addAll(archiveExpenses);
    }

    public Archive toModelType() throws IllegalValueException {
        List<Expense> expenses = new ArrayList<>();
        for (JsonAdaptedExpense jsonAdaptedExpense : archiveExpenses) {
            expenses.add(jsonAdaptedExpense.toModelType());
        }

        Archive archive = new Archive(archiveName, expenses);
        return archive;
    }
}
