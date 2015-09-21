package concurrency;

public class Main {
	public static void main(String[] args) {
		Philosopher[] philosophers = new Philosopher[5];
		Fork[] forks = new Fork[5];
		for (int i = 0; i < 5; i++) {
			philosophers[i] = new Philosopher("Philosophe "+(i+1));
			forks[i] = new Fork("Fourchette "+(i+1));
		}
		
		for (int i = 0; i < 5; i++) {
			philosophers[i].setLeftFork(forks[i%5]);
			philosophers[i].setRightFork(forks[(i+4)%5]);
//			System.out.println(philosophers[i].getPhilosopherName()+" LF = "+philosophers[i].getLeftFork().getName());
//			System.out.println(philosophers[i].getPhilosopherName()+" RF = "+philosophers[i].getRightFork().getName());

		}
		
		for (int i = 0; i < 5; i++) {
			philosophers[i].start();
		}
	}
}
