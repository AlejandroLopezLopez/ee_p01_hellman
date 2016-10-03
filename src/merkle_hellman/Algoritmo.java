/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merkle_hellman;

/**
 *
 * @author Jairo
 */
import java.math.*;
import java.util.*;
public class Algoritmo {
   
 Lista<BigInteger> secuencia=new Lista<BigInteger>();
 Lista<BigInteger> pubKey=new Lista<BigInteger>();
 BigInteger sumatoria,valorQ,valorR,primero,siguiente;
  
  /**
   * Metodo getSumatoria
   * @return 
   */
  public BigInteger getSumatoria(){
    return sumatoria;
  }
  
  /**
   * Genera numero aleatorio con un minimo y maximo como parametro
   * @param minimo
   * @param maximo
   * @return 
   */
  public static int generaNumeroAleatorio(int minimo, int maximo){
        int num =(int)Math.floor(Math.random()*(minimo-(maximo+1))+(maximo+1));
        return num;
  }
  
/**
 *Generar llave prevada: W,EW,Q y R.
 */
  public void generaSecuencia(){
    primero= new BigInteger(8,new Random() );
    secuencia.insertaInicio(primero);
     for (int i = 0; i < 7; i++) {
         siguiente=new BigInteger("4");
         siguiente=primero.multiply(siguiente);
         secuencia.insertaFinal(siguiente);
         primero=siguiente;
     }
     System.out.println("Secuencia W: "+secuencia);
     
     /**
      * Sumatoria de la secuencia super incrementada
      */
     
     Nodo<BigInteger> aux= secuencia.getInicio();
     sumatoria= new BigInteger("0");
     while(aux.getSiguiente()!=null){
       sumatoria= sumatoria.add(aux.getDato());
       aux=aux.getSiguiente();
     }
        sumatoria=sumatoria.add(aux.getDato());
        valorQ=sumatoria.add(new BigInteger(10,new Random()));
       System.out.println("Sumatoria de W: "+ sumatoria+"\n"+"Q: "+valorQ);
      
     /**
      * Generar r
      */
      BigInteger comparable=new BigInteger("1");
     
      int temporal=0;
      do{
          temporal=generaNumeroAleatorio(1,Integer.parseInt(sumatoria.toString()));
          valorR=new BigInteger(temporal+"");
      }while(((valorQ.gcd(valorR)).compareTo(comparable))!=0);
      
        System.out.println("R: "+valorR+"\n"+" Minimo comun multiplo debe ser: "+valorQ.gcd(valorR));
      
      
 }

  /**
   * Generar llave publica
   */
    public void llavePublica(){
    generaSecuencia();
     Nodo<BigInteger> aux=secuencia.getInicio();
     while(aux!=null){
        BigInteger aux2;
        aux2=(aux.getDato().multiply(valorR)).mod(valorQ);
        pubKey.insertaFinal(aux2);
        aux=aux.getSiguiente();
     }
        System.out.println("llave publica: "+pubKey);
    }
    
    
    /**
     * 
     * @param texto
     * @return 
     */
 public  String textoABinario(String texto)
    {
        String textoBinario = "";
        for(char letra : texto.toCharArray())
        {
            textoBinario += String.format("%8s", Integer.toBinaryString(letra));
        }
      
        return textoBinario.replace("\u0020","\u0030");
    }

/**
 * Lista a binarios: ingresa el valor en bits del texto a una lista
 * @param texto
 * @return 
 */ 
 public Lista<BigInteger> listaBinarios(String texto){
    Lista<BigInteger> lista=new Lista<BigInteger>();
    for(char letra :texto.toCharArray()){
        String b=letra+"";
        int i=Integer.parseInt(b);
        BigInteger bin=new BigInteger(i+"");
        lista.insertaFinal(bin);
    }
    return lista;
 }
 
 /**
  * encriptar
  */
 
 public void encriptar(String texto){
    Lista<BigInteger> resultado=new Lista<BigInteger>();
    Lista<BigInteger> textoBinarios;//esta lista solo sirve para almacenar los ninarios con los dos metoso anteriores
    textoBinarios=listaBinarios(textoABinario(texto));
    Nodo<BigInteger> tbin=textoBinarios.getInicio();
    Nodo<BigInteger> pk=pubKey.getInicio();
  
    while(tbin!=null){
     while(pk!=null){
       BigInteger mult=pk.getDato().multiply(tbin.getDato());
       resultado.insertaFinal(mult);
       pk=pk.getSiguiente();
       tbin=tbin.getSiguiente();
     }
       pk=pubKey.getInicio();
    }
    
    Nodo<BigInteger> nres=resultado.getInicio();
    Integer conta=0;
    Integer suma=0;
    while(nres!=null){
        for (int i = 0; i <8; i++) {
            BigInteger f=nres.getDato();
            conta= f.intValue();
            suma+=conta;
            nres=nres.getSiguiente();
        }
        System.out.print(new BigInteger(suma+"")+",");
        suma=suma-suma;
    
    }
    
 }
    /**
     * 
     * @param tope
     * @return 
     */
    public BigInteger aleatorio(BigInteger tope){
         Random num=new Random();
         
         return new BigInteger(tope.bitLength(),num);
    }
}
