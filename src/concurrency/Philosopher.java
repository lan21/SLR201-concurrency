package concurrency;

import java.io.PrintStream;
import java.util.Random;

public class Philosopher extends Thread{
	private String name;
	private Fork rightFork;
	private Fork leftFork;
	private concurrency.State state;
	private int nbOfEating;
	private PrintStream out;
	
	public Philosopher(String name){
		this.name = name;
		this.nbOfEating = 0;
		this.out = System.out;
	}
	
	public void setOutputStream(PrintStream out){
		this.out = out;
	}
	
	public String getPhilosopherName() {
		return name;
	}	
	
	/**
	 * use the fork at the left of the philosopher. If the fork is not available, it waits until it is freed
	 */
	public void useLeftFork(){
		//this.out.println(this.name + " is trying to take the "+this.leftFork.getName()+ " on his left");
		this.leftFork.use(this);		
	}
	
	/**
	 * use the fork at the right of the philosopher. If the fork is not available, it waits until it is freed
	 */
	public void useRightFork(){
		//this.out.println(this.name + " is trying to take the "+this.rightFork.getName()+ " on his right");
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
		this.state = concurrency.State.THINKING;
		this.printState();
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
		this.state = concurrency.State.EATING;
		this.nbOfEating++;
		this.printState();
		try {
			Thread.sleep(timeEating);
			//this.out.println(this.name + " has finished eating");
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
		this.state = concurrency.State.STARVING;
		this.printState();
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
	
	@Override
	public void run(){
		long startTime = System.currentTimeMillis();
		while(System.currentTimeMillis()-startTime<10000L){
			this.think();
			this.starve();
			this.eat();
		}
		this.printNbOfEating();
	}

	public concurrency.State getPhilosopherState() {
		return state;
	}
	
	public void printState(){
		this.out.println(this.name + ":" + this.state);
	}
	
	public String toString(){
		return this.getPhilosopherName();
	}
	
	public void printNbOfEating(){
		this.out.println(this.name + " number of eating : " + this.nbOfEating);
	}
}
