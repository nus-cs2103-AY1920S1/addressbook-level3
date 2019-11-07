package seedu.guilttrip.model.reminders.messages;

import javafx.scene.Node;
import javafx.scene.text.Text;

/**
 * Header of reminder. Will always be included in message pop up.
 */
public class Header extends Cell {
    public static final String type = "Header";
    private String header;
    public Header(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public Node getNode() {
        return new Text(header);
    }
}
