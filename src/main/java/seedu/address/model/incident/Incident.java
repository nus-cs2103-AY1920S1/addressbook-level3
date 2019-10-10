package seedu.address.model.incident;
import java.time.LocalDateTime;
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

    //needs to be entered by operator
    private Description incidentDesc;
    private District location;
    private String callerNumber;


    /**
     * Creates a new Incident report, fields will be filled in through prompts in the GUI.
     * @param caller is the phone number of the caller that reported the incident.
     */

    public Incident(String caller) {
        this.dateTime = LocalDateTime.now();
        this.callerNumber = caller;
        //operator = autofilled when sign in
        //location = prompt and return value from GUI
        incidentDesc = new Description(); //prompt to fill in desc now. if n, empty desc, if yes, use string from GUI
        //car = auto-assigned??
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
