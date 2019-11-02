package seedu.address.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.commons.exceptions.AlfredModelHistoryException;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.TrackableState;
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
            if (c instanceof TrackableState) {
                ModelHistoryRecord newRecord = new ModelHistoryRecord(pList, pListId,
                                                                      mList, mListId,
                                                                      tList, tListId,
                                                                      c);
                addToHistory(newRecord);
            }
        } catch (AlfredException e) {
            throw new AlfredModelHistoryException("Problem encountered making deep copy of EntityLists");
        }
    }

    /**
     * Adds a ModelHistoryRecord to command history. This method checks for capacity constraints of the
     * ModelHistoryManager and is responsible for ensuring a valid sequence of commands in history for Undo/Redo.
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
     * Returns a boolean indicating whether the model can return to previous {@code numToUndo}-th state.
     * @param numToUndo number of states to undo.
     * @return boolean indicating whether an undo is possible.
     */
    public boolean canUndo(int numToUndo) {
        System.out.println("Current index of canUndo: " + this.history.indexOf(this.current));
        if (this.history.indexOf(this.current) - numToUndo + 1 > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Performs a {@code numToUndo} undo operation(s) and returns a ModelHistoryRecord that stores
     * the state of the EntityLists and last used IDs after those command(s) is/are undone.
     * @param numToUndo number of states to undo.
     * @return ModelHistoryRecord with state after command is undone
     * @throws AlfredModelHistoryException
     */
    public ModelHistoryRecord undo(int numToUndo) throws AlfredModelHistoryException {
        if (this.canUndo(numToUndo)) {
            int currentIndex = this.history.indexOf(this.current); //Get prev state pointer index
            this.current = this.history.get(currentIndex - numToUndo); //Update the current state pointer
            return this.current;
        } else {
            throw new AlfredModelHistoryException("Unable to undo any further!");
        }
    }

    /**
     * Returns a List of CommandRecords representing undo-able and redo-able Command History.
     * @return List of CommandRecords representing undo-able and redo-able Command History.
     */
    public ArrayList<CommandRecord> getCommandHistory() {
        ArrayList<CommandRecord> commandHistory = new ArrayList<>();
        //Obtain Redo-able Command History
        commandHistory.add(CommandRecord.getRedoEndPoint());
        int currentIndex = this.history.indexOf(this.current);
        for (int j = this.history.size() - 1; j > currentIndex; j--) {
            Command futureCommand = this.history.get(j).getCommand();
            commandHistory.add(new CommandRecord((j - currentIndex),
                                                 futureCommand.getClass().getSimpleName() + ": `"
                                                 + futureCommand.getCommandInputString() + "`",
                                                 CommandRecord.CommandType.REDO));
        }

        //Set Current Delimiter
        commandHistory.add(CommandRecord.getCurrentStatePoint());

        //Obtain Undo-able Command History
        int index = 1;
        for (int j = this.history.indexOf(this.current); j >= 1; j--) {
            Command histCommand = this.history.get(j).getCommand();
            commandHistory.add(new CommandRecord(index,
                                                 histCommand.getClass().getSimpleName() + ": `"
                                                 + histCommand.getCommandInputString() + "`",
                                                 CommandRecord.CommandType.UNDO));
            index++;
        }
        commandHistory.add(CommandRecord.getUndoEndPoint());
        return commandHistory;
    }

    /**
     * Returns a String representing undo-able and redo-able Command History, separated by `=` delimiter.
     * @return String representing undo-able and redo-able Command History.
     */
    public String getCommandHistoryString() {
        String commandHistory = "-------------<< Cannot Redo Beyond This Point >>-------------\n";
        //Obtain Redo-able Command History
        int currentIndex = this.history.indexOf(this.current);
        for (int j = this.history.size() - 1; j > currentIndex; j--) {
            Command futureCommand = this.history.get(j).getCommand();
            commandHistory += ((j - currentIndex) + ": " + futureCommand.getClass().getSimpleName());
        }

        //Delimiter
        commandHistory += "=====================<< Current State >>=====================\n";

        //Obtain Undo-able Command History
        int index = 1;
        for (int j = this.history.indexOf(this.current); j >= 1; j--) {
            Command histCommand = this.history.get(j).getCommand();
            commandHistory += (index + ": " + histCommand.getClass().getSimpleName() + "\n");
            index++;
        }
        commandHistory += "-------------<< Cannot Undo Beyond This Point >>-------------\n";

        return commandHistory;
    }

    public List<String> getUndoCommandHistory() {
        List<String> undoHistory = new LinkedList<>();
        int index = 1;
        for (int j = this.history.indexOf(this.current); j >= 1; j--) {
            Command histCommand = this.history.get(j).getCommand();
            undoHistory.add(index + ": " + histCommand.getClass().getSimpleName());
            index++;
        }
        undoHistory.add("-------------<< Cannot Undo Beyond This Point >>-------------");
        return undoHistory;
    }

    public List<String> getRedoCommandHistory() {
        List<String> redoHistory = new LinkedList<>();
        redoHistory.add("-------------<< Cannot Redo Beyond This Point >>-------------");
        int currentIndex = this.history.indexOf(this.current);
        for (int j = this.history.size() - 1; j > currentIndex; j--) {
            Command futureCommand = this.history.get(j).getCommand();
            redoHistory.add((j - currentIndex) + ": " + futureCommand.getClass().getSimpleName());
        }
        return redoHistory;
    }

    /**
     * Returns a boolean indicating whether the model can go to the next {@code numToRedo}-th state.
     * @param numToRedo number of commands to redo.
     * @return boolean indicating whether such a redo is possible.
     */
    public boolean canRedo(int numToRedo) {
        if ((this.history.indexOf(this.current) + numToRedo) >= this.history.size()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Performs {@code numToRedo} redo operation(s) and returns a ModelHistoryRecord that stores
     * the state of the EntityLists and last used IDs after the command(s) is/are redone.
     * @param numToRedo number of commands to redo.
     * @return ModelHistoryRecord with state after the command(s) is/are redone
     * @throws AlfredModelHistoryException
     */
    public ModelHistoryRecord redo(int numToRedo) throws AlfredModelHistoryException {
        if (this.canRedo(numToRedo)) {
            int currentIndex = this.history.indexOf(this.current);
            this.current = this.history.get(currentIndex + numToRedo);
            return this.current;
        } else {
            throw new AlfredModelHistoryException("Unable to redo any further!");
        }
    }

    /**
     * Returns the length of the current history of states.
     * @return Integer representing the length of the current history of states.
     */
    public int getLengthOfHistory() {
        return this.history.size();
    }
}
