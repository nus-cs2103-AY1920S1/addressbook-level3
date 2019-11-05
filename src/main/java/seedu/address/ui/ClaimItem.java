package seedu.address.ui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import seedu.address.model.claim.Claim;

public class ClaimItem {
    private final SimpleStringProperty sn = new SimpleStringProperty(this, "sn");;
    private final StringProperty claimId = new SimpleStringProperty(this, "claimId");
    private final StringProperty date = new SimpleStringProperty(this, "date");
    private final StringProperty status = new SimpleStringProperty(this, "status");
    private final StringProperty description = new SimpleStringProperty(this, "description");
    private final StringProperty amount = new SimpleStringProperty(this, "amount");

    public ClaimItem(int sn, Claim claim) {
        this(sn + "",
                claim.getId().toString(),
                claim.getDate().toString(),
                claim.getStatus().toString(),
                claim.getDescription().toString(),
                claim.getAmount().toString());
    }


    public ClaimItem(String sn, String claimId, String date, String status, String description, String amount) {
        setSn(sn);
        setClaimId(claimId);
        setDate(date);
        setStatus(status);
        setDescription(description);
        setAmount(amount);
    }

    public ClaimItem(String claimId, String date, String status, String description, String amount) {
        setClaimId(claimId);
        setDate(date);
        setStatus(status);
        setDescription(description);
        setAmount(amount);
    }

    public StringProperty snProperty() {
//        if (sn == null) {
//            sn = new SimpleStringProperty(this, "sn");
//        }
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
//        if (claimId == null) {
//            claimId = new SimpleStringProperty(this, "claimId");
//        }
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
//        if (date == null) {
//            date = new SimpleStringProperty(this, "date");
//        }
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
//        if (status == null) {
//            status = new SimpleStringProperty(this, "claimId");
//        }
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
//        if (description == null) {
//            description = new SimpleStringProperty(this, "claimId");
//        }
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
//        if (amount == null) {
//            amount = new SimpleStringProperty(this, "claimId");
//        }
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
