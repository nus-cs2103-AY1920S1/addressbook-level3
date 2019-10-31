package tagline.ui.exceptions;

/**
 * Indicates that a prompting session is currently ongoing, and that no command will be executed.
 */
public class PromptOngoingException extends Exception {

    public static final String PROMPT_ONGOING_EXCEPTION_MESSAGE = "Prompting session currently ongoing.";

    public PromptOngoingException() {
        super(PROMPT_ONGOING_EXCEPTION_MESSAGE);
    }

    public PromptOngoingException(Throwable cause) {
        super(PROMPT_ONGOING_EXCEPTION_MESSAGE, cause);
    }
}
