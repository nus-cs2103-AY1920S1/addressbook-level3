//@@author nattanyz
package dream.fcard.gui.components;

import dream.fcard.gui.GuiSettings;
import dream.fcard.logic.respond.Responder;
import dream.fcard.model.State;
import javafx.scene.control.TextField;

public class CommandTextField extends TextField implements UiComponent<TextField> {
    State state;
    public CommandTextField(State state) {
        // create text field
        super();

        this.state = state;

        // add prompt text
        this.setPromptText("Enter command here...");

        // setup styles of commandTextField
        // todo: fix text field background colour :(
//        commandTextField.setStyle("-fx-border-color:" + GuiSettings.getTertiaryUIColour() + ";");
//        commandTextField.setStyle("-fx-control-inner-background:" + GuiSettings.getTertiaryUIColour() + ";");
//        commandTextField.setStyle("-fx-text-fill:#FFFFFF;");
        this.setStyle("-fx-text-fill:" + GuiSettings.getPrimaryTextColour() +";");
        this.setFont(GuiSettings.getCommandTextStyle());

        this.setOnAction((event) -> {
            try {
                Responder.takeInput(this.getText(), this.state);
                // reset text field
                this.setText("");
            } catch (Exception e) {
                // todo: temporary haxx, don't know what exceptions yet
            }
        });
    }
    // todo: add some way to modify commandTextField's appearance when command fails
}
