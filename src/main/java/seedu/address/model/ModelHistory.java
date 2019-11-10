package seedu.address.model;

import java.util.ArrayList;

import seedu.address.commons.exceptions.AlfredModelHistoryException;
import seedu.address.logic.commands.Command;
import seedu.address.model.entitylist.MentorList;
import seedu.address.model.entitylist.ParticipantList;
import seedu.address.model.entitylist.TeamList;

/**
 * API for ModelHistoryManager
 */
public interface ModelHistory {
    public void updateHistory(ParticipantList pList, int pListId,
                              MentorList mList, int mListId,
                              TeamList tList, int tListId, Command c) throws AlfredModelHistoryException;

    public boolean canUndo(int numToUndo);

    public boolean canRedo(int numToRedo);

    public ModelHistoryRecord undo(int numToUndo) throws AlfredModelHistoryException;

    public ModelHistoryRecord redo(int numToRedo) throws AlfredModelHistoryException;

    public ArrayList<CommandRecord> getCommandHistory();
}
