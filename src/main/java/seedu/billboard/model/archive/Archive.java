package seedu.billboard.model.archive;

import static seedu.billboard.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Objects;

import seedu.billboard.model.expense.Expense;
import seedu.billboard.model.expense.ExpenseList;

/**
 * Represents an archive in Billboard
 */
public class Archive extends ExpenseList {

    private String archiveName;

    public Archive(String archiveName, List<Expense> expense) {
        requireAllNonNull(archiveName);
        this.archiveName = archiveName;
        setExpenses(expense);
    }

    public void setArchive(List<Expense> expense) {
        setExpenses(expense);
    }

    public String getArchiveName() {
        return archiveName;
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Archive)) {
            return false;
        }

        Archive otherArchive = (Archive) other;
        return otherArchive.getArchiveName().equals(getArchiveName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(archiveName);
    }

    @Override
    public String toString() {
        return "Archive name: " + archiveName;
    }
}
