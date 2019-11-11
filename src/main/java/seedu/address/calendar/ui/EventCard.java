package seedu.address.calendar.ui;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Represent an event card.
 */
public class EventCard {
    private VBox card;
    private Text content;
    private List<String> strings;

    EventCard(VBox card, Text content) {
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
        setHeight();
    }

    private void setHeight() {
        card.widthProperty().addListener(((observable, oldValue, newValue) -> {
            content.setWrappingWidth((Double) newValue * 0.8);
        }));
    }
}
