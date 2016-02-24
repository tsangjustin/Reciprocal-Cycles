import java.util.Scanner;
import java.math.*;

public class BigDeci {
	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {
		int max_denom;
		max_denom = Integer.parseInt(input.nextLine());
		for (int curr_num = 2; curr_num < max_denom; ++curr_num) {
			int longestRepeating = 1;
			BigDecimal numerator = new BigDecimal(1);
			BigDecimal denominator = new BigDecimal(curr_num);
			BigDecimal fraction = numerator.divide(denominator, curr_num*2, RoundingMode.HALF_UP);
			System.out.println(fraction);
		}
	}
}
