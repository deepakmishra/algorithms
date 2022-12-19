
public class PrimeNumbers {

	public static void main(String[] args) {

		int[] primeSizes = { 100, 100, 100, 100, 100, 100, 100, 100 };

		for (int primeSize : primeSizes) {
			System.out.println(primeSize);
			Utility.timeFunction(true, "printNPrimes1", () -> printNPrimes1(primeSize));
//			Utility.timeFunction(true, "printNPrimes2", () -> printNPrimes2(primeSize));
			Utility.timeFunction(true, "printNPrimes3", () -> printNPrimes3(primeSize));
//			Utility.timeFunction(true, "printNPrimes4", () -> printNPrimes4(primeSize));
		}
	}

	private static void printNPrimes2(int n) {
		int primeCount = 0;
		int[] primes = new int[n];

		for (int number = 2; primeCount < n; number++) {
			if (isPrime2(number, primes, primeCount)) {
				primes[primeCount] = number;
//				System.out.println(number);
				primeCount++;
			}
		}
	}

	private static boolean isPrime2(int number, int[] primes, int primeCount) {
		for (int i = 0; i < primeCount; i++) {
			int factor = primes[i];
			if (number % factor == 0) {
				return false;
			}
		}
		return true;
	}

	private static void printNPrimes1(int n) {
		int primeCount = 0;
		int[] primes = new int[n];

		for (int number = 2; primeCount < n; number++) {
			if (isPrime1(number, primes, primeCount)) {
				primes[primeCount] = number;
//				System.out.println(number);
				primeCount++;
			}
		}
	}

	private static boolean isPrime1(int number, int[] primes, int primeCount) {
		int end = (int) Math.floor(Math.sqrt(number));
		for (int i = 0; i < primeCount; i++) {
			int factor = primes[i];
			if (factor > end) {
				return true;
			}
			if (number % factor == 0) {
				return false;
			}
		}
		return true;
	}

	private static void printNPrimes3(int n) {
		int count = 0;
		for (int number = 2; count < n; number++) {
			if (isPrime3(number)) {
//				System.out.println(number);
				count++;
			}
		}
	}

	private static boolean isPrime3(int number) {
		int end = (int) Math.floor(Math.sqrt(number));
		for (int factor = 2; factor <= end; factor++) {
			if (number % factor == 0) {
				return false;
			}
		}
		return true;
	}

	private static void printNPrimes4(int n) {
		int count = 0;
		for (int number = 2; count < n; number++) {
			if (isPrime4(number)) {
//				System.out.println(number);
				count++;
			}
		}
	}

	private static boolean isPrime4(int number) {
		for (int factor = 2; factor <= number / 2; factor++) {
			if (number % factor == 0) {
				return false;
			}
		}
		return true;
	}
}
