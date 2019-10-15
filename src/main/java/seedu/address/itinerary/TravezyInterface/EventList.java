package seedu.address.itinerary.TravezyInterface;

import seedu.address.itinerary.EventItinerary.Event;

import java.util.ArrayList;
import java.util.Random;

public class EventList {
    private ArrayList<Event> eventList = new ArrayList<>();
    private Random rand = new Random();

    void addEventPrompt() {
        String[] adj = { "Cool", "Awesome", "Amazing", "Brilliant", "Awesome Possum" };
        System.out.println(printLine() + "\n     " + adj[rand.nextInt(4)]
                + "! I have added this event! Here are your events currently:");

        printEventListBullet();

        System.out.println(printLine());
    }

    void remove(int index) {
        eventList.remove(index - 1);
        String[] adj = {"Yes sir", "Affirmative", "Roger", "Got it", "Cool"};
        System.out.println(printLine() + "\n     " + adj[rand.nextInt(4)] 
                + "! I have deleted the task! Here are your events currently:");

        printEventListBullet();

        System.out.println(printLine());
    }

    void listEvent() {
        System.out.println(printLine() + "\n     Here are your events currently:");

        printEventListBullet();

        System.out.println(printLine());
    }

    void showEvent(int index) {
        System.out.println(printLine() + "\n     Roger that! Here are the details of the event:\n");

        System.out.println(eventList.get(index - 1));

        System.out.println(printLine());
    }

    private void printEventListBullet() {
        for (int i = 0; i < eventList.size(); i++) {
            System.out.println("        " + (i + 1) + ". " + eventList.get(i).getTitle());
        }
    }

    public void markAsDone(int index) {
        Event event = eventList.get(index - 1);
        event.markAsDone();
    }

    public void add(Event event) {
        eventList.add(event);
    }

    int size() {
        return eventList.size();
    }

    private String printLine() {
        return "\n★ ・・・・・・・・・・・・・・・・・・・・・・・・・・・・・・・・・・・★\n";
    }
}
