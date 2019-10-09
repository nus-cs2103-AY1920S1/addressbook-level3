package seedu.address.model.events;

/**
 * Describes the status of an appointment
 */
public class Status{
    public static String APPROVED_MESS = "patient appointment was made";
    public static String ACK_MESS = "patient is arrived";
    public static String MISSED_MESS = "patient missed appointment, need to settle";
    public static String SETTLE_MESS = "this missed appointment have been settled";
    public static String CANCELL_MESS = "this appointment have been cancelled";
    private String status;

    public Status(String statusMess){
        this.status = statusMess;
    }

    public Status(){
        this.status = "APPROVED";
    }

    public String getStatusMess() {
        switch (status) {

        case "APPROVED":
        return APPROVED_MESS;
        case "ACKED":
        return ACK_MESS;
        case "MISSED":
        return MISSED_MESS;
        case "SETTLE":
        return SETTLE_MESS;
        default:
        return "status is wrong";
    }
}

    public void setAckStatus(){
        this.status = "ACKED";
    }

    public void setSettleStatus(){
        this.status = "SETTLED";
    }

    public void setMissStatus(){
        this.status = "MISSED";
    }

    public void setCancelStatus(){
        this.status = "CANCELLED";
    }

    @Override
    public String toString() {
        return this.status;
    }
}