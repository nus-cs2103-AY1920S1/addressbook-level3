package seedu.address.storage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.note.Content;
import seedu.address.model.note.DateAdded;
import seedu.address.model.note.DateModified;
import seedu.address.model.note.Note;
import seedu.address.model.note.NoteDescription;
import seedu.address.model.note.NumOfAccess;
import seedu.address.model.note.Title;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;



/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedNote {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Note's %s field is missing!";

    private final String title;
    private final String description;
    private final String content;
    private final Date dateModified;
    private final Date dateAdded;
    private final int numOfAccess;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedNote(@JsonProperty("title") String title,
                           @JsonProperty("description") String description,
                           @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                           @JsonProperty("content") String content,
                           @JsonProperty("dateModified") Date dateModified,
                           @JsonProperty("dateAdded") Date dateAdded,
                           @JsonProperty("numOfAccess") int numOfAccess) {
        this.title = title;
        this.description = description;
        this.content = content;
        this.dateModified = dateModified;
        this.dateAdded = dateAdded;
        this.numOfAccess = numOfAccess;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedNote(Note source) {
        title = source.getTitle().title;
        description = source.getDescription().description;
        content = source.getContent().content;
        dateModified = source.getDateModified().value;
        dateAdded = source.getDateAdded().value;
        numOfAccess = source.getNumOfAccess().numOfAccess;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Note toModelType() throws IllegalValueException {
        final List<Tag> noteTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            noteTags.add(tag.toModelType());
        }

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        if (!Title.isValidTitle(title)) {
            throw new IllegalValueException(Title.MESSAGE_CONSTRAINTS);
        }
        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    NoteDescription.class.getSimpleName()));
        }
        if (!NoteDescription.isValidDescription(description)) {
            throw new IllegalValueException(NoteDescription.MESSAGE_CONSTRAINTS);
        }
        if (content == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Content.class.getSimpleName()));
        }

        if (!Content.isValidContent(content)) {
            throw new IllegalValueException(Content.MESSAGE_CONSTRAINTS);
        }

        final Title modelTitle = new Title(title);
        final NoteDescription modelDescription = new NoteDescription(description);
        final Content modelContent = new Content(content);
        //TODO: insert assert dateModified and numOfAccess not null
        final DateModified modelDateModified = new DateModified(dateModified);
        final DateAdded modelDateAdded = new DateAdded(dateAdded);
        final NumOfAccess modelNumOfAccess = new NumOfAccess(numOfAccess);
        final Set<Tag> modelTags = new HashSet<>(noteTags);
        return new Note(modelTitle, modelDescription, modelTags, modelContent, modelDateModified,
                modelDateAdded, modelNumOfAccess);
    }

}
