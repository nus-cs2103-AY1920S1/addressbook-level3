package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.note.Content;
import seedu.address.model.note.Title;

/**
 * Represents a stub for Question class.
 */
class QuestionStub {
    private final Title title;
    private final Content content;

    QuestionStub(Title title, Content content) {
        requireAllNonNull(title, content);
        this.title = title;
        this.content = content;
    }

    private Title getTitle() {
        return title;
    }

    private Content getContent() {
        return content;
    }

    @Override
    public String toString() {
        return getTitle() + "\n" + getContent();
    }
}
