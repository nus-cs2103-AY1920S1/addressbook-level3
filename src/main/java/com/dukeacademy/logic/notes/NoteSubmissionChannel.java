package com.dukeacademy.logic.notes;

import com.dukeacademy.data.Pair;
import com.dukeacademy.model.notes.Note;

import javafx.scene.image.WritableImage;

/**
 * Represents a channel which NotesLogic implementations can use to retrieve Notes from other components of the
 * application. This is used to reduce coupling.
 */
public interface NoteSubmissionChannel {
    Pair<Note, WritableImage> getNoteAndSketchPair();
}
