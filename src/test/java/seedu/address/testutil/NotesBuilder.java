package seedu.address.testutil;

import seedu.address.model.classid.ClassId;
import seedu.address.model.note.ClassType;
import seedu.address.model.note.Content;
import seedu.address.model.note.Notes;

public class NotesBuilder {
    public static final String DEFAULT_CLASSID = "CS2100";
    public static final String DEFAULT_TYPE = "tut";
    public static final String DEFAULT_CONTENT = "check report";

    private ClassId code;
    private ClassType type;
    private Content content;

    public NotesBuilder() {
        code = new ClassId(DEFAULT_CLASSID);
        type = new ClassType(DEFAULT_TYPE);
        content = new Content(DEFAULT_CONTENT);
    }

    /**
     * Initializes the NotesBuilder with the data of {@code notesToCopy}.
     */
    public NotesBuilder(Notes notesToCopy) {
        code = notesToCopy.getCode();
        type = notesToCopy.getType();
        content = notesToCopy.getContent();

    }

    /**
     * Sets the {@code ClassId} of the {@code Notes} that we are building.
     */
    public NotesBuilder withModuleCode(String code) {
        this.code = new ClassId(code);
        return this;
    }
    /*
     * Sets the {@code ClassType} of the {@code Notes} that we are building.
     */
    public NotesBuilder withClassType(String type) {
        this.type = new ClassType(type);
        return this;
    }

    /**
     * Sets the {@code Content} of the {@code Notes} that we are building.
     */
    public NotesBuilder withContent(String content) {
        this.content = new Content(content);
        return this;
    }

    public Notes build() {
        return new Notes(code, type, content);
    }
}
