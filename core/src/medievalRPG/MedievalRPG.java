package medievalRPG;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import medievalRPG.Item.Arma;
import medievalRPG.characters.Personagem;
import medievalRPG.screens.CreationScreen;

import java.util.ArrayList;

public class MedievalRPG extends Game {
	private SpriteBatch batch;
    private ArrayList<Arma> armas;
    private ArrayList<Personagem> personagems;

	@Override
	public void create() {
        armas=new ArrayList<Arma>();
        personagems=new ArrayList<Personagem>();
        createArmas();
        createPersonagens();
		this.setScreen(new CreationScreen(this,null,armas, personagems));
		batch = new SpriteBatch();
	}


	private void handleInput() {
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
		}
	}

	public void update() {
	}

	@Override
	public void render() {
		handleInput();
		super.render();
	}

	@Override
	public void dispose() {
		if (this.getScreen() != null) {
			this.getScreen().dispose();
		}
		batch.dispose();
	}

    private void createPersonagens(){
        personagems.add(addPersonagem("Mario",0,0,1));
        personagems.get(0).atualizaArma(armas.get(0));
        personagems.add(addPersonagem("Jonas",0,0,2));
        personagems.get(1).atualizaArma(armas.get(1));
        personagems.add(addPersonagem("Michel",0,0,3));
        personagems.get(2).atualizaArma(armas.get(3));
        personagems.add(addPersonagem("Fael",0,0,4));
        personagems.get(3).atualizaArma(armas.get(4));
        personagems.add(addPersonagem("Donatello",0,0,5));
        personagems.get(4).atualizaArma(armas.get(5));
    }

    private Personagem addPersonagem(String n, float x, float y, int id){
        Personagem p;
        p = new Personagem(n,x,y,id);
        return(p);
    }

	private void createArmas(){
        /*int hp, dano, alcance;
        double peso, preco;
        String n;
        fazer uma leitura de arquivo para melhorar essa criação no futuro.
        */
        armas.add(addArma(5, 0.5, 2, "Adaga", 4, Config.WORLD_QUADRADO+(Config.WORLD_QUADRADO/2)));//1 quadrado
        armas.add(addArma(7, 1.0, 10, "Espada Curta", 6, Config.WORLD_QUADRADO+(Config.WORLD_QUADRADO/2)));
        armas.add(addArma(12, 1.5, 15, "Espada Longa", 8, Config.WORLD_QUADRADO+(Config.WORLD_QUADRADO/2)));
        armas.add(addArma(10, 2.0, 10, "Machado", 8, Config.WORLD_QUADRADO+(Config.WORLD_QUADRADO/2)));
        armas.add(addArma(7, 1.0, 25, "Arco Curto", 6, (Config.WORLD_QUADRADO*16)+(Config.WORLD_QUADRADO/2)));//16 quadrados
        armas.add(addArma(10, 1.0, 50, "Arco Longo", 8, (Config.WORLD_QUADRADO*30)+(Config.WORLD_QUADRADO/2)));//30 quadrados
    }

	private Arma addArma(int hp, double peso, double preco, String n, int dano, int alcance){
        Arma arma;
        arma = new Arma(hp, peso, preco, n, dano, alcance);
        return (arma);
    }
}