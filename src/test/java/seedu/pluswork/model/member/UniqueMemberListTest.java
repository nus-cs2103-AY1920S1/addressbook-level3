package seedu.pluswork.model.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.pluswork.logic.commands.CommandTestUtil.VALID_MEMBER_ID_PUBLICITY;
import static seedu.pluswork.testutil.Assert.assertThrows;
import static seedu.pluswork.testutil.TypicalTasksMembers.ELSA_KOH;
import static seedu.pluswork.testutil.TypicalTasksMembers.GABRIEL_SEOW;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.pluswork.model.member.exceptions.DuplicateMemberException;
import seedu.pluswork.model.member.exceptions.MemberNotFoundException;
import seedu.pluswork.testutil.MemberBuilder;

public class UniqueMemberListTest {
    private final UniqueMemberList uniqueMemberList = new UniqueMemberList();

    @Test
    public void contains_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemberList.contains(null));
    }

    @Test
    public void contains_taskNotInList_returnsFalse() {
        assertFalse(uniqueMemberList.contains(GABRIEL_SEOW));
    }

    @Test
    public void contains_taskInList_returnsTrue() {
        uniqueMemberList.add(GABRIEL_SEOW);
        assertTrue(uniqueMemberList.contains(GABRIEL_SEOW));
    }

    @Test
    public void contains_memberWithSameIdentityFieldsInList_returnsTrue() {
        uniqueMemberList.add(GABRIEL_SEOW);
        Member editedMemberGabrielSeow = new MemberBuilder(GABRIEL_SEOW).withTags(VALID_MEMBER_ID_PUBLICITY)
                .build();
        assertTrue(uniqueMemberList.contains(editedMemberGabrielSeow));
    }

    @Test
    public void add_nullMember_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemberList.add(null));
    }

    @Test
    public void add_duplicateTask_throwsDuplicateTaskException() {
        uniqueMemberList.add(GABRIEL_SEOW);
        assertThrows(DuplicateMemberException.class, () -> uniqueMemberList.add(GABRIEL_SEOW));
    }

    @Test
    public void setMember_nullTargetTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemberList.setMember(null, GABRIEL_SEOW));
    }

    @Test
    public void setMember_nullEditedTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemberList.setMember(GABRIEL_SEOW, null));
    }

    @Test
    public void setMember_targetTaskNotInList_throwsTaskNotFoundException() {
        assertThrows(MemberNotFoundException.class, () -> uniqueMemberList.setMember(GABRIEL_SEOW, GABRIEL_SEOW));
    }

    @Test
    public void setMember_editedTaskIsSameTask_success() {
        uniqueMemberList.add(GABRIEL_SEOW);
        uniqueMemberList.setMember(GABRIEL_SEOW, GABRIEL_SEOW);
        UniqueMemberList expectedUniqueMemberList = new UniqueMemberList();
        expectedUniqueMemberList.add(GABRIEL_SEOW);
        assertEquals(expectedUniqueMemberList, uniqueMemberList);
    }

    @Test
    public void setMember_editedTaskHasSameIdentity_success() {
        uniqueMemberList.add(GABRIEL_SEOW);
        Member editedAlice = new MemberBuilder(GABRIEL_SEOW).withTags(VALID_MEMBER_ID_PUBLICITY)
                .build();
        uniqueMemberList.setMember(GABRIEL_SEOW, editedAlice);
        UniqueMemberList expectedUniqueMemberList = new UniqueMemberList();
        expectedUniqueMemberList.add(editedAlice);
        assertEquals(expectedUniqueMemberList, uniqueMemberList);
    }

    @Test
    public void setMember_editedTaskHasDifferentIdentity_success() {
        uniqueMemberList.add(GABRIEL_SEOW);
        uniqueMemberList.setMember(GABRIEL_SEOW, ELSA_KOH);
        UniqueMemberList expecteduniqueMemberList = new UniqueMemberList();
        expecteduniqueMemberList.add(ELSA_KOH);
        assertEquals(expecteduniqueMemberList, uniqueMemberList);
    }

    @Test
    public void setMember_editedTaskHasNonUniqueIdentity_throwsDuplicateTaskException() {
        uniqueMemberList.add(GABRIEL_SEOW);
        uniqueMemberList.add(ELSA_KOH);
        assertThrows(DuplicateMemberException.class, () -> uniqueMemberList.setMember(GABRIEL_SEOW, ELSA_KOH));
    }

    @Test
    public void remove_nullTask_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemberList.remove(null));
    }

    @Test
    public void remove_taskDoesNotExist_throwsTaskNotFoundException() {
        assertThrows(MemberNotFoundException.class, () -> uniqueMemberList.remove(GABRIEL_SEOW));
    }

    @Test
    public void remove_existingTask_removesTask() {
        uniqueMemberList.add(GABRIEL_SEOW);
        uniqueMemberList.remove(GABRIEL_SEOW);
        UniqueMemberList expecteduniqueMemberList = new UniqueMemberList();
        assertEquals(expecteduniqueMemberList, uniqueMemberList);
    }

    @Test
    public void setMembers_nullUniqueTasksList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemberList.setMembers((UniqueMemberList) null));
    }

    @Test
    public void setMembers_uniqueMemberList_replacesOwnListWithProvidedUniqueTasksList() {
        uniqueMemberList.add(GABRIEL_SEOW);
        UniqueMemberList expecteduniqueMemberList = new UniqueMemberList();
        expecteduniqueMemberList.add(ELSA_KOH);
        uniqueMemberList.setMembers(expecteduniqueMemberList);
        assertEquals(expecteduniqueMemberList, uniqueMemberList);
    }

    @Test
    public void setMembers_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueMemberList.setMembers((List<Member>) null));
    }

    @Test
    public void setMembers_list_replacesOwnListWithProvidedList() {
        uniqueMemberList.add(GABRIEL_SEOW);
        List<Member> memberList = Collections.singletonList(ELSA_KOH);
        uniqueMemberList.setMembers(memberList);
        UniqueMemberList expecteduniqueMemberList = new UniqueMemberList();
        expecteduniqueMemberList.add(ELSA_KOH);
        assertEquals(expecteduniqueMemberList, uniqueMemberList);
    }

    @Test
    public void setMembers_listWithDuplicateTasks_throwsDuplicateTaskException() {
        List<Member> listWithDuplicateTasks = Arrays.asList(GABRIEL_SEOW, GABRIEL_SEOW);
        assertThrows(DuplicateMemberException.class, () -> uniqueMemberList.setMembers(listWithDuplicateTasks));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueMemberList.asUnmodifiableObservableList().remove(0));
    }
}
