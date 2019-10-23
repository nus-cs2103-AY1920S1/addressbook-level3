package dream.fcard.gui;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class FlashCardDisplay extends HBox {
    private Text flashCardText;

    public FlashCardDisplay(String text) {
        super();
//        this.setMaxWidth();
//        this.setMinHeight();

        // todo: must figure out how to get card to maintain max width, min height

        this.setPadding(new Insets(10));
        this.setStyle("-fx-background-color:" + GuiSettings.getPrimaryUIColour() +";");

        flashCardText = new Text(text);

        // style flashCardText
        flashCardText.setFont(GuiSettings.getFlashCardTextStyle());
        flashCardText.setFill(Color.web(GuiSettings.getPrimaryTextColour()));
        flashCardText.setWrappingWidth(this.getWidth());

        TextFlow textFlow = new TextFlow(flashCardText);

        // add flashCardText to card
        this.getChildren().add(textFlow);
    }
}
