package seedu.billboard.testutil;

import seedu.billboard.model.ArchiveWrapper;
import seedu.billboard.model.archive.Archive;
import seedu.billboard.model.expense.Expense;

import java.util.List;

public class ArchiveWrapperBuilder {
    private ArchiveWrapper archiveWrapper;

    public ArchiveWrapperBuilder(List<Expense> expenses) {
        archiveWrapper = new ArchiveWrapper(expenses);
    }


    /**
     * Adds a new {@code Archive} to the {@code ArchiveWrapper} that we are building.
     */
    public ArchiveWrapperBuilder withArchive(Archive archive) {
        archiveWrapper.addArchive(archive);
        return this;
    }

    /**
     * Adds a new {@code Expense} to an existing {@code Archive} in the {@code ArchiveWrapper} that we are building.
     * It is given the archiveName given is that of an exisiting archive.
     */
    public ArchiveWrapperBuilder withArchiveExpense(String archiveName, Expense archiveExpense) {
        archiveWrapper.addArchiveExpense(archiveName, archiveExpense);
        return this;
    }

    public ArchiveWrapper build() {
        return archiveWrapper;
    }
}
