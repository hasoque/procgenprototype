package pcg.weapons;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;

public class Weapon {
	public static void generateSword(Graphics2D g, long seed) {
		Random r = new Random(seed);
		Graphics2D g2d = (Graphics2D) g.create();
		int swordwrad = r.nextInt(50) + 40;
		int sharpheight = r.nextInt(60) + 100;
		int swordh = r.nextInt(100) + 580;
		int bulkyness = (int) (Math.log(r.nextInt(9999) + 1) * (r.nextInt(3) + 2)) - 1;
		boolean onesided = r.nextInt(3) == 1 && sharpheight > 100;
		int supportrad = swordwrad + 50;

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		Point point = new Point(onesided ? swordwrad : -r.nextInt(3) - 1, 0);// middle top point
		Point tls = new Point(-swordwrad + 1 - bulkyness, sharpheight);// top blade left
		Point trs = new Point(swordwrad + bulkyness, sharpheight);// top blade right
		Point bls = new Point(-swordwrad + 1, swordh);// buttom blade left
		Point brs = new Point(swordwrad, swordh);// buttom blade right
		Area blade = new Area(new Polygon(new int[] { point.x, trs.x, brs.x, bls.x, tls.x },
				new int[] { point.y, trs.y, brs.y, bls.y, tls.y }, 5));
		Color bladecolor = new Color(r.nextInt()).darker();
		Color handle1 = new Color(r.nextInt(150) + 50, r.nextInt(150) + 50, r.nextInt(150) + 50);

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		int style = r.nextInt(3);
		if (style == 1 && !onesided) {
			int count = 3 + r.nextInt(3);
			for (int i = 0; i < count; i++) {
				if (r.nextInt(5) < 3) {
					Polygon tri = new Polygon();
					int rad = r.nextInt(60) + swordwrad + 40;
					tri.addPoint(0, -(r.nextInt(rad / 2) + 50));// top
					tri.addPoint(-rad, 50);// left
					tri.addPoint(rad, 50);// right
					tri.translate(0, (int) (swordh * ((i + 1) / (float) (count + 1))));
					blade.add(new Area(tri));
				}
			}
		} else if (style == 2 && !onesided) {
			Polygon tri = new Polygon();
			int rad = r.nextInt(60) + swordwrad + 40;
			tri.addPoint(0, (r.nextInt(rad / 2) + 50));// top
			tri.addPoint(-rad, swordh);// left
			tri.addPoint(rad, swordh);// right
			blade.add(new Area(tri));
		} else if (r.nextBoolean() && onesided) {
			int count = 3 + r.nextInt(2);
			for (int i = 0; i < count; i++) {
				Polygon tri = new Polygon();
				int rad = r.nextInt(60) + swordwrad + 40;
				tri.addPoint(0, (r.nextInt(rad / 2) + 50));// top
				tri.addPoint(0, -50);// left
				tri.addPoint(rad, -50);// right
				tri.translate(0, (int) (swordh * ((i + 1) / (float) (count + 1))));
				blade.add(new Area(tri));
			}
		}
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		int shots = r.nextInt(3);
		if (swordwrad > 70 && shots == 1) {
			blade.subtract(new Area(new Rectangle(-20, 0, 40, swordh < 600 ? swordh / 2 + swordh / 4 : swordh / 2)));
			if (swordh > 620) {
				blade.subtract(new Area(new Rectangle(-23, 380, 46, swordh / 4)));
			}
		} else if (swordwrad > 70 && shots == 2) {
			int count = 3 + r.nextInt(2);
			int left = onesided ? -30 : 0;
			for (int i = 0; i < count; i++) {
				if (i < 4)
					blade.subtract(new Area(
							new Ellipse2D.Float(-30 + left, (int) (swordh * ((i + 1) / (float) (count + 1))), 60, 60)));
			}
		}

		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		g2d.setColor(bladecolor);
		g2d.fill(blade);
		g2d.setColor(bladecolor.darker().darker().darker());
		g2d.draw(blade);

		Area shading = new Area(blade);
		shading.intersect(new Area(new Rectangle(0, -10, swordwrad + 100, swordh)));
		g2d.setColor(new Color(255, 255, 255, 20 + r.nextInt(30)));/// shading right
		g2d.fill(shading);
		shading = new Area(blade);
		shading.intersect(new Area(new Rectangle(50, -10, swordwrad + 100, swordh)));
		g2d.setColor(new Color(255, 255, 255, 20 + r.nextInt(30)));/// shading right
		g2d.fill(shading);
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/// clear
		Area handlearea = new Area(new RoundRectangle2D.Float(-supportrad, swordh - 15, supportrad * 2, 40, 50, 50));
		handlearea.add(new Area(new Rectangle(-20, swordh - 20, 40, 200)));
		boolean crest = r.nextBoolean();
		if (crest) {
			Polygon h = new Polygon();
			int height = r.nextInt(40) + 50;
			int rad = r.nextInt(40) + 50;
			h.addPoint(0, -height + swordh);// top
			h.addPoint(-rad, swordh);// left
			h.addPoint(rad, swordh);// right
			handlearea.add(new Area(h));
		}
		// handle

		g2d.setColor(handle1);
		g2d.fill(handlearea);
		g2d.setColor(handle1.darker().darker());
		g2d.draw(handlearea);
		g2d.dispose();
	}

	public static void generateHammerAxe(Graphics2D g, long seed) {
		Random r = new Random(seed);
		Graphics2D g2d = (Graphics2D) g.create();

		int headType = r.nextInt(6);
		boolean onesided = r.nextBoolean();
		int height = 170 + r.nextInt(50);
		int widthrad = 200 + r.nextInt(30);
		int roundness = r.nextInt(150);

		Area head = new Area(new RoundRectangle2D.Float(onesided ? -40 : -widthrad, 0,
				onesided ? widthrad + 40 : widthrad * 2, height, roundness, roundness));
		Area handle = new Area(new Rectangle(-30, 0, 60, 700));
		Color bladecolor = new Color(r.nextInt(150) + 50, r.nextInt(150) + 50, r.nextInt(150) + 50);
		Color handlecolor = new Color(r.nextInt(50) + 150, r.nextInt(50) + 150, r.nextInt(50) + 150);

		if (headType == 0) {// one horn
			Polygon p = new Polygon(new int[] { 0, -60, 60 }, new int[] { -100, 0, 0 }, 3);
			p.translate(0, 0);
			head.add(new Area(p));
		} else if (headType == 1 && !onesided) {// separated
			head.subtract(new Area(new Rectangle(-30, 0, 60, 60)));
			head.subtract(new Area(new Rectangle(-30, height - 60, 60, 60)));
		} else if (headType == 2 && !onesided) {// two horn
			Polygon p = new Polygon(new int[] { 0, -60, 60 }, new int[] { -100, 0, 0 }, 3);
			p.translate(60, 0);
			head.add(new Area(p));
			p.translate(-120, 0);
			head.add(new Area(p));
		} else if (headType == 3 && !onesided) {// two horn seprated
			Polygon p = new Polygon(new int[] { 0, -60, 60 }, new int[] { -100, 0, 0 }, 3);
			p.translate(70, 0);
			head.add(new Area(p));
			p.translate(-140, 0);
			head.add(new Area(p));
			head.subtract(new Area(new Rectangle(-20, 0, 40, 60)));
			head.subtract(new Area(new Rectangle(-20, height - 60, 40, 60)));
		} else if (headType == 4 && !onesided) {// below horn
			Polygon p = new Polygon(new int[] { 0, -60, 60 }, new int[] { 100, 0, 0 }, 3);
			p.translate(70, height);
			head.add(new Area(p));
			p.translate(-140, 0);
			head.add(new Area(p));
			head.subtract(new Area(new Rectangle(-20, 0, 40, 60)));
			head.subtract(new Area(new Rectangle(-20, height - 60, 40, 60)));

			Polygon pp = new Polygon(new int[] { 0, -60, 60 }, new int[] { -100, 0, 0 }, 3);
			pp.translate(70, 0);
			head.add(new Area(pp));
			pp.translate(-140, 0);
			head.add(new Area(pp));
		}
		if (onesided) {
			int backtype = r.nextInt(3);
			int bh = r.nextInt(height / 2) + 60;
			int bw = r.nextInt(height / 2) + 70;
			if (backtype == 0) {// right triangle
				Polygon pp = new Polygon(new int[] { 0, -60 - bw, 0 }, new int[] { bh, 0, 0 }, 3);
				pp.translate(-40, 0);
				head.add(new Area(pp));
			} else if (backtype == 1) {// left triangle
				Polygon pp = new Polygon(new int[] { 0, -60 - bw, 0 }, new int[] { -bh, 0, 0 }, 3);
				pp.translate(-40, bh);
				head.add(new Area(pp));
			} else if (backtype == 2) {// backhorn
				Polygon pp = new Polygon(new int[] { 0, -60 - bw, 0 }, new int[] { -bh, 0, 0 }, 3);
				pp.translate(-40, bh);
				head.add(new Area(pp));
				pp = new Polygon(new int[] { 0, -60 - bw, 0 }, new int[] { bh, 0, 0 }, 3);
				pp.translate(-40, height - bh);
				head.add(new Area(pp));
			}
		}
		boolean bulkedge = r.nextBoolean();
		int bulkiness = r.nextInt(50) + 80;
		int edgeheight = r.nextInt(50) + 150;

		if (bulkedge) {
			if (onesided) {
				Arc2D edge = new Arc2D.Float(widthrad - bulkiness, -edgeheight, bulkiness * 2, height + edgeheight * 2,
						90, -180, Arc2D.PIE);
				Arc2D edge2 = new Arc2D.Float(-bulkiness, -edgeheight, bulkiness * 2, height + edgeheight * 2, -90, 180,
						Arc2D.PIE);
				head.add(new Area(edge));
				head.add(new Area(edge2));
			} else {
				Arc2D edge = new Arc2D.Float(widthrad - bulkiness, -edgeheight, bulkiness * 2, height + edgeheight * 2,
						90, -180, Arc2D.PIE);
				Arc2D edge2 = new Arc2D.Float(-widthrad + bulkiness * 2, -edgeheight, bulkiness * 2,
						height + edgeheight * 2, 90, -180, Arc2D.PIE);
				head.add(new Area(edge));
				head.add(new Area(edge2));
			}
		} else {
			Polygon tri = new Polygon();
			tri.addPoint(-30, -edgeheight / 2);
			tri.addPoint(bulkiness, (-edgeheight) / 2);
			tri.addPoint(bulkiness, (edgeheight + height) / 2);
			tri.addPoint(-30, (edgeheight + height));
			tri.translate(widthrad / 2 + r.nextInt(bulkiness), 0);
			head.add(new Area(tri));
		}

		head.transform(AffineTransform.getTranslateInstance(-widthrad / 8, 50));

		Graphics2D ggg = (Graphics2D) g2d.create();
		ggg.setColor(handlecolor.darker());
		ggg.fill(handle);
		ggg.setColor(handlecolor.darker().darker().darker());
		ggg.draw(handle);
		ggg.dispose();

		ggg = (Graphics2D) g2d.create();
		ggg.setColor(bladecolor);
		ggg.fill(head);
		ggg.setColor(bladecolor.darker().darker());
		ggg.draw(head);

		ggg.setColor(new Color(255, 255, 255, 30));
		Area shading = new Area(head);
		shading.intersect(new Area(new Rectangle(widthrad / 2, -150, widthrad * 2, height * 3)));
		ggg.fill(shading);
		shading = new Area(head);
		shading.intersect(new Area(new Rectangle(widthrad / 4, -150, widthrad * 2, height * 3)));
		ggg.fill(shading);
		ggg.dispose();

		g2d.dispose();
	}

	public static void generateAxe(Graphics2D g, long seed) {
		Random r = new Random(seed);

		int mwr = r.nextInt(50) + 80;
		int mhr = 90;
		int lwr = r.nextInt(40) + 70;
		int lhr = r.nextInt(40) + 100;
		int rwr = r.nextInt(30) + 100;
		int rhr = r.nextInt(50) + 70;
		int bwr = r.nextInt(30) + 100;
		int bhr = r.nextInt(50) + 120;
		int middletype = r.nextInt(6);
		int lefttype = r.nextInt(5);
		int righttype = r.nextInt(5);
		int bladetype = r.nextInt(2);

		Area middle = new Area();
		Area blade = new Area();
		Area left = new Area();
		Area right = new Area();
		Area handle = new Area(rectangle(0, 340, 30, 350, 0));

		if (middletype == 0) {
			boolean two = r.nextBoolean();
			if (two) {
				middle.add(triangle(-mhr / 4, -mhr, mwr / 2, mhr, 0));
				middle.add(triangle(mhr / 4, -mhr, mwr / 2, mhr, 0));
			} else {
				middle.add(triangle(0, -mhr, mwr / 2, mhr, 0));
			}
			middle.add(oval(0, 0, mwr, mhr / 2, 0));
		} else if (middletype == 1)
			middle.add(diamond(0, 0, mwr, mhr, 0));
		else if (middletype == 2)
			middle.add(trapezoid(0, 0, mhr, mhr, mhr / 2, mhr / 2, mwr, 90));
		else if (middletype == 3)
			middle.add(trapezoid(0, 0, mhr, mhr, mhr / 2, mhr / 2, mwr, 270));
		else if (middletype == 4)
			middle.add(oval(0, 0, mwr, mhr, 0));
		else
			middle.add(rectangle(0, 0, mwr, (int) (mhr / 1.5), 0));

		if (lefttype == 0)
			left.add(roundrectangle(0, 0, lwr, lhr, 100, 100, 0));
		else if (lefttype == 1)
			left.add(diamond(0, 0, lwr, lhr, 0));
		else if (lefttype == 2)
			left.add(oval(0, 0, lwr, lhr, 0));
		else if (lefttype == 3)
			left.add(trapezoid(0, 0, (int) (mhr * 1.2), (int) (mhr * 1.2), mhr / 2, mhr / 2, mwr, 90));
		else
			left.add(rectangle(0, 0, lwr, lhr, 0));

		if (righttype == 0)
			right.add(roundrectangle(0, 0, rwr, rhr, 100, 100, 0));
		else if (righttype == 1)
			right.add(diamond(0, 0, rwr, rhr, 0));
		else if (righttype == 2)
			right.add(oval(0, 0, rwr, rhr, 0));
		else
			right.add(rectangle(0, 0, rwr, rhr, 0));

		if (bladetype == 0)
			blade.add(arcpie(0, 0, (int) (bwr * 1.5), (int) (bhr * 1.5), 260, 190));
		else if (bladetype == 1)
			blade.add(trapezoid(bwr / 2, -80, bhr, bhr, bhr, bhr * 2, (int) (bwr / 1.5), 90));
		else if (bladetype == 2)
			blade.add(oval(0, 0, bwr, bhr, 0));
		else
			blade.add(rectangle(0, 0, bwr, bhr, 0));

		middle.transform(AffineTransform.getTranslateInstance(0, 40));
		left.transform(AffineTransform.getTranslateInstance(-mwr - lwr / 2, 40));
		right.transform(AffineTransform.getTranslateInstance(mwr + rwr / 3, 40));
		blade.transform(AffineTransform.getTranslateInstance(mwr + rwr / 4 + bwr / 8, 40 + bhr / 3));
		middle.add(left);
		middle.add(right);
		middle.add(blade);

		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(Color.GRAY);
		g2d.fill(handle);
		g2d.setColor(Color.GRAY.darker().darker());
		g2d.draw(handle);
		g2d.setColor(Color.GRAY);
		g2d.fill(middle);
		g2d.setColor(Color.GRAY.darker().darker());
		g2d.draw(middle);
		g2d.dispose();
	}

	public static void generateAxe2(Graphics2D g, int seed) {
		Random r = new Random(seed);

		int mwr = r.nextInt(50) + 80;
		int mhr = 90;
		int lwr = r.nextInt(40) + 70;
		int lhr = r.nextInt(40) + 100;
		int rwr = r.nextInt(30) + 50;
		int rhr = r.nextInt(50) + 70;
		int bwr = r.nextInt(30) + 100;
		int bhr = r.nextInt(50) + 120;
		int middletype = r.nextInt(6);
		int lefttype = r.nextInt(5);
		int righttype = r.nextInt(5);
		int bladetype = r.nextInt(4);

		Area middle = new Area();
		Area blade = new Area();
		Area left = new Area();
		Area right = new Area();
		Area handle = new Area(rectangle(0, 340, 30, 350, 0));

		if (middletype == 0) {
			boolean two = r.nextBoolean();
			if (two) {
				middle.add(triangle(-mhr / 4, -mhr, mwr / 2, mhr, 0));
				middle.add(triangle(mhr / 4, -mhr, mwr / 2, mhr, 0));
			} else {
				middle.add(triangle(0, -mhr, mwr / 2, mhr, 0));
			}
			middle.add(oval(0, 0, mwr, mhr / 2, 0));
		} else if (middletype == 1)
			middle.add(diamond(0, 0, mwr, mhr, 0));
		else if (middletype == 2)
			middle.add(trapezoid(0, 0, mhr, mhr, mhr / 2, mhr / 2, mwr, 90));
		else if (middletype == 3)
			middle.add(trapezoid(0, 0, mhr, mhr, mhr / 2, mhr / 2, mwr, 270));
		else if (middletype == 4)
			middle.add(oval(0, 0, mwr, mhr, 0));
		else
			middle.add(rectangle(0, 0, mwr, (int) (mhr / 1.5), 0));

		if (lefttype == 0)
			left.add(roundrectangle(0, 0, lwr, lhr, 100, 100, 0));
		else if (lefttype == 1)
			left.add(diamond(0, 0, lwr, lhr, 0));
		else if (lefttype == 2)
			left.add(oval(0, 0, lwr, lhr, 0));
		else if (lefttype == 3)
			left.add(trapezoid(0, 0, (int) (mhr * 1.2), (int) (mhr * 1.2), mhr / 2, mhr / 2, mwr, 90));
		else
			left.add(rectangle(0, 0, lwr, lhr, 0));

		if (righttype == 0)
			right.add(roundrectangle(0, 0, rwr, rhr, 100, 100, 0));
		else if (righttype == 1)
			right.add(diamond(0, 0, rwr, rhr, 0));
		else if (righttype == 2)
			right.add(oval(0, 0, rwr, rhr, 0));
		else
			right.add(rectangle(0, 0, rwr, rhr, 0));

		if (bladetype == 0)
			blade.add(arcpie(0, 0, (int) (bwr * 1.5), (int) (bhr * 1.5), 260, 190));
		else if (bladetype == 1)
			blade.add(trapezoid(bwr / 2, -80, bhr, bhr, bhr, bhr * 2, (int) (bwr / 1.5), 90));
		else
			blade.add(triangle(bwr / 2, 0, (int) (bwr / 1.3), (int) (bhr * 1.7), 90));

		middle.transform(AffineTransform.getTranslateInstance(0, 40));
		left.transform(AffineTransform.getTranslateInstance(-mwr - lwr / 2, 40));
		right.transform(AffineTransform.getTranslateInstance(mwr + rwr / 3, 40));
		blade.transform(AffineTransform.getTranslateInstance(mwr + rwr / 4 + bwr / 8, 40 + bhr / 3));
		middle.add(left);
		middle.add(right);
		middle.add(blade);

		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(Color.GRAY);
		g2d.fill(handle);
		g2d.setColor(Color.GRAY.darker().darker());
		g2d.draw(handle);
		g2d.setColor(Color.GRAY);
		g2d.fill(middle);
		g2d.setColor(Color.GRAY.darker().darker());
		g2d.draw(middle);
		g2d.dispose();
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
