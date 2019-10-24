package seedu.billboard.model.versionedbillboard;

import java.util.Stack;

import seedu.billboard.model.Model;

/**
 * Undo command List, store the previous model and command.
 */
public class VersionedBillboard {
    private static Stack<Model> modelList = new Stack<>();
    private static Stack<String> cmdList = new Stack<>();
    private static int currentState = 0;

    /**
     * Saves the current billboard state in state history.
     */
    public static void commit(Model model) {
        if (currentState != 0) {
            for (int i = 0; i < currentState; i++) {
                modelList.pop();
                cmdList.pop();
            }
            currentState = 0;
        }
        modelList.push(model);
    }

    /**
     *  Restores the previous billboard state from state history.
     */
    public static Model undo() {
        currentState++;
        return modelList.get(modelList.size() - currentState - 1);
    }

    /**
     *  Restores a previously undone billboard state from state history.
     */
    public static Model redo() {
        currentState--;
        return modelList.get(modelList.size() - currentState - 1);
    }

    public static boolean isUndoable() {
        return currentState != cmdList.size() && !cmdList.empty();
    }

    public static boolean isRedoable() {
        return currentState != 0 && !cmdList.empty();
    }

    public static void addCmd(String cmd) {
        cmdList.push(cmd);
    }

    public static String getUndoCmd() {
        return cmdList.get(cmdList.size() - currentState);
    }

    public static String getRedoCmd() {
        return cmdList.get(cmdList.size() - currentState);
    }

    /**
     * Clear the undo command List.
     */
    public static void clearList() {
        modelList.clear();
        cmdList.clear();
        currentState = 0;
    }

    /**
     * Compare the history of two Billboard states.
     */
    public boolean compareBillboardModels(Stack<Model> modelList) {
        return VersionedBillboard.modelList.equals(modelList);
    }

    public static void setCurrentState(int currentState) {
        VersionedBillboard.currentState = currentState;
    }
}
