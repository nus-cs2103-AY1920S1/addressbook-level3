package seedu.jarvis.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.jarvis.commons.core.tag.Tag;
import seedu.jarvis.commons.util.CourseUtil;
import seedu.jarvis.logic.commands.course.ShowCourseHelpCommand;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.model.cca.CcaName;
import seedu.jarvis.model.cca.CcaTracker;
import seedu.jarvis.model.cca.CcaType;
import seedu.jarvis.model.cca.Equipment;
import seedu.jarvis.model.cca.EquipmentList;
import seedu.jarvis.model.cca.ccaprogress.CcaCurrentProgress;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestone;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestoneList;
import seedu.jarvis.model.cca.ccaprogress.CcaProgress;
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
            CourseUtil.getCourse("GER1000").get(),
            CourseUtil.getCourse("CS2030").get(),
            CourseUtil.getCourse("CS2040").get(),
            CourseUtil.getCourse("CS2100").get(),
            CourseUtil.getCourse("GET1028").get(),
            CourseUtil.getCourse("GES1011").get(),
            CourseUtil.getCourse("MA1101R").get(),
            CourseUtil.getCourse("CS2101").get(),
            CourseUtil.getCourse("CS2102").get(),
            CourseUtil.getCourse("CS2103T").get(),
            CourseUtil.getCourse("GEQ1000").get(),
            CourseUtil.getCourse("ST2334").get()
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

    public static CcaTracker getSampleCcaTracker() {
        EquipmentList swimmingEquipmentList = new EquipmentList();
        swimmingEquipmentList.addEquipment(new Equipment("goggles"));
        swimmingEquipmentList.addEquipment(new Equipment("swimming cap"));
        swimmingEquipmentList.addEquipment(new Equipment("swimming trunks"));
        CcaMilestoneList swimmingCcaMilestoneList = new CcaMilestoneList();
        swimmingCcaMilestoneList.add(new CcaMilestone("beginner"));
        swimmingCcaMilestoneList.add(new CcaMilestone("bronze"));
        swimmingCcaMilestoneList.add(new CcaMilestone("silver"));
        swimmingCcaMilestoneList.add(new CcaMilestone("gold"));
        swimmingCcaMilestoneList.add(new CcaMilestone("gold star"));
        swimmingCcaMilestoneList.add(new CcaMilestone("lifeguard 1"));
        swimmingCcaMilestoneList.add(new CcaMilestone("lifeguard 2"));
        swimmingCcaMilestoneList.add(new CcaMilestone("joseph schooling"));
        CcaProgress swimmingCcaProgress = new CcaProgress();
        swimmingCcaProgress.setMilestones(swimmingCcaMilestoneList);
        swimmingCcaProgress.setCcaCurrentProgress(new CcaCurrentProgress(4));
        Cca swimmingCca = new Cca(new CcaName("Swimming"), new CcaType("sport"), swimmingEquipmentList,
                swimmingCcaProgress);
        CcaTracker ccaTracker = new CcaTracker();
        ccaTracker.addCca(swimmingCca);

        EquipmentList guitarEquipmentList = new EquipmentList();
        guitarEquipmentList.addEquipment(new Equipment("guitar"));
        guitarEquipmentList.addEquipment(new Equipment("guitar stand"));
        guitarEquipmentList.addEquipment(new Equipment("music score"));
        guitarEquipmentList.addEquipment(new Equipment("conductor stick"));
        guitarEquipmentList.addEquipment(new Equipment("backing music"));

        CcaMilestoneList guitarCcaMilestoneList = new CcaMilestoneList();
        guitarCcaMilestoneList.add(new CcaMilestone("grade 1"));
        guitarCcaMilestoneList.add(new CcaMilestone("grade 2"));
        guitarCcaMilestoneList.add(new CcaMilestone("grade 3"));
        guitarCcaMilestoneList.add(new CcaMilestone("grade 4"));
        guitarCcaMilestoneList.add(new CcaMilestone("grade 5"));
        guitarCcaMilestoneList.add(new CcaMilestone("grade 6"));
        guitarCcaMilestoneList.add(new CcaMilestone("grade 7"));
        guitarCcaMilestoneList.add(new CcaMilestone("grade 8"));
        guitarCcaMilestoneList.add(new CcaMilestone("instructor"));
        CcaProgress guitarCcaProgress = new CcaProgress();
        guitarCcaProgress.setMilestones(guitarCcaMilestoneList);
        guitarCcaProgress.setCcaCurrentProgress(new CcaCurrentProgress(6));
        Cca guitarCca = new Cca(new CcaName("Guitar"), new CcaType("performingArt"), guitarEquipmentList,
                guitarCcaProgress);
        ccaTracker.addCca(guitarCca);

        return ccaTracker;
    }
}
