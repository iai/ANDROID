/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.art.iai;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulo
 */
public class Teste {
    public static void main(String[] args){
        Cachorro cao = new Cachorro();
        cao.Andar();
        
        Animal animal = new Animal();
        animal.Andar();
        
        int a = 100;
        int b = 1000;
        
        if((a > b) && (b == 1000)){
            System.out.println("A é maior que B");
        }
        else if(a == 100){
            System.out.println("A é igual a 100");
        }
        else{
           System.out.println("A é menor que B");
        }
    }
}
