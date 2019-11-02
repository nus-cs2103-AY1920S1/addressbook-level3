package seedu.address.model.util;

import org.apache.commons.math3.exception.MathArithmeticException;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import static java.util.stream.DoubleStream.of;

/**
 * A utility class for performing gradient descent for linear regression
 */
public final class GradientDescent {

    private static final double LEARNING_RATE = 0.001;
    private static final double TOLERANCE = 1E-11;
    private static final int MAX_ITERATIONS = 100000;

    private final int numInputs;
    private final double [] inputData;
    private double [] actualValues;
    private double theta0;
    private double theta1;
    private double dataMean;
    private double dataRange;
    private double outputMean;
    private double outputRange;
    private int iterations;


    /**
     * Sole constructor for the Gradient Descent class
     * Instantiates {@code inputData} as a m x n matrix where m is the size of the training set and
     * n is the number of input variables
     * Initializes theta to a (n + 1) array representing theta0, theta1 ... thetaX, where theta0 is a constant
     * and theta1 ... thetaX are the input variables
     * @param actualValues the actual values of the target variable (which is to be predicted by the model)
     * @param inputData the values of input variables (which will be used to predict the target variable)
     */
    public GradientDescent(double[] actualValues, double[] inputData) {
        this.inputData = inputData;
        this.numInputs = this.inputData.length;
//        this.numThetas = inputData.length + 1;
        this.theta0 = 0;
        this.theta1 = 0;
//        this.dataMeans = new double[numThetas];
//        this.dataRanges = new double[numThetas];
        this.actualValues = actualValues;
        this.iterations = 0;
        this.scaleData();
        this.train();
    }

    /**
     * Process data to optimise gradient descent
     */
    private void scaleData() {
//        IntStream.range(0, inputData.length).forEach(x -> {
//            double mean = DoubleStream.of(inputData[x]).sum() / numInputs;
//            double range = DoubleStream.of(inputData[x]).max().orElseThrow(MathArithmeticException::new)
//                    - DoubleStream.of(inputData[x]).min().orElseThrow(MathArithmeticException::new);
//            this.dataMeans[x] = mean;
//            this.dataRanges[x] = range;
//            IntStream.range(0, numInputs).forEach(i -> inputData[x][i] = (inputData[x][i] - mean) / range);
//        });
//        double outputMean = DoubleStream.of(actualValues).sum() / numInputs;
//        double outputRange = DoubleStream.of(actualValues).max().orElseThrow(MathArithmeticException::new)
//                - DoubleStream.of(actualValues).min().orElseThrow(MathArithmeticException::new);
//        this.dataMeans[numThetas - 1] = outputMean;
//        this.dataRanges[numThetas - 1] = outputRange;
//        IntStream.range(0, numInputs).forEach(i -> actualValues[i] = (actualValues[i] - outputMean) / outputRange);
        this.dataMean = DoubleStream.of(inputData).sum() / numInputs;
        this.dataRange = DoubleStream.of(inputData).max().orElseThrow(MathArithmeticException::new)
                - DoubleStream.of(inputData).min().orElseThrow(MathArithmeticException::new);
        IntStream.range(0, numInputs).forEach(i -> this.inputData[i] = (this.inputData[i] - dataMean) / dataRange);
        this.outputMean = DoubleStream.of(actualValues).sum() / numInputs;
        this.outputRange = DoubleStream.of(actualValues).max().orElseThrow(MathArithmeticException::new)
                - DoubleStream.of(actualValues).min().orElseThrow(MathArithmeticException::new);
        IntStream.range(0, numInputs).forEach(i -> this.actualValues[i] = (this.actualValues[i] - outputMean) / outputRange);
    }

    /**
     * Performs the gradient descent algorithm to optimise {@code theta0} & {@code theta1}
     */
    public void train() {
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

    //    private double deriveTheta(int theta) {
//        double cost;
//        if (theta == 0) {
//            cost = IntStream.range(0, numThetas).asDoubleStream().reduce(0, (x, y) ->
//                    x + (actualValues[(int)y] - computeHypothesis(inputData[theta][(int)y])));
//        } else {
//            cost = IntStream.range(0, numThetas).asDoubleStream().reduce(0, (x, y) ->
//                    x + (actualValues[(int)y] - computeHypothesis(inputData[theta-1][(int)y])) * inputData[theta-1][(int)y]);
//        }
//        return (1.0 / numInputs) * cost;
//    }

    private double deriveTheta(int theta) {
        double cost = 0.0;
        if (theta == 0) {
            for (int i = 0; i < numInputs; i++) {
                cost += computeHypothesis(inputData[i]) - inputData[i];
            }
        } else {
            for (int i = 0; i < numInputs; i++) {
                cost += (computeHypothesis(inputData[i]) - inputData[i]) * inputData[i];
            }
        }
        return (1.0 / numInputs) * cost;
    }

//    private double computeHypothesis(double... inputs) {
//        double value = thetas[0];
//        for (int i = 1; i < numThetas; i++) {
//            value += this.thetas[i] * inputs[i-1];
//        }
//        return value;
//    }

    private double computeHypothesis(double input) {
        return theta0 + theta1 * input;
    }

//    public double predict(double... inputs) {
//        return scaleDataPoint(thetas[0] + IntStream.range(1, numThetas).asDoubleStream().reduce(0, (x, y) ->
//                x += this.thetas[(int)y] * scaleDataPoint(inputs[(int)y - 1], (int)y - 1)
//        ), numThetas - 1);
//    }

    public double predict(double input) {
        double scaledData = (input - dataMean) / dataRange;
        double result = computeHypothesis(scaledData);
        return result * outputRange + outputMean;
    }

    /**
     * Perform feature scaling (and mean normalisation) for all variables in the data set to optimise gradient descent
     */
//    private double scaleDataPoint(double data, int idx) {
//        double mean = dataMeans[idx];
//        double range = dataRanges[idx];
//        return (data - mean) / range;
//    }

    public int getIterations() {
        return this.iterations;
    }
}
