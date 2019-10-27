//@@author nattanyz
package dream.fcard.gui.components;

import dream.fcard.gui.GuiSettings;
import dream.fcard.logic.respond.Responder;
import dream.fcard.model.State;
import javafx.scene.control.TextField;

/**
 * UI component representing the text field for user to enter commands.
 */
public class CommandTextField extends TextField implements UiComponent<TextField> {
    private State state;

    /**
     * Creates a new instance of CommandTextField.
     */
    public CommandTextField() {
        // create text field
        super();

        // add prompt text
        this.setPromptText("Enter command here...");

        // setup styles of commandTextField
        // todo: fix text field background colour :(
        //this.setStyle("-fx-border-color:" + GuiSettings.getTertiaryUiColour() + ";");
        //this.setStyle("-fx-control-inner-background:" + GuiSettings.getTertiaryUiColour() + ";");
        //this.setStyle("-fx-text-fill:#FFFFFF;");

        this.setStyle("-fx-text-fill:" + GuiSettings.getPrimaryTextColour() + ";");
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

    public void setState(State applicationState) {
        this.state = applicationState;
    }
}
