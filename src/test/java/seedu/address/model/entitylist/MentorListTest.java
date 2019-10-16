package seedu.address.model.entitylist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import seedu.address.commons.exceptions.AlfredModelException;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.PrefixType;
import seedu.address.testutil.TypicalMentors;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MentorListTest {
    private MentorList mList = new MentorList();

    /**
     * Resets the mentor list.
     */
    @BeforeAll
    private void reset() {
        this.mList = new MentorList();
    }

    @Test
    public void add_validMentor_successful() {
        try {
            this.mList.add(TypicalMentors.A);
        } catch (Exception e) {
            // Silence error
        }
        assertEquals(this.mList.list().size(), 1);
    }

    @Test
    public void add_similarMentor_fail() {
        try {
            this.mList.add(TypicalMentors.A);
        } catch (Exception e) {
            // Silence error
        }
        assertThrows(AlfredModelException.class, () ->
                this.mList.add(TypicalMentors.A_UPDATED)
        );
    }


    @Test
    public void get_validMentor_success() {
        try {
            this.mList.add(TypicalMentors.A);
            Mentor get = this.mList.get(new Id(PrefixType.M, 3));
            assertEquals(get, TypicalMentors.A);
        } catch (Exception e) {
            // Silence error
        }
    }

    @Test
    public void get_invalidMentor_fail() {
        try {
            this.mList.add(TypicalMentors.A);
            assertThrows(AlfredModelException.class, () ->
                    this.mList.get(new Id(PrefixType.M, 2)));
        } catch (Exception e) {
            // Silence error
        }
    }

    @Test
    public void update_validMentor_success() {
        try {
            this.mList.add(TypicalMentors.A);
            this.mList.update(new Id(PrefixType.P, 1),
                    TypicalMentors.A_UPDATED);
            assertTrue(mList.getSpecificTypedList().get(0)
                    .getOrganization().equals(new Name("Organization Updated")));
        } catch (Exception e) {
            // Silence error
        }
    }

    @Test
    public void update_similarMentor_fail() {
        try {
            this.mList.add(TypicalMentors.A);
            this.mList.add(TypicalMentors.B);
            assertThrows(AlfredModelException.class, () -> {
                this.mList.update(new Id(PrefixType.M, 31), TypicalMentors.A_SIMILAR);
            });
        } catch (Exception e) {
            // Silence error
        }
    }

    @Test
    public void delete_validMentor_success() {
        try {
            this.mList.add(TypicalMentors.A);
            this.mList.delete(new Id(PrefixType.P, 3));
            assertTrue(this.mList.list().size() == 0);
        } catch (Exception e) {
            // Silence error
        }
    }
}
