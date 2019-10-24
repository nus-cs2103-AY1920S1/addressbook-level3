//@@author nattanyz
package dream.fcard.gui.components;

import dream.fcard.gui.GuiSettings;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class FlashCardDisplay extends HBox implements UiComponent<HBox> {
    private Text flashCardText;

    public FlashCardDisplay(String text) {
        super();

        // set up dimensions
        this.setPrefSize(GuiSettings.getMinWidth() - 2*GuiSettings.getPadding(),
            200);
        this.setPadding(new Insets(GuiSettings.getPadding()));

        // set up background colour and radius
        this.setStyle("-fx-background-color:" + GuiSettings.getPrimaryUIColour() +";");
//        this.setStyle("-fx-background-radius:" + GuiSettings.getRadius() +";");

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
