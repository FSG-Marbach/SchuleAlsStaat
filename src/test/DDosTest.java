/**
 * 
 */
package test;

/**
 * @author Maximilian
 *
 */
public class DDosTest {

	/**
	 * 
	 */
	public DDosTest() {
		DDosProtection prot = new DDosProtection(600, 300);

		while (true) {
			long start = System.currentTimeMillis();
			prot.connect();
			System.out.print("Connections per second: ");
			System.out
					.println(1000d / ((double) System.currentTimeMillis() - (double) start));
			// System.out.println(prot.connections);
		}

	}

	public static void main(String[] args) {
		new DDosTest();
	}

}
