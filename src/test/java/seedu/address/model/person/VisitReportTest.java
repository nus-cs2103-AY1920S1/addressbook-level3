package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TypicalVisits;

public class VisitReportTest {
    @Test
    public void equals() {
        VisitReport visitReport = TypicalVisits.REPORT_1;

        // same object -> returns true
        assertTrue(visitReport.equals(visitReport));

        // same values -> returns true
        VisitReport visitReportCopy = new VisitReport(visitReport.date);
        visitReportCopy.setDetails(visitReport.getMedication(), visitReport.getDiagnosis(), visitReport.getRemarks());
        visitReportCopy.setName(new Name(visitReport.getName()));
        assertTrue(visitReport.equals(visitReportCopy));

        // different types -> returns false
        assertFalse(visitReport.equals(1));

        // null -> returns false
        assertFalse(visitReport.equals(null));

        // different visitList -> returns false
        VisitReport differentVisitReport = TypicalVisits.REPORT_2;
        assertFalse(visitReport.equals(differentVisitReport));
    }
}
