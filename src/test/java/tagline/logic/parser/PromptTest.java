//@@author tanlk99
package tagline.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class PromptTest {
    private static final String PREFIX_NAME_STRING = "--n";
    private static final String PREFIX_EMAIL_STRING = "--e";
    private static final String PROMPT_QUESTION_NAME_STRING = "Please enter a name.";
    private static final String PROMPT_QUESTION_EMAIL_STRING = "Please enter an email";
    private static final String PROMPT_RESPONSE_STRING = "ABCDEFGH";

    @Test
    public void newPrompt_nullFields_throwsException() {
        assertThrows(NullPointerException.class, () -> new Prompt(null, PROMPT_QUESTION_NAME_STRING));
        assertThrows(NullPointerException.class, () -> new Prompt(PREFIX_NAME_STRING, null));
    }

    @Test
    public void equals() {
        Prompt prompt = new Prompt(PREFIX_NAME_STRING, PROMPT_QUESTION_NAME_STRING);

        //same object -> true
        Prompt editedPrompt = prompt;
        assertEquals(prompt, editedPrompt);

        //same fields -> true
        editedPrompt = new Prompt(PREFIX_NAME_STRING, PROMPT_QUESTION_NAME_STRING);
        assertEquals(prompt, editedPrompt);

        //different prefix -> false
        editedPrompt = new Prompt(PREFIX_EMAIL_STRING, PROMPT_QUESTION_NAME_STRING);
        assertNotEquals(prompt, editedPrompt);

        //different question -> false
        editedPrompt = new Prompt(PREFIX_NAME_STRING, PROMPT_QUESTION_EMAIL_STRING);
        assertNotEquals(prompt, editedPrompt);

        //different response -> false
        editedPrompt = new Prompt(PREFIX_NAME_STRING, PROMPT_QUESTION_NAME_STRING);
        editedPrompt.setPromptResponse(PROMPT_RESPONSE_STRING);
        assertNotEquals(prompt, editedPrompt);
    }
}
