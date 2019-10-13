import EventItinerary.Event;
import TravezyInterface.EventList;
import TravezyInterface.Ui;
import TravezyInterface.Parser;

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
