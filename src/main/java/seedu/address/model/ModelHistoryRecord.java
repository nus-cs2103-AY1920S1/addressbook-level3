package seedu.address.model;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.model.entitylist.MentorList;
import seedu.address.model.entitylist.ParticipantList;
import seedu.address.model.entitylist.TeamList;

/**
 * Represents a snapshot of all the EntityLists and their last used IDs at a point in time.
 */
public class ModelHistoryRecord {
    private ParticipantList pList;
    private MentorList mList;
    private TeamList tList;

    private int pListId;
    private int mListId;
    private int tListId;

    /**
     * Constructor for ModelHistoryRecord. Important to note that a deep copy will be made of
     * each of the EntityLists passed in, so that the EntityLists stored in ModelHistoryRecord
     * will not be modified by subsequent commands.
     * @param pList ParticipantList (current state)
     * @param pListId Current lastUsedId of Participantlist
     * @param mList MentorList (current state)
     * @param mListId Current lastUsedId of MentorList
     * @param tList TeamList (current state)
     * @param tListId Current lastUsedId of TeamList
     */
    public ModelHistoryRecord(ParticipantList pList, int pListId,
                              MentorList mList, int mListId,
                              TeamList tList, int tListId) throws AlfredException {
        this.pList = pList.copy();
        this.pListId = pListId;
        this.mList = mList.copy();
        this.mListId = mListId;
        this.tList = tList.copy();
        this.tListId = tListId;
    }

    public ParticipantList getParticipantList() {
        return this.pList;
    }

    public int getParticipantListLastUsedId() {
        return this.pListId;
    }

    public MentorList getMentorList() {
        return this.mList;
    }

    public int getMentorListLastUsedId() {
        return this.mListId;
    }

    public TeamList getTeamList() {
        return this.tList;
    }

    public int getTeamListLastUsedId() {
        return this.tListId;
    }
}
