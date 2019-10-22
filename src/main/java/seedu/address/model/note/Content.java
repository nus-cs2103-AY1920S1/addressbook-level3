package seedu.address.model.note;

/**
 * Content of the note
 */
public class Content {
    private String content;

    public Content(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
