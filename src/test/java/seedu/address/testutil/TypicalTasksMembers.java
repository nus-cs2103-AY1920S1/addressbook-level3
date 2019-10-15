package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_MEMBER_ID_FINANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEMBER_ID_PUBLICITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEMBER_NAME_FINANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEMBER_NAME_PUBLICITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEMBER_TAG_FINANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEMBER_TAG_PUBLICITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FINANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PUBLICITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_FINANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TASK_NAME_PUBLICITY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ProjectDashboard;
import seedu.address.model.member.Member;
import seedu.address.model.member.MemberId;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;

/**
 * A utility class containing a list of {@code Task} objects to be used in tests.
 */
public class TypicalTasksMembers {

    //TASKS
    public static final Task ORDER_SHIRTS = new TaskBuilder().withName("Order shirts from supplier")
            .withStatus(TaskStatus.UNBEGUN)
            .withTags("inventory").build();
    public static final Task PRINT_POSTERS = new TaskBuilder().withName("Print posters for student fair")
            .withStatus(TaskStatus.UNBEGUN)
            .withTags("urgent", "publicity").build();
    public static final Task GET_SPONSORS = new TaskBuilder().withName("Get sponsors for student fair")
            .withStatus(TaskStatus.DOING).build();
    public static final Task RECRUIT_MEMBERS = new TaskBuilder().withName("Recruit members for student fair")
            .withStatus(TaskStatus.DONE)
            .withTags("manpower").build();
    public static final Task ORDER_CATERING = new TaskBuilder()
            .withName("Find caterers for snacks in student fair booth")
            .withStatus(TaskStatus.DOING)
            .withTags("inventory")
            .build();
    public static final Task BRIEFING_PREPARATION = new TaskBuilder()
            .withName("Prepare presentation briefing")
            .withStatus(TaskStatus.DONE)
            .withTags("personal")
            .build();
    public static final Task ORGANISE_STORE = new TaskBuilder()
            .withName("Organise inventory store before student fair")
            .withStatus(TaskStatus.UNBEGUN)
            .withTags("inventory", "urgent")
            .build();

    // Manually added
    public static final Task FIND_VP = new TaskBuilder()
            .withName("Find vice president of planning")
            .withStatus(TaskStatus.DOING)
            .withTags("manpower", "urgent")
            .build();
    public static final Task FAREWELL_PARTY = new TaskBuilder()
            .withStatus(TaskStatus.DONE)
            .withTags("recreation", "important")
            .withName("Ida Mueller").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final Task REVIEW_BUDGET = new TaskBuilder().withName(VALID_TASK_NAME_FINANCE)
            .withStatus(TaskStatus.UNBEGUN)
            .withTags(VALID_TAG_FINANCE).build();
    public static final Task BUILD_WEBSITE = new TaskBuilder().withName(VALID_TASK_NAME_PUBLICITY)
            .withStatus(TaskStatus.DOING)
            .withTags(VALID_TAG_PUBLICITY)
            .build();

    //MEMBERS
    public static final Member JOHN_DOE = new MemberBuilder().withName("John Doe")
            .withId(new MemberId("JD"))
            .withTags("help").build();
    public static final Member GABRIEL_SEOW = new MemberBuilder().withName("Gabriel Seow")
            .withId(new MemberId("GS"))
            .withTags("ChiefProgrammer").build();
    public static final Member ELSA_KOH = new MemberBuilder().withName("Elsa Koh")
            .withId(new MemberId("EK")).build();
    public static final Member ARUN = new MemberBuilder().withName("Arun")
            .withId(new MemberId("AR"))
            .withTags("Programmer").build();
    public static final Member ABHINAV = new MemberBuilder().withName("Abhinav")
            .withId(new MemberId("AB"))
            .withTags("UIdesigner")
            .build();
    public static final Member SEAH_LYNN = new MemberBuilder().withName("Seah Lynn")
            .withId(new MemberId("SL"))
            .withTags("Programmer")
            .build();
    public static final Member RANDOM = new MemberBuilder().withName("Random Member")
            .withId(new MemberId("RM"))
            .withTags("random", "member")
            .build();

    // Manually added
    public static final Member ANDY = new MemberBuilder()
            .withName("Andy Anderson")
            .withId(new MemberId("AA"))
            .withTags("random", "member")
            .build();
    public static final Member BENSON = new MemberBuilder()
            .withId(new MemberId("BB"))
            .withTags("random", "member")
            .withName("Benson Bon").build();

    // Manually added - Task's details found in {@code CommandTestUtil}
    public static final Member FINANCE_MEMBER = new MemberBuilder().withName(VALID_MEMBER_NAME_FINANCE)
            .withId(new MemberId(VALID_MEMBER_ID_FINANCE))
            .withTags(VALID_MEMBER_TAG_FINANCE).build();
    public static final Member PUBLICITY_MEMBER = new MemberBuilder().withName(VALID_MEMBER_NAME_PUBLICITY)
            .withId(new MemberId(VALID_MEMBER_ID_PUBLICITY))
            .withTags(VALID_MEMBER_TAG_PUBLICITY)
            .build();

    private TypicalTasksMembers() {} // prevents instantiation

    /**
     * Returns an {@code ProjectDashboard} with all the typical persons.
     */
    public static ProjectDashboard getTypicalProjectDashboard() {
        ProjectDashboard ab = new ProjectDashboard();
        for (Task task : getTypicalTasksMembers()) {
            ab.addTask(task);
        }

        for (Member member : getTypicalMembers()) {
            ab.addMember(member);
        }
        return ab;
    }

    public static List<Task> getTypicalTasksMembers() {
        return new ArrayList<>(Arrays.asList(ORDER_SHIRTS, PRINT_POSTERS, GET_SPONSORS,
                RECRUIT_MEMBERS, ORDER_CATERING, BRIEFING_PREPARATION, ORGANISE_STORE));
    }

    public static List<Member> getTypicalMembers() {
        return new ArrayList<>(Arrays.asList(JOHN_DOE, GABRIEL_SEOW, ELSA_KOH, ABHINAV, ARUN,
                SEAH_LYNN, RANDOM));
    }
}
