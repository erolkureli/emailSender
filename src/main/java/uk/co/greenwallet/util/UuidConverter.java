package uk.co.greenwallet.util;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UuidConverter {
	public static byte[] getByteArrayFormUuid(UUID uuid) {
		ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
		buffer.putLong(uuid.getMostSignificantBits());
		buffer.putLong(uuid.getLeastSignificantBits());
		return buffer.array();
	}
}
