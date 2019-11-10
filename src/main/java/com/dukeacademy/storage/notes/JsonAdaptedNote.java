package com.dukeacademy.storage.notes;

import java.util.UUID;

import com.dukeacademy.commons.exceptions.IllegalValueException;
import com.dukeacademy.model.notes.Note;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A JSON serializable version of a Note.
 */
public class JsonAdaptedNote {
    private final String title;
    private final String content;
    private final String sketchIdMostSignificantBits;
    private final String sketchIdLeastSignificantBits;

    @JsonCreator
    public JsonAdaptedNote(@JsonProperty("title") String title, @JsonProperty("content") String content,
                           @JsonProperty("sketchIdMostSignificantBits") String sketchIdMostSignificantBits,
                           @JsonProperty("sketchIdLeastSignificantBits") String sketchIdLeastSignificantBits) {
        this.title = title;
        this.content = content;
        this.sketchIdMostSignificantBits = sketchIdMostSignificantBits;
        this.sketchIdLeastSignificantBits = sketchIdLeastSignificantBits;
    }

    /**
     * Constructor, converts a given Note instance to the Json serializable version.
     * @param source the Note to convert
     */
    public JsonAdaptedNote(Note source) {
        this.title = source.getTitle();
        this.content = source.getContent();
        this.sketchIdMostSignificantBits = String.valueOf(source.getSketchId().getMostSignificantBits());
        this.sketchIdLeastSignificantBits = String.valueOf(source.getSketchId().getLeastSignificantBits());
    }

    /**
     * Converts the JsonSerializableNote to a Note instance.
     * @return the converted Note instance
     * @throws IllegalValueException if there are invalid values
     */
    public Note toModel() throws IllegalValueException {
        if (title == null) {
            throw new IllegalValueException("Title cannot be null.");
        }

        Long mostSignificantBits = Long.valueOf(sketchIdMostSignificantBits);
        Long leastSignificantBits = Long.valueOf(sketchIdLeastSignificantBits);
        final UUID sketchId = new UUID(mostSignificantBits, leastSignificantBits);
        return new Note(title, content, sketchId);
    }
}
