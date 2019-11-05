package seedu.address.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Row item to contain claim for CheckContactWindow.
 */
public class ClaimItem {

    private final SimpleStringProperty sn = new SimpleStringProperty(this, "sn");
    private final SimpleStringProperty claimId = new SimpleStringProperty(this, "claimId");
    private final SimpleStringProperty date = new SimpleStringProperty(this, "date");
    private final SimpleStringProperty status = new SimpleStringProperty(this, "status");
    private final SimpleStringProperty description = new SimpleStringProperty(this, "description");
    private final SimpleStringProperty amount = new SimpleStringProperty(this, "amount");

    public ClaimItem(String sn, String claimId, String date, String status, String description, String amount) {
        setSn(sn);
        setClaimId(claimId);
        setDate(date);
        setStatus(status);
        setDescription(description);
        setAmount(amount);
    }

    public ClaimItem() {
        this("", "", "", "", "", "");
    }

    public StringProperty snProperty() {
        return sn;
    }

    public void setSn(String value) {
        sn.set(value);
    }

    public String getSn() {
        System.out.println("getSn");
        return sn.get();
    }

    public StringProperty claimIdProperty() {
        return claimId;
    }

    public void setClaimId(String value) {
        claimId.set(value);
    }

    public String getClaimId() {
        System.out.println("getClaimId");
        return claimId.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String value) {
        date.set(value);
    }

    public String getDate() {
        System.out.println("getDate");
        return date.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String value) {
        status.set(value);
    }

    public String getStatus() {
        System.out.println("getStatus");
        return status.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String value) {
        description.set(value);
    }

    public String getDescription() {
        System.out.println("getDescription");
        return description.get();
    }

    public StringProperty amountProperty() {
        return amount;
    }

    public void setAmount(String value) {
        amount.set(value);
    }

    public String getAmount() {
        System.out.println("getAmount");
        return amount.get();
    }
}
