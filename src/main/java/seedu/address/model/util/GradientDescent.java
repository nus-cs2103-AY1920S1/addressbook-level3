package seedu.address.model.util;

import java.util.stream.IntStream;

import javafx.collections.ObservableList;
import seedu.address.model.transaction.BankAccountOperation;

/**
 * A utility class for performing 2-variable gradient descent
 * TODO: Add feature normalization
 */
public final class GradientDescent {

    private static final double LEARNING_RATE = 0.0006;
    private static final double TOLERANCE = 1E-11;
    private static final int MAX_ITERATIONS = 100000;

    private double theta0;
    private double theta1;
    private double[][] data;
    private int iterations;
    private ObservableList<BankAccountOperation> transactionHistory;

    public GradientDescent(ObservableList<BankAccountOperation> transactionHistory) {
        this.theta0 = 0;
        this.theta1 = 0;
        this.iterations = 0;
        this.transactionHistory = transactionHistory;
        this.processData();
        this.train();
    }

    /**
     * Populates a 2-column matrix with transaction dates and amounts
     */
    public void processData() {
        this.data = new double[this.transactionHistory.size()][2];
        IntStream.range(0, transactionHistory.size()).forEach(i -> {
            BankAccountOperation transaction = transactionHistory.get(i);
            this.data[i][0] = Date.daysBetween(transaction.getDate(), Date.now());
            this.data[i][1] = transaction.getAmount().getIntegerValue();
        });
    }

    /**
     * TODO
     */
    private double deriveThetaZeroCost() {
        Double cost = IntStream.range(0, data[0].length).asDoubleStream().reduce(0, (x, y) -> {
            return x + (data[(int) y][0] - predict(data[(int) y][0]));
        });
        return -2 * cost / data[0].length;
    }

    /**
     * TODO
     */
    private double deriveThetaOneCost() {
        Double cost = IntStream.range(0, data[0].length).asDoubleStream().reduce(0, (x, y) -> {
            return x + (data[(int) y][0] - predict(data[(int) y][0]) * data[(int) y][0]);
        });
        return -2 * cost / data[0].length;
    }

    public double predict(double x) {
        return theta0 + theta1 * x;
    }

    /**
     * Performs the gradient descent algorithm to optimise {@code theta0} & {@code theta1}
     */
    public void train() {
        double delta0;
        double delta1;
        do {
            this.iterations++;
            delta0 = LEARNING_RATE * deriveThetaZeroCost();
            delta1 = LEARNING_RATE * deriveThetaOneCost();
            // System.out.println((Math.abs(delta0) + Math.abs(delta1)));
            double temp0 = theta0 - delta0;
            double temp1 = theta1 - delta1;
            theta0 = temp0;
            theta1 = temp1;
        } while ((Math.abs(delta0) + Math.abs(delta1)) > TOLERANCE && iterations <= MAX_ITERATIONS);
    }

    public int getIterations() {
        return this.iterations;
    }
}
