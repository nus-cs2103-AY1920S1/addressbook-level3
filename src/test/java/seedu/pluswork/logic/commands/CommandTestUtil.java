package seedu.pluswork.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_INVENTORY_NAME;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_INVENTORY_PRICE;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_MEMBER_ID;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_MEMBER_NAME;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_INDEX;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_NAME;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_STATUS;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_TASK_TAG;
import static seedu.pluswork.testutil.Assert.assertThrows;
import static seedu.pluswork.testutil.TypicalTasksMembers.ORDER_SHIRTS;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.ProjectDashboard;
import seedu.pluswork.model.inventory.InvName;
import seedu.pluswork.model.inventory.Price;
import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.member.MemberId;
import seedu.pluswork.model.member.MemberNameContainsKeywordsPredicate;
import seedu.pluswork.model.task.NameContainsKeywordsPredicate;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.model.task.TaskStatus;
import seedu.pluswork.testutil.EditMemberDescriptorBuilder;
import seedu.pluswork.testutil.EditTaskDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    /* Task related names will be split into four categories: Finance and Publicity */
    public static final String VALID_TASK_NAME_FINANCE = "Review Project Budget";
    public static final String VALID_MEMBER_NAME_FINANCE = "Gabriel Seow";
    public static final String VALID_MEMBER_ID_FINANCE = "GS";
    public static final String VALID_TASK_NAME_PUBLICITY = "Build Project Website";
    public static final String VALID_MEMBER_NAME_PUBLICITY = "Abhinav";
    public static final String VALID_MEMBER_ID_PUBLICITY = "AB";
    public static final String VALID_TASK_STATUS_FINANCE = "unbegun";
    public static final String VALID_TASK_STATUS_PUBLICITY = "doing";
    public static final String VALID_TAG_FINANCE = "finance";
    public static final String VALID_MEMBER_TAG_FINANCE = "ChiefProgrammer";
    public static final String VALID_TAG_PUBLICITY = "publicity";
    public static final String VALID_MEMBER_TAG_PUBLICITY = "UIdesigner";
    public static final String VALID_TAG_URGENCY = "urgent";
    public static final String VALID_TASK_DEADLINE = "20-12-2020 10:00";

    public static final String TASK_NAME_DESC_FINANCE = " " + PREFIX_TASK_NAME + VALID_TASK_NAME_FINANCE;
    public static final String MEMBER_NAME_DESC_FINANCE = " " + PREFIX_MEMBER_NAME + VALID_MEMBER_NAME_FINANCE;
    public static final String TASK_NAME_DESC_PUBLICITY = " " + PREFIX_TASK_NAME + VALID_TASK_NAME_PUBLICITY;
    public static final String MEMBER_NAME_DESC_PUBLICITY = " " + PREFIX_MEMBER_NAME + VALID_MEMBER_NAME_PUBLICITY;
    public static final String TASK_STATUS_DESC_FINANCE = " " + PREFIX_TASK_STATUS + VALID_TASK_STATUS_FINANCE;
    public static final String TASK_STATUS_DESC_PUBLICITY = " " + PREFIX_TASK_STATUS + VALID_TASK_STATUS_PUBLICITY;
    public static final String TAG_DESC_PUBLICITY = " " + PREFIX_TASK_TAG + VALID_TAG_PUBLICITY;
    public static final String TAG_DESC_URGENCY = " " + PREFIX_TASK_TAG + VALID_TAG_URGENCY;
    public static final String TAG_DESC_FINANCE = " " + PREFIX_TASK_TAG + VALID_TAG_FINANCE;
    public static final String MEMBER_NAME_JOHN_DOE = " " + PREFIX_MEMBER_NAME + "John Doe";
    public static final String MEMBER_ID_JOHN_DOE = " " + PREFIX_MEMBER_ID + "JD";
    public static final String TASK_DEADLINE_DESC = " " + PREFIX_DEADLINE + VALID_TASK_DEADLINE;

    public static final String VALID_INVENTORY_NAME_SPORTS = "Sports equipments";
    public static final String VALID_INVENTORY_NAME_MUSIC = "Drums";
    public static final double VALID_INVENTORY_PRICE_SPORTS = 79.50;
    public static final double VALID_INVENTORY_PRICE_MUSIC = 999;
    public static final String INVENTORY_NAME_DESC_SPORTS = " " + PREFIX_INVENTORY_NAME + VALID_INVENTORY_NAME_SPORTS;
    public static final String INVENTORY_PRICE_DESC_SPORTS = " " + PREFIX_INVENTORY_PRICE
            + VALID_INVENTORY_PRICE_SPORTS;
    public static final String INVENTORY_TASKID_DESC_SPORTS = " " + PREFIX_TASK_INDEX + 1;
    public static final String INVENTORY_MEMBERID_DESC_SPORTS = " " + PREFIX_MEMBER_ID + "AR";

    public static final String INVALID_TASK_NAME_DESC = " " + PREFIX_TASK_NAME
            + "Project Review&"; // '&' not allowed in names
    public static final String INVALID_MEMBER_NAME_DESC = " " + PREFIX_MEMBER_NAME
            + "Project Review&"; // '&' not allowed in names

    public static final String INVALID_TASK_STATUS_DESC = " " + PREFIX_TASK_STATUS
            + "reviewing"; // invalid status - statuses are determined in {@code TaskStatus}
    public static final String INVALID_TAG_DESC = " " + PREFIX_TASK_TAG
            + "paused*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditTaskCommand.EditTaskDescriptor TASK_DESC_FINANCE;
    public static final EditTaskCommand.EditTaskDescriptor TASK_DESC_PUBLICITY;
    public static final EditTaskCommand.EditTaskDescriptor TASK_DESC_DEADLINE;
    public static final EditInventoryCommand.EditInventoryDescriptor INVENTORY_DESC_TOY;
    public static final EditMemberCommand.EditMemberDescriptor MEMBER_DESC_FINANCE;
    public static final EditMemberCommand.EditMemberDescriptor MEMBER_DESC_PUBLICITY;

    static {
        TASK_DESC_FINANCE = new EditTaskDescriptorBuilder().withName(VALID_TASK_NAME_FINANCE)
                .withStatus(TaskStatus.UNBEGUN)
                .withTags(VALID_TAG_FINANCE).build();
        TASK_DESC_PUBLICITY = new EditTaskDescriptorBuilder().withName(VALID_TASK_NAME_PUBLICITY)
                .withStatus(TaskStatus.DOING)
                .withTags(VALID_TAG_PUBLICITY).build();
        TASK_DESC_DEADLINE = new EditTaskDescriptorBuilder(ORDER_SHIRTS)
                .withDeadline(LocalDateTime.now()
                        .plusMonths(3)).build();

        INVENTORY_DESC_TOY = new EditInventoryCommand.EditInventoryDescriptor();
        INVENTORY_DESC_TOY.setName(new InvName("toy"));
        INVENTORY_DESC_TOY.setPrice(new Price(1));
        INVENTORY_DESC_TOY.setTaskId(new Index(1));
        INVENTORY_DESC_TOY.setMemId(new MemberId("rak"));

        MEMBER_DESC_FINANCE = new EditMemberDescriptorBuilder().withName(VALID_MEMBER_NAME_FINANCE)
                .withTags(VALID_TAG_FINANCE).build();
        MEMBER_DESC_PUBLICITY = new EditMemberDescriptorBuilder().withName(VALID_MEMBER_NAME_PUBLICITY)
                .withTags(VALID_TAG_PUBLICITY).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered task list and selected task in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        ProjectDashboard expectedProjectDashboard = new ProjectDashboard(actualModel.getProjectDashboard());
        List<Task> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTasksList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedProjectDashboard, actualModel.getProjectDashboard());
        assertEquals(expectedFilteredList, actualModel.getFilteredTasksList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the task at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showTaskAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTasksList().size());

        Task task = model.getFilteredTasksList().get(targetIndex.getZeroBased());
        final String[] splitName = task.getName().fullName.split("\\s+");
        model.updateFilteredTasksList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredTasksList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the member at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showMemberAtId(Model model, MemberId targetId) {
        List<Member> lastShownList = model.getFilteredMembersList();

        boolean contains = false;
        Member targetMember = null;

        for (int i = 0; i < lastShownList.size(); i++) {
            if (lastShownList.get(i).getId().equals(targetId)) {
                contains = true;
                targetMember = lastShownList.get(i);
                break;
            }
        }
        assertTrue(contains);

        final String[] splitName = targetMember.getName().fullName.split("\\s+");
        model.updateFilteredMembersList(new MemberNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredMembersList().size());
    }

}
