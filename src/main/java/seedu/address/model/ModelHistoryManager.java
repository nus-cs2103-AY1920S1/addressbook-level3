package seedu.address.model;

import java.util.ArrayList;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.commons.exceptions.AlfredModelHistoryException;
import seedu.address.model.entitylist.MentorList;
import seedu.address.model.entitylist.ParticipantList;
import seedu.address.model.entitylist.TeamList;

/**
 * ModelHistoryManager tracks the state of ModelManager across the execution of all commands.
 */
public class ModelHistoryManager implements ModelHistory {
    private ArrayList<ModelHistoryRecord> history;
    private int index; //Points to the current state in `history`

    /**
     * Constructor for ModelHistoryManager. Initialised with the starting state of the EntityLists
     * and their last used IDs when Alfred is first started.
     * @param pList
     * @param pListId
     * @param mList
     * @param mListId
     * @param tList
     * @param tListId
     * @throws AlfredModelHistoryException
     */
    public ModelHistoryManager(ParticipantList pList, int pListId,
                               MentorList mList, int mListId,
                               TeamList tList, int tListId) throws AlfredModelHistoryException {
        //Index 0 will always represent the starting state of model. No further
        //undo-s are available beyond this point.
        try {
            ModelHistoryRecord initRecord = new ModelHistoryRecord(pList, pListId,
                                                                   mList, mListId,
                                                                   tList, tListId);
            this.index = 0;
            this.history = new ArrayList<ModelHistoryRecord>();
            this.history.add(initRecord);
        } catch (AlfredException e) {
            throw new AlfredModelHistoryException("Problem encountered making deep copy of EntityLists");
        }
    }

    /**
     * Generates a new ModelHistoryRecord to record the current state of the EntityLists and their last
     * used IDs.
     * @param pList ParticipantList (current state)
     * @param pListId ParticipantList's Last Used Id
     * @param mList MentorList (current state)
     * @param mListId MentorLists's Last Used Id
     * @param tList TeamList (current state)
     * @param tListId TeamList's Last Used Id
     * @throws AlfredModelHistoryException
     */
    public void updateHistory(ParticipantList pList, int pListId,
                              MentorList mList, int mListId,
                              TeamList tList, int tListId) throws AlfredModelHistoryException {
        try {
            ModelHistoryRecord newRecord = new ModelHistoryRecord(pList, pListId,
                    mList, mListId,
                    tList, tListId);
            this.index += 1;
            this.history.add(this.index, newRecord);
        } catch (AlfredException e) {
            throw new AlfredModelHistoryException("Problem encountered making deep copy of EntityLists");
        }
    }

    /**
     * Returns a boolean indicating whether the model can return to a previous backward state.
     * @return boolean indicating whether an undo is possible.
     */
    public boolean canUndo() {
        if (index > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns a boolean indicating whether the model can go a previous forward state.
     * @return boolean indicating whether an redo is possible.
     */
    public boolean canRedo() {
        //TODO: Update this in v1.3-4
        return false;
    }

    /**
     * Performs an undo operation and returns a ModelHistoryRecord that stores
     * the state of the EntityLists and last used IDs after a command is undone.
     * @return ModelHistoryRecord with state after command is undone
     * @throws AlfredModelHistoryException
     */
    public ModelHistoryRecord undo() throws AlfredModelHistoryException {
        if (this.canUndo()) {
            this.index -= 1;
            ModelHistoryRecord hr = this.history.get(this.index);
            ParticipantList.setLastUsedId(hr.getParticipantListLastUsedId());
            MentorList.setLastUsedId(hr.getMentorListLastUsedId());
            TeamList.setLastUsedId(hr.getTeamListLastUsedId());
            return hr;
        } else {
            throw new AlfredModelHistoryException("Unable to undo any further!");
        }
    }

    /**
     * Performs a redo operation and returns a ModelHistoryRecord that stores
     * the state of the EntityLists and last used IDs after a command is redone.
     * @return ModelHistoryRecord with state after command is redone
     * @throws AlfredModelHistoryException
     */
    public ModelHistoryRecord redo() throws AlfredModelHistoryException {
        //TODO: Update this in v1.3-1.4
        throw new AlfredModelHistoryException("Not yet implemented");
    }

    /**
     * Returns the length of the current history of states.
     * @return Integer representing the length of the current history of states.
     */
    public int getLengthOfHistory() {
        return this.history.size();
    }
}
