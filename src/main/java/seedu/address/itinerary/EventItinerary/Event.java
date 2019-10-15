package seedu.address.itinerary.EventItinerary;

public class Event {
    private int num;
    private String desc, location, tag, notes, title, time;
    private boolean isDone;

    public Event(String title, int num) {
        this.title = title;
        this.num = num;
        this.tag = "";
        this.isDone = false;
    }

    public void markAsDone() {
        isDone = true;
    }

    @Override
    public String toString() {

        String event = printBorder() + "\n"
                + String.format("     | Num: %-3d | Title: %-36s| %s |\n", num, title, 
                getStatusIcon()) + printBorder() + "\n"
                + String.format("     | Time 🕒 : %-10s | Location 📍 : %-20s |\n", 
                time, location)
                + printBorder() + "\n"
                + String.format("     | Description 🖋️  : %-40s |\n", desc) + printBorder();
        return event;
    }

    public String getTitle() {
        return "[" + getStatusIcon() + "] " + title;
    }

    private String getStatusIcon() {
        return (isDone ? "✓" : "✘"); // return tick or X symbols
    }

    private String printBorder() {
        // Allows up to 50 characters long in title
        return "     o-----------------------------------------------------------o";
    }
}
