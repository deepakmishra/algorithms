import java.util.List;

public class Inheritance {

	public static void main(String[] args) {
		Animal hen = new Ovaiparous();
		Animal eagle = new Bird();
		Animal.hunt(eagle, hen, 10);
	}
}

abstract class Account {
	protected int balance = 0;
	protected List<Account> childrenAccounts;

	abstract void debit(int amount);

	abstract void credit(int amount);

	public int getBalance() {
		int totalBalance = getWithdrawableBalance();
		for (Account child : childrenAccounts) {
			totalBalance += child.getBalance();
		}
		return totalBalance;
	}

	public int getWithdrawableBalance() {
		return balance;
	}
}

class Bank {
	void transferMoney(Account account1, Account account2, int amount) {
		account1.debit(amount);
		account2.credit(amount);
	}
}

abstract class Animal {
	int legs = 4;
	int ears = 2;
	int eyes = 2;

	public abstract void move(int distance);

	public void eat(int quantity, boolean nonVeg) {

	}

	public static void hunt(Animal predator, Animal prey, int distance) {
		prey.move(distance / 2);
		predator.move(distance * 3 / 2);

		System.out.println("kill the prey");
	}
}

class Terrestial extends Animal { // IS-A
	int tails = 1; // HAS-A

	public void move(int distance) {
		System.out.println("moved " + distance + " on foot wiggling tail: count " + tails);
	}
}

class Ovaiparous extends Terrestial { // IS-A

	public void move(int distance) {
		System.out.println("after laying eggs moved " + distance + " on foot wiggling tail: count " + tails);
	}
}

class Bird extends Animal { // IS-A
	int wings = 2;

	public void move(int distance) {
		System.out.println("moved " + distance + " by wings: count " + wings);
	}
}
