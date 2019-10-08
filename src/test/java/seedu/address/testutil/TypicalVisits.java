package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.model.person.Name;
import seedu.address.model.person.VisitList;
import seedu.address.model.person.VisitReport;

/**
 * A utility class containing a list of {@code VisitReport} and  {@code VisitList} objects to be used in tests.
 */
public class TypicalVisits {
    public static final VisitReport REPORT_1 = new VisitReportBuilder().withName("Alice Pauline")
            .withDate("31/12/2012").withPrescription("2 day MC")
            .withDiagnosis("Flu").withRemark("Your're going to die.").build();

    public static final VisitReport REPORT_2 = new VisitReportBuilder().withName("Benson Meier")
            .withDate("30/11/2012").withPrescription("1 packet of condom")
            .withDiagnosis("HPV").withRemark("Try not to spread the love.").build();

    public static final VisitReport REPORT_3 = new VisitReportBuilder().withName("Carl Kurz")
            .withDate("31/10/2012").withPrescription("JavaFX")
            .withDiagnosis("Brain cancer").withRemark("You'll be cured in no time").build();

    public static final VisitReport REPORT_4 = new VisitReportBuilder().withName("Daniel Meier")
            .withDate("01/03/2016").withPrescription("3 packets of cigs")
            .withDiagnosis("Lung Cancer").withRemark("Life is too short to not smoke").build();

    public static final VisitReport REPORT_5 = new VisitReportBuilder().withName("Elle Meyer")
            .withDate("12/7/1999").withPrescription("1 day MC")
            .withDiagnosis("Eye Cancer, Mouth Cancer, Face Cancer").withRemark("You'll be fine in no time").build();

    public static final VisitReport REPORT_6 = new VisitReportBuilder().withName("Fiona Kunz")
            .withDate("26/8/2011").withPrescription("antidepressants")
            .withDiagnosis("depression!!!").withRemark("Stay away from windows").build();

    public static final VisitReport REPORT_7 = new VisitReportBuilder().withName("George Best")
            .withDate("31/12/2012").withPrescription("1 watermelon")
            .withDiagnosis("stomach ache").withRemark("not enough can always increase the dosage").build();

    public static VisitList getLongestTypicalVisitList(String name) {
        ArrayList<VisitReport> typicalList = new ArrayList<VisitReport>(Arrays.asList(REPORT_1, REPORT_2, REPORT_3));
        for (VisitReport v : typicalList) {
            v.setName(new Name(name));
        }
        return new VisitList(typicalList);
    }

    public static VisitList getShortTypicalVisitList(String name) {
        ArrayList<VisitReport> typicalList = new ArrayList<VisitReport>(Arrays.asList(REPORT_1));
        for (VisitReport v : typicalList) {
            v.setName(new Name(name));
        }
        return new VisitList(typicalList);
    }

    public static VisitList getLongTypicalVisitList(String name) {
        ArrayList<VisitReport> typicalList = new ArrayList<VisitReport>(Arrays.asList(REPORT_1, REPORT_2));
        for (VisitReport v : typicalList) {
            v.setName(new Name(name));
        }
        return new VisitList(typicalList);
    }

}
