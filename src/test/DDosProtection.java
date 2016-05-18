/**
 * 
 */
package test;

/**
 * @author Maximilian
 *
 */
public class DDosProtection {

	public int connections = 0;
	public double max = 0;
	public double time = 0;
	DDosProtectionThread thread;

	public void connect() {
		try {
			Thread.sleep((int) (connections * (time / (double) max)));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connections++;

	}

	public DDosProtection(int startSpeed, int endSpeed) {
		this.max = (double) startSpeed / 180d;
		this.time = 1000d / ((double) endSpeed / 60d);
		thread = new DDosProtectionThread();
		thread.setDDosProtection(this);
		Thread t = new Thread(thread);
		t.start();

	}

	void sink() {
		connections--;
		if (connections < 0)
			connections = 0;
	}

}
