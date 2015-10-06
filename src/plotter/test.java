package plotter;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class test
{
	static double maxzero = 0.003942697635;
	public static void main(String[] args) {
		double magnitude = Math.pow(10, Math.floor(Math.log10(maxzero)));
		double interval = magnitude/100;
		double max = magnitude*(round(maxzero/magnitude, 2, RoundingMode.UP));
		System.out.println(maxzero); System.out.println(max); System.out.println(interval);
	}
	
		// custom rounding
	private static double round(double value, int places, RoundingMode mode) {
	    if (places < 0) throw new IllegalArgumentException();
	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, mode);
	    return bd.doubleValue();
	}
}