package pcg.map;

import java.util.Random;

public class MapGen {
	public static Random r = new Random();
	public int[] noise;
	public int w, h;

	// public MapGen(int w, int h, int occurances, int value, int blocksize) {
	// this.w = w;
	// this.h = h;
	// noise = new int[w * h];
	// for (int i = 0; i < occurances; i++) {
	// int val = r.nextInt(value);
	// int randomx = r.nextInt(w - 80) + 40;
	// int randomy = r.nextInt(h - 80) + 40;
	// for (int yy = -blocksize / 2; yy < blocksize / 2; yy++) {
	// if (yy + randomy < 0 || yy + randomy > h - 1)
	// continue;
	// for (int xx = -blocksize / 2; xx < blocksize / 2; xx++) {
	// if (xx + randomx < 0 || xx + randomx > w - 1)
	// continue;
	// noise[(xx + randomx) + (yy + randomy) * w] = val;
	// }
	// }
	// }
	// }
	public MapGen(int w, int h, int blocksize) {
		this.w = w;
		this.h = h;
		noise = new int[w * h];
		blocksize /= 2;
		do {
			for (int y = 0; y < h; y += blocksize) {
				for (int x = 0; x < w; x += blocksize) {
					if (r.nextInt(3) == 1)
						continue;
					int val = ((r.nextInt(90) + 10) * 50) - 2470;
					for (int yy = 0; yy < blocksize; yy++) {
						if (yy + y - 32 < 0 || yy + y > h - 1 - 32)
							continue;
						for (int xx = 0; xx < blocksize; xx++) {
							if (xx + x - 32 < 0 || xx + x > w - 1 - 32)
								continue;
							noise[(xx + x) + (yy + y) * w] += val;
						}
					}
				}
			}
			blocksize /= 2;
		} while (blocksize > 2);
	}

	public static int[] createNormalTop(int w, int h) {
		MapGen noise = new MapGen(w, h, 256);
		int[] result = new int[w * h];
		float max = 0;

		noise.noise = blur(noise.noise, w, h, 15);
		for (int i = 0; i < noise.noise.length; i++) {
			if (max < Math.abs(noise.noise[i]))
				max = Math.abs(noise.noise[i]);
		}
		for (int i = 0; i < result.length; i++) {
			result[i] = (int) (Math.abs((noise.noise[i] * 2) / max) * 100);
		}

		int[] truemap = new int[w * h];

		for (int i = 0; i < truemap.length; i++) {
			if (result[i] > 95)
				truemap[i] = 2;
			else if (result[i] > 24)
				truemap[i] = 1;
			else
				truemap[i] = 0;
		}
		for (int j = 0; j < w * h / 5000; j++) {
			int tx = r.nextInt(w - 64) + 32;
			int ty = r.nextInt(w - 64) + 32;
			for (int i = 0; i < 60; i++) {
				int rx = r.nextInt(24) - 12 + tx;
				int ry = r.nextInt(24) - 12 + ty;
				for (int yy = 0; yy < 8; yy++) {
					for (int xx = 0; xx < 8; xx++) {
						if (truemap[xx + rx + (yy + ry) * w] == 1 && result[xx + rx + (yy + ry) * w] < 60) {
							truemap[xx + rx + (yy + ry) * w] = 4;
						}
					}
				}
			}
		}
		for (int i = 0; i < w * h / 240; i++) {
			int rx = r.nextInt(w - 32) + 16;
			int ry = r.nextInt(h - 32) + 16;
			for (int yy = 0; yy < 16; yy++) {
				for (int xx = 0; xx < 16; xx++) {
					if (r.nextInt(3) == 1 && truemap[xx + rx + (yy + ry) * w] == 1) {
						truemap[xx + rx + (yy + ry) * w] = 5;
					}
				}
			}
		}
		return truemap;
	}

	public static int[] createCaveFloor1(int w, int h) {
		MapGen noise = new MapGen(w, h, 16);
		MapGen noise2 = new MapGen(w, h, 16);
		MapGen noise3 = new MapGen(w, h, 8);

		int[] result = new int[w * h];
		for (int i = 0; i < result.length; i++) {
			result[i] = Math.abs(noise.noise[i] - noise2.noise[i] - noise3.noise[i]);
		}

		int[] truemap = new int[w * h];
		result = blur(result, w, h, 10);

		for (int i = 0; i < truemap.length; i++) {
			if ((result[i] > 200 && result[i] < 300) || (result[i] > 400 && result[i] < 500))
				truemap[i] = 15;
			else
				truemap[i] = 2;
		}
		for (int i = 0; i < w * h / 1040; i++) {
			int rx = r.nextInt(w - 32) + 16;
			int ry = r.nextInt(h - 32) + 16;
			for (int j = 0; j < 10; j++) {
				int bx = rx + r.nextInt(10) - 10;
				int by = ry + r.nextInt(10) - 10;
				for (int yy = -4; yy < 4; yy++) {
					for (int xx = -4; xx < 4; xx++) {
						if (r.nextInt(5) == 1 && truemap[xx + bx + (yy + by) * w] == 2) {
							truemap[xx + bx + (yy + by) * w] = 12;
						}
					}
				}
			}
		}
		return truemap;
	}

	private static int[] blur(int[] val, int w, int h, int rad) {
		int[] result = new int[w * h];
		for (int yy = 0; yy < h; yy++) {
			for (int xx = 0; xx < w; xx++) {
				int tilevalue = val[xx + yy * w] / (rad * rad) * 2;
				for (int ty = -rad / 2; ty < rad / 2; ty++) {
					if (ty + yy < 0 || ty + yy > w - 1)
						continue;
					for (int tx = -rad / 2; tx < rad / 2; tx++) {
						if (tx + xx < 0 || tx + xx > h - 1)
							continue;
						result[(tx + xx) + (ty + yy) * w] += tilevalue;
					}
				}
			}
		}
		return result;
	}

	public static int nya = 0;

}
