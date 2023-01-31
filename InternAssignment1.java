import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author for the code, new Games 24x7 interns batch of 2023 - Radhika, Shyam,
 *         Kedarnath and Royston guided by Deepak Mishra
 * @author for the documentation and refactoring, Deepak Mishra
 * 
 * @apiNote Assignment 1
 *          <p>
 *          These are the collated solutions of the java coding assignments that
 *          were given to new interns at the end of first week of training.
 *          Description of the problems are in the docs of respective function.
 *          Signatures of the solutions were fixed as below.
 *          </p>
 * @apiNote List<Integer> primeNumbers (int n)
 * @apiNote int seaView (int [] floors)
 * @apiNote boolean hasSum (int [] arr, int k )
 * 
 */
public class InternAssignment1 {

	/**
	 * Problem: Given an array of integers, we have to find whether there are any
	 * two numbers which sum upto to a specific number. Expected time complexity
	 * O(n)
	 * 
	 * @implNote We will use a set to solve this problem and find the solution in
	 *           only one pass. <br/>
	 *           While iterating on the array arr, for every number num, we will
	 *           check whether the set contains target - num. If yes, then return
	 *           true, else we will add num in the set.
	 * 
	 * @param arr    the array of integer in which we have to find whether there are
	 *               any two numbers which sum upto to a specific number
	 * @param target the specific number to which we have to check whether any two
	 *               numbers in the array sum upto
	 * 
	 * @return true, if any two numbers in arr sum upto target, else false
	 */
	public static boolean hasSum(int[] arr, int target) {
		Set<Integer> set = new HashSet<>();
		for (int num : arr) {
			if (set.contains(target - num)) {
				return true;
			}
			set.add(num);
		}
		return false;
	}

	/**
	 * This is a utility function to check whether a number is prime or not
	 * 
	 * <p>
	 * This is not a optimal way to check prime or not, but this approach was chosen
	 * to make trainees use List collection.
	 * </p>
	 * 
	 * @implNote We take the number and previously existing primes in a list as
	 *           inputs. We check whether input is divisible only by all existing
	 *           primes less than square root of the number.
	 * 
	 * @implNote If number to check for prime is 50 and existingPrimes are <br/>
	 *           [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47] <br/>
	 *           we will check for divisibility of 50 with only 2, 3, 5 and 7
	 *           because square root of 50 is 7.
	 * 
	 * @param number         the number to check whether it is prime or not
	 * @param existingPrimes list of all existing primes less than the number
	 * 
	 * @return true, if the number is prime, else false
	 * 
	 */
	private static boolean isPrime(int number, List<Integer> existingPrimes) {
		int sqrt = (int) Math.sqrt(number);
		for (int prime : existingPrimes) {
			if (prime > sqrt)
				return true;
			if (number % prime == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Problem: Given a number n, return list of first n prime numbers
	 * 
	 * @implNote we check all numbers from 2 for prime using
	 *           {@link #isPrime(int, List)} and if prime then add in the list of
	 *           primeNumbers and return if primeNumbers size = n
	 * 
	 * @param n which represents count of first n prime numbers to return
	 * 
	 * @return the list of first n prime numbers
	 */
	public static List<Integer> primeNumbers(int n) {
		List<Integer> primeNumbers = new ArrayList<>();
		for (int input = 2; primeNumbers.size() < n; input++) {
			if (isPrime(input, primeNumbers)) {
				primeNumbers.add(input);
			}
		}
		return primeNumbers;
	}

	/**
	 * Problem: Given an array of integers floors which represents the height of
	 * buildings in order and sea is ahead of the array towards infinity. We have to
	 * count how many building will have a sea view from any floor.
	 * 
	 * @implNote we iterate the array reverse i.e. check from towards the sea. For
	 *           every building, we have to check if all other buildings towards the
	 *           sea from that building are shorter than current. If yes, then we
	 *           can count it, else no. <br/>
	 *           While traversing reverse, we keep maintaining current maximum
	 *           height of building and compare the current height to current
	 *           maximum.
	 * 
	 * @param floors array of number of floors of all building
	 * @return count of number of buildings with sea view
	 */
	public static int seaView(int[] floors) {
		if (floors == null)
			return 0;
		int count = 0;
		int maxHeight = 0;
		for (int i = floors.length - 1; i >= 0; i--) {
			if (floors[i] > maxHeight) {
				count++;
				maxHeight = floors[i];
			}
		}
		return count;
	}

	public static void main(String[] args) {
		int[] arr = { 50, 10, 20, 30, 40, 40, 20 };

		System.out.println("hasSum " + hasSum(arr, 90));
		System.out.println("primeNumbers " + primeNumbers(15));
		System.out.println("seaView " + seaView(arr));
	}

}