package seedu.address.model.util;

import static java.util.Objects.requireNonNull;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import org.apache.commons.math3.exception.MathArithmeticException;


/**
 * A utility class for performing gradient descent for linear regression
 */
public final class GradientDescent {

    private static final double LEARNING_RATE = 0.003;
    private static final double TOLERANCE = 1E-11;
    private static final int MAX_ITERATIONS = 100000;
    private static final int OUTPUT_SCALE_FACTOR = 100;

    private double variable;
    private double result;
    private final int numInputs;
    private double [] inputData;
    private double [] actualValues;
    private double theta0;
    private double theta1;
    private double minData;
    private double maxData;
    private double dataMean;
    private double dataRange;
    private double minOutput;
    private double maxOutput;
    private double outputMean;
    private double outputRange;
    private int iterations;

    /**
     * Sole constructor for the Gradient Descent class
     * @param actualValues the actual values of the target variable (which is to be predicted by the model)
     * @param inputData the values of input variables (which will be used to predict the target variable)
     */
    public GradientDescent(double[] actualValues, double[] inputData) {
        requireNonNull(actualValues);
        requireNonNull(inputData);
        this.inputData = inputData;
        this.numInputs = this.inputData.length;
        this.theta0 = 0;
        this.theta1 = 0;
        this.actualValues = actualValues;
        this.iterations = 0;
        this.scaleData();
        this.train();
    }

    /**
     * Normalise features in data set to optimise gradient descent
     */
    private void scaleData() {
        this.dataMean = DoubleStream.of(inputData).sum() / numInputs;
        this.minData = DoubleStream.of(inputData).min().orElseThrow(MathArithmeticException::new);
        this.maxData = DoubleStream.of(inputData).max().orElseThrow(MathArithmeticException::new);
        this.dataRange = maxData - minData;
        IntStream.range(0, numInputs).forEach(i -> this.inputData[i] = (this.inputData[i] - dataMean) / dataRange);
        this.outputMean = DoubleStream.of(actualValues).sum() / numInputs;
        this.minOutput = DoubleStream.of(actualValues).min().orElseThrow(MathArithmeticException::new);
        this.maxOutput = DoubleStream.of(actualValues).max().orElseThrow(MathArithmeticException::new);
        this.outputRange = maxOutput - minOutput;
        IntStream.range(0, numInputs).forEach(i ->
                this.actualValues[i] = (this.actualValues[i] - outputMean) / outputRange);
    }

    /**
     * Performs the gradient descent algorithm to optimise {@code theta0} & {@code theta1}
     */
    private void train() {
        double delta0;
        double delta1;
        do {
            this.iterations++;
            delta0 = LEARNING_RATE * deriveTheta(0);
            delta1 = LEARNING_RATE * deriveTheta(1);
            theta0 -= delta0;
            theta1 -= delta1;
        } while((Math.abs(delta0) > TOLERANCE || Math.abs(delta1) > TOLERANCE) && iterations <= MAX_ITERATIONS);
    }

    /**
     * @param theta the "independent" variable
     * @return The (partial) derivative of the sum of costs for values of theta
     */
    private double deriveTheta(int theta) {
        double costGradient = 0.0;
        if (theta == 0) {
            for (int i = 0; i < numInputs; i++) {
                costGradient += actualValues[i] - computeHypothesis(inputData[i]);
            }
        } else {
            for (int i = 0; i < numInputs; i++) {
                costGradient += (actualValues[i] - computeHypothesis(inputData[i])) * inputData[i];
            }
        }
        return (-2.0 / numInputs) * costGradient;
    }

    /**
     * @param input An independent variable found in the training data
     * @return A prediction on the dependent variable based on current values of {@code theta0} and {@code theta1}
     */
    private double computeHypothesis(double input) {
        return theta0 + theta1 * input;
    }

    /**
     * @param input An independent variable specified by the user
     * @return A prediction on the dependent variable based on the final values of {@code theta0} and {@code theta1}
     */
    public double predict(double input) {
        this.variable = input;
        double scaledData = (input - dataMean) / dataRange;
        this.result = computeHypothesis(scaledData) * outputRange + outputMean;
        return result;
    }

    public double getActualValue(int idx) {
        return (this.actualValues[idx] * outputRange + outputMean) / OUTPUT_SCALE_FACTOR;
    }

    public double getInputData(int idx) {
        return this.inputData[idx] * dataRange + dataMean;
    }

    public int getNumInputs() {
        return this.numInputs;
    }

    public double getMinData() {
        return minData;
    }

    public double getMaxData() {
        return maxData;
    }


    public double getMinOutput() {
        return (minOutput / OUTPUT_SCALE_FACTOR);
    }

    public double getMaxOutput() {
        return (maxOutput / OUTPUT_SCALE_FACTOR);
    }

    public double getVariable() {
        return variable;
    }

    public double getResult() {
        return (result / OUTPUT_SCALE_FACTOR);
    }
}
