package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.commons.exceptions.AlfredModelHistoryException;
import seedu.address.model.entity.Email;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Phone;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entitylist.MentorList;
import seedu.address.model.entitylist.ParticipantList;
import seedu.address.model.entitylist.TeamList;
import seedu.address.testutil.TypicalMentors;
import seedu.address.testutil.TypicalParticipants;
import seedu.address.testutil.TypicalTeams;

class ModelHistoryManagerTest {
    private ParticipantList pList;
    private MentorList mList;
    private TeamList tList;
    private ModelHistoryManager hm;
    private Participant newP;

    @BeforeEach
    void beforeEach() throws AlfredException {
        pList = TypicalParticipants.getTypicalParticipantList();
        ParticipantList.setLastUsedId(1);
        mList = TypicalMentors.getTypicalMentorList();
        MentorList.setLastUsedId(2);
        tList = TypicalTeams.getTypicalTeamList();
        TeamList.setLastUsedId(3);
        hm = new ModelHistoryManager(pList, ParticipantList.getLastUsedId(),
                                     mList, MentorList.getLastUsedId(),
                                     tList, TeamList.getLastUsedId());
        newP = new Participant(new Name("Test Person"),
                               new Id(PrefixType.P, 123),
                               new Email("testperson@gmail.com"),
                               new Phone("93200000"));
    }

    @Test
    void updateHistory_changeToEntityList_success() throws AlfredModelHistoryException, AlfredException {
        pList.add(newP);
        hm.updateHistory(pList, ParticipantList.getLastUsedId(),
                         mList, MentorList.getLastUsedId(),
                         tList, TeamList.getLastUsedId(), null);
        assertEquals(2, hm.getLengthOfHistory());
        assertTrue(hm.canUndo());
    }

    @Test
    void updateHistory_noEntityChange_success() throws AlfredModelHistoryException {
        hm.updateHistory(pList, ParticipantList.getLastUsedId(),
                         mList, MentorList.getLastUsedId(),
                         tList, TeamList.getLastUsedId(), null);
        assertEquals(2, hm.getLengthOfHistory());
        assertTrue(hm.canUndo());
    }

    @Test
    void canUndo_initialModelHistoryManager_false() {
        assertFalse(hm.canUndo());
    }

    @Test
    void canUndo_entityChange_true() throws AlfredModelHistoryException {
        hm.updateHistory(pList, ParticipantList.getLastUsedId(),
                         mList, MentorList.getLastUsedId(),
                         tList, TeamList.getLastUsedId(), null);
        assertTrue(hm.canUndo());
    }

    @Test
    void canRedo() {
        //TODO: Update this in v1.3-1.4
        assertFalse(hm.canRedo());
    }

    @Test
    void undo_testEqualityOfLists_success() throws AlfredException {
        pList.add(newP);
        hm.updateHistory(pList, ParticipantList.getLastUsedId(),
                         mList, MentorList.getLastUsedId(),
                         tList, TeamList.getLastUsedId(), null);
        hm.updateHistory(pList, ParticipantList.getLastUsedId(),
                         mList, MentorList.getLastUsedId(),
                         tList, TeamList.getLastUsedId(), null);

        assertTrue(hm.canUndo());
        ModelHistoryRecord hr = hm.undo();
        ParticipantList historyPList = hr.getParticipantList();
        assertEquals(pList.getSpecificTypedList(), historyPList.getSpecificTypedList());
    }

    @Test
    void undo_testLastUsedIdSetting_success() throws AlfredModelHistoryException, AlfredException {
        int lastUsedIdBefore = ParticipantList.getLastUsedId();
        hm.updateHistory(pList, ParticipantList.getLastUsedId(),
                         mList, MentorList.getLastUsedId(),
                         tList, TeamList.getLastUsedId(), null);
        pList.add(newP);
        hm.updateHistory(pList, ParticipantList.getLastUsedId(),
                         mList, MentorList.getLastUsedId(),
                         tList, TeamList.getLastUsedId(), null);
        ModelHistoryRecord hr = hm.undo();
        assertEquals(lastUsedIdBefore, hr.getParticipantListLastUsedId());
        assertEquals(hr.getParticipantListLastUsedId(), ParticipantList.getLastUsedId());
    }

    @Test
    void redo() {
        //TODO: Update this in v1.3-1.4
        assertTrue(true);
    }

    @Test
    void getLengthOfHistory() {
        assertEquals(1, hm.getLengthOfHistory());
    }
}
