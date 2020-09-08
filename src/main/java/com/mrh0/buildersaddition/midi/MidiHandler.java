package com.mrh0.buildersaddition.midi;

import javax.sound.midi.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.net.URL;
public class MidiHandler {
	public IMidiEvent midiEvent;
	private ArrayList<MidiDevice> devices; 
	public MidiHandler(IMidiEvent midiEvent) {
		this.midiEvent = midiEvent;
		devices = new ArrayList<MidiDevice>();
		MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
		for (int i = 0; i < infos.length; i++) {
			try {
				MidiDevice device;
				device = MidiSystem.getMidiDevice(infos[i]);
				System.out.println(infos[i]);
				List<Transmitter> transmitters = device.getTransmitters();
				for(int j = 0; j<transmitters.size();j++) {
					transmitters.get(j).setReceiver(
					new MidiInputReceiver(device.getDeviceInfo().toString()));
				}
				
				Transmitter trans = device.getTransmitter();
				trans.setReceiver(new MidiInputReceiver(device.getDeviceInfo().toString()));
				device.open();
				devices.add(device);
				System.out.println(device.getDeviceInfo());
			} 
			catch (MidiUnavailableException e) {}
		}
	}
	
	public boolean hasDevice() {
		return devices.size() > 0;
	}
	
	/*public MidiHandler(IMidiEvent midiEvent, URL url) {
		this.midiEvent = midiEvent;
		Sequence sequence;
		try {
			sequence = MidiSystem.getSequence(url);
	        long start = System.nanoTime();
	        int trackNumber = 0;
	        boolean gotNote = false;
	        for (Track track :  sequence.getTracks()) {
	            trackNumber++;
	            System.out.println("Track " + trackNumber + ": size = " + track.size());
	            System.out.println();
	            for (int i=0; i < track.size(); i++) { 
	                MidiEvent event = track.get(i);
	                System.out.print("@" + event.getTick() + " ");
	                //while(System.nanoTime() - start < event.getTick() * 100000 && gotNote) {}
	                MidiMessage message = event.getMessage();
	                if (message instanceof ShortMessage) {
	                    ShortMessage sm = (ShortMessage) message;
	                    System.out.print("Channel: " + sm.getChannel() + " ");
	                    if (sm.getCommand() == NOTE_ON) {
	                    	gotNote = true;
	                        int key = sm.getData1();
	                        int octave = (key / 12)-1;
	                        int note = key % 12;
	                        String noteName = NOTE_NAMES[note];
	                        int velocity = sm.getData2();
	                        int mcnote = playNote(noteName, octave);
	                        if(midiEvent != null)
	                        	midiEvent.minecraftNote(mcnote,  velocity > 0);
	                        System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
	                    } else if (sm.getCommand() == NOTE_OFF) {
	                        int key = sm.getData1();
	                        int octave = (key / 12)-1;
	                        int note = key % 12;
	                        String noteName = NOTE_NAMES[note];
	                        int velocity = sm.getData2();
	                        System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
	                    } else {
	                        System.out.println("Command:" + sm.getCommand());
	                    }
	                } else {
	                    System.out.println("Other message: " + message.getClass());
	                }
	            }
	
	            System.out.println();
	        }
		} catch (InvalidMidiDataException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public void close() {
		for(MidiDevice d : devices)
			d.close();
	}
	
	public static final int NOTE_ON = 0x90;
    public static final int NOTE_OFF = 0x80;
    public static final String[] NOTE_NAMES = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
	public class MidiInputReceiver implements Receiver {
		public String name;
		public MidiInputReceiver(String name) {
			this.name = name;
		}
		public void send(MidiMessage msg, long timeStamp) {
			if (msg instanceof ShortMessage) {
                ShortMessage sm = (ShortMessage) msg;
                //System.out.print("Channel: " + sm.getChannel() + " ");
                if (sm.getCommand() == NOTE_ON) {
                	
                    int key = sm.getData1();
                    int octave = (key / 12)-1;
                    int note = key % 12;
                    //int d2 = sm.getData2();
                    //System.out.println("d2: " + d2);
                    
                    String noteName = NOTE_NAMES[note];
                    int velocity = sm.getData2();
                    int mcnote = playNote(noteName, octave);
                    if(midiEvent != null)
                    	midiEvent.minecraftNote(mcnote,  velocity > 0);
                    //System.out.println("Played: " + mcnote); // playNote(noteName, octave)
                    //System.out.println("Note on, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                } else if (sm.getCommand() == NOTE_OFF) {
                    int key = sm.getData1();
                    int octave = (key / 12)-1;
                    int note = key % 12;
                    String noteName = NOTE_NAMES[note];
                    int velocity = sm.getData2();
                    //System.out.println("Note off, " + noteName + octave + " key=" + key + " velocity: " + velocity);
                } else {
                    //System.out.println("Command:" + sm.getCommand());
                }
            } else {
                System.out.println("Midi Controller: " + msg.getClass());
            }
			
		}
		public void close() {}
	}
	
	public int playNote(String note, int octave) {
		int r = -1;
		
		/*if(octave == 0) {
			switch(note) {
				case "A":
					r = -35; break;
				case "A#":
					r = -34; break;
				case "B":
					r = -33; break;
			}
		}*/
		
		if(octave == 1) {
			switch(note) {
				/*case "C":
					r = -32; break;
				case "C#":
					r = -31; break;
				case "D":
					r = -30; break;
				case "D#":
					r = -29; break;
				case "E":
					r = -28; break;
				case "F":
					r = -27; break;*/
				case "F#":
					r = -26; break;
				case "G":
					r = -25; break;
				case "G#":
					r = -24; break;
				case "A":
					r = -23; break;
				case "A#":
					r = -22; break;
				case "B":
					r = -21; break;
			}
		}
		
		if(octave == 2) {
			switch(note) {
				case "C":
					r = -20; break;
				case "C#":
					r = -19; break;
				case "D":
					r = -18; break;
				case "D#":
					r = -15; break;
				case "E":
					r = -14; break;
				case "F":
					r = -13; break;
				case "F#":
					r = -12; break;
				case "G":
					r = -11; break;
				case "G#":
					r = -10; break;
				case "A":
					r = -9; break;
				case "A#":
					r = -8; break;
				case "B":
					r = -7; break;
			}
		}
		
		
		if(octave == 3) {
			switch(note) {
				case "C":
					r = -6; break;
				case "C#":
					r = -5; break;
				case "D":
					r = -4; break;
				case "D#":
					r = -3; break;
				case "E":
					r = -2; break;
				case "F":
					r = -1; break;
			
			
				case "F#":
					r = 0; break;
				case "G":
					r = 1; break;
				case "G#":
					r = 2; break;
				case "A":
					r = 3; break;
				case "A#":
					r = 4; break;
				case "B":
					r = 5; break;
			}
		}
		
		if(octave == 4) {
			switch(note) {
				case "C":
					r = 6; break;
				case "C#":
					r = 7; break;
				case "D":
					r = 8; break;
				case "D#":
					r = 9; break;
				case "E":
					r = 10; break;
				case "F":
					r = 11; break;
				case "F#":
					r = 12; break;
				case "G":
					r = 13; break;
				case "G#":
					r = 14; break;
				case "A":
					r = 15; break;
				case "A#":
					r = 16; break;
				case "B":
					r = 17; break;
			}
		}
		
		if(octave == 5) {
			switch(note) {
				case "C":
					r = 18; break;
				case "C#":
					r = 19; break;
				case "D":
					r = 20; break;
				case "D#":
					r = 21; break;
				case "E":
					r = 22; break;
				case "F":
					r = 23; break;
				case "F#":
					r = 24; break;
				
				case "G":
					r = 25; break;
				case "G#":
					r = 26; break;
				case "A":
					r = 27; break;
				case "A#":
					r = 28; break;
				case "B":
					r = 29; break;
			}
		}
		
		if(octave == 6) {
			switch(note) {
				case "C":
					r = 42; break;
				case "C#":
					r = 43; break;
				case "D":
					r = 44; break;
				case "D#":
					r = 45; break;
				case "E":
					r = 46; break;
				case "F":
					r = 47; break;
				case "F#":
					r = 48; break;
				case "G":
					r = 49; break;
			}
		}
		
		/*if(octave == 6) {
			switch(note) {
				case "C":
					r = 30; break;
				case "C#":
					r = 31; break;
				case "D":
					r = 32; break;
				case "D#":
					r = 33; break;
				case "E":
					r = 34; break;
				case "F":
					r = 35; break;
				case "F#":
					r = 36; break;
				case "G":
					r = 37; break;
				case "G#":
					r = 38; break;
				case "A":
					r = 39; break;
				case "A#":
					r = 40; break;
				case "B":
					r = 41; break;
			}
		}*/
		
		

		return r;
	}
}