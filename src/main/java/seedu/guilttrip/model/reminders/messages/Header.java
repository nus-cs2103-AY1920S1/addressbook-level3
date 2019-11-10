package seedu.guilttrip.model.reminders.messages;

import javafx.scene.Node;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Header of generalReminder. Will always be included in message pop up.
 */
public class Header extends Cell {
    public static final String TYPE = "Header";
    private static final String DEFAULTFONT = "/fonts/Faraco_Hand.ttf";
    private static final double DEFAULTFONTSIZE = 10;
    private String header;
    private String fontName = DEFAULTFONT;
    private double fontSize = DEFAULTFONTSIZE;
    private Font font;

    public Header(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setFont(String font, double size) {
        this.fontName = font;
        this.fontSize = size;
        this.font = Font.font(fontName, fontSize);
    }

    @Override
    public Node getNode() {
        Text header = new Text(this.header);
        header.setFont(font);
        return header;
    }
}
