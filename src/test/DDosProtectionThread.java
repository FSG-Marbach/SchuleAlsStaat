/**
 * 
 */
package test;

/**
 * @author Maximilian
 *
 */
public class DDosProtectionThread implements Runnable {

	DDosProtection prot;

	@Override
	public void run() {
		while (true) {
			prot.sink();
			try {
				Thread.sleep((int) prot.time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void setDDosProtection(DDosProtection prot) {
		this.prot = prot;
	}
}
