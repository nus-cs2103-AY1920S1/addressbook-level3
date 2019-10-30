package seedu.address.model.display.detailwindow;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.websocket.Cache;

/**
 * Data for closest common location
 */
public class ClosestCommonLocationData {
    private boolean isOk = false;
    private String firstClosest;
    private String secondClosest;
    private String thirdClosest;
    private long firstAvg;
    private long secondAvg;
    private long thirdAvg;
    private ArrayList<String> invalidLocation;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    public BufferedImage getImage() {
        if (firstClosest.contains("NUS_")) {
            logger.warning(firstClosest + " must not have NUS_ prefix");
        }
        return Cache.loadImage("NUS_" + firstClosest);
    }

    public String getFirstClosest() {
        return firstClosest;
    }

    public String getSecondClosest() {
        return secondClosest;
    }

    public String getThirdClosest() {
        return thirdClosest;
    }

    public long getFirstAvg() {
        return firstAvg;
    }

    public long getSecondAvg() {
        return secondAvg;
    }

    public long getThirdAvg() {
        return thirdAvg;
    }

    public ArrayList<String> getInvalidLocation() {
        return invalidLocation;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setFirstClosest(String firstClosest) {
        this.firstClosest = firstClosest;
    }

    public void setSecondClosest(String secondClosest) {
        this.secondClosest = secondClosest;
    }

    public void setThirdClosest(String thirdClosest) {
        this.thirdClosest = thirdClosest;
    }

    public void setFirstAvg(long firstAvg) {
        this.firstAvg = firstAvg;
    }

    public void setSecondAvg(long secondAvg) {
        this.secondAvg = secondAvg;
    }

    public void setThirdAvg(long thirdAvg) {
        this.thirdAvg = thirdAvg;
    }

    public void setInvalidLocation(ArrayList<String> invalidLocation) {
        this.invalidLocation = invalidLocation;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    /**
     * Method to give the String representation of ClosestCommonLocationData.
     */
    public String toString() {
        String result = "";
        result += firstClosest + ": " + firstAvg + "\n";
        result += secondClosest + ": " + secondAvg + "\n";
        result += secondClosest + ": " + thirdAvg + "\n";
        return result;
    }
}
