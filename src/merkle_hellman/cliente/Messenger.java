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
public class Messenger {
    
    public static Conector servidor,cliente;
    public static void main(String []args){
      
       VCliente cliente=new VCliente();
       cliente.main();
       
    }
    
    public static void initCliente(String ip){
        cliente= new Conector(ip);
        cliente.start();
    }
}
