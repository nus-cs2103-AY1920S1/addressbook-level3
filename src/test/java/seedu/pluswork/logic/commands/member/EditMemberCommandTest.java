package seedu.pluswork.logic.commands.member;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pluswork.logic.commands.CommandTestUtil.MEMBER_DESC_FINANCE;
import static seedu.pluswork.logic.commands.CommandTestUtil.MEMBER_DESC_PUBLICITY;
import static seedu.pluswork.logic.commands.CommandTestUtil.VALID_MEMBER_NAME_FINANCE;
import static seedu.pluswork.logic.commands.CommandTestUtil.VALID_TAG_FINANCE;
import static seedu.pluswork.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.pluswork.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.pluswork.testutil.TypicalIds.ID_FIRST_MEMBER;
import static seedu.pluswork.testutil.TypicalIds.ID_SECOND_MEMBER;
import static seedu.pluswork.testutil.TypicalTasksMembers.getTypicalProjectDashboard;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.pluswork.commons.core.Messages;
import seedu.pluswork.commons.core.index.Index;
import seedu.pluswork.logic.commands.member.EditMemberCommand;
import seedu.pluswork.logic.commands.universal.ClearCommand;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.ModelManager;
import seedu.pluswork.model.ProjectDashboard;
import seedu.pluswork.model.UserPrefs;
import seedu.pluswork.model.UserSettings;
import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.member.MemberId;
import seedu.pluswork.testutil.EditMemberDescriptorBuilder;
import seedu.pluswork.testutil.MemberBuilder;

public class EditMemberCommandTest {
    private Model model = new ModelManager(getTypicalProjectDashboard(), new UserPrefs(), new UserSettings());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Index indexLastMember = Index.fromOneBased(model.getFilteredMembersList().size());
        Member lastMember = model.getFilteredMembersList().get(indexLastMember.getZeroBased());

        MemberBuilder memInList = new MemberBuilder(lastMember);
        Member editedMember = memInList.withName(VALID_MEMBER_NAME_FINANCE)
                .withTags(VALID_TAG_FINANCE).build();

        EditMemberCommand.EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder()
                .withName(VALID_MEMBER_NAME_FINANCE).withTags(VALID_TAG_FINANCE).build();
        EditMemberCommand editMemberCommand = new EditMemberCommand(lastMember.getId(), descriptor);

        String expectedMessage = String.format(EditMemberCommand.MESSAGE_EDIT_MEMBER_SUCCESS, editedMember);

        Model expectedModel = new ModelManager(new ProjectDashboard(
                model.getProjectDashboard()), new UserPrefs(), new UserSettings());
        expectedModel.setMember(lastMember, editedMember);

        assertCommandSuccess(editMemberCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditMemberCommand editMemberCommand = new EditMemberCommand(ID_FIRST_MEMBER,
                new EditMemberCommand.EditMemberDescriptor());

        int memIndex = 0;

        List<Member> memList = model.getFilteredMembersList();

        for (int i = 0; i < memList.size(); i++) {
            if (memList.get(i).getId().equals(ID_FIRST_MEMBER)) {
                memIndex = i;
            }
        }

        Member editedMember = model.getFilteredMembersList().get(memIndex);

        String expectedMessage = String.format(EditMemberCommand.MESSAGE_EDIT_MEMBER_SUCCESS, editedMember);

        Model expectedModel = new ModelManager(new ProjectDashboard(
                model.getProjectDashboard()), new UserPrefs(), new UserSettings());

        assertCommandSuccess(editMemberCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {

        Member memInFilteredList = model.getFilteredMembersList().get(0);
        MemberId memId = memInFilteredList.getId();
        Member editedMember = new MemberBuilder(memInFilteredList).withName(VALID_MEMBER_NAME_FINANCE).withId(memId)
                .build();
        EditMemberCommand editMemberCommand = new EditMemberCommand(memId,
                new EditMemberDescriptorBuilder().withName(VALID_MEMBER_NAME_FINANCE).build());

        String expectedMessage = String.format(EditMemberCommand.MESSAGE_EDIT_MEMBER_SUCCESS, editedMember);

        Model expectedModel = new ModelManager(new ProjectDashboard(
                model.getProjectDashboard()), new UserPrefs(), new UserSettings());
        expectedModel.setMember(model.getFilteredMembersList().get(0), editedMember);

        assertCommandSuccess(editMemberCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidMemberIdUnfilteredList_failure() {
        MemberId invalidId = new MemberId("invalid id");
        EditMemberCommand.EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder()
                .withName(VALID_MEMBER_NAME_FINANCE).build();
        EditMemberCommand editMemberCommand = new EditMemberCommand(invalidId, descriptor);

        assertCommandFailure(editMemberCommand, model, Messages.MESSAGE_INVALID_MEMBER_ID);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidMemberIdFilteredList_failure() {
        MemberId invalidId = new MemberId("invalid id");
        // ensures that outOfBoundIndex is still in bounds of address book list

        EditMemberCommand editMemberCommand = new EditMemberCommand(invalidId,
                new EditMemberDescriptorBuilder().withName(VALID_MEMBER_NAME_FINANCE).build());

        assertCommandFailure(editMemberCommand, model, Messages.MESSAGE_INVALID_MEMBER_ID);
    }

    @Test
    public void equals() {
        final EditMemberCommand standardCommand = new EditMemberCommand(ID_FIRST_MEMBER, MEMBER_DESC_FINANCE);

        // same values -> returns true
        EditMemberCommand.EditMemberDescriptor copyDescriptor =
                new EditMemberCommand.EditMemberDescriptor(MEMBER_DESC_FINANCE);
        EditMemberCommand commandWithSameValues = new EditMemberCommand(ID_FIRST_MEMBER, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditMemberCommand(ID_SECOND_MEMBER, MEMBER_DESC_FINANCE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditMemberCommand(ID_FIRST_MEMBER, MEMBER_DESC_PUBLICITY)));
    }
}

