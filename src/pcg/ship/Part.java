package pcg.ship;

import java.awt.Shape;
import java.util.ArrayList;

public abstract class Part {
	long seed = 0;

	public Part(long seed) {
		this.seed = seed;

	}

	public abstract ArrayList<Shape> getShapes();
}
