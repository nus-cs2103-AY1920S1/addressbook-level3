package seedu.address.model;

import seedu.address.commons.exceptions.AlfredModelHistoryException;
import seedu.address.model.entitylist.MentorList;
import seedu.address.model.entitylist.ParticipantList;
import seedu.address.model.entitylist.TeamList;

/**
 * API for ModelHistoryManager
 */
public interface ModelHistory {
    public void updateHistory(ParticipantList pList, int pListId,
                              MentorList mList, int mListId,
                              TeamList tList, int tListId) throws AlfredModelHistoryException;

    public boolean canUndo();

    public boolean canRedo();

    public ModelHistoryRecord undo() throws AlfredModelHistoryException;

    public ModelHistoryRecord redo() throws AlfredModelHistoryException;
}
