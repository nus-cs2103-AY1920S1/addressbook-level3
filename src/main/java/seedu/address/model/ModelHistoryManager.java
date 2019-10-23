package seedu.address.model;

import java.util.LinkedList;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.commons.exceptions.AlfredModelHistoryException;
import seedu.address.logic.commands.Command;
import seedu.address.model.entitylist.MentorList;
import seedu.address.model.entitylist.ParticipantList;
import seedu.address.model.entitylist.TeamList;

/**
 * ModelHistoryManager tracks the state of ModelManager across the execution of all commands.
 */
public class ModelHistoryManager implements ModelHistory {
    private static final int capacity = 50; //Length of command/state history that will be tracked/can be undone

    private LinkedList<ModelHistoryRecord> history;
    private ModelHistoryRecord current; //points to the current state of Model

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
                                                                   tList, tListId,
                                                                   null); //Command is initialised to null
            this.current = initRecord;
            this.history = new LinkedList<ModelHistoryRecord>();
            this.history.add(this.current);
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
                              TeamList tList, int tListId, Command c) throws AlfredModelHistoryException {
        try {
            ModelHistoryRecord newRecord = new ModelHistoryRecord(pList, pListId,
                                                                  mList, mListId,
                                                                  tList, tListId,
                                                                  c);
            addToHistory(newRecord);
        } catch (AlfredException e) {
            throw new AlfredModelHistoryException("Problem encountered making deep copy of EntityLists");
        }
    }

    /**
     * Adds a ModelHistoryRecord to command history. This method checks for capacity constraints of the
     * ModelHisxtoryManager and is responsible for ensuring a valid sequence of commands in history for Undo/Redo.
     * @param r
     */
    private void addToHistory(ModelHistoryRecord r) {
        if (this.history.size() >= ModelHistoryManager.capacity) {
            this.history.remove(0);
        }

        int currentIndex = this.history.indexOf(this.current);
        if (currentIndex != this.history.size() - 1) {
            //Current state has possible redos. Adding a new command to history invalidates the future redos.
            this.history = new LinkedList<ModelHistoryRecord>(this.history.subList(0, currentIndex + 1));
        }
        this.history.add(r);
        this.current = r;
    }

    /**
     * Returns a boolean indicating whether the model can return to a previous backward state.
     * @return boolean indicating whether an undo is possible.
     */
    public boolean canUndo() {
        if (this.history.indexOf(this.current) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Performs an undo operation and returns a ModelHistoryRecord that stores
     * the state of the EntityLists and last used IDs after a command is undone.
     * @return ModelHistoryRecord with state after command is undone
     * @throws AlfredModelHistoryException
     */
    public ModelHistoryRecord undo() throws AlfredModelHistoryException {
        if (this.canUndo()) {
            int currentIndex = this.history.indexOf(this.current); //Get prev state pointer index
            this.current = this.history.get(currentIndex - 1); //Update the current state pointer
            ParticipantList.setLastUsedId(this.current.getParticipantListLastUsedId());
            MentorList.setLastUsedId(this.current.getMentorListLastUsedId());
            TeamList.setLastUsedId(this.current.getTeamListLastUsedId());
            return this.current;
        } else {
            throw new AlfredModelHistoryException("Unable to undo any further!");
        }
    }

    /**
     * Returns a String representing undo-able and redo-able Command History, separated by `=` delimiter.
     * @return String representing undo-able and redo-able Command History.
     */
    public String getCommandHistory() {
        String commandHistory = "";
        //Obtain Redo-able Command History
        int currentIndex = this.history.indexOf(this.current);
        for (int j = this.history.size() - 1; j > currentIndex; j--) {
            Command futureCommand = this.history.get(j).getCommand();
            commandHistory += ((j - currentIndex) + ": " + futureCommand.getClass().getSimpleName() + "\n");
        }

        //Delimiter
        commandHistory += "=============================================================\n";

        //Obtain Undo-able Command History
        int index = 1;
        for (int j = this.history.indexOf(this.current); j >= 0; j--) {
            Command histCommand = this.history.get(j).getCommand();
            if (histCommand == null) {
                commandHistory += "*: Initialised State. Cannot undo.\n";
            } else {
                commandHistory += (index + ": " + histCommand.getClass().getSimpleName() + "\n");
            }
            index++;
        }

        return commandHistory;
    }

    /**
     * Returns a boolean indicating whether the model can go a previous forward state.
     * @return boolean indicating whether an redo is possible.
     */
    public boolean canRedo() {
        if (this.history.indexOf(this.current) == this.history.size() - 1) {
            return false;
        } else {
            return true;
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
