package seedu.address.model.employee;

/**
 * Represents the Employee's Finances in the App.
 * Not immutable - totalPay and pendingPay can be mutated
 */
public class EmployeeFinances {
    //data fields
    private EmployeeTotalSalary totalPay;
    private EmployeePendingPay pendingPay;

    public EmployeeFinances(EmployeeTotalSalary totalPay, EmployeePendingPay pendingPay) {
        this.totalPay = totalPay;
        this.pendingPay = pendingPay;
    }

    public EmployeeTotalSalary getTotalPay() {
        return totalPay;
    }

    public EmployeePendingPay getPendingPay() {
        return pendingPay;
    }

    public void setFinances(EmployeeTotalSalary totalPay, EmployeePendingPay pendingPay) {
        this.totalPay = totalPay;
        this.pendingPay = pendingPay;
    }

}
