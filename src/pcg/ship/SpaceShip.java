package pcg.ship;

import java.util.ArrayList;
import java.util.Random;

/**
 * s
 * 
 * @author Henrix Mart
 *
 */
public class SpaceShip {
	long seed;

	public SpaceShip(long seed) {
		this.seed = seed;
	}

	public ArrayList<Part> createShip() {
		ArrayList<Part> parts = new ArrayList<>();
		Random r = new Random(seed);
		for(int x = 0; x < 5; x++) {
			parts.add(new Hull(r.nextLong()));
		}

		return parts;
	}

}
