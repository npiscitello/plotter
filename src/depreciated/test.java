package depreciated;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class test
{
	static double maxzero = 0.003942697635;
	public static void main(String[] args) {
		System.out.println(1 > Math.sqrt(-1));
	}
	
		// custom rounding
	private static double round(double value, int places, RoundingMode mode) {
	    if (places < 0) throw new IllegalArgumentException();
	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, mode);
	    return bd.doubleValue();
	}
}