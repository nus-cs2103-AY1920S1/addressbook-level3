package seedu.weme.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.weme.model.template.Template;

/**
 * A utility class containing a list of {@code Template} objects to be used in tests.
 */
public class TypicalTemplates {

    public static final Template DRAKE = new TemplateBuilder()
        .withName("Drake Reaction")
        .withFilePath("src/test/data/templates/drake_template.jpg")
        .build();
    public static final Template IS_THIS = new TemplateBuilder()
        .withName("Is This")
        .withFilePath("src/test/data/templates/is_this_template.jpg")
        .build();
    public static final Template QUIZ_KID = new TemplateBuilder()
        .withName("Quiz Kid")
        .withFilePath("src/test/data/templates/quiz_kid_template.jpg")
        .build();
    public static final Template PIKA = new TemplateBuilder()
            .withName("Surprised Pikachu")
            .withFilePath("src/test/data/templates/pikachu_template.jpg")
            .withIsArchived(true)
            .build();

    private TypicalTemplates() {
    } // prevents instantiation

    public static List<Template> getTypicalTemplates() {
        return new ArrayList<>(Arrays.asList(DRAKE, IS_THIS, QUIZ_KID, PIKA));
    }
}
