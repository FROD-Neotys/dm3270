# dm3270
Yet another TN3270 Terminal Emulator. Not usable just yet.
#### Modes
There are four ways in which this software can currently be used - Spy, Replay, Mainframe and Terminal. These are described below.
![Initial screen](main1.png =200x200?raw=true "main")
#### Spy Mode
Waits for a terminal emulator (not this one) to connect to the port specified, and then it completes the connection to the actual mainframe at the URL:port specified. The mainframe session can then be used as normal, however all communication buffers are recorded and can be saved in a session file to replay later.
#### Replay Mode
Reruns the session that was saved in the specifed session file. Individual buffers can be selected and examined.
#### Mainframe Mode
Waits for a terminal emulator (not this one) to connect to the port specifed. This program is now masquerading as a mainframe and the mainframe window can be used to send the terminal various 3270 commands. The session can also be saved for later replaying.
#### Terminal Mode
Connects this terminal to the pretend mainframe described above.
#### Screenshots
![Main screen](console.png?raw=true "dm3270")