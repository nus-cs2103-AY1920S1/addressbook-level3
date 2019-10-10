package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.model.note.Content;
import seedu.address.model.note.Title;

public class QuestionStub {
    private final Title title;
    private final Content content;

    public QuestionStub(Title title, Content content) {
        requireAllNonNull(title, content);
        this.title = title;
        this.content = content;
    }

    public Title getTitle() {
        return title;
    }

    public Content getContent() {
        return content;
    }

    @Override
    public String toString() {
        return getTitle() + "\n" + getContent();
    }
}
