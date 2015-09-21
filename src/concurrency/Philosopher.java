package concurrency;

import java.util.Random;

public class Philosopher extends Thread{
	private String name;
	private Fork rightFork;
	private Fork leftFork;
	private concurrency.State state;
	
	public Philosopher(String name){
		this.name = name;
	}

	public String getPhilosopherName() {
		return name;
	}	
	
	/**
	 * use the fork at the left of the philosopher. If the fork is not available, it waits until it is freed
	 */
	public void useLeftFork(){
		System.out.println(this.name + " is trying to take the "+this.leftFork.getName()+ " on his left");
		this.leftFork.use(this);		
	}
	
	/**
	 * use the fork at the right of the philosopher. If the fork is not available, it waits until it is freed
	 */
	public void useRightFork(){
		System.out.println(this.name + " is trying to take the "+this.rightFork.getName()+ " on his right");
		this.rightFork.use(this);
	}
	
	/**
	 * release the fork at his left
	 */
	public void releaseLeftFork(){
		this.leftFork.release();
	}
	
	/**
	 * release the fork at his left
	 */
	public void releaseRightFork(){
		this.rightFork.release();
	}
	
	/**
	 * changes the state of this philosopher to thinking
	 */
	public void think(){
		int timeThinking = new Random().nextInt(1000);
		System.out.println(this.name + " is thinking");
		this.state = concurrency.State.THINKING;
		try {
			Thread.sleep(timeThinking);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * changes the state of this philosopher to eating
	 */
	public void eat(){
		int timeEating = new Random().nextInt(5000);
		System.out.println(this.name + " is eating");
		this.state = concurrency.State.EATING;
		try {
			Thread.sleep(timeEating);
			System.out.println(this.name + " has finished eating");
			this.releaseLeftFork();
			this.releaseRightFork();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * changes the state of this philosopher to starving 
	 */
	public void starve(){
		System.out.println(this.name + " is starving");
		this.state = concurrency.State.STARVING;
		this.useLeftFork();
		this.useRightFork();
	}

	public Fork getRightFork() {
		return rightFork;
	}

	public void setRightFork(Fork rightFork) {
		this.rightFork = rightFork;
	}

	public Fork getLeftFork() {
		return leftFork;
	}

	public void setLeftFork(Fork leftFork) {
		this.leftFork = leftFork;
	}
	
	public void run(){
		while(true){
			this.think();
			this.starve();
			this.eat();
		}
	}

	public concurrency.State getPhilosopherState() {
		return state;
	}
	
	public String toString(){
		return this.getPhilosopherName();
	}
}
