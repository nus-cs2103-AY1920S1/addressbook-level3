package com.dukeacademy.storage.notes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.dukeacademy.commons.exceptions.IllegalValueException;
import com.dukeacademy.model.notes.NoteBank;
import com.dukeacademy.model.notes.StandardNoteBank;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * A JSON serializable version of the NoteBank class used for storage purposes.
 */
@JsonRootName(value = "noteBank")
public class JsonSerializableNoteBank {
    private final List<JsonAdaptedNote> notes = new ArrayList<>();

    @JsonCreator
    public JsonSerializableNoteBank(@JsonProperty("notes") List<JsonAdaptedNote> notes) {
        this.notes.addAll(notes);
    }

    /**
     * Constructor, converts a given NoteBank instance to the Json serializable version.
     * @param source the NoteBank to convert
     */
    public JsonSerializableNoteBank(NoteBank source) {
        notes.addAll(source.getReadOnlyNotesObservableList().stream()
                .map(JsonAdaptedNote::new).collect(Collectors.toList()));
    }

    /**
     * Converts the JsonSerializableNoteBank to a NoteBank instance.
     * @return the converted NoteBank instance
     * @throws IllegalValueException if there are invalid values
     */
    public NoteBank toModelType() throws IllegalValueException {
        StandardNoteBank noteBank = new StandardNoteBank();
        for (JsonAdaptedNote note : notes) {
            noteBank.addNote(note.toModel());
        }

        return noteBank;
    }
}
