/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioconference;

import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import java.io.File;

/**
 *
 * @author Kornelius-Mg
 */
public class SoundRecord extends Thread {
    TargetDataLine line;
    public static final String PATH = System.getenv("tmp")+"\\msg_client\\son.wav";
    public SoundRecord(){
        super();
    }
    
    @Override
    
    public void run(){
        demarrer();
    }
    public void demarrer(){
        AudioFormat audioFormat = new AudioFormat(44100,16,2,true,true);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class,audioFormat);
        boolean etat = AudioSystem.isLineSupported(info);
        this.line = null;
        if(!etat){
            System.err.println("Le format specifie n'est pas supporte");
            return;
        }
        try{
            this.line = (TargetDataLine)AudioSystem.getLine(info);
            this.line.open(audioFormat,102400);
            this.line.start();
            AudioFileFormat.Type  targetType = AudioFileFormat.Type.WAVE;
            AudioInputStream flux = new AudioInputStream(line);
            AudioSystem.write(flux, targetType, new File(PATH));
        }catch(LineUnavailableException | IOException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void interrupt(){
        this.line.close();
        System.out.println("Enregistrement interrompu");
        this.stop();
    }
}
