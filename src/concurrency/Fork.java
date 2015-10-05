package concurrency;

public class Fork {
	private String name;
	private Philosopher user;

	public Fork(String name) {
		this.name = name;
	}

	public synchronized void use(Philosopher p) {
		try {
			while (true) {
				if (this.user == null) {
					this.user = p;
					//System.out.println(p.getPhilosopherName() + " uses " + this.name);
					return;
				} else {
					//System.out.println(this.name + " is already used by "	+ user.getPhilosopherName());
					//System.out.println(p.getPhilosopherName() + " is waiting for " + this.name);
					//System.out.println(p.getPhilosopherName() + " : " + p.getPhilosopherState());
					wait();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized void release() {
		if (this.user != null) {
			//System.out.println(this.name + " is released by " + user.getPhilosopherName());
			this.user = null;
			notifyAll();
		}
	}

	public String getName() {
		return name;
	}
	
	public String toString(){
		return this.getName();
	}

}
