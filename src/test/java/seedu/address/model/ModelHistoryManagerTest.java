package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.commons.exceptions.AlfredModelHistoryException;
import seedu.address.logic.commands.addcommand.AddMentorCommand;
import seedu.address.logic.commands.addcommand.AddParticipantCommand;
import seedu.address.logic.commands.listcommand.ListParticipantCommand;
import seedu.address.model.entity.Email;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Phone;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.SubjectName;
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
    private Mentor newM;

    @BeforeEach
    void beforeEach() throws AlfredException {
        pList = TypicalParticipants.getTypicalParticipantList();
        ParticipantList.setLastUsedId(10);
        mList = TypicalMentors.getTypicalMentorList();
        MentorList.setLastUsedId(10);
        tList = TypicalTeams.getTypicalTeamList();
        TeamList.setLastUsedId(10);
        hm = new ModelHistoryManager(pList, ParticipantList.getLastUsedId(),
                                     mList, MentorList.getLastUsedId(),
                                     tList, TeamList.getLastUsedId());
        newP = new Participant(new Name("Test Person"),
                               new Id(PrefixType.P, 11),
                               new Email("testperson@gmail.com"),
                               new Phone("93200000"));

        newM = new Mentor(new Name("Test Mentor"),
                          new Id(PrefixType.M, 11),
                          new Phone("+6592222222"),
                          new Email("testmentor@gmail.com"),
                          new Name("Test Organization"),
                          SubjectName.SOCIAL);
    }

    /**
     * Helper method to simulate the execution of an AddParticipantCommand.
     * @throws AlfredException
     */
    private void executeAddParticipantCommandAndUpdateModelHistory() throws AlfredException {
        pList.add(newP);
        hm.updateHistory(pList, ParticipantList.getLastUsedId(),
                         mList, MentorList.getLastUsedId(),
                         tList, TeamList.getLastUsedId(), new AddParticipantCommand(newP));
    }

    @Test
    void updateHistory_isTrackableStateCommand() throws AlfredException {
        executeAddParticipantCommandAndUpdateModelHistory();
        assertEquals(2, hm.getLengthOfHistory()); //ModelHistoryRecord with TrackableState Command added successfully.
        assertTrue(hm.canUndo(1));
    }

    @Test
    void updateHistory_notTrackableStateCommand() throws AlfredException {
        hm.updateHistory(pList, ParticipantList.getLastUsedId(),
                         mList, MentorList.getLastUsedId(),
                         tList, TeamList.getLastUsedId(), new ListParticipantCommand());
        assertEquals(1, hm.getLengthOfHistory());
        assertFalse(hm.canUndo(1));
    }

    @Test
    void undo_testEqualityOfLists_success() throws AlfredException {
        ParticipantList origPList = pList.copy();
        executeAddParticipantCommandAndUpdateModelHistory();
        assertTrue(hm.canUndo(1));
        ModelHistoryRecord hr = hm.undo(1);
        ParticipantList historyPList = hr.getParticipantList();
        assertEquals(origPList.getSpecificTypedList(), historyPList.getSpecificTypedList());
    }

    @Test
    void undo_testLastUsedIdSetting_success() throws AlfredModelHistoryException, AlfredException {
        int origPListLastUsedId = ParticipantList.getLastUsedId();
        executeAddParticipantCommandAndUpdateModelHistory();
        ModelHistoryRecord hr = hm.undo(1);
        assertEquals(origPListLastUsedId, hr.getParticipantListLastUsedId());
    }

    @Test
    void canUndo_testUndoEndPoint() throws AlfredException {
        executeAddParticipantCommandAndUpdateModelHistory();
        assertTrue(hm.canUndo(1));
        ModelHistoryRecord hr = hm.undo(1);
        assertFalse(hm.canUndo(1));
    }

    @Test
    void canUndo_initialModelHistoryManager_false() {
        assertFalse(hm.canUndo(1));
    }

    @Test
    void canRedo_testRedoEndPoint() throws AlfredException {
        assertFalse(hm.canRedo(1));
        executeAddParticipantCommandAndUpdateModelHistory();
        assertFalse(hm.canRedo(1));
        hm.undo(1);
        assertTrue(hm.canRedo(1));
        mList.add(newM);
        //Overwrites the valid redo-able commands, so canRedo() should return false.
        hm.updateHistory(pList, ParticipantList.getLastUsedId(),
                         mList, MentorList.getLastUsedId(),
                         tList, TeamList.getLastUsedId(), new AddMentorCommand(newM));
        assertFalse(hm.canRedo(1));
    }

    @Test
    void redo_testEqualityOfLists_success() throws AlfredException {
        executeAddParticipantCommandAndUpdateModelHistory();
        ModelHistoryRecord hr = hm.undo(1);
        hr = hm.redo(1);
        assertEquals(pList.getSpecificTypedList(), hr.getParticipantList().getSpecificTypedList());
    }

    @Test
    void redo_testLastUsedIdSetting_success() throws AlfredException {
        executeAddParticipantCommandAndUpdateModelHistory();
        ModelHistoryRecord hr = hm.undo(1);
        hr = hm.redo(1);
        assertEquals(pList.getLastUsedId(), hr.getParticipantListLastUsedId());
    }

    @Test
    void canRedo_initialModelHistoryManager_false() {
        assertFalse(hm.canRedo(1));
    }

    @Test
    void getCommandHistory_initial() {
        assertEquals(hm.getCommandHistory().size(), 3);
        assertEquals(hm.getCommandHistory().get(0).getCommandType(), CommandRecord.CommandType.END);
        assertEquals(hm.getCommandHistory().get(1).getCommandType(), CommandRecord.CommandType.CURR);
        assertEquals(hm.getCommandHistory().get(2).getCommandType(), CommandRecord.CommandType.END);
    }

    @Test
    void getCommandHistory_withUndoRedo() throws AlfredException {
        executeAddParticipantCommandAndUpdateModelHistory();
        mList.add(newM);
        hm.updateHistory(pList, ParticipantList.getLastUsedId(),
                         mList, MentorList.getLastUsedId(),
                         tList, TeamList.getLastUsedId(), new AddMentorCommand(newM));
        hm.undo(1);
        assertEquals(hm.getCommandHistory().size(), 5);
        assertEquals(hm.getCommandHistory().get(0).getCommandType(), CommandRecord.CommandType.END);
        assertEquals(hm.getCommandHistory().get(1).getCommandType(), CommandRecord.CommandType.REDO);
        assertEquals(hm.getCommandHistory().get(2).getCommandType(), CommandRecord.CommandType.CURR);
        assertEquals(hm.getCommandHistory().get(3).getCommandType(), CommandRecord.CommandType.UNDO);
        assertEquals(hm.getCommandHistory().get(4).getCommandType(), CommandRecord.CommandType.END);
    }

    @Test
    void canUndoRedoMultiple_success() throws AlfredException {
        for (int i = 0; i < 5; i++) {
            hm.updateHistory(pList, ParticipantList.getLastUsedId(),
                             mList, MentorList.getLastUsedId(),
                             tList, TeamList.getLastUsedId(), new AddMentorCommand(newM));
        }
        //Check Multiple Undo
        assertFalse(hm.canUndo(6));
        assertTrue(hm.canUndo(5));
        hm.undo(3);
        assertTrue(hm.canUndo(2));

        //Check Multiple Redo
        assertFalse(hm.canRedo(5));
        assertTrue(hm.canRedo(3));
        hm.redo(2);
        assertTrue(hm.canRedo(1));
    }

    @Test
    void getLengthOfHistory() {
        assertEquals(1, hm.getLengthOfHistory());
    }
}
