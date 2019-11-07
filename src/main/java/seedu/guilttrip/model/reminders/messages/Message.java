package seedu.guilttrip.model.reminders.messages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.model.ModelManager;
import seedu.guilttrip.model.entry.Entry;


/**
 * Optional pop up message for reminder.
 */
public class Message {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private Entry entry;
    private HashMap<Pair<Integer, Integer>, Cell> messageMap = new HashMap<>();

    private Pair<Integer, Integer> headerCoordinates;
    private List<Pair<Integer, Integer>> imageCoordinates;
    private Pair<Integer, Integer> noteCoordinates;
    private Pair<Integer, Integer> statsCoordinates;

    public Message(String headerString, int x_coordinate, int y_coordinate) {
        Header header = new Header(headerString);
        messageMap.put(new Pair(x_coordinate, y_coordinate), header);
        this.headerCoordinates = new Pair(x_coordinate, y_coordinate);
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
    public boolean placeNote(String reminderNote, int x_coordinate, int y_coordinate) {
        Pair<Integer, Integer> coordinates = new Pair(x_coordinate, y_coordinate);
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

    /**
     * Include stats details in message. Only applicable for budget reminders. Only one stats pannel.
     * @return
     */
    public boolean placeStats(int x_coordinate, int y_coordinate) {
        Pair<Integer, Integer> coordinates = new Pair(x_coordinate, y_coordinate);
        update(this.entry);
        if (!(messageMap.containsKey(coordinates))) {
            this.statsCoordinates = coordinates;
            return true;
        } else {
            return false;
        }
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
     * @param coordinate_x
     * @param coodinate_y
     * @param new_x
     * @param new_y
     * @return if shifting is successful.
     */
    public boolean shiftCell(int coordinate_x, int coodinate_y, int new_x, int new_y) {
        Pair<Integer, Integer> oldPos = new Pair(coordinate_x, coodinate_y);
        Pair<Integer, Integer> newPos = new Pair(new_x, new_y);
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
        logger.info("Pop Up Rendered. Number of Nodes: " + nodes.size());
        return message;
    }

    //===== Methods for cloning message =====

    /**
     * Makes a copy of this message. Used when cloning reminder.
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
