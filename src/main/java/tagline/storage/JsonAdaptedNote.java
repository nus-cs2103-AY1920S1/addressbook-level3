package tagline.storage;

//import java.sql.Time;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import tagline.commons.exceptions.IllegalValueException;
import tagline.model.note.Content;
import tagline.model.note.Date;
import tagline.model.note.Note;
import tagline.model.note.NoteId;
import tagline.model.note.NoteIdCounter;
import tagline.model.note.TimeCreated;
import tagline.model.note.TimeLastEdited;
import tagline.model.note.Title;
import tagline.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Note}.
 */
class JsonAdaptedNote {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Note's %s field is missing!";

    private final String content;
    private final String timeCreated;
    private final String timeLastEdited;
    private final String title;
    private final String noteId;
    private final String noteIdCount;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedNote} with the given note details.
     */
    @JsonCreator
    public JsonAdaptedNote(@JsonProperty("noteId") String noteId, @JsonProperty("title") String title,
            @JsonProperty("content") String content, @JsonProperty("timeCreated") String timeCreated,
            @JsonProperty("timeLasEdited") String timeLastEdited, @JsonProperty("noteIdCount") String noteIdCount,
            @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.noteId = noteId;
        this.title = title;
        this.content = content;
        this.timeCreated = timeCreated;
        this.timeLastEdited = timeLastEdited;
        this.noteIdCount = noteIdCount;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Note} into this class for Jackson use.
     */
    public JsonAdaptedNote(Note source) {

        noteId = source.getNoteId().getStorageString();
        title = source.getTitle().titleDescription;
        content = source.getContent().value;
        timeCreated = source.getTimeCreated().getTime().getStorageString();
        timeLastEdited = source.getTimeLastEdited().getTime().getStorageString();
        noteIdCount = NoteIdCounter.getStorageString();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted note object into the model's {@code Note} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted note.
     */
    public Note toModelType() throws IllegalValueException {
        final List<Tag> noteTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            noteTags.add(tag.toModelType());
        }

        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Title.class.getSimpleName()));
        }
        final Title modelTitle = new Title(title);

        if (noteId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, NoteId.class.getSimpleName()));
        }

        if (!NoteId.isValidNoteId(noteId)) {
            throw new IllegalValueException(NoteId.MESSAGE_CONSTRAINTS);
        }
        final NoteId modelNoteId = new NoteId(noteId);

        if (content == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Content.class.getSimpleName()));
        }
        if (!Content.isValidContent(content)) {
            throw new IllegalValueException(Content.MESSAGE_CONSTRAINTS);
        }
        final Content modelContent = new Content(content);

        if (timeCreated == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, TimeCreated.class.getSimpleName()));
        }
        //regex for Date validation is ommitted due to its complexity
        //if (!TimeCreated.isValidTimeCreated(timeCreated)) {
        //    throw new IllegalValueException(TimeCreated.MESSAGE_CONSTRAINTS);
        //}
        final TimeCreated modelTimeCreated = new TimeCreated(new Date(timeCreated));

        if (timeLastEdited == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, TimeLastEdited.class.getSimpleName()));
        }
        //regex for Date validation is ommitted due to its complexity
        final TimeLastEdited modelTimeLastEdited = new TimeLastEdited(new Date(timeLastEdited));

        if (noteIdCount == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, NoteIdCounter.class.getSimpleName()));
        }
        if (!NoteIdCounter.isValidNoteIdCount(noteIdCount)) {
            throw new IllegalValueException(NoteIdCounter.MESSAGE_CONSTRAINTS);
        }
        NoteIdCounter.setCountFromStorage(noteIdCount);

        final Set<Tag> modelTags = new HashSet<>(noteTags);
        return new Note(modelNoteId, modelTitle, modelContent, modelTimeCreated, modelTimeLastEdited, modelTags);
    }

}
