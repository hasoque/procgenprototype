package pcg;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import pcg.map.MapGen;
import pcg.map.Perlin;
import pcg.weapons.PixelWeapon;
import pcg.weapons.Weapon;

public class MainExe {

	public static void main(String[] args) {
		pcgSwordAndHammerAxe();
	}

	public static void pcgSwordAndHammerAxe() {
		int w = 512;
		int h = 256;
		boolean stop = false;

		while (!stop) {
			int xx = 0;
			Random r = new Random();
			BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB_PRE);
			Graphics2D g2dd = img.createGraphics();
			g2dd.setColor(Color.WHITE);
			g2dd.fillRect(0, 0, w, h);
			while (xx < 128) {
				Graphics2D g2d = (Graphics2D) g2dd.create();
				g2d.translate(30 + (xx % 16) * 30, 10 + (xx / 16) * 30);
				g2d.rotate(Math.toRadians(45));
				g2d.scale(0.05f, 0.05f);
				if(r.nextBoolean()) {
					Weapon.generateHammerAxe(g2d, r.nextLong() + xx);
				}else {
					Weapon.generateSword(g2d, r.nextLong() + xx);
				}
				xx++;
			}
			System.out.println();
			stop = JOptionPane.showConfirmDialog(null, null, "Another", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
					new ImageIcon(img.getScaledInstance(w * 2, h * 2, Image.SCALE_AREA_AVERAGING))) == JOptionPane.CANCEL_OPTION;
		}
	}

	public static void untranslated() {
		int w = 256;
		int h = 256;

		while (true) {
			int xx = 0;
			BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2dd = img.createGraphics();
			g2dd.setColor(Color.WHITE);
			g2dd.fillRect(0, 0, w, h);
			Random r = new Random();
			g2dd.translate(120, 50);
			g2dd.scale(.2f, .2f);
			PixelWeapon.generateAxe(g2dd, r.nextLong() + xx);
			System.out.println();
			JOptionPane.showMessageDialog(null, null, "Another", JOptionPane.YES_NO_OPTION,
					new ImageIcon(img.getScaledInstance(w * 2, h * 2, Image.SCALE_AREA_AVERAGING)));
		}
	}

	public static void pixelBased() {
		int w = 256;
		int h = 256;
		int[] map = new int[w * h];
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		while (true) {
			map = Perlin.generate(w, h);
			int[] pixels = new int[w * h];
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					int i = x + y * w;
					pixels[i] = new Color(map[i], map[i], map[i]).getRGB();
				}
			}
			System.out.println();
			img.setRGB(0, 0, w, h, pixels, 0, w);
			JOptionPane.showMessageDialog(null, null, "Another", JOptionPane.YES_NO_OPTION,
					new ImageIcon(img.getScaledInstance(w * 2, h * 2, Image.SCALE_AREA_AVERAGING)));
			MapGen.nya++;
			// System.out.println(nya);
		}
	}
}