package plotter;

import java.awt.Dimension;

public class Backend {

		// define application window
	static GUI MainWindow = new GUI("plotter", new Dimension(600,600), new Dimension(600,600));	
	
		// global variables
	private static boolean para = true;
	
		// launch application window, initialize values
	public static void main(String[] args) {
		MainWindow.setVisible(true);
		MainWindow.updateStatus(true, "Application Launch Successful!");
		MainWindow.updateOutData(new double[6]);
	}
	
		// change the plot mode
	public static void setParametric(boolean selection) {
		if(selection) {
			para = true;
		} else {
			para = false;
		}
		plot();
	}
	
		// plot the function
	public static void plot() {
		double[] values = new double[6];
		try {
			values = parse(MainWindow.getValues());
		} catch(NumberFormatException e) {
			MainWindow.updateStatus(false, "Check your inputs - are they all numbers?");
			MainWindow.clearPlot();
			return;
		}
		double[][] zeroes = getZeroes(values);
		double xzero = max(zeroes[0]); double yzero = max(zeroes[1]); double maxzero = 0;
			// test the two zeros, find the larger one or state that they're both nonexistent
		try {
			maxzero = parse(MainWindow.getXMax());
			if(maxzero <= 0) {
				throw new NumberFormatException();
			}
		} catch(NumberFormatException e) {
			if(xzero > yzero && xzero == xzero && xzero > 0) {
				maxzero = xzero;
			} else if(yzero == yzero && yzero > 0) {
				maxzero = yzero;
			} else {
				MainWindow.updateStatus(false, "Check your X Max - is it a positive number?");
				MainWindow.clearPlot();
				return;
			}

		}
		double[][] tables = generateTables(values, maxzero);

		MainWindow.updatePlot(tables, para);
		
			// update zero and max data - vals[zx,mx,mtx,zy,my,mty]
		int xmaxindex = maxIndex(tables[1]); int ymaxindex = maxIndex(tables[3]);
		double[] outputvals = new double[6];
		
			// test if one or both zeroes doesn't exist
		String statusmessage_zexist = "Both zeroes exist"; boolean xzerodne = false; boolean yzerodne = false;
		try {
			outputvals[0] = round(xzero, 3, "half");
		}
		catch(NumberFormatException e) { 
			statusmessage_zexist = "X zero does not exist";
			xzerodne = true;
		}
		try { 
			outputvals[3] = round(yzero, 3, "half");
		}
		catch(NumberFormatException e) {
			statusmessage_zexist = "Y zero does not exist";
			yzerodne = true;
		}
		if(xzerodne && yzerodne) {
			statusmessage_zexist = "Neither zero exists";
		}
		MainWindow.updateStatus(true, statusmessage_zexist);
		outputvals[1] = round(tables[1][xmaxindex], 3, "half");
		outputvals[2] = round(tables[0][xmaxindex], 3, "half");
		outputvals[4] = round(tables[3][ymaxindex], 3,"half");
		outputvals[5] = round(tables[2][ymaxindex], 3, "half");
		MainWindow.updateOutData(outputvals);
		return;
	}
	
		// parse input list
	private static double[] parse(String[] strvalues) throws NumberFormatException{
		double[] dubvals = new double[6];
			// try to convert the inputs to numbers
		for(int i = 0; i < dubvals.length; i++) {
			dubvals[i] = Double.parseDouble(strvalues[i]);
			if(i == 0 || i == 3) {
				dubvals[i] /= 2;
			}		
		}
		return dubvals;
	}
		// parse xmax input
	private static double parse(String value) throws NumberFormatException {
		return Double.parseDouble(value);
	}
		
		// calculate the zeroes of the x and y functions
	private static double[][] getZeroes(double[] values) {
		double[][] zeroes = new double[2][2];
		zeroes[0][0] = (-values[1]+Math.sqrt(Math.pow(values[1], 2)-4*values[0]*values[2]))/(2*values[0]);
		zeroes[0][1] = (-values[1]-Math.sqrt(Math.pow(values[1], 2)-4*values[0]*values[2]))/(2*values[0]);
		zeroes[1][0] = (-values[4]+Math.sqrt(Math.pow(values[4], 2)-4*values[3]*values[5]))/(2*values[3]);
		zeroes[1][1] = (-values[4]-Math.sqrt(Math.pow(values[4], 2)-4*values[3]*values[5]))/(2*values[3]);
		return zeroes;
	}
	
		// generate the tables for the plot
	private static double[][] generateTables(double[] values, double maxzero) {
			// find the magnitude, interval for table calculation, and maximum x
		double magnitude = Math.pow(10, Math.floor(Math.log10(maxzero)));
		double interval = magnitude/100;
		double max = magnitude*(round(maxzero/magnitude, 2, "half"));
			// calculate tables
		int i = 0; int roundvalue = (int)-(Math.log10(magnitude)-2);
		double[][] table = new double[4][(int)round(max/interval, 0,"up")];
		while(i < table[0].length) {
			double x = round(i*interval, roundvalue, "half");
			table[0][i] = x; table[2][i] = x;
			table[1][i] = round(values[0]*Math.pow(x,2)+values[1]*x+values[2], roundvalue, "half");
			table[3][i] = round(values[3]*Math.pow(x,2)+values[4]*x+values[5], roundvalue, "half");
			i++;
		}
		return table;
	}
	
		// custom rounding utility - throws an exception if answer doesn't exist
	private static double round(double value, int places, String mode) throws NumberFormatException {
		double roundadj = 0.5;
		switch(mode) {
		case "up":
			roundadj = 1;
			break;
		case "down":
			roundadj = 0;
			break;
		case "half":
			roundadj = 0.5;
			break;
		}
		double rounded = Math.floor((value*Math.pow(10,places))+roundadj)/Math.pow(10, places);
		if(rounded != rounded) {
			throw new NumberFormatException();
		} else {
			return (rounded);
		}
	}
	
		// find maximum
	private static double max(double[] input) {
		double max = input[0];
		for(int i = 1; i < input.length; i++) {
			if(input[i] > max|| input[i-1] != input[i-1]) {
				max = input[i];
			}
		}
		return max;
	}
	
		// find index of maximum
	private static int maxIndex(double[] input) {
		double in_max = max(input);
		int i;
		for(i=0; input[i] != in_max; i++) {	}
		return i;
	}
}