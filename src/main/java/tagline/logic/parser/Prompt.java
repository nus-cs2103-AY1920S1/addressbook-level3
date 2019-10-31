package tagline.logic.parser;

import static tagline.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a prompt requested by the parser for the user.
 */
public class Prompt {
    private final String argumentPrefix;
    private final String promptQuestion;
    private String promptResponse;

    public Prompt(String argumentPrefix, String promptQuestion) {
        requireAllNonNull(argumentPrefix, promptQuestion);
        this.argumentPrefix = argumentPrefix;
        this.promptQuestion = promptQuestion;
        this.promptResponse = ""; //default empty response
    }

    public String getPromptQuestion() {
        return promptQuestion;
    }

    public String getPromptResponse() {
        return promptResponse;
    }

    public String getArgumentPrefix() {
        return argumentPrefix;
    }

    public void setPromptResponse(String promptResponse) {
        this.promptResponse = promptResponse;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof Prompt) {
            return argumentPrefix.equals(((Prompt) other).argumentPrefix)
                    && promptQuestion.equals(((Prompt) other).promptQuestion)
                    && promptResponse.equals(((Prompt) other).promptResponse);
        }

        return false;
    }

    @Override
    public String toString() {
        return new StringBuilder("Prefix: ").append(argumentPrefix)
                .append(", Question: ").append(promptQuestion)
                .append(", Response: ").append(promptResponse).toString();
    }
}
