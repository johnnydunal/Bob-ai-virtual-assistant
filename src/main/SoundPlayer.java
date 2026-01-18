package main;

import java.io.*;
import javax.sound.sampled.*;

public class SoundPlayer {

	public static void playSound(String soundFileName) {
		try {
			
			// Load sound as resource stream from the JAR
			InputStream audioSrc = SoundPlayer.class.getResourceAsStream("/Resources/" + soundFileName);
			if (audioSrc == null) {
				return;
			}
            
			// Add buffer for mark/reset support
			InputStream bufferedIn = new java.io.BufferedInputStream(audioSrc);
			
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
