package depreciated;
/*// calculations break down sometimes, probably due to floating point calculation errors...
package plotter;

import java.awt.Dimension;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Backend_Depreciated {

	static GUI MainWindow = new GUI("plotter", new Dimension(500,500), new Dimension(500,500));	
	
	private static double[] values = {0,0,0,0,0,0};
	private static boolean plot = false;
	
	public static void main(String[] args) {
		MainWindow.setVisible(true);
	}
	
		// plot the function
	public static void plot(String[] strvalues) {
		try {
				// try to convert the inputs to numbers
			for(int i = 0; i < values.length; i++) {
				values[i] = Double.parseDouble(strvalues[i]);
				if(i == 0 || i == 3) {
					values[i] /= 2;
				}
			}			
			MainWindow.updateStatus(true, "Equation updated successfully");
			plot = true;
			
		} catch(NumberFormatException e) {
			MainWindow.updateStatus(false, "Check your inputs - are they all numbers?");
			plot = false;
		}

		if(plot) {
				// find the zeroes of the functions
			double[] zeroes = getZeroes(values);
				// find the larger of the two zeroes
			double maxzero;
			if(zeroes[1] > zeroes[0]) {
				maxzero = zeroes[1];
			} else {
				maxzero = zeroes[0];
			}
			System.out.println(maxzero);
				// check for NaN
			try {
					// find the magnitude, interval for table calculation, and maximum x
				int magnitude = (int) Math.floor(Math.log10(maxzero)); double interval = Math.pow(10, magnitude-2);
				double max = Math.pow(10, magnitude)*(round(maxzero/Math.pow(10, magnitude), 2, RoundingMode.UP));
					// calculate tables
				double[][] X = new double[2][(int)(round(max/interval,0,RoundingMode.DOWN))];
				double[][] Y = new double[2][(int)(round(max/interval,0,RoundingMode.DOWN))];
				int j = 0;
				for(double i = 0; i < max; i += interval) {
					try {
						X[0][j] = i; Y[0][j] = i;
						X[1][j] = values[0]*Math.pow(i,2)+values[1]*i+values[2];
						Y[1][j] = values[3]*Math.pow(i,2)+values[4]*i+values[5];
						j++;
					} catch(ArrayIndexOutOfBoundsException e) {
						System.out.println("Out of bounds exception caught...");
					}
				}
				MainWindow.updatePlot(X, Y);
			} catch(NumberFormatException e) {
				MainWindow.updateStatus(false, "No X-Intercept!");
			}
		}
		
	}
	
		// calculate the zeroes of the x and y functions
	private static double[] getZeroes(double[] values) {
		double[] zeroes = {0,0};
			// get zero of x
		double plus = (-values[1]+Math.sqrt(Math.pow(values[1], 2)-4*values[0]*values[2]))/(2*values[0]);
		if(plus > 0) {
			zeroes[0] = plus;
		} else {
			zeroes[0] = (-values[1]-Math.sqrt(Math.pow(values[1], 2)-4*values[0]*values[2]))/(2*values[0]);
		}
			// get zero of y
		plus = (-values[4]+Math.sqrt(Math.pow(values[4], 2)-4*values[3]*values[5]))/(2*values[3]);
		if(plus > 0) {
			zeroes[1] = plus;
		} else {
			zeroes[1] = (-values[4]-Math.sqrt(Math.pow(values[4], 2)-4*values[3]*values[5]))/(2*values[3]);
		}
		return zeroes;
	}
	
		// custom rounding utility
	public static double round(double value, int places, RoundingMode mode) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, mode);
	    return bd.doubleValue();
	}
}*/
