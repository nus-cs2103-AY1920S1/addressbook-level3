package thrift.logic.commands;

import thrift.model.Model;

/**
 * Allows the command to perform undo and redo.
 */
public interface Undoable {

    /**
     * Undo that specified commands. <br>
     * E.g. Undo {@link AddExpenseCommand} will delete the expenses created by this command.
     *
     * @param model {@code Model} which the command should operate on.
     */
    void undo(Model model);
}
