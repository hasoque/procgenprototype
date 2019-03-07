package pcg.ship;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.Random;

public class Hull extends Part {

	public Hull(long seed) {
		super(seed);
	}

	@Override
	public ArrayList<Shape> getShapes() {
		ArrayList<Shape> shapes = new ArrayList<>();
		Random r = new Random(seed);
		Shape shape1 = null;
		if (r.nextInt(2) == 1) {
			int[] xx = new int[] { r.nextInt(30) - 10, r.nextInt(30) + 5, r.nextInt(30) - 10 };
			int[] yy = new int[] { r.nextInt(30) + 10, r.nextInt(30) - 10, r.nextInt(30) + 10 };
			shape1 = new Polygon(xx, yy, 3);
		} else {
			Point p = new Point(r.nextInt(50), r.nextInt(50));
			Dimension xy = new Dimension(r.nextInt(20) + 10, r.nextInt(20) + 10);
			while (xy.getHeight() * xy.getWidth() > 350 && xy.getHeight() * xy.getWidth() < 100) {
				xy = new Dimension(r.nextInt(20) + 10, r.nextInt(20) + 10);
			}
			shape1 = new Rectangle(p, xy);
		}

		shapes.add(shape1);
		return shapes;
	}

}
