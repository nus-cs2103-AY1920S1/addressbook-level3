package seedu.flashcard.ui;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.flashcard.model.flashcard.Flashcard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represent a single flashcard in the full view mode.
 */
public class FullFlashcardCard extends UiPart<Region> {

    private static final String FXML = "FullFlashcardCard.fxml";

    public final Flashcard flashcard;

    @FXML
    private HBox cardPane;
    @FXML
    private Label word;
    @FXML
    private Label id;
    @FXML
    private Label definition;
    @FXML
    private Label answer;
    @FXML
    private FlowPane tags;
    @FXML
    private ListView choices;

    private List<String> allChoices;

    public FullFlashcardCard(Flashcard flashcard, int displayedIndex) {
        super(FXML);
        this.flashcard = flashcard;
        id.setText(displayedIndex + ". ");
        word.setText(flashcard.getWord().word);
        definition.setText(flashcard.getDefinition().definition);
        answer.setText(flashcard.getAnswer().choice);
        flashcard.getTags().stream()
            .sorted(Comparator.comparing(tag -> tag.tagName))
            .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        allChoices = new ArrayList<>();
        flashcard.getChoices().stream()
            .forEach(choice -> allChoices.add(choice.choice));
        ListProperty<String> listProperty = new SimpleListProperty<>();
        listProperty.set(FXCollections.observableArrayList(allChoices));
        choices.itemsProperty().bind(listProperty);
    }
}
