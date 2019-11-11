package tagline.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tagline.logic.parser.CommandParserTestUtil.getFilledPrompt;
import static tagline.logic.parser.ParserUtil.getArgsWithFilledPrompts;
import static tagline.logic.parser.ParserUtil.getPromptResponseFromPrefix;
import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_EMAIL;
import static tagline.logic.parser.contact.ContactCliSyntax.PREFIX_NAME;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

public class ParserUtilTest {
    private static final String ARGS = "ORIGINAL INPUT";
    private static final Prompt PREAMBLE_PROMPT = getFilledPrompt("", "", "123");
    private static final Prompt PREFIX_NAME_PROMPT = getFilledPrompt(PREFIX_NAME.getPrefix(), "", "Bob");
    private static final Prompt PREFIX_EMAIL_PROMPT = getFilledPrompt(PREFIX_EMAIL.getPrefix(), "", "b@b.com");

    @Test
    public void getArgsWithFilledPrompts_successful() {
        //Calling this method with empty prompt list should not change args
        assertEquals(ARGS, getArgsWithFilledPrompts(ARGS, Collections.emptyList()));

        assertEquals("123 " + ARGS, getArgsWithFilledPrompts(ARGS, Collections.singletonList(PREAMBLE_PROMPT)));
        assertEquals(ARGS + " " + PREFIX_NAME.getPrefix() + "Bob",
                getArgsWithFilledPrompts(ARGS, Collections.singletonList(PREFIX_NAME_PROMPT)));

        assertEquals("123 " + ARGS + " " + PREFIX_NAME.getPrefix() + "Bob",
                getArgsWithFilledPrompts(ARGS, Arrays.asList(PREAMBLE_PROMPT, PREFIX_NAME_PROMPT)));
        assertEquals("123 " + ARGS + " " + PREFIX_NAME.getPrefix() + "Bob",
                getArgsWithFilledPrompts(ARGS, Arrays.asList(PREFIX_NAME_PROMPT, PREAMBLE_PROMPT)));

        assertEquals("123 " + ARGS + " " + PREFIX_NAME.getPrefix() + "Bob " + PREFIX_EMAIL.getPrefix()
                + "b@b.com", getArgsWithFilledPrompts(ARGS,
                Arrays.asList(PREAMBLE_PROMPT, PREFIX_NAME_PROMPT, PREFIX_EMAIL_PROMPT)));
    }

    @Test
    public void getPromptResponseFromPrefix_successful() {
        assertEquals("", getPromptResponseFromPrefix("", Collections.emptyList()));
        assertEquals(PREAMBLE_PROMPT.getPromptResponse(), getPromptResponseFromPrefix("",
                Arrays.asList(PREFIX_NAME_PROMPT, PREAMBLE_PROMPT, PREFIX_EMAIL_PROMPT)));
        assertEquals("", getPromptResponseFromPrefix("",
                Arrays.asList(PREFIX_NAME_PROMPT, PREFIX_EMAIL_PROMPT)));
    }
}
