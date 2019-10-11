package seedu.address.model.incident;
import java.time.LocalDateTime;
import java.util.Scanner;

import seedu.address.model.person.Person;
import seedu.address.model.vehicle.District;
import seedu.address.model.vehicle.Vehicle;

/**
 * Represents an incident report in the IMS.
 */
public class Incident {

    //is autofilled
    private Person operator;
    private LocalDateTime dateTime;
    private Vehicle car;
    private IncidentID id;

    //needs to be entered by operator
    private Description incidentDesc;
    private District location;
    private String callerNumber;


    /**
     * Creates a new Incident report, fields will be filled in through prompts in the GUI.
     * @param caller is the phone number of the caller that reported the incident.
     */

    public Incident(String caller) {
        //this.operator = autofilled on sign in
        this.dateTime = LocalDateTime.now();
        this.id = new IncidentID(dateTime.getMonthValue(), dateTime.getYear());
        this.incidentDesc = promptForDescription();
        this.location = promptForLocation();
        this.callerNumber = caller;
        //this.car = VehicleAssigner.assignVehicle(location);
    }


    /**
     * static method to prompt operator for incident location
     * @return district which will be stored to location
     */
    public static District promptForLocation() {
        System.out.println("Enter location:"); //need to change to GUI prompt
        Scanner sc = new Scanner(System.in);   //need to change to GUI input
       int dist = Integer.parseInt(sc.next());
        while(!District.isValidDistrict(dist)) {
            System.out.println("Please enter a valid district");
            dist = Integer.parseInt(sc.next());
        }
        return new District(dist);
    }

    public static Description promptForDescription() {
        System.out.println("Enter incident description now? y/n"); //change to GUI
        Scanner sc = new Scanner(System.in); //change to GUI
        String desc = "";
        if(sc.next().equals("y")) {
            System.out.println("Please enter description:");
            desc = sc.nextLine();
        }
        return new Description(desc);
    }


    public LocalDateTime getTime() {
        return this.dateTime;
    }

    public Description getDesc() {
        return this.incidentDesc;
    }

    public String getCallerNumber() {
        return this.callerNumber;
    }

    public Vehicle getCar() {
        return car;
    }
}
