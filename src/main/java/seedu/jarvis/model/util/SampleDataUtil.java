package seedu.jarvis.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.jarvis.commons.core.tag.Tag;
import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.logic.commands.course.ShowCourseHelpCommand;
import seedu.jarvis.model.course.Course;
import seedu.jarvis.model.course.CoursePlanner;
import seedu.jarvis.model.finance.FinanceTracker;
import seedu.jarvis.model.finance.installment.Installment;
import seedu.jarvis.model.finance.installment.InstallmentDescription;
import seedu.jarvis.model.finance.installment.InstallmentMoneyPaid;
import seedu.jarvis.model.finance.purchase.Purchase;
import seedu.jarvis.model.finance.purchase.PurchaseDescription;
import seedu.jarvis.model.finance.purchase.PurchaseMoneySpent;
import seedu.jarvis.model.planner.Planner;
import seedu.jarvis.model.planner.enums.Frequency;
import seedu.jarvis.model.planner.enums.Priority;
import seedu.jarvis.model.planner.enums.Status;
import seedu.jarvis.model.planner.tasks.Deadline;
import seedu.jarvis.model.planner.tasks.Event;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.planner.tasks.Todo;

/**
 * Contains utility methods for populating {@code CoursePlanner} and {@code FinanceTracker} with sample data.
 */
public class SampleDataUtil {

    public static Course[] getSampleCourses() {
        return new Course[] {
            CourseUtil.getCourse("CG1111").get(),
            CourseUtil.getCourse("CS1010").get(),
            CourseUtil.getCourse("CS1231").get(),
            CourseUtil.getCourse("MA1511").get(),
            CourseUtil.getCourse("MA1512").get(),
            CourseUtil.getCourse("GER1000").get()
        };
    }

    public static Purchase[] getSamplePurchases() {
        return new Purchase[] {
            new Purchase(new PurchaseDescription("Dinner at Reedz"), new PurchaseMoneySpent("5.50"),
                    LocalDate.of(2019, 10, 31)),
            new Purchase(new PurchaseDescription("Gongcha"), new PurchaseMoneySpent("2.80"),
                    LocalDate.of(2019, 10, 31)),
            new Purchase(new PurchaseDescription("Lunch at Deck"), new PurchaseMoneySpent("4.50"),
                    LocalDate.of(2019, 10, 31))
        };
    }

    public static Installment[] getSampleInstallments() {
        return new Installment[] {
            new Installment(new InstallmentDescription("Spotify"), new InstallmentMoneyPaid("9.5")),
            new Installment(new InstallmentDescription("Transport fees"), new InstallmentMoneyPaid("60"))
        };
    }

    public static Task[] getSampleTasks() {
        return new Task[]{
                new Todo("Return equipment", Priority.LOW, null,
                        Status.NOT_DONE, Set.of(new Tag("track"))),
                new Deadline("geq forum posts", Priority.HIGH, Frequency.MONTHLY, Status.DONE,
                        Set.of(new Tag("school"), new Tag("geq")),
                        LocalDate.parse("7/11/2019", Task.getDateFormat())),
                new Event("workshop", null, null, Status.NOT_DONE, Set.of(),
                        LocalDate.parse("10/11/2019", Task.getDateFormat()),
                        LocalDate.parse("15/11/2019", Task.getDateFormat()))
        };
    }




    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public static CoursePlanner getSampleCoursePlanner() {
        CoursePlanner cp = new CoursePlanner();
        Arrays.stream(getSampleCourses()).forEach(cp::addCourse);
        cp.setCourseDisplayText(ShowCourseHelpCommand.MESSAGE_HELP);
        return cp;
    }

    public static FinanceTracker getSampleFinanceTracker() {
        FinanceTracker financeTracker = new FinanceTracker();
        Arrays.stream(getSamplePurchases()).forEach(financeTracker::addPurchaseToBack);
        Arrays.stream(getSampleInstallments()).forEach(financeTracker::addInstallment);
        return financeTracker;
    }

    public static Planner getSamplePlanner() {
        Planner planner = new Planner();
        Arrays.stream(getSampleTasks()).forEach(planner::addTask);
        return planner;

    }
}
