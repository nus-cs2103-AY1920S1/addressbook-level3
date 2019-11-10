package seedu.guilttrip.model.reminders.messages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.model.ModelManager;
import seedu.guilttrip.model.entry.Entry;


/**
 * Optional pop up message for generalReminder.
 */
public class Message {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private static final String defaultbgiLink = "/images/page3.png";
    private static final String defaultFont = "/fonts/Faraco_Hand.ttf";
    private static final double defaultNoteFontSize = 10;
    private static final double defaultHeaderFontSize = 20;

    private double hGap = 5;
    private double vGap = 5;

    private Entry entry;
    private HashMap<Pair<Integer, Integer>, Cell> messageMap = new HashMap<>();
    private Background bgi;

    private Pair<Integer, Integer> headerCoordinates;
    private List<Pair<Integer, Integer>> imageCoordinates;
    private Pair<Integer, Integer> noteCoordinates;
    private Pair<Integer, Integer> statsCoordinates;

    public Message(String headerString, int xCoordinate, int yCoordinate) {
        Header header = new Header(headerString);
        messageMap.put(new Pair(xCoordinate, yCoordinate), header);
        this.headerCoordinates = new Pair(xCoordinate, yCoordinate);
        try {
            buildBackground(defaultbgiLink);
        } catch (IOException e) {
            this.bgi = null;
        }

    }

    /**
     * Used for cloning
     */
    private Message() {};


    //===== getters and setters =====

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
    }

    public HashMap<Pair<Integer, Integer>, Cell> getMessageMap() {
        return messageMap;
    }

    public void setMessageMap(HashMap<Pair<Integer, Integer>, Cell> messageMap) {
        this.messageMap = messageMap;
    }

    public Pair<Integer, Integer> getHeaderCoordinates() {
        return headerCoordinates;
    }

    public void setHeaderCoordinates(Pair<Integer, Integer> headerCoordinates) {
        this.headerCoordinates = headerCoordinates;
    }

    public List<Pair<Integer, Integer>> getImageCoordinates() {
        return imageCoordinates;
    }

    public void setImageCoordinates(List<Pair<Integer, Integer>> imageCoordinates) {
        this.imageCoordinates = imageCoordinates;
    }

    public Pair<Integer, Integer> getNoteCoordinates() {
        return noteCoordinates;
    }

    public void setNoteCoordinates(Pair<Integer, Integer> noteCoordinates) {
        this.noteCoordinates = noteCoordinates;
    }

    public Pair<Integer, Integer> getStatsCoordinates() {
        return statsCoordinates;
    }

    public void setStatsCoordinates(Pair<Integer, Integer> statsCoordinates) {
        this.statsCoordinates = statsCoordinates;
    }

    //==========

    public void setHeader(String headerString) {
        Header header = (Header) messageMap.get(headerCoordinates);
        header.setHeader(headerString);
    }
    /**
     * Places an image in message. Can have multiple images.
     * This loads an image from outside the app and stores it inside the app.
     * @param imagePath
     * @return
     */
    public boolean placeNewImage(
            String imagePath, String imageName, int xCoordinate, int yCoordinate, boolean willStore)
            throws IOException {
        Pair<Integer, Integer> coordinates = new Pair<>(xCoordinate, yCoordinate);
        if (!(messageMap.containsKey(coordinates))) {
            ReminderImage image = new ReminderImage(imageName, imagePath, willStore);
            ImageStorage.saveImage(imageName, image.getImage());
            messageMap.put(coordinates, image);
            this.imageCoordinates.add(coordinates);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Places an image in message. Can have multiple images.
     * This loads an image from outside the app and stores it inside the app.
     * @return
     */
    public boolean placeImage(String imageName, int xCoordinate, int yCoordinate)
            throws IOException {
        Pair<Integer, Integer> coordinates = new Pair<>(xCoordinate, yCoordinate);
        if (!(messageMap.containsKey(coordinates))) {
            ReminderImage image = new ReminderImage(imageName);
            messageMap.put(coordinates, image);
            this.imageCoordinates.add(coordinates);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Places an note in message. Can only have 1 note.
     * @param reminderNote
     * @return
     */
    public boolean placeNote(String reminderNote, int xCoordinate, int yCoordinate) {
        Pair<Integer, Integer> coordinates = new Pair(xCoordinate, yCoordinate);
        Note note = new Note(reminderNote);
        note.update(entry);
        this.noteCoordinates = coordinates;
        if (!(messageMap.containsKey(coordinates))) {
            messageMap.put(coordinates, note);
            return true;
        } else {
            return false;
        }
    }

    public boolean setHeaderFont(String fontName, double fontSize) {
        if (headerCoordinates == null) {
            return false;
        } else {
            Header header = (Header) messageMap.get(headerCoordinates);
            header.setFont(fontName, fontSize);
            return true;
        }
    }

    public boolean setNoteFont(String fontName, double fontSize) {
        if (noteCoordinates == null) {
            return false;
        } else {
            Note note = (Note) messageMap.get(noteCoordinates);
            note.setFont(fontName, fontSize);
            return true;
        }
    }

    /**
     * Restores default font
     */
    public void restoreDefaultFont() throws IOException {
        Header header = (Header) messageMap.get(headerCoordinates);
        header.setFont(defaultFont, defaultNoteFontSize);
        Note note = (Note) messageMap.get(noteCoordinates);
        note.setFont(defaultFont, defaultNoteFontSize);
        buildBackground(defaultbgiLink);
    }

    /**
     * Sets background image.
     * @param imageLink file path of image.
     * @throws IOException image not found.
     */
    public void buildBackground(String imageLink) throws IOException {
        Image background =
                new ReminderImage(
                        "/images/page3.png", "DefaultBackground", false).getImage();
        this.bgi = new Background(new BackgroundImage(
                background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, null));
    }

    public void setBgi(String imageLink) throws IOException {
        buildBackground(imageLink);
    }

    public void setSpace(double hGap, double vGap) {
        this.hGap = hGap;
        this.vGap = vGap;
    }

    /**
     * Remove cell from pop up.
     * @param coordinateX column number
     * @param coordinateY row number
     * @return boolean if deletion was successful.
     */
    public boolean deleteCell(int coordinateX, int coordinateY) {
        Pair<Integer, Integer> key = new Pair<>(coordinateX, coordinateY);
        if (messageMap.containsKey(key)) {
            Cell cell = messageMap.get(key);
            if (headerCoordinates.equals(key)) {
                this.headerCoordinates = null;
            } else if (imageCoordinates.contains(key)) {
                this.imageCoordinates.remove(key);
            } else if (noteCoordinates.equals(key)) {
                this.noteCoordinates = null;
            } else if (statsCoordinates.equals(key)) {
                this.statsCoordinates = null;
            }
            messageMap.remove(key);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Only can shift a cell if old position contains cell and new position is empty.
     * @param xCoordinate
     * @param yCoordinate
     * @param newX
     * @param newY
     * @return if shifting is successful.
     */
    public boolean shiftCell(int xCoordinate, int yCoordinate, int newX, int newY) {
        Pair<Integer, Integer> oldPos = new Pair(xCoordinate, yCoordinate);
        Pair<Integer, Integer> newPos = new Pair(newX, newY);
        if (messageMap.containsKey(oldPos) && !messageMap.containsKey(newPos)) {
            Cell target = messageMap.get(oldPos);
            messageMap.put(newPos, target);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Updates message to reflect current state of entry.
     * @param entry
     */
    public void update(Entry entry) {
        this.entry = entry;
        Note note = (Note) this.messageMap.get(noteCoordinates);
        note.update(entry);
    }

    /**
     * Generates the message pop up.
     * @return
     */
    public GridPane render() {
        GridPane message = new GridPane();
        List<Node> nodes = new ArrayList<>();
        for (Pair key : messageMap.keySet()) {
            Cell cell = messageMap.get(key);
            Node node = cell.getNode();
            nodes.add(node);
            GridPane.setConstraints(node, (int) key.getKey(), (int) key.getValue());
        }
        for (Node node : nodes) {
            message.getChildren().addAll(nodes);
        }
        if (bgi != null) {
            message.setBackground(bgi);
            logger.info("bgi found");
        } else {
            logger.info("no bgi.");
        }
        message.setHgap(hGap);
        message.setVgap(vGap);
        logger.info("Pop Up Rendered. Number of Nodes: " + nodes.size());
        return message;
    }

    //===== Methods for cloning message =====

    /**
     * Makes a copy of this message. Used when cloning generalReminder.
     * @return clone
     */
    public Message clone() {
        Message newMessage = new Message();
        newMessage.setMessageMap(this.getMessageMap());
        newMessage.setHeaderCoordinates(this.getHeaderCoordinates());
        newMessage.setImageCoordinates(this.getImageCoordinates());
        newMessage.setNoteCoordinates(this.getNoteCoordinates());
        newMessage.setStatsCoordinates(this.getStatsCoordinates());
        return newMessage;
    }
}
