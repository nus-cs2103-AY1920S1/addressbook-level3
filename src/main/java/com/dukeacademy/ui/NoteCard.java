package com.dukeacademy.ui;

import com.dukeacademy.model.notes.Note;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 * Controller class for a card Ui component used to display preview information of a user's Note in the application.
 */
public class NoteCard extends UiPart<Region> {
    private static final String FXML = "NoteCard.fxml";
    private static final String emptyContentPlaceholder = "Nothing to preview";

    @FXML
    private Label noteId;

    @FXML
    private Label noteTitle;

    @FXML
    private Label noteContent;

    public NoteCard(Note note, int index) {
        super(FXML);
        noteId.setText(String.valueOf(index));
        noteTitle.setText(note.getTitle().toUpperCase());

        // Insert placeholder text if the content is empty
        if ("".equals(note.getContent())) {
            noteContent.setText(emptyContentPlaceholder);
            noteContent.setTextFill(Color.GRAY);
            return;
        }

        // Preview the first line of the note's contents
        String contentsPreview = note.getContent().split("\n")[0];
        noteContent.setText(contentsPreview);
        noteContent.setTextFill(Color.BLACK);
    }
}
