package seedu.address.model.cap.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.cap.CapLog;
import seedu.address.model.cap.ReadOnlyCapLog;
import seedu.address.model.cap.person.AcademicYear;
import seedu.address.model.cap.person.Credit;
import seedu.address.model.cap.person.Description;
import seedu.address.model.cap.person.Faculty;
import seedu.address.model.cap.person.Grade;
import seedu.address.model.cap.person.ModuleCode;
import seedu.address.model.cap.person.Semester;
import seedu.address.model.cap.person.SemesterPeriod;
import seedu.address.model.cap.person.Title;
import seedu.address.model.cap.tag.Tag;
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
                    new Description("This module "
                        + "introduces the necessary conceptual and analytical tools for systematic"
                        + " and rigorous development of software systems. It covers four main areas "
                        + "of software development, "
                        + "namely object-oriented system analysis, object-oriented system modelling "
                        + "and design, implementation, "
                        + "and testing, with emphasis on system modelling and design "
                        + "and implementation of software modules that work "
                        + "cooperatively to fulfill the requirements of the system."),
                        new Credit(4), new Faculty("Computing"), new Grade("A")),
                new Module(new ModuleCode("CS2030"), new Title("Programming Methodology II"),
                    new Semester(new SemesterPeriod(1), new AcademicYear("1920")),
                    new Description(
                        "This module is a follow up to CS1010. It explores two modern programming paradigms, "
                        + "object-oriented programming and functional programming."),
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

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
