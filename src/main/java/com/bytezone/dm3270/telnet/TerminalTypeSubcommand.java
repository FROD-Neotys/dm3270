package com.bytezone.dm3270.telnet;

import com.bytezone.dm3270.display.Screen;
import com.bytezone.dm3270.streams.TelnetState;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;

public class TerminalTypeSubcommand extends TelnetSubcommand {

  private static final byte OPTION_IS = 0;
  private static final byte OPTION_SEND = 1;

  public TerminalTypeSubcommand(byte[] buffer, int offset, int length,
      TelnetState telnetState) {
    super(buffer, offset, length, telnetState);

    if (buffer[3] == OPTION_IS) {
      type = SubcommandType.IS;
      value = new String(buffer, 4, length - 6);
    } else if (buffer[3] == OPTION_SEND) {
      type = SubcommandType.SEND;
      value = "";
    } else {
      throw new InvalidParameterException(
          String.format("Unknown subcommand type: %02X%n", buffer[3]));
    }
  }

  @Override
  public void process(Screen screen) {
    if (type == SubcommandType.SEND) {
      byte[] header = {TelnetCommand.IAC, TelnetCommand.SB, TERMINAL_TYPE, OPTION_IS};
      byte[] terminal = getTerminalString().getBytes(StandardCharsets.US_ASCII);
      byte[] reply = new byte[header.length + terminal.length + 2];

      System.arraycopy(header, 0, reply, 0, header.length);
      System.arraycopy(terminal, 0, reply, header.length, terminal.length);

      reply[reply.length - 2] = TelnetCommand.IAC;
      reply[reply.length - 1] = TelnetCommand.SE;

      telnetState.setTerminal(getTerminalString());

      setReply(new TerminalTypeSubcommand(reply, 0, reply.length, telnetState));
    }
  }

  private String getTerminalString() {
    return telnetState.doDeviceType() + (telnetState.do3270Extended() ? "-E" : "");
  }

  @Override
  public String toString() {
    switch (type) {
      case SEND:
        return type + " TerminalType";
      case IS:
        return type + " TerminalType " + getTerminalString();
      default:
        return "SUB: " + "Unknown";
    }
  }

}
