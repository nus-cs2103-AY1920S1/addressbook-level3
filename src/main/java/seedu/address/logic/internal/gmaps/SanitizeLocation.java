package seedu.address.logic.internal.gmaps;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import seedu.address.commons.exceptions.TimeBookInvalidLocation;
import seedu.address.websocket.GmapsApi;

/**
 * This call is used to find the valid location name
 */
public class SanitizeLocation implements Serializable {
    private ArrayList<String> validLocationList = new ArrayList<>();
    private transient GmapsApi gmapsApi;

    /**
     * Takes in gmapsApi so that it could be replaced by a gmapsApi stub
     * @param gmapsApi
     */
    public SanitizeLocation(GmapsApi gmapsApi) {
        this.gmapsApi = gmapsApi;
    }

    /**
     * This method is used to get the list of sanitized location
     * @return
     */
    public ArrayList<String> getValidLocationList() {
        return this.validLocationList;
    }

    /**
     * This method is used to santize the location name on nus mods to a google maps identifiable location
     * @param locationName
     * @return
     * @throws ConnectException
     */
    public String sanitize(String locationName) throws ConnectException, TimeBookInvalidLocation {
        String validLocation = "NUS_" + locationName;
        validLocation = validLocation.split("-")[0];
        if (!validLocationList.contains(validLocation)) {
            JSONObject apiResponse = gmapsApi.getLocation(validLocation);
            String status = GmapsJsonUtils.getStatus(apiResponse);
            if (status.equals("OK")) {
                validLocationList.add(validLocation);
            } else {
                validLocation = "NUS_" + validLocation.split("_")[1];
                if (!validLocationList.contains(validLocation)) {
                    apiResponse = gmapsApi.getLocation(validLocation);
                    status = GmapsJsonUtils.getStatus(apiResponse);
                    if (status.equals("OK")) {
                        validLocationList.add(validLocation);
                    } else {
                        throw new TimeBookInvalidLocation("Cannot identify" + validLocation);
                    }
                }
            }
        }
        return validLocation;
    }

    /**
     * This method is used to save the instance of this object
     */
    public void save() {
        try {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream("data/SanitizeLocationSerialization");
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(this);

            out.close();
            file.close();

            System.out.println("SanitizeLocation has been serialized");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
