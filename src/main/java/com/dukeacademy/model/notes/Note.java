package com.dukeacademy.model.notes;

import static com.dukeacademy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.UUID;

/**
 * Represents a Note in the note bank. Notes can be created by users to store information in the form text or a
 * simple sketch. The Note class serves as a model for a user's notes. It contains a title, the text contents and
 * also a unique sketch id used to name the image file storing the sketch. Notes are identified from each other by
 * their unique sketch ids.
 */
public class Note {
    private final String title;
    private final String content;
    private final UUID sketchId;

    public Note(String title, String content) {
        requireAllNonNull(title, content);

        this.title = title;
        this.content = content;
        this.sketchId = UUID.randomUUID();
    }

    public Note(String title, String content, UUID sketchId) {
        this.title = title;
        this.content = content;
        this.sketchId = sketchId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public UUID getSketchId() {
        return sketchId;
    }

    /**
     * Returns a new Note instance with the same title and unique sketch id, but with its contents updated to the
     * String provided. This new Note instance represents the same note, but with its contents changed. This method
     * is used to maintain immutability of the Note class.
     * @param noteContents the new contents for the note to be updated with
     * @return the updated note
     */
    public Note withNewNoteContents(String noteContents) {
        return new Note(this.title, noteContents, this.sketchId);
    }

    /**
     * Two note instances are equal if their unique sketch ids are the same.
     * @param object the object to be compared to
     * @return true if the object is the same note
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof Note) {
            return this.sketchId.equals(((Note) object).sketchId);
        }

        return false;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getTitle())
                .append(" Title: ")
                .append(title)
                .append(" Content: ")
                .append(content)
                .append(" SketchId: ")
                .append(sketchId);
        return builder.toString();
    }
}
