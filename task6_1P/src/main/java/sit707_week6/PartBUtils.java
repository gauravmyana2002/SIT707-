package sit707_week6;

public class PartBUtils {

	/**
	 * Calculates the sum from 1 to n using a loop with simple statements only.
	 *
	 * @param n upper bound
	 * @return sum from 1 to n
	 */
	public static int sumUpTo(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("n must be non-negative");
		}

		int total = 0;
		for (int i = 1; i <= n; i++) {
			total += i;
		}

		return total;
	}

	/**
	 * Counts how many even numbers appear from 1 to n using a loop that contains
	 * a conditional statement in the loop body.
	 *
	 * @param n upper bound
	 * @return number of even values from 1 to n
	 */
	public static int countEvenNumbersUpTo(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("n must be non-negative");
		}

		int evenCount = 0;
		for (int i = 1; i <= n; i++) {
			if (i % 2 == 0) {
				evenCount++;
			}
		}

		return evenCount;
	}
}
