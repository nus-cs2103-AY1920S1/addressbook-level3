package seedu.address.calendar.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class EventCard {
    private static final int LINE_HEIGHT = 30;
    private static final int MAX_CHAR_PER_LINE = 40;
    private static final int HEADER_HEIGHT = 20;
    private VBox card;
    private Label content;
    private List<String> strings;

    EventCard(VBox card, Label content) {
        this.card = card;
        this.content = content;
        strings = new ArrayList<>();
    }

    void add(String string) {
        strings.add(string);
    }

    void makeInvisible() {
        clear();
        card.setVisible(false);
        card.setManaged(false);
    }

    private void clear() {
        strings = new ArrayList<>();
        content.setText("");
    }

    void makeVisible() {
        setContent();
        card.setVisible(true);
        card.setManaged(true);
    }

    boolean hasContent() {
        return !strings.isEmpty();
    }

    private void setContent() {
        String formattedResponse = strings.stream()
                .reduce("", (prev, curr) -> prev + "\n∙" + curr);
        content.setText(formattedResponse);
        setHeight(formattedResponse);
    }

    private void setHeight(String formattedResponse) {
        int length = formattedResponse.length();
        int height = (length / MAX_CHAR_PER_LINE + 1) * LINE_HEIGHT;
        content.setPrefHeight(height);
        card.setMinHeight(height + HEADER_HEIGHT);
    }
}
