package seedu.address.model.member;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEMBER_ID_PUBLICITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEMBER_NAME_FINANCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MEMBER_NAME_PUBLICITY;
import static seedu.address.testutil.TypicalTasksMembers.ELSA_KOH;
import static seedu.address.testutil.TypicalTasksMembers.GABRIEL_SEOW;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.MemberBuilder;

public class MemberTest {
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Member member = new MemberBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> member.getTags().remove(0));
    }

    @Test
    public void isSameMember() {
        // same object -> returns true
        assertTrue(GABRIEL_SEOW.isSameMember(GABRIEL_SEOW));

        // null -> returns false
        assertFalse(GABRIEL_SEOW.isSameMember(null));

        Member editedMemberGabrielSeow = new MemberBuilder(GABRIEL_SEOW).build();

        // different name -> returns false
        editedMemberGabrielSeow = new MemberBuilder(GABRIEL_SEOW)
                .withName(VALID_MEMBER_NAME_PUBLICITY).build();
        assertFalse(GABRIEL_SEOW.isSameMember(editedMemberGabrielSeow));

        // same name, different attributes -> returns true
        editedMemberGabrielSeow = new MemberBuilder(GABRIEL_SEOW)
                .withId(new MemberId("GT"))
                .build();
        assertTrue(GABRIEL_SEOW.isSameMember(editedMemberGabrielSeow));

        // same name, different attributes -> returns true
        editedMemberGabrielSeow = new MemberBuilder(GABRIEL_SEOW)
                .withTags(VALID_MEMBER_ID_PUBLICITY)
                .build();
        assertTrue(GABRIEL_SEOW.isSameMember(editedMemberGabrielSeow));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Member orderShirtsCopy = new MemberBuilder(GABRIEL_SEOW).build();
        assertTrue(GABRIEL_SEOW.equals(orderShirtsCopy));

        // same object -> returns true
        assertTrue(GABRIEL_SEOW.equals(GABRIEL_SEOW));

        // null -> returns false
        assertFalse(GABRIEL_SEOW.equals(null));

        // different type -> returns false
        assertFalse(GABRIEL_SEOW.equals(5));

        // different Member -> returns false
        assertFalse(GABRIEL_SEOW.equals(ELSA_KOH));

        // different name -> returns false
        Member editedMemberGabrielSeow = new MemberBuilder(GABRIEL_SEOW).withName(VALID_MEMBER_NAME_PUBLICITY).build();
        assertFalse(GABRIEL_SEOW.equals(editedMemberGabrielSeow));

        // different tags -> returns false
        editedMemberGabrielSeow = new MemberBuilder(GABRIEL_SEOW).withTags(VALID_MEMBER_ID_PUBLICITY).build();
        assertFalse(GABRIEL_SEOW.equals(editedMemberGabrielSeow));

        // different Member status return false
        editedMemberGabrielSeow = new MemberBuilder(GABRIEL_SEOW).withId(new MemberId("GT")).build();
        assertFalse(GABRIEL_SEOW.equals(editedMemberGabrielSeow));
    }
}
