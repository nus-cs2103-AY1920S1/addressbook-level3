package tagline.logic.parser.exceptions;

import static java.util.Objects.requireNonNull;

import java.util.List;

import tagline.logic.parser.Prompt;

/**
 * Represents a request for missing information from the user.
 * Can contain either a list of prompts to request.
 */
public class PromptRequestException extends ParseException {
    public static final String PROMPT_REQUEST_EXCEPTION_MESSAGE = "Requesting prompts from user.";

    private List<Prompt> requests;

    public PromptRequestException(List<Prompt> requests) {
        super(PROMPT_REQUEST_EXCEPTION_MESSAGE);
        requireNonNull(requests);
        this.requests = requests;
    }

    public List<Prompt> getPrompts() {
        return requests;
    }
}
