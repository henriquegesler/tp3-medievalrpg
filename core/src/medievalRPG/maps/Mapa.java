package medievalRPG.maps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Created by Henrique on 16/01/2017.
 */
public class Mapa {
    private Texture mapa;
    public int tamanhoMapX, tamanhoMapY, mapX, mapY;
    public ArrayList<Colidiveis> colidiveis;

    public Mapa(int tamX,int tamY, String local, int x, int y){
        tamanhoMapX=tamX;
        tamanhoMapY=tamY;
        mapa= new Texture(local);
        mapX = x;
        mapY = y;
        colidiveis = new ArrayList<Colidiveis>();
    }

    public void render(SpriteBatch batch, int zX, int zY){
        batch.draw(mapa,mapX+zX,mapY+zY,tamanhoMapX,tamanhoMapY);
    }

    public void createEspaco(float x, float y){
        Colidiveis e;
        e = new Colidiveis(x,y);
        colidiveis.add(e);
    }

    public void atualizaEspaco(int local, float novoX, float novoY){
        colidiveis.get(local).x=novoX;
        colidiveis.get(local).y=novoY;
    }

    public void removeEspaco(int local){
        colidiveis.remove(local);
    }
}
