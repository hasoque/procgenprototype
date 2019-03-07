package pcg.weapons;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Random;

public class PixelWeapon {

	public static void generateAxe(Graphics2D g, long seed) {
		
		
		
	}

	public static Area triangle(int x, int y, int width, int height, float rotate) {
		Area area = new Area(new Polygon(new int[] { 0, -1, 1 }, new int[] { -1, 1, 1 }, 3));
		AffineTransform p = AffineTransform.getTranslateInstance(x, y);
		p.scale(width, height);
		p.rotate(Math.toRadians(rotate));
		area.transform(p);
		return area;
	}

	public static Area diamond(int x, int y, int width, int height, float rotate) {
		Area area = new Area(new Polygon(new int[] { 0, -1, 0, 1 }, new int[] { -1, 0, 1, 0 }, 4));
		AffineTransform p = AffineTransform.getTranslateInstance(x, y);
		p.scale(width, height);
		p.rotate(Math.toRadians(rotate));
		return area.createTransformedArea(p);
	}

	public static Area rectangle(int x, int y, int wr, int hr, float rotate) {
		return new Area(new Rectangle(x - wr, y - hr, wr * 2, hr * 2))
				.createTransformedArea(AffineTransform.getRotateInstance(Math.toRadians(rotate), 0, 0));
	}

	public static Area roundrectangle(int x, int y, int wr, int hr, float arcw, float arch, float rotate) {
		return new Area(new RoundRectangle2D.Float(x - wr, y - hr, wr * 2, hr * 2, arcw, arch))
				.createTransformedArea(AffineTransform.getRotateInstance(Math.toRadians(rotate), 0, 0));
	}

	public static Area trapezoid(float x, float y, int topleftradius, int toprightradius, int bottomleftradius,
			int bottomrightradius, int height, float rotate) {
		Area area = new Area(
				new Polygon(new int[] { -topleftradius, toprightradius, bottomrightradius, -bottomleftradius },
						new int[] { -height, -height, height, height }, 4));
		AffineTransform p = AffineTransform.getTranslateInstance(x, y);
		p.rotate(Math.toRadians(rotate));
		return area.createTransformedArea(p);
	}

	public static Area shearsquare(float x, float y, int topx, int topradius, int bottomx, int bottomradius, int height,
			float rotate) {
		Area area = new Area(new Polygon(
				new int[] { topx - topradius, topx + topradius, bottomx + bottomradius, -bottomx - bottomradius },
				new int[] { -height, -height, height, height }, 4));
		AffineTransform p = AffineTransform.getTranslateInstance(x, y);
		p.rotate(Math.toRadians(rotate));
		return area.createTransformedArea(p);
	}

	public static Area righttriangle(int x, int y, int width, int height, float rotate) {
		Area area = new Area(new Polygon(new int[] { 1, -1, 1 }, new int[] { -1, 1, 1 }, 3));
		AffineTransform p = AffineTransform.getTranslateInstance(x, y);
		p.scale(width, height);
		p.rotate(Math.toRadians(rotate));
		area.transform(p);
		return area;
	}

	public static Area oval(int x, int y, int wr, int hr, float rotate) {
		Area result = new Area(new Ellipse2D.Float(x - wr, y - hr, wr * 2, hr * 2));
		return result.createTransformedArea(AffineTransform.getRotateInstance(Math.toRadians(rotate), 0, 0));
	}

	public static Area arcpie(int x, int y, int wr, int hr, float start, float end) {
		Area result = new Area(new Arc2D.Float(x - wr, y - hr, wr * 2, hr * 2, start, end, Arc2D.PIE));
		return result;
	}
}
