package tagline.ui;

import java.util.ArrayList;
import java.util.List;

import tagline.logic.parser.Prompt;

/**
 * Handles the UI side of user prompts.
 */
public class PromptHandler {
    private boolean aborted = false;

    private String pendingCommand;
    private int nextIndex = 0;
    private List<Prompt> promptList = new ArrayList<>();
    private List<Prompt> filledPromptList = new ArrayList<>();

    public PromptHandler(String pendingCommand, List<Prompt> promptList) {
        this.pendingCommand = pendingCommand;
        this.promptList.addAll(promptList);
    }

    public String getPendingCommand() {
        return pendingCommand;
    }

    public List<Prompt> getFilledPromptList() {
        return filledPromptList;
    }

    /**
     * Fills the next unfilled prompt in the list.
     */
    public void fillNextPrompt(String commandText) {
        Prompt currentPrompt = promptList.get(nextIndex);
        currentPrompt.setPromptResponse(commandText);

        nextIndex++;
        filledPromptList.add(currentPrompt);
    }

    /**
     * Returns the next prompt in the list.
     */
    public String getNextPrompt() {
        return promptList.get(nextIndex).getPromptQuestion();
    }

    /**
     * Returns true if all prompts are filled.
     */
    public boolean isComplete() {
        return nextIndex == promptList.size();
    }

    /**
     * Aborts the command.
     */
    public void setAborted() {
        aborted = true;
    }

    /**
     * Returns true if the command has been aborted.
     */
    public boolean isAborted() {
        return aborted;
    }
}
