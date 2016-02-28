/*
 * If prime, keep dividing
 * If non-prime, take prime and dividec
*/

public class ReciprocalCycles {
	//Global Variables
	//private int leadingZero_;
	private int leadingProd_;
	private int intervalGap_;
	//private String strLeadingDecimal_;
	public int denominator_;
	private int[] leadingDecimal_;
	private int[] trailingDecimal_;
	private int indexTrailing_;
	private int indexLeading_;
	private boolean hasLeadingDecimal;

	public boolean isOdd(int num) {
		if ((num % 2) == 0 || (num % 5) == 0) {
			return false;
		}
		return true;
	}
 	
	public String toString() {
		String mainString = "1/"+denominator_+" = 0.";
		for (int i = 0; i <= indexLeading_; ++i) {
			mainString += leadingDecimal_[i];
		}
		if (indexTrailing_ > -1) {
			mainString += "(";
		}
		for (int i = 0; i <= indexTrailing_; ++i) {
			mainString += trailingDecimal_[i];
		}
		if (indexTrailing_ > -1) {
			mainString += "), cycle length "+intervalGap_;
		}
		return mainString;
	}

	/*
	// If divisible by 2 or 5
	public void getLeadingNum(int prod) {
		int numZero = findLenCycle(prod, 0);
		//leadingDecimal_ = new int[numZero];
		// int base10 = 10;
		// for (int i = 0; i < numZero; ++i) {
		// 	base10 *= 10;
		// }
		// int intDec = base10 / prod;
		// //double doubleDec = (double)intDec / (double)base10;
		// double doubleDec = 1.0 / (double)base10;
		// int wholeDec = (int)intDec / (int)base10;
		// String strLeadingDecimal_ = Double.toString(wholeDec);
	}
	*/

	public int findLenCycle(int num, int rem) {
		int numExponent;
		if (rem == 0) {
			numExponent = 1;
			int remain = 2;
			int product = 10;
			while (remain != rem) {
				leadingDecimal_[++indexLeading_] = product / num;
				remain = product % num;
				product = remain * 10;
				++numExponent;
			}
		} else {
			numExponent = 0;
			int remain = 2;
			int product = 10;
			while (remain != rem) {
				if (!hasLeadingDecimal) {
					trailingDecimal_[++indexTrailing_] = product / num;
				}
				remain = product % num;
				product = remain * 10;
				++numExponent;
			}
			intervalGap_ = numExponent;
		}
		return numExponent;
	}

	public void updateDecimal(int oddNum) {
		int remain = leadingDecimal_[0] % oddNum;
		leadingDecimal_[0] /= oddNum;
		//int product = remain;
		for (int i = 1; i <= indexLeading_; ++i) {
			remain = remain * 10 + leadingDecimal_[i];
			leadingDecimal_[i] = remain / oddNum;
			remain %= oddNum;
		}
		for (int i = 0; i <= intervalGap_; ++i) {
			remain = remain * 10;
			trailingDecimal_[i] = remain / oddNum;
			remain = remain % oddNum;
			++indexTrailing_;
		}
		--indexTrailing_;
	}

	public ReciprocalCycles(int maxDenom) {
		//amtMoveDecimal_ = 0;
		intervalGap_ = 1;
		denominator_ = maxDenom;
		int oddNum = 1;
		trailingDecimal_ = new int[maxDenom];
		indexLeading_ = -1;
		indexTrailing_ = -1;
		hasLeadingDecimal = false;
		//arrReciprocalLength_ = new int[maxDenom];
		//primeFactors_ = new int[(int)Math.ceil((Math.log(maxDenom) / Math.log(2)))];
		int longestRepeating = 2;
		if (!isOdd(maxDenom)) {
			leadingProd_ = 1;
			int numEven = 0;
			while ((maxDenom % 2) == 0) {
				maxDenom /= 2;
				leadingProd_ *= 2;
				++numEven;
			}
			while ((maxDenom % 5) == 0) {
				maxDenom /= 5;
				leadingProd_ *= 5;
				++numEven;
			}
			hasLeadingDecimal = true;
			leadingDecimal_ = new int[numEven];
			findLenCycle(leadingProd_, 0);
			//getLeadingNum(leadingProd_);
			//Change to array of prime factors
			//getLeadingDecimal();
			oddNum = denominator_ / leadingProd_;
			//decimalValue_ = 1.0 / (double)denominator;
		}
		if (maxDenom > 1) {
			int base10 = 1;
			int numZero = findLenCycle(maxDenom, 1);
			if (hasLeadingDecimal) {
				updateDecimal(oddNum);
			}
		}
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java ReciprocalCycles <denominator>");
		} else {
			int max_denom = 0;
			try {
				max_denom = Integer.parseInt(args[0]);
				//System.out.println(max_denom);
				if (max_denom == 1) {
					System.out.println("1/1 = 1");
				} else if (max_denom < 1 || max_denom > 2000) {
					System.out.println("Error: Denominator must be an integer in [1, 2000]. Received '" + args[0] + "'.");
				} else {
					ReciprocalCycles rc = new ReciprocalCycles(max_denom);
					System.out.println(rc.toString());
				}
			} catch (NumberFormatException e) {
				System.out.println("Error: Denominator must be an integer in [1, 2000]. Received '" + args[0] + "'.");
			}
		}
	}
}