package seedu.address.model.util;

import javafx.collections.ObservableList;
import seedu.address.model.transaction.BankAccountOperation;

/**
 * A utility class for performing gradient descent on a 2-column matrix
 */
public class GradientDescent {

    private static final double LEARNING_RATE = 0.01;
    private static final double TOLERANCE = 1E-11;

    private double theta0;
    private double theta1;
    private double [][] data;
    private int iterations;
    ObservableList<BankAccountOperation> transactionHistory;

    public GradientDescent(ObservableList<BankAccountOperation> transactionHistory) {
        this.theta0 = 0;
        this.theta1 = 0;
        this.iterations = 0;
        this.transactionHistory = transactionHistory;
        this.processData();
        this.train();
    }

    public void processData() {
        this.data = new double[this.transactionHistory.size()][2];
        for (int i = 0; i < transactionHistory.size(); i++) {
            BankAccountOperation transaction = transactionHistory.get(i);
            this.data[i][0] = Date.daysBetween(transaction.getDate(), Date.now());
            this.data[i][1] = transaction.getAmount().getIntegerValue();
        }
    }

    public double predict(double x) {
        return theta0 + theta1 * x;
    }

    private double getResult (double[][] data, boolean doCalibration) {
        double result = 0;
        for (int i = 0; i < data.length; i++) {
            result = (predict(data[i][0]) - data[i][1]);
            if (doCalibration) {
                result = result * data[i][0];
            }
        }
        return result;
    }

    public void train() {
        double delta0, delta1;
        do {
            this.iterations ++;
            System.out.println("SUBS: " + (LEARNING_RATE*((double) 1/data.length)) * getResult(data, false));
            double temp0 = theta0 - LEARNING_RATE*(((double) 1 / data.length) * getResult(data, false));
            double temp1 = theta1 - LEARNING_RATE*(((double) 1 / data.length) * getResult(data, true));
            delta0 = theta0 - temp0;
            delta1 = theta1 - temp1;
            theta0 = temp0;
            theta1 = temp1;
            if (iterations >= 10000) {
                break;
            }
        } while((Math.abs(delta0) + Math.abs(delta1)) > TOLERANCE);
    }

    public int getIterations() {
        return this.iterations;
    }
}
