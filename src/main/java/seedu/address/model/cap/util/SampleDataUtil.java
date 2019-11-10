package seedu.address.model.cap.util;

import seedu.address.model.cap.CapLog;
import seedu.address.model.cap.ReadOnlyCapLog;
import seedu.address.model.cap.person.AcademicYear;
import seedu.address.model.cap.person.Credit;
import seedu.address.model.cap.person.Faculty;
import seedu.address.model.cap.person.Grade;
import seedu.address.model.cap.person.ModuleCode;
import seedu.address.model.cap.person.Semester;
import seedu.address.model.cap.person.SemesterPeriod;
import seedu.address.model.cap.person.Title;

import seedu.address.model.common.Module;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Semester[] getSampleSemester() {
        Semester semester1 = new Semester(new SemesterPeriod(1), new AcademicYear("1920"));
        semester1.addModules(
            new Module[] {
                new Module(new ModuleCode("CS2103"), new Title("Software Engineering"),
                    new Semester(new SemesterPeriod(1), new AcademicYear("1920")),
                    new Credit(4), new Faculty("Computing"), new Grade("A")),
                new Module(new ModuleCode("CS2030"), new Title("Programming Methodology II"),
                    new Semester(new SemesterPeriod(1), new AcademicYear("1920")),
                    new Credit(4), new Faculty("Computing"), new Grade("A-"))
                }
        );

        return new Semester[] {
            semester1
        };
    }

    public static ReadOnlyCapLog getSampleCapLog() {
        CapLog sampleAb = new CapLog();
        for (Semester sampleSemester : getSampleSemester()) {
            sampleAb.addSemester(sampleSemester);
        }
        return sampleAb;
    }

}
