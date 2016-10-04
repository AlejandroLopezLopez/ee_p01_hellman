/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merkle_hellman.cliente;

/**
 *
 * @author Jairo
 */
import java.net.*;
import java.io.*;
import merkle_hellman.Algoritmo;
public class Conector extends Thread{
    private Socket s;
    private ServerSocket ss;
    private InputStreamReader entradaSocket;
    private DataOutputStream salida;
    private BufferedReader entrada;
    final int puerto=800;

    /**
     * 
     * @param ip 
     */
    public Conector(String ip){
       try{
           this.s=new Socket(ip,this.puerto);
         
        //Creacion de entrada de datos para la lectura del mensaje
         this.entradaSocket=new InputStreamReader(s.getInputStream());
         this.entrada=new BufferedReader(entradaSocket);
         //Creacion de la salida de datos para el envio del mensaje
         this.salida=new DataOutputStream(s.getOutputStream());
         this.salida.writeUTF("Conectado--> \n");
       }catch(Exception e){}
    }
    
    public void run(){
       String texto;
        while(true){
          try{
           texto=entrada.readLine();
           VCliente.jTextArea1.setText(VCliente.jTextArea1.getText()+"\n"+texto);
          }catch(IOException e){}
        
        }
    }
    
    /**
     * 
     * @param mensaje 
     */
    public void enviarMensaje(String mensaje){
        System.out.println("Enviando");
        try{
          this.salida=new DataOutputStream(s.getOutputStream());
          this.salida.writeUTF((mensaje)+"\n");//aqui modifique para encriptar
        }catch(IOException e){
            System.out.println("Problema al enviar");
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
}
