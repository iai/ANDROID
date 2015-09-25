/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.art.iai;

/**
 *
 * @author Paulo
 */
public class Cachorro extends Animal implements IAnimal{
    public String raca;
    public String brinquedoPreferido;
    
    public void Latir(){
        System.out.println("Au au");
    }
    
    public void AbanarRabo(){
        
    }
    
    @Override
    public void Andar(){
        System.out.println("Andar do Cachorro");
    }

    @Override
    public void Correr() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Morrer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
