package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.addcommand.AddParticipantCommand;
import seedu.address.model.entity.Email;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Phone;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.Team;
import seedu.address.model.entitylist.MentorList;
import seedu.address.model.entitylist.ParticipantList;
import seedu.address.model.entitylist.TeamList;
import seedu.address.testutil.TypicalMentors;
import seedu.address.testutil.TypicalParticipants;
import seedu.address.testutil.TypicalTeams;

class ModelHistoryRecordTest {
    private ModelHistoryRecord hr;
    private ParticipantList pList;
    private int pListId;
    private MentorList mList;
    private int mListId;
    private TeamList tList;
    private int tListId;
    private Participant newP;
    private Command c;

    @BeforeEach
    void setUp() throws AlfredException {
        pListId = 1;
        pList = TypicalParticipants.getTypicalParticipantList();
        ParticipantList.setLastUsedId(pListId);

        mListId = 2;
        mList = TypicalMentors.getTypicalMentorList();
        MentorList.setLastUsedId(mListId);

        tListId = 3;
        tList = TypicalTeams.getTypicalTeamList();
        TeamList.setLastUsedId(tListId);

        newP = new Participant(new Name("Test Person"),
                               new Id(PrefixType.P, 123),
                               new Email("testperson@gmail.com"),
                               new Phone("93200000"));
        Command c = new AddParticipantCommand(newP);
        hr = new ModelHistoryRecord(pList, pListId,
                                    mList, mListId,
                                    tList, tListId,
                                    c);
    }

    @Test
    void getParticipantList_checkDeepCopy() {
        assertTrue(hr.getParticipantList() != pList);
        List<Participant> copiedPList = hr.getParticipantList().getSpecificTypedList();
        List<Participant> origPList = pList.getSpecificTypedList();
        for (int i = 0; i < copiedPList.size(); i++) {
            Participant copiedParticipant = copiedPList.get(i);
            Participant origParticipant = origPList.get(i);
            assertTrue(copiedParticipant != origParticipant);
            assertTrue(copiedParticipant.getEmail() != origParticipant.getEmail());
            assertTrue(copiedParticipant.getPhone() != origParticipant.getPhone());
            assertTrue(copiedParticipant.getId() != origParticipant.getId());
            assertTrue(copiedParticipant.getName() != origParticipant.getName());
        }
    }

    @Test
    void getMentorList_checkDeepCopy() {
        assertTrue(hr.getMentorList() != mList);
        List<Mentor> copiedMList = hr.getMentorList().getSpecificTypedList();
        List<Mentor> origMList = mList.getSpecificTypedList();
        for (int i = 0; i < copiedMList.size(); i++) {
            Mentor copiedMentor = copiedMList.get(i);
            Mentor origMentor = origMList.get(i);
            assertTrue(copiedMentor != origMentor);
            assertTrue(copiedMentor.getName() != origMentor.getName());
            assertTrue(copiedMentor.getId() != origMentor.getId());
            assertTrue(copiedMentor.getPhone() != origMentor.getPhone());
            assertTrue(copiedMentor.getEmail() != origMentor.getEmail());
            assertTrue(copiedMentor.getOrganization() != origMentor.getOrganization());
            assertTrue(copiedMentor.getSubject() == origMentor.getSubject()); //Subject is an Enum
        }
    }

    @Test
    void getTeamList_checkDeepCopy() {
        assertTrue(hr.getTeamList() != tList);
        List<Team> copiedTList = hr.getTeamList().getSpecificTypedList();
        List<Team> origTList = tList.getSpecificTypedList();
        for (int i = 0; i < copiedTList.size(); i++) {
            Team copiedTeam = copiedTList.get(i);
            Team origTeam = origTList.get(i);
            assertTrue(copiedTeam != origTeam);
            assertTrue(copiedTeam.getId() != origTeam.getId());
            assertTrue(copiedTeam.getName() != origTeam.getName());
            assertTrue(copiedTeam.getParticipants() != origTeam.getParticipants());
            assertTrue(copiedTeam.getMentor() != origTeam.getMentor());
            assertTrue(copiedTeam.getSubject() == origTeam.getSubject()); //Subject is an Enum
            assertTrue(copiedTeam.getScore() != origTeam.getScore());
            assertTrue(copiedTeam.getProjectName() != origTeam.getProjectName());
            assertTrue(copiedTeam.getLocation() != origTeam.getLocation());
        }
    }
}
