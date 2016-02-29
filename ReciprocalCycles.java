/*******************************************************************************
 * Name        : ReciprocalCycles.java
 * Author      : Michael Curry and Justin Tsang
 * Version     : 1.0
 * Date        : 2/29/2016
 * Description : Produce the decimal equivalent with repeating decimal of fraction
 * Pledge      : I pledge my honor that I have abided by the Stevens Honor System
 ******************************************************************************/
public class ReciprocalCycles {
	//Global Variables
	private int leadingProd_;
	private int intervalGap_;
	public int denominator_;
	private int[] leadingDecimal_;
	private int[] trailingDecimal_;
	private int indexTrailing_;
	private int indexLeading_;
	private boolean hasLeadingDecimal_;

	// Check if number given has 2's or 5's as prime factors
	public boolean isOdd(int num) {
		if ((num % 2) == 0 || (num % 5) == 0) {
			return false;
		}
		return true;
	}
 	
 	// Print decimal to screen
	public String toString() {
		String mainString = "1/"+denominator_+" = 0.";
		// If denominator_ has 2's and 2's in prime factors, print the terminating decimals
		for (int i = 0; i <= indexLeading_; ++i) {
			mainString += leadingDecimal_[i];
		}
		// Check if there are repeating numbers
		if (indexTrailing_ > -1) {
			mainString += "(";
		}
		// Print the repeating decimals
		for (int i = 0; i <= indexTrailing_; ++i) {
			mainString += trailingDecimal_[i];
		}
		// Close off repeating decimal and print length of repeating decimal
		if (indexTrailing_ > -1) {
			mainString += "), cycle length "+intervalGap_;
		}
		return mainString;
	}

	// Find length of repeating decimal
	public int findLenCycle(int num, int rem) {
		int numExponent;
		// If finding the leading decimals of 2s and 5s
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
		// If finding trailing decimals
		} else {
			numExponent = 0;
			int remain = 2;
			int product = 10;
			while (remain != rem) {
				if (!hasLeadingDecimal_) {
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

	// If number has 2's and 5's, the repeating decimal is dependent on leading decimal
	public void updateDecimal(int oddNum) {
		int remain = leadingDecimal_[0] % oddNum;
		leadingDecimal_[0] /= oddNum;
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

	// Main function that initiates values and calls functiosn
	public ReciprocalCycles(int maxDenom) {
		intervalGap_ = 1;
		denominator_ = maxDenom;
		trailingDecimal_ = new int[maxDenom];
		indexLeading_ = -1;
		indexTrailing_ = -1;
		hasLeadingDecimal_ = false;
		int oddNum = 1;

		// If the number is divisble by 2 or 5
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
			hasLeadingDecimal_ = true;
			leadingDecimal_ = new int[numEven];
			findLenCycle(leadingProd_, 0);
			oddNum = denominator_ / leadingProd_;
		}
		// If the number is odd or has numbers not 2 or 5
		if (maxDenom > 1) {
			int numZero = findLenCycle(maxDenom, 1);
			// If maxDenom has leading decimal
			if (hasLeadingDecimal_) {
				updateDecimal(oddNum);
			}
		}
	}

	public static void main(String[] args) {
		// Check if number is missing denominator
		if (args.length != 1) {
			System.out.println("Usage: java ReciprocalCycles <denominator>");
		} else {
			int max_denom = 0;
			// Try to convert string to int
			try {
				max_denom = Integer.parseInt(args[0]);
				// If input is 1
				if (max_denom == 1) {
					System.out.println("1/1 = 1");
				// If input is outside range
				} else if (max_denom < 1 || max_denom > 2000) {
					System.out.println("Error: Denominator must be an integer in [1, 2000]. Received '" + args[0] + "'.");
				// Input is int and inside range
				} else {
					ReciprocalCycles rc = new ReciprocalCycles(max_denom);
					System.out.println(rc.toString());
				}
			// Input is not int
			} catch (NumberFormatException e) {
				System.out.println("Error: Denominator must be an integer in [1, 2000]. Received '" + args[0] + "'.");
			}
		}
	}
}