package seedu.address.model.note;

/**
 * Note Class
 */
public class Note {

    private Module code;
    private Content content;

    public Note(Module code, Content content) {
        this.code = code;
        this.content = content;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Module getCode() {
        return code;
    }

    public void setCode(Module code) {
        this.code = code;
    }
}
