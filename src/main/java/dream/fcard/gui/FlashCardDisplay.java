package dream.fcard.gui;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class FlashCardDisplay extends VBox {
    private Text flashCardText;

    public FlashCardDisplay(String text) {
        super();
        this.setMaxWidth(USE_COMPUTED_SIZE);

        // todo: figure out how to get card to maintain max width, min height

        this.setPadding(new Insets(10));
        this.setStyle("-fx-background-color:" + GuiSettings.getPrimaryUIColour() +";");

        flashCardText = new Text(text);

        // style flashCardText
        flashCardText.setFont(GuiSettings.getFlashCardTextStyle());
        flashCardText.setFill(Color.web(GuiSettings.getPrimaryTextColour()));
        flashCardText.setWrappingWidth(this.getWidth());

        // add flashCardText to card
        this.getChildren().add(flashCardText);
    }
}
