
package br.art.iai;

public class Animal {
    public int numeroDePatas;
    public String nomeEspecie;
    public int quantidadeTempoVivo;
    public String corDoPelo;
    public String ambiente;

    public Animal() {
    }

    public Animal(int quantidadeTempoVivo) {
        int numero = 0;
        this.quantidadeTempoVivo = quantidadeTempoVivo;
    }
    
    public void Andar(){
        System.out.println("Andar da classe Animal");
    }
    
    public void Correr(){
        
    }
    
    public void Morrer(){
        this.quantidadeTempoVivo = 0;
    }
}
