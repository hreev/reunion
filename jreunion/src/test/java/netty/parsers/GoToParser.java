package netty.parsers;

import java.util.regex.*;

import netty.Packet;
import netty.packets.GoToPacket;
import netty.parsers.PacketParser.Server;

import org.slf4j.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
@Server
public class GoToParser implements PacketParser {

	private static final Logger logger = LoggerFactory.getLogger(GoToParser.class);
	
	static final Pattern regex = Pattern.compile("^goto (\\d+) (\\d+) (\\d+) (\\d+(?:\\.\\d+)?)$"); 
	
	@Override
	public Pattern getPattern() {
		return regex;
	}

	@Override
	public Packet parse(Matcher match, String input) {
		GoToPacket packet = new GoToPacket();
		int n = 0;
		packet.setX(Integer.parseInt(match.group(++n)));

		packet.setY(Integer.parseInt(match.group(++n)));

		packet.setZ(Integer.parseInt(match.group(++n)));

		packet.setRotation(Double.parseDouble(match.group(++n)));
		
		return packet;
	}
	

}
