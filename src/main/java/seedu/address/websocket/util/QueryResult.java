package seedu.address.websocket.util;

public class QueryResult {
    private Integer responseCode;
    private String responseResult;

    public QueryResult(Integer responseCode, String responseResult){
        this.responseCode = responseCode;
        this.responseResult = responseResult;
    }

    public Integer getResponseCode(){
        return this.responseCode;
    }

    public String getResponseResult(){
        return this.responseResult;
    }

    /**
     * Processes any errors and returns true if not errors are found
     * @return
     */
    public boolean process(){
        if (this.responseCode == null) {
            System.out.println("ERROR: " + this.responseResult);
            return false;
        } else if (this.responseCode != 200) {
            System.out.println("ERROR: " + this.responseResult + " Response Code: " + this.responseCode);
            return false;
        } else {
            return true;
        }
    }
}
