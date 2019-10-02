package TravezyInterface;

import EventItinerary.Event;

import java.util.ArrayList;
import java.util.Random;

public class EventList {
    private ArrayList<Event> eventList = new ArrayList<>();
    private Random rand = new Random();

    void addEventPrompt() {
        String[] adj = {"Cool", "Awesome", "Amazing", "Brilliant", "Awesome Possum"};
        System.out.println(printLine() + "\n     " + adj[rand.nextInt(3)] +
                "! I have added this event! Here are your events currently:");

        printEventListBullet();

        System.out.println(printLine());
    }

    void listEvent() {
        System.out.println(printLine() + "\n     Here are your events currently:");

        printEventListBullet();

        System.out.println(printLine());
    }

    void showEvent(int index) {
        System.out.println(printLine() + "\n     Roger that! Here is the detail of the event:\n");

        System.out.println(eventList.get(index - 1));

        System.out.println(printLine());
    }

    private void printEventListBullet() {
        for (int i = 0; i < eventList.size(); i++) {
            System.out.println("        " + (i + 1) + ". " + eventList.get(i).getTitle());
        }
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
