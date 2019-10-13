package seedu.mark.model.annotation;

import static java.util.Objects.requireNonNull;

public class ParagraphContent {

    private String content;

    public ParagraphContent(String content) {
        requireNonNull(content); //but can be blank, as in phantom paragraph

        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
