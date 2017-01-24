package medievalRPG.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import medievalRPG.Config;
import medievalRPG.Item.Arma;
import medievalRPG.characters.Inimigo;
import medievalRPG.characters.Personagem;
import medievalRPG.maps.Mapa;

import java.util.ArrayList;

/**
 * Created by Henrique on 10/01/2017.
 */
public class SelectScreen extends BaseScreen{
    private Texture pronto;
    private boolean escolhido, p1, p2, p3, p4, p5;
    private String missao;
    private Mapa mapa;
    private ArrayList<Double> precos;
    private ArrayList<Arma> armas;
    private ArrayList<Personagem> perDoJogador, aliados;
    private ArrayList<Inimigo> inimigos;
    private int quantidadeInimigos;

    public SelectScreen(Game game, BaseScreen previous,
                        ArrayList<Arma> armas, ArrayList<Personagem> p){
        super(game, previous);
        this.armas=armas;
        this.aliados=p;
    }

    @Override
    public void appear() {
        Gdx.gl.glClearColor(.83f, .83f, .83f, 1);
        escolhido=false;
        p1=false;
        p2=false;
        p3=false;
        p4=false;
        p5=false;
        mapa = new Mapa(1350,1050,"map.png",0, 0);
        pronto = new Texture("images/simboloOK.png");
        inimigos = new ArrayList<Inimigo>();
        perDoJogador=new ArrayList<Personagem>();
        precos = new ArrayList<Double>();
        perDoJogador.add(aliados.get(aliados.size()-1));
    }

    @Override
    public void cleanUp() {

    }

    @Override
    public void handleInput() {
        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if(button== Input.Buttons.LEFT){
                    mouseDownLeft(screenX,screenY);
                }
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        });
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void draw() {
        batch.begin();
        desenhaTela(batch);
        batch.end();
    }

    private void desenhaTela(SpriteBatch batch){
        drawText("Aliados possíves para batalha",0.9f,
                700,10,1);
        drawText("Nome",0.8f,
                650,20,1);
        drawText("Arma",0.8f,
                650,360,1);
        drawText("Vida",0.8f,
                650,700,1);
        drawText("Nivel",0.8f,
                650,800,1);
        drawText("Preço",0.8f,
                650,900,1);
        for(int i=0;i<aliados.size()-1;i++){
            drawText(""+aliados.get(i).nome,0.8f,
                    600-(i*50),20,1);
            if(aliados.get(i).ataque!=null){
                drawText(""+aliados.get(i).ataque.nome,0.8f,
                        600-(i*50),360,1);
            }
            drawText(""+aliados.get(i).vida,0.8f,
                    600-(i*50),700,1);
            drawText(""+aliados.get(i).nivel,0.8f,
                    600-(i*50),800,1);
            precos.add(((aliados.get(i).vida/2.0)*(aliados.get(i).ataque.danoMaximo/2))+((aliados.get(i).ataque.alcanceMax-25)/50));
            drawText(""+precos.get(i),0.8f,
                    600-(i*50),900,1);
        }
        drawText("Missões possíves",0.9f,
                300,10,1);
        drawText("1 Bandido",0.8f,
                250,20,1);
        drawText("2 Bandidos",0.8f,
                200,20,1);
        drawText("3 Bandidos",0.8f,
                150,20,1);
        drawText("4 Bandidos",0.8f,
                100,20,1);
        drawText("Pagamento",0.9f,
                300,400,1);
        drawText("10",0.8f,
                250,400,1);
        drawText("22",0.8f,
                200,400,1);
        drawText("35",0.8f,
                150,400,1);
        drawText("50",0.8f,
                100,400,1);
        drawText("Moedas Restantes",0.9f,
                300,800,1);
        drawText(""+perDoJogador.get(0).moedas,0.8f,
                250,800,1);
        drawText("Missão Selecionada",0.9f,
                200,800,1);
        drawText(""+missao,0.8f,
                150,800,1);
        batch.draw(pronto,800,50,200,50);
    }

    private void mouseDownLeft(int x, int y){
        y= Config.WORLD_HEIGHT-y;
        if(x>=20 && x<=950) {
            if (y >= 550 && y <= 590) {
                if (avaliaPersonagem(precos.get(0)) && !p1) {
                    perDoJogador.add(aliados.get(0));
                    perDoJogador.get(perDoJogador.size()-1).posJogadorY=perDoJogador.size()*50;
                    perDoJogador.get(0).moedas -= precos.get(0);
                    p1=true;
                }
            } else if (y >= 500 && y <= 540) {
                if (avaliaPersonagem(precos.get(1)) && !p2) {
                    perDoJogador.add(aliados.get(1));
                    perDoJogador.get(perDoJogador.size()-1).posJogadorY=perDoJogador.size()*50;
                    perDoJogador.get(0).moedas -= precos.get(1);
                    p2=true;
                }
            } else if (y >= 450 && y <= 490) {
                if (avaliaPersonagem(precos.get(2)) && !p3) {
                    perDoJogador.add(aliados.get(2));
                    perDoJogador.get(perDoJogador.size()-1).posJogadorY=perDoJogador.size()*50;
                    perDoJogador.get(0).moedas -= precos.get(2);
                    p3=true;
                }
            } else if (y >= 400 && y <= 440) {
                if (avaliaPersonagem(precos.get(3)) && !p4) {
                    perDoJogador.add(aliados.get(3));
                    perDoJogador.get(perDoJogador.size()-1).posJogadorY=perDoJogador.size()*50;
                    perDoJogador.get(0).moedas -= precos.get(3);
                    p4=true;
                }
            } else if (y >= 350 && y <= 390) {
                if (avaliaPersonagem(precos.get(4)) && !p5) {
                    perDoJogador.add(aliados.get(4));
                    perDoJogador.get(perDoJogador.size()-1).posJogadorY=perDoJogador.size()*50;
                    perDoJogador.get(0).moedas -= precos.get(4);
                    p5=true;
                }
            }
        }
        if(x>=20 && x<=450){
            if(y>=200 && y<=250){
                if(!escolhido){
                    perDoJogador.get(0).moedas+=10;
                    missao="1 Bandido";
                    quantidadeInimigos=1;
                    escolhido=true;
                }
            }
            else if(y>=150 && y<=190){
                if(!escolhido){
                    perDoJogador.get(0).moedas+=22;
                    missao="2 Bandidos";
                    quantidadeInimigos=2;
                    escolhido=true;
                }
            }
            else if(y>=100 && y<=140){
                if(!escolhido){
                    perDoJogador.get(0).moedas+=35;
                    missao="3 Bandidos";
                    quantidadeInimigos=3;
                    escolhido=true;
                }
            }
            else if(y>=50 && y<=90){
                if(!escolhido){
                    perDoJogador.get(0).moedas+=50;
                    missao="4 Bandidos";
                    quantidadeInimigos=4;
                    escolhido=true;
                }
            }
        }
        if(x>=800 && x<=1000){
            if(y>=50 && y<=100){
                if(escolhido){
                    for(int i=0;i<quantidadeInimigos;i++){
                        inimigos.add(new Inimigo("Bandido",mapa.tamanhoMapX-50,50+(i*100),100+i));
                        inimigos.get(i).atualizaArma(armas.get(1));
                    }
                    navigateScreen();
                }
            }
        }
    }

    private boolean avaliaPersonagem(double valor){
        boolean pode;
        double total;
        total=perDoJogador.get(0).moedas;
        //System.out.println(""+(total-valor));
        if((total-valor)>0){
            pode=true;
        }
        else{
            pode=false;
        }
        return(pode);
    }

    private void navigateScreen(){
        game.setScreen(new BattleScreen(game, SelectScreen.this, armas, perDoJogador, inimigos, mapa));
    }
}
