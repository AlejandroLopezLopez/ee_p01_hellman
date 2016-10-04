/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merkle_hellman.servidor;

/**
 *
 * @author Jairo
 */
import java.net.*;
import java.io.*;
import merkle_hellman.servidor.VServidor;
import merkle_hellman.Algoritmo;
public class Conector extends Thread {
    
    private Socket s;
    private ServerSocket ss;
    private InputStreamReader entradaSocket;
    private DataOutputStream salida;
    private BufferedReader entrada;
    final int puerto=800;
    /**
     * 
     * @param nombre 
     */
    public Conector(String nombre){
        super(nombre);
    }
    /**
     * Este metodo encripta el mensaje para el servidor y al momento de recibirlo lo desencripta y lo muestra en pantalla
     * @param mensaje 
     */
    public void enviarMsg(String mensaje){
        System.out.println("Enviando");
        try{
           Algoritmo.encriptar(mensaje);
          this.salida=new DataOutputStream(s.getOutputStream());
          this.salida.writeUTF(Algoritmo.desencriptar()+"\n");
          Algoritmo.vaciar();
        }catch(IOException e){
            System.out.println("Problema al enviar");
        }; 
    }
    /**
     * 
     */
    public void run(){
    
       String text="test";
       try{
         this.ss=new ServerSocket(puerto);
         this.s= ss.accept();
         //Creacion de entrada de datos para la lectura del mensaje
         this.entradaSocket=new InputStreamReader(s.getInputStream());
         this.entrada=new BufferedReader(entradaSocket);
         //Creacion de la salida de datos para el envio del mensaje
         this.salida=new DataOutputStream(s.getOutputStream());
         while(true){
             text=this.entrada.readLine();
             System.out.println(text);
             VServidor.jTextArea1.setText(VServidor.jTextArea1.getText()+"\n"+text);
         }
       }catch(IOException e){
           System.out.println("Algun tipo de error ha ocurrido");
       };
    }
    
    /**
     * 
     * @return 
     */
    public String leerMensaje(){
       try{
         return this.entrada.readLine();
       }catch(IOException e){}
       
       return null;
    }
    
    /**
     * 
     */
    public void desconectar(){
      try{
        s.close();
      }catch(IOException e){}
      try{
        ss.close();
      }catch(IOException e){}
    
    }
    
    
    
    
    
}
