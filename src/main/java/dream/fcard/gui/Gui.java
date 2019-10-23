package dream.fcard.gui;

import dream.fcard.model.cards.FlashCard;
import javafx.scene.Node;

public class Gui {
    // the instance of the main window of the application, containing all UI components
    MainWindow mainWindow;

    public Gui() {

    }

    void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    MainWindow getMainWindow() {
        return this.mainWindow;
    }

    GuiSettings getGuiSettings() {
        return GuiSettings.getInstance();
    }

    void renderFront(FlashCard flashCard) {
        // get text to be rendered
        String cardText = flashCard.getFront();

        // generate Node representing flashCard's contents
        FlashCardDisplay node = new FlashCardDisplay(cardText);

        // display the Node in the Gui
        this.display(node);
    }

    void display(Node node) {
        // get primary display area of MainWindow

        // show the Node in the display area
    }

    void setTitle(String title) {
        this.getMainWindow().setTitle(title);
    }
}
