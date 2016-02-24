import java.util.Scanner;
import java.math.*;
public class ReciprocalCycle {
	//Global Variables
	private static Scanner input = new Scanner(System.in);
	private int[] arrReciprocalLength;

	public boolean isPrime(int num) {
		int limit = (int)Math.floor(Math.sqrt(num));
		for (int curr = 2; curr <= limit; ++curr) {
			if (num % curr == 0) {
				return false;
			}
		}
		return true;
	}

	public ReciprocalCycle() {
		int max_denom;
		max_denom = Integer.parseInt(input.nextLine());
		arrReciprocalLength = new int[max_denom];
		for (int curr_num = 2; curr_num < max_denom; ++curr_num) {
			int longestRepeating = 1;
			int numberMultiplier = primeFactorize(curr_num);
			double number = Math.pow(10, numberMultiplier);
			BigDecimal numerator = new BigDecimal(number);
			BigDecimal denominator = new BigDecimal(curr_num);
			BigDecimal fraction = numerator.divide(denominator, curr_num*2, RoundingMode.HALF_UP);
			System.out.println(fraction);
		}
	}
	/*
	public class ReciprocalNumber {
		private int amtTwo_;
		private int lenLongestRecipCycle_;

		public ReciprocalNumber() {
			amtTwo_ = 0;
			lenLongestRecipCycle_ = 1;
		}

		public int getAmtTwo() {
			return amtTwo_;
		}

		public int getLenLongestRecipCycle() {
			return lenLongestRecipCycle_;
		}

		public void setAmtTwo(int amt) {
			amtTwo_ = amt;
		}

		public void setLenLongestRecipCycle(int len) {
			lenLongestRecipCycle_ = len;
		}
	}*/

	public int findLenCycle(int maxNum) {
		int lenLongestCycle = 1;
		return lenLongestCycle;
	}

	//Set num Twos and longest length
	public int primeFactorize(int num) {
		//Return number of 2's
		ReciprocalCycle recipNumber = new ReciprocalCycle();
		int curr_prime = 3;
		int numTwo = 0;
		int longestCycle = 1;
		int currIndex = num;

		if (!isPrime(num)) {
			while (num % 2 == 0) {
				num /= 2;
				++numTwo;
			}
			while (num > 1) {
				if ((isPrime(curr_prime)) && (num % curr_prime == 0)) {
					int lengthPrimeCycle;
					if ((lengthPrimeCycle = findLenCycle(curr_prime)) > longestCycle) {
						longestCycle = lengthPrimeCycle;
					}
					while (num % curr_prime == 0) {
						num /= curr_prime;
					}
				}
				++curr_prime;
			}
			arrReciprocalLength [currIndex] = longestCycle;
			return numTwo;
		}
		arrReciprocalLength[num] = findLenCycle(num);
		return 0;
	}
	public static void main(String[] args) {
		ReciprocalCycle rLst = new ReciprocalCycle();
	}
}
// Prime number has no decimal shift
// Non prime number:
// Decimal shift: shift by 10^ (max # 2 or 5) prime factors
// Interval gap = length of cycle for max prime number * (set #) ^ (# times in prime factor array - 1)
