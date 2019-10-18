package seedu.address.ui.modules;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.card.Card;
import seedu.address.statistics.CardStatistics;
import seedu.address.ui.UiPart;

/**
 * An UI component that displays information of a {@code Card} with additional info.
 */
public class CardWithInfoCard extends UiPart<Region> {

    private static final String FXML = "CardWithInfoCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    @FXML
    private VBox cardContainer;

    @FXML
    private Label cardWord;

    @FXML
    private Label cardMeaning;

    @FXML
    private FlowPane tags;

    @FXML
    private Label correctRate;

    public CardWithInfoCard(Card card, CardStatistics cardStatistics) {
        super(FXML);
        if (!card.getId().equals(cardStatistics.getCardId())) {
            throw new AssertionError("Card and card statistics should have the same id.\n"
                + card + "\n\n\n" + cardStatistics);
        }

        cardWord.setText(card.getWord().value);
        cardMeaning.setText(card.getMeaning().value);
        cardMeaning.setWrapText(true);
        cardMeaning.prefWidthProperty().bind(cardContainer.widthProperty());
        card.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        double correctRateToShow = cardStatistics.getNumShown() == 0
                ? 0
                : cardStatistics.getNumCorrect() * 100.0 / cardStatistics.getNumShown();

        correctRate.setText(String.format("%.1f%%", correctRateToShow));
        if (correctRateToShow <= 50) {
            correctRate.setStyle("-fx-text-fill: #FF69B4;");
        } else if (correctRateToShow >= 80) {
            correctRate.setStyle("-fx-text-fill: #ADFF2F;");
        }
    }
}
