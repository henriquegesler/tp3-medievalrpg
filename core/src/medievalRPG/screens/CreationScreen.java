package medievalRPG.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import medievalRPG.Config;
import medievalRPG.Item.Arma;
import medievalRPG.characters.Personagem;
import java.util.ArrayList;

import static com.badlogic.gdx.Gdx.input;

/**
 * Created by Henrique on 19/01/2017.
 */
public class CreationScreen extends BaseScreen{
    private Texture mais, menos, pronto;
    private Personagem jogador;
    private boolean escolheu=false;
    private ArrayList<Personagem> personagems;
    private ArrayList<Arma> armas;
    private int pontos, estagio;

    public CreationScreen(Game game, BaseScreen previous,
                          ArrayList<Arma> armas, ArrayList<Personagem> p){
        super(game, previous);
        this.armas=armas;
        this.personagems=p;
        pontos=27;
        estagio=0;
    }

    @Override
    public void appear() {
        Gdx.gl.glClearColor(.83f, .83f, .83f, 1);
        jogador = new Personagem("Jogador",0, 50, 0);
        Config.WORLD_QUADRADO=500;
        mais = new Texture("images/simboloMais.png");
        menos = new Texture("images/simboloMenos.png");
        pronto = new Texture("images/simboloOK.png");
    }

    @Override
    public void cleanUp() {

    }

    @Override
    public void handleInput() {
        input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                if(keycode==Input.Keys.F1){
                    navigateScreen();
                }
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
        if(estagio==0){
            desenhaMenu0(batch);
        }
        else if(estagio==1){
            desenhaMenu1(batch);
        }
        batch.end();
    }


    private void desenhaMenu0(SpriteBatch batch){
        drawText("Nome: "+ jogador.nome,1.0f,
                700,700,1);
        drawText("Atributos ",1.0f,
                650,700,1);
        drawText("Pontos restantes: "+pontos,0.5f,
                630,1000,1);
        batch.draw(menos,950,560,40,40);
        drawText("Força: ",0.8f,
                600,700,1);
        drawText(""+ jogador.forca.valor, 0.8f,
                600,1000,1);
        batch.draw(mais,1070,560,40,40);
        batch.draw(menos,950,510,40,40);
        drawText("Destreza: ",0.8f,
                550,700,1);
        drawText(""+ jogador.destreza.valor, 0.8f,
                550,1000,1);
        batch.draw(mais,1070,510,40,40);
        batch.draw(menos,950,460,40,40);
        drawText("Constituicao: ",0.8f,
                500,700,1);
        drawText(""+ jogador.constituicao.valor, 0.8f,
                500,1000,1);
        batch.draw(mais,1070,460,40,40);
        batch.draw(menos,950,410,40,40);
        drawText("Inteligencia: ",0.8f,
                450,700,1);
        drawText(""+ jogador.inteligencia.valor, 0.8f,
                450,1000,1);
        batch.draw(mais,1070,410,40,40);
        batch.draw(menos,950,360,40,40);
        drawText("Sabedoria: ",0.8f,
                400,700,1);
        drawText(""+ jogador.sabedoria.valor, 0.8f,
                400,1000,1);
        batch.draw(mais,1070,360,40,40);
        batch.draw(menos,950,310,40,40);
        drawText("Carisma: ",0.8f,
                350,700,1);
        drawText(""+ jogador.carisma.valor, 0.8f,
                350,1000,1);
        batch.draw(mais,1070,310,40,40);
        batch.draw(pronto,850,250,200,50);
        jogador.render(batch,0,0);
    }

    private void desenhaMenu1(SpriteBatch batch){
        drawText("Armas Compraveis: ",1.0f,
                700,10,1);
        drawText("Nome ",0.9f,
                650,20,1);
        drawText("Dano",0.9f,
                650,335,1);
        drawText("Alcance",0.9f,
                650,450,1);
        drawText("Preco",0.9f,
                650,615,1);
        for(int i=0;i<armas.size();i++){
            drawText(""+armas.get(i).nome,0.8f,
                    600-(i*50),20,1);
            drawText(""+armas.get(i).danoMaximo,0.8f,
                    600-(i*50),360,1);
            drawText(""+(armas.get(i).alcanceMax-25)/50,0.8f,
                    600-(i*50),510,1);
            drawText(""+armas.get(i).preco,0.8f,
                    600-(i*50),625,1);
        }
        drawText("Armas do Jogador: ",0.8f,
                700,760,1);
        drawText("Nome ",0.7f,
                650,770,1);
        drawText("Dano",0.7f,
                650,1000,1);
        drawText("Alc.",0.7f,
                650,1100,1);
        drawText("Preco",0.7f,
                650,1180,1);
        if(jogador.ataque!=null){
            drawText(""+ jogador.ataque.nome,0.6f,
                    600,770,1);
            drawText(""+ jogador.ataque.danoMaximo,0.6f,
                    600,1020,1);
            drawText(""+(jogador.ataque.alcanceMax-25)/50,0.6f,
                    600,1110,1);
            drawText(""+ jogador.ataque.preco,0.6f,
                    600,1200,1);
        }
        drawText("Moedas: "+jogador.moedas,0.8f,
                550,770,1);
        batch.draw(pronto,300,200,200,50);
        jogador.render(batch,700,-20);
    }

    private void mouseDownLeft(int x, int y){
        y=Config.WORLD_HEIGHT-y;
        if(estagio==0){
            mouseEstagio0(x,y);
        }
        else if(estagio==1){
            mouseEstagio1(x,y);
        }
    }

    private void mouseEstagio0(int x, int y){
        if(x>=1070 && x<=1110){
            if(y>=560 && y<=610){
                jogador.forca.valor+=1;
                if(jogador.forca.valor>20){
                    jogador.forca.valor=20;
                }
                else{
                    if(!avaliaPontos(jogador.forca.valor, 0)){
                        jogador.forca.valor-=1;
                    }
                }
            }
            else if(y>=510 && y<=550){
                jogador.destreza.valor+=1;
                if(jogador.destreza.valor>20){
                    jogador.destreza.valor=20;
                }
                else{
                    if(!avaliaPontos(jogador.destreza.valor, 0)){
                        jogador.destreza.valor-=1;
                    }
                }
            }
            else if(y>=460 && y<=500){
                jogador.constituicao.valor+=1;
                if(jogador.constituicao.valor>20){
                    jogador.constituicao.valor=20;
                }
                else{
                    if(!avaliaPontos(jogador.constituicao.valor, 0)){
                        jogador.constituicao.valor-=1;
                    }
                }
            }
            else if(y>=410 && y<=450){
                jogador.inteligencia.valor+=1;
                if(jogador.inteligencia.valor>20){
                    jogador.inteligencia.valor=20;
                }
                else{
                    if(!avaliaPontos(jogador.inteligencia.valor, 0)){
                        jogador.inteligencia.valor-=1;
                    }
                }
            }
            else if(y>=360 && y<=400){
                jogador.sabedoria.valor+=1;
                if(jogador.sabedoria.valor>20){
                    jogador.sabedoria.valor=20;
                }
                else{
                    if(!avaliaPontos(jogador.sabedoria.valor, 0)){
                        jogador.sabedoria.valor-=1;
                    }
                }
            }
            else if(y>=310 && y<=350){
                jogador.carisma.valor+=1;
                if(jogador.carisma.valor>20){
                    jogador.carisma.valor=20;
                }
                else{
                    if(!avaliaPontos(jogador.carisma.valor, 0)){
                        jogador.carisma.valor-=1;
                    }
                }
            }
        }
        else if(x>=950 && x<=990){
            if(y>=560 && y<=610){
                jogador.forca.valor-=1;
                if(jogador.forca.valor<8){
                    jogador.forca.valor=8;
                }
                else{
                    if(!avaliaPontos(jogador.forca.valor, 1)){
                        jogador.forca.valor+=1;
                    }
                }
            }
            else if(y>=510 && y<=550){
                jogador.destreza.valor-=1;
                if(jogador.destreza.valor<8){
                    jogador.destreza.valor=8;
                }
                else{
                    if(!avaliaPontos(jogador.destreza.valor, 1)){
                        jogador.destreza.valor+=1;
                    }
                }
            }
            else if(y>=460 && y<=500){
                jogador.constituicao.valor-=1;
                if(jogador.constituicao.valor<8){
                    jogador.constituicao.valor=8;
                }
                else{
                    if(!avaliaPontos(jogador.constituicao.valor, 1)){
                        jogador.constituicao.valor+=1;
                    }
                }
            }
            else if(y>=410 && y<=450){
                jogador.inteligencia.valor-=1;
                if(jogador.inteligencia.valor<8){
                    jogador.inteligencia.valor=8;
                }
                else{
                    if(!avaliaPontos(jogador.inteligencia.valor, 1)){
                        jogador.inteligencia.valor+=1;
                    }
                }
            }
            else if(y>=360 && y<=400){
                jogador.sabedoria.valor-=1;
                if(jogador.sabedoria.valor<8){
                    jogador.sabedoria.valor=8;
                }
                else{
                    if(!avaliaPontos(jogador.sabedoria.valor, 1)){
                        jogador.sabedoria.valor+=1;
                    }
                }
            }
            else if(y>=310 && y<=350){
                jogador.carisma.valor-=1;
                if(jogador.carisma.valor<8){
                    jogador.carisma.valor=8;
                }
                else{
                    if(!avaliaPontos(jogador.carisma.valor, 1)){
                        jogador.carisma.valor+=1;
                    }
                }
            }
        }
        if(x>=850 && x<=1050) {
            if (y>=250 && y<=300){
                estagio+=1;
            }
        }
    }

    private void mouseEstagio1(int x, int y){
        if(x>=20 && x<=700){
            if(y>=550 && y<=590){
                verificaDinheiro(armas.get(0));
                jogador.atualizaArma(armas.get(0));
                escolheu=true;
            }
            else if(y>=500 && y<=540){
                verificaDinheiro(armas.get(1));
                jogador.atualizaArma(armas.get(1));
                escolheu=true;
            }
            else if(y>=450 && y<=490){
                verificaDinheiro(armas.get(2));
                jogador.atualizaArma(armas.get(2));
                escolheu=true;
            }
            else if(y>=400 && y<=440){
                verificaDinheiro(armas.get(3));
                jogador.atualizaArma(armas.get(3));
                escolheu=true;
            }
            else if(y>=350 && y<=390){
                verificaDinheiro(armas.get(4));
                jogador.atualizaArma(armas.get(4));
                escolheu=true;
            }
            else if(y>=300 && y<=340){
                verificaDinheiro(armas.get(5));
                jogador.atualizaArma(armas.get(5));
                escolheu=true;
            }
        }
        if(x>=300 && x<=500){
            if(y>=200 && y<=250){
                if(escolheu){
                    personagems.add(jogador);
                    navigateScreen();
                }
            }
        }
    }

    private void verificaDinheiro(Arma arma){
        if(jogador.ataque!=null){
            jogador.moedas+= jogador.ataque.preco;
            jogador.moedas-=arma.preco;
        }
        else{
            jogador.moedas-=arma.preco;
        }
    }

    private boolean avaliaPontos(int valor, int caso){
        boolean pode=false;
        if(caso==0){
            if(valor==9){
                pontos-=1;
                if(pontos<0){
                    pontos+=1;
                }
                else{
                    pode=true;
                }
            }
            else if(valor==10){
                pontos-=1;
                if(pontos<0){
                    pontos+=1;
                }
                else{
                    pode=true;
                }
            }
            else if(valor==11){
                pontos-=1;
                if(pontos<0){
                    pontos+=1;
                }
                else{
                    pode=true;
                }
            }
            else if(valor==12){
                pontos-=1;
                if(pontos<0){
                    pontos+=1;
                }
                else{
                    pode=true;
                }
            }
            else if(valor==13){
                pontos-=1;
                if(pontos<0){
                    pontos+=1;
                }
                else{
                    pode=true;
                }
            }
            else if(valor==14){
                pontos-=2;
                if(pontos<0){
                    pontos+=2;
                }
                else{
                    pode=true;
                }
            }
            else if(valor==15){
                pontos-=2;
                if(pontos<0){
                    pontos+=2;
                }
                else{
                    pode=true;
                }
            }
            else if(valor==16){
                pontos-=2;
                if(pontos<0){
                    pontos+=2;
                }
                else{
                    pode=true;
                }
            }
            else if(valor==17){
                pontos-=3;
                if(pontos<0){
                    pontos+=3;
                }
                else{
                    pode=true;
                }
            }
            else if(valor==18){
                pontos-=3;
                if(pontos<0){
                    pontos+=3;
                }
                else{
                    pode=true;
                }
            }
            else if(valor==19){
                pontos-=4;
                if(pontos<0){
                    pontos+=4;
                }
                else{
                    pode=true;
                }
            }
            else if(valor==20){
                pontos-=5;
                if(pontos<0){
                    pontos+=5;
                }
                else{
                    pode=true;
                }
            }
        }
        else if(caso==1){
            if(valor==8){
                pontos+=1;
                pode=true;
            }
            else if(valor==9){
                pontos+=1;
                pode=true;
            }
            else if(valor==10){
                pontos+=1;
                pode=true;
            }
            else if(valor==11){
                pontos+=1;
                pode=true;
            }
            else if(valor==12){
                pontos+=1;
                pode=true;
            }
            else if(valor==13){
                pontos+=2;
                pode=true;
            }
            else if(valor==14){
                pontos+=2;
                pode=true;
            }
            else if(valor==15){
                pontos+=2;
                pode=true;
            }
            else if(valor==16){
                pontos+=3;
                pode=true;
            }
            else if(valor==17){
                pontos+=3;
                pode=true;
            }
            else if(valor==18){
                pontos+=4;
                pode=true;
            }
            else if(valor==19){
                pontos+=5;
                pode=true;
            }
        }
        return(pode);
    }

    private void navigateScreen(){
        game.setScreen(new SelectScreen(game, CreationScreen.this,armas,personagems));
    }
}
