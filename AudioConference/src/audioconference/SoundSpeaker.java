/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioconference;

import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

/**
 *
 * @author Kornelius-Mg
 */
public class SoundSpeaker extends Thread {
    private InputStream in;
    public SoundSpeaker(InputStream in){
        super();
        this.in = in;
    }
   
    public void lire(){
        AudioInputStream audioIn = null;
        try{
            audioIn = AudioSystem.getAudioInputStream(in);
            
            AudioFormat format = audioIn.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class,format);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
            byte bytes[] = new byte[1024];
            int byteRead = 0;
            while(((byteRead = audioIn.read(bytes,0,bytes.length))!= -1)){
                line.write(bytes, 0, byteRead);
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}
