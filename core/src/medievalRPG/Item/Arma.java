package medievalRPG.Item;

/**
 * Created by Henrique on 29/12/2016.
 */
public class Arma extends Item{
    public int danoMaximo, alcanceMax;

    public Arma(int hp, double peso, double preco, String n, int dano, int alcance){
        super(hp,peso,preco,n);
        this.danoMaximo=dano;
        this.alcanceMax=alcance;
    }
}
