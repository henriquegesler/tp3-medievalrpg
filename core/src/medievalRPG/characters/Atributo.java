package medievalRPG.characters;

/**
 * Created by Henrique on 19/01/2017.
 */
public class Atributo {
    public int valor, modificador;

    public Atributo(int v){
        valor=v;
        modificador=achaModificador(v);
    }

    public int achaModificador(int valor){
        int mod;
        switch (valor){
            default:
                mod=0;
            case 1:
                mod=-5;
            case 2|3:
                mod=-4;
            case 4|5:
                mod=-3;
            case 6|7:
                mod=-2;
            case 8|9:
                mod=-1;
            case 10|11:
                mod=0;
            case 12|13:
                mod=1;
            case 14|15:
                mod=2;
            case 16|17:
                mod=3;
            case 18|19:
                mod=4;
            case 20:
                mod=5;
        }
        return(mod);
    }
}
