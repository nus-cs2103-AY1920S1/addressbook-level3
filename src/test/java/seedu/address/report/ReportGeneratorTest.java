package seedu.address.report;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalBodies.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author bernicechio
public class ReportGeneratorTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void isSameReportGenerator() {
        ReportGenerator reportGenerator1 = new ReportGenerator();
        ReportGenerator reportGenerator2 = new ReportGenerator();
        assertTrue(reportGenerator1.isSameReportGenerator(reportGenerator2));
    }

    @Test
    public void generate_success() {
        assertTrue(ReportGenerator.generate(ALICE));
        assertFalse(ReportGenerator.generate(null));
    }

    @Test
    public void generateAll_success() {
        assertFalse(ReportGenerator.generateAll(model.getFilteredBodyList()));
        model.addEntity(ALICE);
        assertTrue(ReportGenerator.generateAll(model.getFilteredBodyList()));
        assertFalse(ReportGenerator.generateAll(null));
    }

}
