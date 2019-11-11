package seedu.jarvis.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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
                    LocalDate.of(2019, 10, 31)),
            new Purchase(new PurchaseDescription("Lunch at Utown"), new PurchaseMoneySpent("4.00"),
                    LocalDate.of(2019, 11, 2)),
            new Purchase(new PurchaseDescription("Stuffd Kebab for Dinner"), new PurchaseMoneySpent("5.80"),
                    LocalDate.of(2019, 11, 2)),
            new Purchase(new PurchaseDescription("Herschel Bag on Taobao"), new PurchaseMoneySpent("69.50"),
                    LocalDate.of(2019, 11, 4)),
            new Purchase(new PurchaseDescription("Laptop case on Taobao"), new PurchaseMoneySpent("16.00"),
                    LocalDate.of(2019, 11, 4)),
            new Purchase(new PurchaseDescription("Clothes at Editors Market"), new PurchaseMoneySpent("80"),
                    LocalDate.of(2019, 11, 5)),
            new Purchase(new PurchaseDescription("Dinner at PGP"), new PurchaseMoneySpent("7"),
                    LocalDate.of(2019, 11, 7)),
            new Purchase(new PurchaseDescription("Hot tea"), new PurchaseMoneySpent("0.8"),
                    LocalDate.of(2019, 11, 8))
        };
    }

    public static Installment[] getSampleInstallments() {
        return new Installment[] {
            new Installment(new InstallmentDescription("Spotify subscription"), new InstallmentMoneyPaid("9.5")),
            new Installment(new InstallmentDescription("Transport fees"), new InstallmentMoneyPaid("84.50")),
            new Installment(new InstallmentDescription("OCBC credit card fees"),
                    new InstallmentMoneyPaid("20")),
            new Installment(new InstallmentDescription("Netflix subscription"),
                    new InstallmentMoneyPaid("60")),
            new Installment(new InstallmentDescription("Singtel phone bill"), new InstallmentMoneyPaid("40")),
            new Installment(new InstallmentDescription("Photoshop subscription"),
                    new InstallmentMoneyPaid("12.50")),
            new Installment(new InstallmentDescription("Standard Chartered bank fees"),
                    new InstallmentMoneyPaid("60"))
        };
    }

    public static Task[] getSampleTasks() {
        return new Task[]{
            new Todo("Return equipment", Priority.LOW, null,
                    Status.NOT_DONE, Set.of(new Tag("track"))),
            new Deadline("geq forum posts", Priority.HIGH, Frequency.MONTHLY, Status.DONE,
                    Set.of(new Tag("school"), new Tag("geq")),
                    LocalDate.parse("8/11/2019", Task.getDateFormat())),
            new Event("workshop", null, null, Status.NOT_DONE, Set.of(),
                    LocalDate.parse("10/11/2019", Task.getDateFormat()),
                    LocalDate.parse("15/11/2019", Task.getDateFormat())),
            new Event("Sara's party!", null, null, Status.NOT_DONE, Set.of(new Tag("social")),
                    LocalDate.parse("16/11/2019", Task.getDateFormat()),
                    LocalDate.parse("16/11/2019", Task.getDateFormat())),
            new Todo("Buy dog food", Priority.HIGH, null, Status.NOT_DONE,
                    Set.of(new Tag("rover"), new Tag("finance"))),
            new Deadline("CS2106 Lab 5", Priority.MED, null, Status.DONE,
                    Set.of(new Tag("cs2106"), new Tag("school")),
                    LocalDate.parse("14/11/2019", Task.getDateFormat())),
            new Todo("Get present for Sara", null, null, Status.NOT_DONE,
                    Set.of(new Tag("finance"))),
            new Todo("Water plant", null, Frequency.WEEKLY, Status.NOT_DONE,
                    Set.of()),
            new Deadline("CS2101 Reflection", Priority.HIGH, null, Status.NOT_DONE,
                    Set.of(new Tag("cs2101"), new Tag("school")),
                    LocalDate.parse("17/11/2019", Task.getDateFormat())),
            new Deadline("", Priority.HIGH, null, Status.NOT_DONE,
                    Set.of(new Tag("cs2101"), new Tag("school")),
                    LocalDate.parse("17/11/2019", Task.getDateFormat())),
            new Todo("Collect forms from Track students", null, null, Status.NOT_DONE,
                    Set.of(new Tag("track"))),
            new Event("Rock climbing competition", null, null, Status.NOT_DONE, Set.of(),
                    LocalDate.parse("25/11/2019", Task.getDateFormat()),
                    LocalDate.parse("26/11/2019", Task.getDateFormat()))
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

        // Swimming
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

        // Guitar
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

        // Hall comm
        EquipmentList hallCommEquipmentList = new EquipmentList();
        hallCommEquipmentList.addEquipment(new Equipment("laptop"));
        hallCommEquipmentList.addEquipment(new Equipment("paper"));
        hallCommEquipmentList.addEquipment(new Equipment("pen"));
        hallCommEquipmentList.addEquipment(new Equipment("speakers "));
        hallCommEquipmentList.addEquipment(new Equipment("table"));
        hallCommEquipmentList.addEquipment(new Equipment("chair"));
        hallCommEquipmentList.addEquipment(new Equipment("lounge"));
        hallCommEquipmentList.addEquipment(new Equipment("spare room"));
        hallCommEquipmentList.addEquipment(new Equipment("bean bags"));
        hallCommEquipmentList.addEquipment(new Equipment("drinks"));
        CcaMilestoneList hallCommCcaMilestoneList = new CcaMilestoneList();
        hallCommCcaMilestoneList.add(new CcaMilestone("helper"));
        hallCommCcaMilestoneList.add(new CcaMilestone("subcomm member"));
        hallCommCcaMilestoneList.add(new CcaMilestone("subcomm leader"));
        hallCommCcaMilestoneList.add(new CcaMilestone("committee member"));
        hallCommCcaMilestoneList.add(new CcaMilestone("committee leader"));
        hallCommCcaMilestoneList.add(new CcaMilestone("deputy house head"));
        hallCommCcaMilestoneList.add(new CcaMilestone("house head"));
        CcaProgress hallCommCcaProgress = new CcaProgress();
        hallCommCcaProgress.setMilestones(hallCommCcaMilestoneList);
        hallCommCcaProgress.setCcaCurrentProgress(new CcaCurrentProgress(5));
        Cca hallCommCca = new Cca(new CcaName("House comm"), new CcaType("club"), hallCommEquipmentList,
                hallCommCcaProgress);
        ccaTracker.addCca(hallCommCca);

        // Scouts
        EquipmentList scoutsEquipmentList = new EquipmentList();
        scoutsEquipmentList.addEquipment(new Equipment("uniform"));
        scoutsEquipmentList.addEquipment(new Equipment("tents"));
        scoutsEquipmentList.addEquipment(new Equipment("day bag"));
        scoutsEquipmentList.addEquipment(new Equipment("comfortable shoes"));
        scoutsEquipmentList.addEquipment(new Equipment("leather shoes"));
        scoutsEquipmentList.addEquipment(new Equipment("hiking pole"));
        scoutsEquipmentList.addEquipment(new Equipment("hiking bag"));
        scoutsEquipmentList.addEquipment(new Equipment("bug spray"));
        scoutsEquipmentList.addEquipment(new Equipment("troop flag"));
        CcaMilestoneList scoutsCcaMilestoneList = new CcaMilestoneList();
        scoutsCcaMilestoneList.add(new CcaMilestone("Member"));
        scoutsCcaMilestoneList.add(new CcaMilestone("Assistant Patrol Leader"));
        scoutsCcaMilestoneList.add(new CcaMilestone("Patrol Leader"));
        scoutsCcaMilestoneList.add(new CcaMilestone("Senior Patrol Leader"));
        scoutsCcaMilestoneList.add(new CcaMilestone("Troop Leader"));
        scoutsCcaMilestoneList.add(new CcaMilestone("Auxiliary helper"));
        scoutsCcaMilestoneList.add(new CcaMilestone("Assistant Group Scout Leader"));
        scoutsCcaMilestoneList.add(new CcaMilestone("Group Scout Leader"));
        CcaProgress scoutsCcaProgress = new CcaProgress();
        scoutsCcaProgress.setMilestones(scoutsCcaMilestoneList);
        scoutsCcaProgress.setCcaCurrentProgress(new CcaCurrentProgress(2));
        Cca scoutsCca = new Cca(new CcaName("Scouts"), new CcaType("uniformedGroup"), scoutsEquipmentList,
                scoutsCcaProgress);
        ccaTracker.addCca(scoutsCca);

        // NUSSU welfare committee
        EquipmentList nussuWelfareCommEquipmentList = new EquipmentList();
        nussuWelfareCommEquipmentList.addEquipment(new Equipment("pen"));
        nussuWelfareCommEquipmentList.addEquipment(new Equipment("paper"));
        nussuWelfareCommEquipmentList.addEquipment(new Equipment("laptop"));
        nussuWelfareCommEquipmentList.addEquipment(new Equipment("attendance sheet"));
        nussuWelfareCommEquipmentList.addEquipment(new Equipment("mahjong paper"));
        nussuWelfareCommEquipmentList.addEquipment(new Equipment("markers"));
        CcaMilestoneList nussuWelfareCommCcaMilestoneList = new CcaMilestoneList();
        nussuWelfareCommCcaMilestoneList.add(new CcaMilestone("helper"));
        nussuWelfareCommCcaMilestoneList.add(new CcaMilestone("subcomm member"));
        nussuWelfareCommCcaMilestoneList.add(new CcaMilestone("subcomm head"));
        nussuWelfareCommCcaMilestoneList.add(new CcaMilestone("committee member"));
        nussuWelfareCommCcaMilestoneList.add(new CcaMilestone("project director"));
        nussuWelfareCommCcaMilestoneList.add(new CcaMilestone("Director"));
        nussuWelfareCommCcaMilestoneList.add(new CcaMilestone("General Secretary"));
        nussuWelfareCommCcaMilestoneList.add(new CcaMilestone("Vice president"));
        nussuWelfareCommCcaMilestoneList.add(new CcaMilestone("President"));
        CcaProgress nussuWelfareCommCcaProgress = new CcaProgress();
        nussuWelfareCommCcaProgress.setMilestones(nussuWelfareCommCcaMilestoneList);
        nussuWelfareCommCcaProgress.setCcaCurrentProgress(new CcaCurrentProgress(9));
        Cca nussuWelfareCommCca = new Cca(new CcaName("NUSSU Welfare Committee"), new CcaType("club"),
                nussuWelfareCommEquipmentList, nussuWelfareCommCcaProgress);
        ccaTracker.addCca(nussuWelfareCommCca);

        // Hall photography committee
        EquipmentList hallPhotogEquipmentList = new EquipmentList();
        hallPhotogEquipmentList.addEquipment(new Equipment("camera"));
        hallPhotogEquipmentList.addEquipment(new Equipment("tripod"));
        hallPhotogEquipmentList.addEquipment(new Equipment("Remote Shutter Release"));
        hallPhotogEquipmentList.addEquipment(new Equipment("Prime Lens"));
        hallPhotogEquipmentList.addEquipment(new Equipment("Photography Lighting Equipment"));
        hallPhotogEquipmentList.addEquipment(new Equipment("SD Memory Cards"));
        hallPhotogEquipmentList.addEquipment(new Equipment("Camera Cleaning Kit"));
        hallPhotogEquipmentList.addEquipment(new Equipment("Camera Strap"));
        hallPhotogEquipmentList.addEquipment(new Equipment("Camera Bag"));
        hallPhotogEquipmentList.addEquipment(new Equipment("Photography Studio Equipment"));
        CcaMilestoneList hallPhotogCcaMilestoneList = new CcaMilestoneList();
        hallPhotogCcaMilestoneList.add(new CcaMilestone("beginner"));
        hallPhotogCcaMilestoneList.add(new CcaMilestone("photography assistant"));
        hallPhotogCcaMilestoneList.add(new CcaMilestone("subcomm member"));
        hallPhotogCcaMilestoneList.add(new CcaMilestone("committee member"));
        hallPhotogCcaMilestoneList.add(new CcaMilestone("project director"));
        hallPhotogCcaMilestoneList.add(new CcaMilestone("Vice president"));
        hallPhotogCcaMilestoneList.add(new CcaMilestone("President"));
        CcaProgress hallPhotogCcaProgress = new CcaProgress();
        hallPhotogCcaProgress.setMilestones(hallPhotogCcaMilestoneList);
        hallPhotogCcaProgress.setCcaCurrentProgress(new CcaCurrentProgress(3));
        Cca hallPhotogCca = new Cca(new CcaName("Hall photograph comm"), new CcaType("club"),
                hallPhotogEquipmentList, hallPhotogCcaProgress);
        ccaTracker.addCca(hallPhotogCca);

        // Ultimate varsity team
        EquipmentList ultimateEquipmentList = new EquipmentList();
        ultimateEquipmentList.addEquipment(new Equipment("Ultimate Frisbee"));
        ultimateEquipmentList.addEquipment(new Equipment("Cleats"));
        ultimateEquipmentList.addEquipment(new Equipment("Bag"));
        ultimateEquipmentList.addEquipment(new Equipment("Ultimate Frisbee Gloves"));
        ultimateEquipmentList.addEquipment(new Equipment("Clif Bars"));
        ultimateEquipmentList.addEquipment(new Equipment("preworkout drink"));
        ultimateEquipmentList.addEquipment(new Equipment("stool"));
        ultimateEquipmentList.addEquipment(new Equipment("change of clothes"));
        ultimateEquipmentList.addEquipment(new Equipment("water bottle"));
        ultimateEquipmentList.addEquipment(new Equipment("cap"));
        CcaMilestoneList ultimateCcaMilestoneList = new CcaMilestoneList();
        ultimateCcaMilestoneList.add(new CcaMilestone("beginner"));
        ultimateCcaMilestoneList.add(new CcaMilestone("member"));
        ultimateCcaMilestoneList.add(new CcaMilestone("comm member"));
        ultimateCcaMilestoneList.add(new CcaMilestone("comm director"));
        ultimateCcaMilestoneList.add(new CcaMilestone("Vice captain"));
        ultimateCcaMilestoneList.add(new CcaMilestone("Captain"));
        ultimateCcaMilestoneList.add(new CcaMilestone("Player-Coach"));
        ultimateCcaMilestoneList.add(new CcaMilestone("Assistant Coach"));
        ultimateCcaMilestoneList.add(new CcaMilestone("Coach"));
        CcaProgress ultimateCcaProgress = new CcaProgress();
        ultimateCcaProgress.setMilestones(ultimateCcaMilestoneList);
        ultimateCcaProgress.setCcaCurrentProgress(new CcaCurrentProgress(3));
        Cca ultimateCca = new Cca(new CcaName("NUS Ultimate"), new CcaType("sport"),
                ultimateEquipmentList, ultimateCcaProgress);
        ccaTracker.addCca(ultimateCca);

        // Political society
        EquipmentList politicalSocEquipmentList = new EquipmentList();
        politicalSocEquipmentList.addEquipment(new Equipment("Formal shirt"));
        politicalSocEquipmentList.addEquipment(new Equipment("Court shoes or formal shoes"));
        politicalSocEquipmentList.addEquipment(new Equipment("Formal Pants"));
        politicalSocEquipmentList.addEquipment(new Equipment("Tie"));
        politicalSocEquipmentList.addEquipment(new Equipment("Nice watch"));
        politicalSocEquipmentList.addEquipment(new Equipment("Clipboard"));
        politicalSocEquipmentList.addEquipment(new Equipment("Laptop"));
        CcaMilestoneList politicalSocCcaMilestoneList = new CcaMilestoneList();
        politicalSocCcaMilestoneList.add(new CcaMilestone("helper"));
        politicalSocCcaMilestoneList.add(new CcaMilestone("subcomm member"));
        politicalSocCcaMilestoneList.add(new CcaMilestone("subcomm head"));
        politicalSocCcaMilestoneList.add(new CcaMilestone("committee member"));
        politicalSocCcaMilestoneList.add(new CcaMilestone("project director"));
        politicalSocCcaMilestoneList.add(new CcaMilestone("Director"));
        politicalSocCcaMilestoneList.add(new CcaMilestone("General Secretary"));
        politicalSocCcaMilestoneList.add(new CcaMilestone("Vice president"));
        politicalSocCcaMilestoneList.add(new CcaMilestone("President"));
        CcaProgress politicalSocCcaProgress = new CcaProgress();
        politicalSocCcaProgress.setMilestones(politicalSocCcaMilestoneList);
        politicalSocCcaProgress.setCcaCurrentProgress(new CcaCurrentProgress(3));
        Cca politicalSocCca = new Cca(new CcaName("NUS Political Society"), new CcaType("club"),
                politicalSocEquipmentList, politicalSocCcaProgress);
        ccaTracker.addCca(politicalSocCca);

        // Computing club
        EquipmentList computingClubEquipmentList = new EquipmentList();
        computingClubEquipmentList.addEquipment(new Equipment("Tie"));
        computingClubEquipmentList.addEquipment(new Equipment("Matric card"));
        computingClubEquipmentList.addEquipment(new Equipment("Clipboard"));
        computingClubEquipmentList.addEquipment(new Equipment("Formal Pants"));
        computingClubEquipmentList.addEquipment(new Equipment("Court shoes or formal shoes"));
        computingClubEquipmentList.addEquipment(new Equipment("Formal shirt"));
        computingClubEquipmentList.addEquipment(new Equipment("Laptop"));
        CcaMilestoneList computingClubCcaMilestoneList = new CcaMilestoneList();
        computingClubCcaMilestoneList.setMilestones(List.of(
                new CcaMilestone("helper"),
                new CcaMilestone("subcomm member"),
                new CcaMilestone("subcomm head"),
                new CcaMilestone("committee member"),
                new CcaMilestone("comm director"),
                new CcaMilestone("project director"),
                new CcaMilestone("Vice president"),
                new CcaMilestone("president"),
                new CcaMilestone("advisor")
                ));
        CcaProgress computingClubCcaProgress = new CcaProgress();
        computingClubCcaProgress.setMilestones(computingClubCcaMilestoneList);
        computingClubCcaProgress.setCcaCurrentProgress(new CcaCurrentProgress(3));
        Cca computingClubCca = new Cca(new CcaName("Computing club"), new CcaType("club"),
                computingClubEquipmentList, computingClubCcaProgress);
        ccaTracker.addCca(computingClubCca);

        // Street jazz
        EquipmentList streetJazzEquipmentList = new EquipmentList();
        streetJazzEquipmentList.addEquipment(new Equipment("Dance shoes"));
        streetJazzEquipmentList.addEquipment(new Equipment("Extra tights"));
        streetJazzEquipmentList.addEquipment(new Equipment("Hairbrush"));
        streetJazzEquipmentList.addEquipment(new Equipment("comb"));
        streetJazzEquipmentList.addEquipment(new Equipment("elastic ties"));
        streetJazzEquipmentList.addEquipment(new Equipment("barrettes"));
        streetJazzEquipmentList.addEquipment(new Equipment("pins"));
        streetJazzEquipmentList.addEquipment(new Equipment("hairspray"));
        streetJazzEquipmentList.addEquipment(new Equipment("Towel"));
        streetJazzEquipmentList.addEquipment(new Equipment("Deodorant"));
        streetJazzEquipmentList.addEquipment(new Equipment("Perfume"));
        streetJazzEquipmentList.addEquipment(new Equipment("Water bottle"));
        streetJazzEquipmentList.addEquipment(new Equipment("snack"));
        streetJazzEquipmentList.addEquipment(new Equipment("Plastic bag"));
        CcaMilestoneList streetJazzCcaMilestoneList = new CcaMilestoneList();
        streetJazzCcaMilestoneList.setMilestones(List.of(
                new CcaMilestone("grade 1"),
                new CcaMilestone("grade 2"),
                new CcaMilestone("grade 3"),
                new CcaMilestone("grade 4"),
                new CcaMilestone("grade 5"),
                new CcaMilestone("grade 6"),
                new CcaMilestone("grade 7"),
                new CcaMilestone("grade 8"),
                new CcaMilestone("instructor")
        ));
        CcaProgress streetJazzCcaProgress = new CcaProgress();
        streetJazzCcaProgress.setMilestones(streetJazzCcaMilestoneList);
        streetJazzCcaProgress.setCcaCurrentProgress(new CcaCurrentProgress(3));
        Cca streetJazzCca = new Cca(new CcaName("Street Jazz"), new CcaType("performingArt"),
                streetJazzEquipmentList, streetJazzCcaProgress);
        ccaTracker.addCca(streetJazzCca);

        // Hall football team
        EquipmentList hallFootballEquipmentList = new EquipmentList();
        hallFootballEquipmentList.addEquipment(new Equipment("Cleats"));
        hallFootballEquipmentList.addEquipment(new Equipment("Shin Guards"));
        hallFootballEquipmentList.addEquipment(new Equipment("Socks"));
        hallFootballEquipmentList.addEquipment(new Equipment("Soccer Ball"));
        hallFootballEquipmentList.addEquipment(new Equipment("Goalkeeper Gloves"));
        hallFootballEquipmentList.addEquipment(new Equipment("Gear Bag"));
        hallFootballEquipmentList.addEquipment(new Equipment("Ball Bag"));
        hallFootballEquipmentList.addEquipment(new Equipment("Gear Backpack"));
        hallFootballEquipmentList.addEquipment(new Equipment("Water Bottle"));
        hallFootballEquipmentList.addEquipment(new Equipment("Uniform"));
        hallFootballEquipmentList.addEquipment(new Equipment("Practice Clothing"));
        hallFootballEquipmentList.addEquipment(new Equipment("Ball pump"));
        CcaMilestoneList hallFootballCcaMilestoneList = new CcaMilestoneList();
        hallFootballCcaMilestoneList.setMilestones(List.of(
                new CcaMilestone("neighbourhood"),
                new CcaMilestone("community center"),
                new CcaMilestone("sunday league"),
                new CcaMilestone("s-league development team"),
                new CcaMilestone("s-league bench"),
                new CcaMilestone("s-league main team"),
                new CcaMilestone("malaysia league"),
                new CcaMilestone("English Football League Two "),
                new CcaMilestone("English Football League One"),
                new CcaMilestone("English Football League Championship "),
                new CcaMilestone("Premier League")
        ));
        CcaProgress hallFootballCcaProgress = new CcaProgress();
        hallFootballCcaProgress.setMilestones(hallFootballCcaMilestoneList);
        hallFootballCcaProgress.setCcaCurrentProgress(new CcaCurrentProgress(3));
        Cca hallFootballCca = new Cca(new CcaName("Football"), new CcaType("sport"),
                hallFootballEquipmentList, hallFootballCcaProgress);
        ccaTracker.addCca(hallFootballCca);

        return ccaTracker;
    }
}
