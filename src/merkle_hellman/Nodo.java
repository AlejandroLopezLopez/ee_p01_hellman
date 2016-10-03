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
public class Nodo<T> {
    
    Nodo<T> siguiente;
    T dato;
  
  public Nodo(T dato){
    this.dato=dato;
    this.siguiente=null;
  }
 
  public Nodo<T> getSiguiente(){
      return siguiente;  
  }
  public void setSiguiente(Nodo<T> s){
      this.siguiente=s;
  }
  
  public T getDato(){
      return dato;
  }
  public void setDato(T dato){
      this.dato=dato; 
  }
  
    
}
