package seedu.address.report;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalBodies.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.ReportGenerator;

//@@author bernicechio
public class ReportGeneratorTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private ReportGenerator reportGenerator = new ReportGenerator();

    @Test
    public void isSameReportGenerator() {
        ReportGenerator reportGenerator1 = new ReportGenerator();
        ReportGenerator reportGenerator2 = new ReportGenerator();
        assertTrue(reportGenerator1.isSameReportGenerator(reportGenerator2));
    }

    @Test
    public void generate_success() {
        assertTrue(reportGenerator.generate(ALICE, ""));
        assertTrue(reportGenerator.generate(ALICE, "Manager A"));
        assertFalse(reportGenerator.generate(null, "Manager A"));
    }

    @Test
    public void generateAll_success() {
        assertFalse(reportGenerator.generateAll(model.getFilteredBodyList(), "Manager A"));
        model.addEntity(ALICE);
        assertTrue(reportGenerator.generateAll(model.getFilteredBodyList(), ""));
        assertTrue(reportGenerator.generateAll(model.getFilteredBodyList(), "Manager A"));
        assertFalse(reportGenerator.generateAll(null, "Manager A"));
    }

    @Test
    public void generateSummary_success() {
        assertFalse(reportGenerator.generateSummary(model.getFilteredBodyList(), "Manager A"));
        model.addEntity(ALICE);
        assertTrue(reportGenerator.generateSummary(model.getFilteredBodyList(), ""));
        assertTrue(reportGenerator.generateSummary(model.getFilteredBodyList(), "Manager A"));
        assertFalse(reportGenerator.generateSummary(null, "Manager A"));
    }

}
