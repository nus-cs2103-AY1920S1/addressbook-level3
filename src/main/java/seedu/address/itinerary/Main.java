package seedu.address.itinerary;

import seedu.address.itinerary.EventItinerary.Event;
import seedu.address.itinerary.TravezyInterface.EventList;
import seedu.address.itinerary.TravezyInterface.Parser;
import seedu.address.itinerary.TravezyInterface.Ui;

import java.util.Scanner;

//v1.1 changes
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EventList eventList = new EventList();
        Ui ui = new Ui();

        eventList.add(new Event("test", 1));

        ui.greet();

        while(ui.getActivityLevel() > 0) {
            String command = sc.nextLine();

            Parser.processCommand(command, ui, eventList);
        }

        sc.close();
    }
}
