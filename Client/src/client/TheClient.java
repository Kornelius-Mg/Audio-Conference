package client;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Kornelius-Mg
 */
public class TheClient {
    private Socket client;
    public RxThreadClient rx;
    public ExThreadClient ex;
    public TheClient(){
        try {
            this.client = new Socket("127.0.0.1",12500);
            this.ex = new ExThreadClient(client);
            this.rx = new RxThreadClient(client);
            this.rx.start();
            this.ex.start();
            System.out.println("Client connecte au serveur");
        } catch (IOException ex) {
            Logger.getLogger(TheClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void fermer(){
        this.ex.interrupt();
        this.rx.interrupt();
        try {
            this.client.close();
        } catch (IOException ex){
            Logger.getLogger(TheClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
