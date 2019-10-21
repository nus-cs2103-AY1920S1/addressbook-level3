package seedu.billboard.model.undo;

import java.util.Stack;

import seedu.billboard.model.Model;

/**
 * Undo command List, store the previous model and command.
 */
public class UndoList {
    private static Stack<Model> modelList;
    private static Stack<String> cmdList;
    private static boolean isFirst;

    public UndoList() {
        this.modelList = new Stack<>();
        this.cmdList = new Stack<>();
    }
    public static Stack<Model> getModelList() {
        return modelList;
    }

    public static Stack<String> getCmdList() {
        return cmdList;
    }

    public static void addModel(Model model) {
        modelList.push(model);
    }

    public static void addCmd(String cmd) {
        cmdList.push(cmd);
    }

    public static Model getModel() {
        modelList.pop();
        return modelList.pop();
    }

    public static String getCmd() {
        return cmdList.pop();
    }

    public static boolean isEmpty() {
        return modelList.isEmpty();
    }

    /**
     * Clear the undo command List.
     */
    public static void clearList() {
        modelList.clear();
        cmdList.clear();
    }
}
