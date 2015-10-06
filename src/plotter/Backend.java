// make the doubles look nicer; round!
package plotter;

import java.awt.Dimension;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Backend {

	static GUI MainWindow = new GUI("plotter", new Dimension(500,500), new Dimension(500,500));	
	
	public static void main(String[] args) {
		MainWindow.setVisible(true);
	}
	
		// plot the function
	public static void plot(String[] strvalues) {
		double[] values = parseNumbers(strvalues);
		double[] zeroes = getZeroes(values);
		double maxzero = max(zeroes);
			// test if there was no x-intercept
		if(maxzero != maxzero) {
			MainWindow.updateStatus(false, "No X-Intercept!");
			return;
		}
		MainWindow.updatePlot(generateTables(values, maxzero));
	}
	
		// parse input
	private static double[] parseNumbers(String[] strvalues) {
		double[] dubvals = new double[6];
		try {
			// try to convert the inputs to numbers
			for(int i = 0; i < dubvals.length; i++) {
				dubvals[i] = Double.parseDouble(strvalues[i]);
				if(i == 0 || i == 3) {
					dubvals[i] /= 2;
				}
			}			
			MainWindow.updateStatus(true, "Equation updated successfully");
		} catch(NumberFormatException e) {
			MainWindow.updateStatus(false, "Check your inputs - are they all numbers?");
		}
		return dubvals;
	}
		
		// calculate the zeroes of the x and y functions
	private static double[] getZeroes(double[] values) {
		double[] zeroes = new double[4];
		zeroes[0] = (-values[1]+Math.sqrt(Math.pow(values[1], 2)-4*values[0]*values[2]))/(2*values[0]);
		zeroes[1] = (-values[1]-Math.sqrt(Math.pow(values[1], 2)-4*values[0]*values[2]))/(2*values[0]);
		zeroes[2] = (-values[4]+Math.sqrt(Math.pow(values[4], 2)-4*values[3]*values[5]))/(2*values[3]);
		zeroes[3] = (-values[4]-Math.sqrt(Math.pow(values[4], 2)-4*values[3]*values[5]))/(2*values[3]);
		return zeroes;
	}
	
		// generate the tables for the plot
	private static double[][] generateTables(double[] values, double maxzero) {
			// find the magnitude, interval for table calculation, and maximum x
		double magnitude = Math.pow(10, Math.floor(Math.log10(maxzero)));
		double interval = magnitude/100;
		double max = magnitude*(round(maxzero/magnitude, 2, RoundingMode.UP));
			// calculate tables
		int j = 0;
		double[][] table = new double[4][(int)round(max/interval, 0, RoundingMode.HALF_UP)];
		for(double i = 0; i < maxzero; i += interval) {
			table[0][j] = i; table[2][j] = i;
			table[1][j] = values[0]*Math.pow(i,2)+values[1]*i+values[2];
			table[3][j] = values[3]*Math.pow(i,2)+values[4]*i+values[5];
			j++;
		}
		return table;
	}
	
		// custom rounding utility
	private static double round(double value, int places, RoundingMode mode) {
	    if (places < 0) throw new IllegalArgumentException();
	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, mode);
	    return bd.doubleValue();
	}
	
		// find maximum
	private static double max(double[] input) {
		double max = input[0];
		for(int i = 1; i < input.length; i++) {
			if(input[i] > input[i-1] || input[i-1] != input[i-1]) {
				max = input[i];
			}
		}
		return max;
	}
}
