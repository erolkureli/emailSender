package uk.co.greenwallet.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidConverter {
	public static UUID getGuidFromByteArray(byte[] bytes) {
		ByteBuffer bb = ByteBuffer.wrap(bytes);
		long high = bb.getLong();
		long low = bb.getLong();
		UUID uuid = new UUID(high, low);
		return uuid;
	}
}
