package medievalRPG.Item;

/**
 * Created by Henrique on 29/12/2016.
 */
public class Item {
    public int vida;
    public double peso, preco;
    public String nome;

    public Item(int hp, double peso, double preco, String n){
        this.vida=hp;
        this.peso=peso;
        this.preco=preco;
        this.nome=n;
    }
}
