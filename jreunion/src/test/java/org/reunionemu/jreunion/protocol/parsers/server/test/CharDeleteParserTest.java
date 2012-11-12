package org.reunionemu.jreunion.protocol.parsers.server.test;

import static org.junit.Assert.*;

import java.util.regex.*;


import org.junit.Test;
import org.reunionemu.jreunion.protocol.Packet;
import org.reunionemu.jreunion.protocol.packets.client.CharDeletePacket;
import org.reunionemu.jreunion.protocol.parsers.server.CharDeleteParser;

public class CharDeleteParserTest {

	@Test
	public void test() {
		CharDeleteParser parser = new CharDeleteParser();
		Pattern pattern = parser.getPattern();

		int slot = 1;
		String charMsg = "char_del " + slot;
		Matcher matcher = pattern.matcher(charMsg);
		assertTrue(matcher.matches());
		assertFalse(pattern.matcher("char_del" + slot).matches());
		assertFalse(pattern.matcher("char_del").matches());

		Packet packet = parser.parse(matcher, charMsg);
		assertNotNull(packet);
		assertTrue(packet instanceof CharDeletePacket);
		assertEquals(slot, ((CharDeletePacket) packet).getSlot());
		assertEquals(charMsg, packet.toString());

	}

}