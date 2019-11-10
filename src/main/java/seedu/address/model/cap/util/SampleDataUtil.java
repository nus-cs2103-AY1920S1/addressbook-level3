package seedu.address.model.cap.util;

import seedu.address.model.cap.CapLog;
import seedu.address.model.cap.ReadOnlyCapLog;
import seedu.address.model.cap.person.AcademicYear;
import seedu.address.model.cap.person.Credit;
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
    public static Module[] getSampleModule() {
        Module[] modules =
            new Module[] {
                new Module(new ModuleCode("CS2103"), new Title("Software Engineering"),
                    new Semester(new SemesterPeriod(1), new AcademicYear("1920")),
                    new Credit(4), new Grade("A")),
                new Module(new ModuleCode("CS2030"), new Title("Programming Methodology II"),
                    new Semester(new SemesterPeriod(1), new AcademicYear("1920")),
                    new Credit(4), new Grade("A-")),
                new Module(new ModuleCode("CS3233"), new Title("Competitive Programming"),
                    new Semester(new SemesterPeriod(2), new AcademicYear("1920")),
                    new Credit(4), new Grade("B+")),
                new Module(new ModuleCode("CS3226"), new Title("Web Programming and Applications"),
                    new Semester(new SemesterPeriod(2), new AcademicYear("1920")),
                    new Credit(4), new Grade("A")),
                };
        return modules;
    }

    public static ReadOnlyCapLog getSampleCapLog() {
        CapLog sampleAb = new CapLog();
        for (Module sampleModule : getSampleModule()) {
            sampleAb.addModule(sampleModule);
        }
        return sampleAb;
    }

}
