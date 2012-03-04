import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.apache.commons.lang3.RandomStringUtils;
import org.xerial.snappy.Snappy;

public class ZipAndSnappyPerformance {
	final static int LOOP_COUNT = 10;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final String str = RandomStringUtils.randomAlphanumeric(1000);
		// Snappy圧縮
		compressSnappy(str);
		// Zip圧縮
		compressZip(str);
	}

	/**
	 * @param str
	 */
	private static void compressZip(final String str) {
		final long zipStart = System.currentTimeMillis();
		final Inflater inflater = new Inflater();
		final Deflater deflater = new Deflater();
		for (int i = 0; i < LOOP_COUNT; i++) {
			deflater.setInput(str.getBytes());
			deflater.finish();
			final byte[] output = new byte[150];
			final int compLength = deflater.deflate(output);
			deflater.reset();
			System.out.println(compLength);

			// inflater.setInput(output, 0, compLength);
			// final byte[] result = new byte[150];
			// final int resultLength = inflater.inflate(result);
			// inflater.reset();
		}
	}

	/**
	 * @param str
	 */
	private static void compressSnappy(final String str) {
		try {
			final long snappyStart = System.currentTimeMillis();
			for (int i = 0; i < LOOP_COUNT; i++) {
				byte[] snappyByte = Snappy.compress(str);
				// new String(Snappy.uncompress(snappyByte));
				System.out.println(snappyByte.length);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
