package seedu.address.itinerary.EventItinerary;

@SuppressWarnings({"unused", "FieldCanBeLocal", "UnnecessaryLocalVariable"})
public class Event {
    private int time, num;
    private String desc, location, tag, notes, title;

    public Event(String title, int num) {
        this.title = title;
        this.num = num;
        this.desc = "";
        this.location = "";
        this.tag = "";
        this.notes = "";
    }

    @Override
    public String toString() {

        String event = printBorder() + "\n" +
                String.format("     | Num: %-3d | Title: %-40s|\n", num, title) +
                printBorder();
        return event;
    }

    public String getTitle() {
        return title;
    }

    private String printBorder() {
        // Allows up to 50 characters long in title
        return "     o----------+------------------------------------------------o";
    }
}

