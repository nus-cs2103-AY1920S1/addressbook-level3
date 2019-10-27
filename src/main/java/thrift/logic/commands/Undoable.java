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
     * @return feedback message of the operation result for display.
     */
    String undo(Model model);

    /**
     * Redo undone command. <br>
     * E.g. Redo {@link AddExpenseCommand} will add back the expense which was deleted by undo function.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display.
     */
    String redo(Model model);
}
