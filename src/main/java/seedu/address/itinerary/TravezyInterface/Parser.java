package seedu.address.itinerary.TravezyInterface;

import seedu.address.itinerary.EventItinerary.Event;

import java.util.StringJoiner;

public class Parser {
    public static void processCommand(String command, Ui ui, EventList eventList) {
        String[] commandArr = command.split(" ");

        switch(commandArr[0]) {
            case "addEvent":
                Event event = new Event(getTitle(command), eventList.size() + 1);
                eventList.add(event);
                eventList.addEventPrompt();
                break;

            case "listEvent":
                eventList.listEvent();
                break;

            case "showEvent":
                eventList.showEvent(Integer.parseInt(commandArr[1]));
                break;

            case "selectEvent":
                // Only can use level 2 commands
                ui.setActivityLevel(2);
                break;

            case "exit":
                ui.setActivityLevel(0);
                ui.exit();
                break;

            default:
                System.out.println("Hello world!");
                break;
        }
    }

    private static String getTitle(String command) {
        String[] commandArr = command.split(" ");
        StringJoiner sj = new StringJoiner(" ");

        for (int i = 1; i < commandArr.length; i++) {
            sj.add(commandArr[i]);
        }

        return sj.toString();
    }
}
