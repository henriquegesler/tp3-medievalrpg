package medievalRPG.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import medievalRPG.Config;
import medievalRPG.Item.Arma;
import medievalRPG.Item.Item;
import medievalRPG.maps.Colidiveis;

import java.util.Random;

import java.util.ArrayList;

/**
 *
 * @author fegemo
 */

public class Personagem implements Comparable<Personagem> {
    protected Texture spriteSheet;
    public Personagem alvo;
    protected TextureRegion[][] quadrosDaAnimacao;
    protected Animation cima, cimaEsquerda, cimaDireita, parado, direita, esquerda, baixo, baixoEsquerda, baixoDireita, posicao;
    public float tempoDaAnimacao, vX, vY, limiteX, limiteY, posJogadorX, posJogadorY, posDeslocaX, posDeslocaY;
    public float limiteDeMovimentacao, moviX, moviY, experiencia;
    public boolean andandoX, andandoY, andou, inimigo, possuiInimigo, atacando, acertou;
    public int vertical, horizontal, iniciativa, acoes, vida, vidaAtual, id, classeDeArmadura, estado, dano;
    public String nome;
    public Item item1, item2, item3, item4;
    public Arma ataque;
    public Atributo forca, destreza, constituicao, inteligencia, sabedoria, carisma;
    public int proficiencia, nivel;
    public double moedas;
    Random gerador;
    
    public Personagem(String n, float jogadorX, float jogadorY, int id) {
        gerador = new Random();
        experiencia=0;
        nivel=1;
        proficiencia=2;
        this.id = id;
        this.posJogadorX = jogadorX;
        this.posJogadorY = jogadorY;
        this.nome=n;
        spriteSheet = new Texture("personagens/spritesheet-masculino.png");
        quadrosDaAnimacao = TextureRegion.split(spriteSheet,100,180);
        parado = animacaoParado(0);
        direita = animacaoMovimento(0);
        esquerda = animacaoMovimento(1);
        cimaDireita = animacaoMovimento(2);
        cimaEsquerda = animacaoMovimento(3);
        cima = animacaoMovimento(0);
        baixo = animacaoMovimento(0);
        baixoDireita = animacaoMovimento(0);
        baixoEsquerda = animacaoMovimento(1);
        tempoDaAnimacao = 0;
        posicao=parado;
        vertical=1;
        horizontal=1;
        vX=0.0f;
        vY=0.0f;
        limiteX=0;
        limiteY=0;
        posDeslocaX=0;
        posDeslocaY=0;
        andandoX=false;
        andandoY=false;
        andou=false;
        acertou=false;
        limiteDeMovimentacao =(Config.WORLD_QUADRADO*6)+(Config.WORLD_QUADRADO/2);//325, 6 quadrados
        iniciativa = gerador.nextInt(21); //
        acoes=2;
        inimigo=false;
        possuiInimigo=false;
        vida=3+gerador.nextInt(8);
        vidaAtual=vida;
        classeDeArmadura=10;
        estado=2;
        forca = new Atributo(8);
        destreza = new Atributo(8);
        constituicao = new Atributo(8);
        inteligencia = new Atributo(8);
        sabedoria = new Atributo(8);
        carisma = new Atributo(8);
        moedas=20+gerador.nextInt(61);
    }

    public void update() {
        //verifica se o jogador chegou na coordenadaX e para o movimento.
        if (posDeslocaX==(posJogadorX)){
            vX=0;
            horizontal=1;
            orientacao();
            andandoX=false;
        }
        //verifica se o jogador chegou na coordenadaY e para o movimento.
        if (posDeslocaY==(posJogadorY)){
            vY=0;
            vertical=1;
            orientacao();
            andandoY=false;
        }
        //verifica se o jogador está parado
        if(!andandoX && !andandoY){
            if (andou){
                acoes--;
                andou=false;
                //System.out.println("Possui "+acoes+" acoes.");
            }
        }
    }

    public void handleInput(final ArrayList<Inimigo> alvos, final ArrayList<Colidiveis> espacos,
                            final int zoomX, final int zoomY){
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
                if(button==Input.Buttons.LEFT){
                    //Seleciona ataque
                    mouseDownLeft(alvos, zoomX, zoomY, screenX, screenY);
                }
                else if(button==Input.Buttons.RIGHT){
                    //Seleciona movimentação
                    mouseDownRight(espacos, zoomX, zoomY);
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
    
    public void render(SpriteBatch batch, int zoomX, int zoomY) {
    	tempoDaAnimacao += Gdx.graphics.getDeltaTime();
    	TextureRegion quadroCorrente = posicao.getKeyFrame(tempoDaAnimacao);
        posJogadorX+=vX;
    	posJogadorY+=vY;
        moviX+=vX;
        moviY+=vY;
        //variavel serve para saber qual a proporção do tamanho X para o render manter a qualidade do sprite
        float tamX, tamY;
        tamY=Config.WORLD_QUADRADO-(Config.WORLD_QUADRADO/5);
        tamX=(tamY*100)/180;
        // onde 180 é o valor original da altura do personagem e 100 é o valor original da largura, entao faz-se
        // a proporção.
        batch.draw(quadroCorrente,
                posJogadorX+(tamX/2)+zoomX, posJogadorY+(Config.WORLD_QUADRADO/10)+zoomY,
                tamX,tamY);
    }

    public void renderZonas(SpriteBatch batch, int zoomX, int zoomY){
        Texture alcAtaque, movimentacao;
        alcAtaque = new Texture("images/circuloAtaque.png");
        movimentacao = new Texture("images/circuloMovimentacao.png");
        batch.draw(alcAtaque,
                posJogadorX+(Config.WORLD_QUADRADO/2)- ataque.alcanceMax -(ataque.alcanceMax /13) + zoomX,
                posJogadorY+(Config.WORLD_QUADRADO/2)- ataque.alcanceMax -(ataque.alcanceMax /13) + zoomY,
                (ataque.alcanceMax *2)+((ataque.alcanceMax *10)/65),(ataque.alcanceMax *2)+((ataque.alcanceMax *10)/65));
        if(!andandoX && !andandoY){
            batch.draw(movimentacao,
                    posJogadorX+(Config.WORLD_QUADRADO/2)- limiteDeMovimentacao -(limiteDeMovimentacao /13) + zoomX,
                    posJogadorY+(Config.WORLD_QUADRADO/2)- limiteDeMovimentacao -(limiteDeMovimentacao /13) + zoomY,
                    (limiteDeMovimentacao *2)+((limiteDeMovimentacao *10)/65),(limiteDeMovimentacao *2)+((limiteDeMovimentacao *10)/65));
        }
    }
    
    public Animation animacaoMovimento(int linha){
    	Animation movimento;
    	movimento = new Animation(0.125f, new TextureRegion[] {
                quadrosDaAnimacao[linha][0],
                quadrosDaAnimacao[linha][1],
                quadrosDaAnimacao[linha][2],
                quadrosDaAnimacao[linha][3],
                quadrosDaAnimacao[linha][4],
                quadrosDaAnimacao[linha][5],
                quadrosDaAnimacao[linha][6],
                quadrosDaAnimacao[linha][7],
              });
        movimento.setPlayMode(PlayMode.LOOP);
        return(movimento);
    }
    
    public Animation animacaoParado(int linha){
    	Animation movimento;
    	movimento = new Animation(0.1f, new TextureRegion[] {
    		quadrosDaAnimacao[linha][0],
    	});
    	movimento.setPlayMode(PlayMode.LOOP_PINGPONG);
    	return(movimento);
    }


    public ArrayList<Inimigo> verificandoArredores(ArrayList<Inimigo> list){
        ArrayList<Inimigo> alcancados = new ArrayList<Inimigo>();
        Inimigo candidato;
        float dX, dY;
        double dist;
        for(int i=0; i<list.size(); i++){
            candidato = list.get(i);
            dX=calculaDistanciaParcial(posJogadorX+(Config.WORLD_QUADRADO/2),candidato.posJogadorX+(Config.WORLD_QUADRADO/2));
            dY=calculaDistanciaParcial(posJogadorY+(Config.WORLD_QUADRADO/2),candidato.posJogadorY+(Config.WORLD_QUADRADO/2));
            dist=Math.abs(calcularDistancia(dX,dY));
            if(dist<ataque.alcanceMax){
                if(candidato.estado==2){
                    alcancados.add(candidato);
                    possuiInimigo=true;
                }
            }
        }
        return(alcancados);
    }

    public float calculaDistanciaParcial(float valor1, float valor2){
        float valor;
        valor=valor1-valor2;
        if (Math.abs(valor)>0 && Math.abs(valor)<+(Config.WORLD_QUADRADO)/2){
            valor=0;
        }
        return(valor);
    }

    public double calcularDistancia(float dist1, float dist2) {
        double dist;
        dist = Math.sqrt((dist1 * dist1) + (dist2 * dist2));
        return(dist);
    }

    public float limiteMovimentacao(float dist){
        float limite;
        if (Math.abs(dist)<=(Config.WORLD_QUADRADO/2)){
            limite=0;
        }
        else if(Math.abs(dist)>(Config.WORLD_QUADRADO/2) &&
                Math.abs(dist)<=((Config.WORLD_QUADRADO)+((Config.WORLD_QUADRADO/2)))){
            limite=(Config.WORLD_QUADRADO);
        }
        else if (Math.abs(dist)>((Config.WORLD_QUADRADO)+((Config.WORLD_QUADRADO/2))) &&
                Math.abs(dist)<=((Config.WORLD_QUADRADO*2)+((Config.WORLD_QUADRADO/2)))){
            limite=(Config.WORLD_QUADRADO*2);
        }
        else if (Math.abs(dist)>((Config.WORLD_QUADRADO*2)+((Config.WORLD_QUADRADO/2))) &&
                Math.abs(dist)<=((Config.WORLD_QUADRADO*3)+((Config.WORLD_QUADRADO/2)))){
            limite=(Config.WORLD_QUADRADO*3);
        }
        else if (Math.abs(dist)>((Config.WORLD_QUADRADO*3)+((Config.WORLD_QUADRADO/2))) &&
                Math.abs(dist)<=((Config.WORLD_QUADRADO*4)+((Config.WORLD_QUADRADO/2)))){
            limite=(Config.WORLD_QUADRADO*4);
        }
        else if (Math.abs(dist)>((Config.WORLD_QUADRADO*4)+((Config.WORLD_QUADRADO/2))) &&
                Math.abs(dist)<=((Config.WORLD_QUADRADO*5)+((Config.WORLD_QUADRADO/2)))){
            limite=(Config.WORLD_QUADRADO*5);
        }
        else{
            limite=(Config.WORLD_QUADRADO*6);
        }
        return(limite);
    }

    @Override
    public int compareTo(Personagem outroPersonagem) {
        if (this.iniciativa > outroPersonagem.iniciativa) {
            return -1;
        }
        if (this.iniciativa < outroPersonagem.iniciativa) {
            return 1;
        }
        return 0;
    }

    public void atualizaTurno(){
        this.acoes=2;
    }

    public void atualizaArma(Arma armaNova){
        ataque=armaNova;
    }

    public void atualizaEstado() {
        //o personagem possui 3 estados, consciente=2, inconsciente=1 e morto=0
        if (vidaAtual>0) {
            estado=2;
        }
        else if(vidaAtual<0 && vidaAtual>-10){
            estado=1;
        }
        else{
            estado=0;
        }
    }

    public void mouseDownRight(final ArrayList<Colidiveis> espacos, final int zoomX, final int zoomY){
        float distX, distY;
        double distancia;
        posDeslocaX=Gdx.input.getX()-zoomX;
        posDeslocaY= Config.WORLD_HEIGHT-Gdx.input.getY()-zoomY;
        distX=calculaDistanciaParcial(posDeslocaX,posJogadorX+(Config.WORLD_QUADRADO/2));
        distY=calculaDistanciaParcial(posDeslocaY,posJogadorY+(Config.WORLD_QUADRADO/2));
        distancia=calcularDistancia(distX, distY);
        if(distancia<= limiteDeMovimentacao){
            //System.out.println(nome);
            //System.out.println("Possuia "+acoes+" acoes.");
            if(distX>=0){
                vX=2;
                horizontal=2;
                andandoX=true;
                limiteX=limiteMovimentacao(distX);
                //atualiza o deslocaX para o valor atualizado no mapa, a posição correta centralizada
                posDeslocaX=posJogadorX;
                posDeslocaX+=limiteX;
            }
            else if(distX<=0){
                vX=-2;
                horizontal=0;
                andandoX=true;
                limiteX=limiteMovimentacao(distX);
                //atualiza o deslocaX para o valor atualizado no mapa, a posição correta centralizada
                posDeslocaX=posJogadorX;
                posDeslocaX-=limiteX;
            }
            if(distY>=0){
                vY=2;
                vertical=2;
                andandoY=true;
                limiteY=limiteMovimentacao(distY);
                //atualiza o deslocaY para o valor atualizado no mapa, a posição correta centralizada
                posDeslocaY=posJogadorY;
                posDeslocaY+=limiteY;
            }
            else if(distY<=0){
                vY=-2;
                vertical=0;
                andandoY=true;
                limiteY=limiteMovimentacao(distY);
                //atualiza o deslocaY para o valor atualizado no mapa, a posição correta centralizada
                posDeslocaY=posJogadorY;
                posDeslocaY-=limiteY;
            }
            orientacao();
            andou=true;
            for(int i=0; i<espacos.size();i++){
                if(espacos.get(i).x==posDeslocaX){
                    if(espacos.get(i).y==posDeslocaY){
                        posDeslocaY=posJogadorY;
                        posDeslocaX=posJogadorX;
                        andou = false;
                        //System.out.println("Não pode se mover para este local, escolha um novo");
                        //System.out.println("Colisao com espaco: "+i);
                        i=espacos.size();
                    }
                }
            }
        }
    }

    public void mouseDownLeft(final ArrayList<Inimigo> alvos, final int zoomX, final int zoomY, int x, int y){
        //analisar ataques, colocar na tela as rolagens e danos para melhor identificação de problemas
        float distX, distY;
        float posAtaqueX, posAtaqueY;
        int varX, varY;
        //Descobrimos os valores trataveis do clique e atualizamos as variaveis para eles.
        //O inputY é invertido, então descobrimos o seu novo tamanho real.
        posAtaqueX=x-zoomX;
        posAtaqueY=Config.WORLD_HEIGHT-y-zoomY;
            /*
            Calculamos as ditancias entre o clique e a posicao do jogador.
             */
        distX=calculaDistanciaParcial(posAtaqueX,posJogadorX+(Config.WORLD_QUADRADO/2));
        distY=calculaDistanciaParcial(posAtaqueY,posJogadorY+(Config.WORLD_QUADRADO/2));
            /*
            Descobrimos um limite de distancia nos baseando no tamanho dos quadrados.
            Esse novo valor descoberto será o valor do clique em quadrado.
             */
        posAtaqueX=distX/50;
        varX=Math.round(posAtaqueX);
        limiteX=varX*50;
        //System.out.println(""+limiteX);
        posAtaqueY=distY/50;
        varY=Math.round(posAtaqueY);
        limiteY=varY*50;
        //System.out.println(""+limiteY);
        //atualizando o X
        posAtaqueX=posJogadorX;
        if(distX>0){
            horizontal=2;
            posAtaqueX+=limiteX;
        }
        else if(distX<0){
            horizontal=0;
            posAtaqueX+=limiteX;
        }
        //atualizando o Y
        posAtaqueY=posJogadorY;
        if(distY>0){
            vertical=2;
            posAtaqueY+=limiteY;
        }
        else if(distY<0){
            vertical=0;
            posAtaqueY+=limiteY;
        }
        if(possuiInimigo){
            //System.out.println("Tem inimigo");
            //System.out.println(""+posAtaqueX);
            //System.out.println(""+posAtaqueY);
                /*
                Comparando os valores de cliques em quadrados com as posições dos inimigos
                que estão no alcance do jogador descobrimos qual inimigos queremos atacar.
                 */
            for (int i=0;i<alvos.size();i++){
                /*
                System.out.println(""+alvos.get(i).nome);
                System.out.println(""+alvos.get(i).posJogadorX);
                System.out.println(""+alvos.get(i).posJogadorY);
                 */
                if(posAtaqueX==alvos.get(i).posJogadorX){
                    //System.out.println("X deu match");
                    if(posAtaqueY==alvos.get(i).posJogadorY && acoes>0){
                        //System.out.println("Y deu match");
                        atacando=true;
                        alvo=alvos.get(i);
                        //System.out.println(nome);
                        //System.out.println("Possuia "+acoes+" acoes.");
                        dano = atacar(alvos.get(i));
                        //System.out.println("Vida antes do ataque: " + alvos.get(0).vidaAtual);
                        alvos.get(i).vidaAtual -= dano;
                        //System.out.println("Vida depois do ataque: " + alvos.get(0).vidaAtual);
                        acoes=0;
                        //System.out.println("Possui "+acoes+" acoes.");
                    }
                }
            }
        }
    }

    public int atacar(Inimigo alvo){
        int dadoDeAtaque, d=0;
        dadoDeAtaque=1+gerador.nextInt(20);
        acertou=false;
        System.out.println(nome+" atacou com "+dadoDeAtaque+
                " contra "+alvo.nome+" com "+alvo.classeDeArmadura);
        if(dadoDeAtaque>alvo.classeDeArmadura){
            d=1+gerador.nextInt(ataque.danoMaximo);
            acertou=true;
            //System.out.println("Ataque com sucesso causara "+dano);
        }
        else{
            //System.out.println("Ataque falho e causara: "+dano);
        }
        return(d);
    }

    public void orientacao(){
        if(vertical==0){
            if(horizontal==0){
                posicao=baixoEsquerda;
            }
            else if(horizontal==1){
                posicao=baixo;
            }
            else if(horizontal==2){
                posicao=baixoDireita;
            }
        }
        else if(vertical==1){
            if(horizontal==0){
                posicao=esquerda;
            }
            else if(horizontal==1){
                posicao=parado;
            }
            else if(horizontal==2){
                posicao=direita;
            }
        }
        else if(vertical==2){
            if(horizontal==0){
                posicao=cimaEsquerda;
            }
            else if(horizontal==1){
                posicao=cima;
            }
            else if(horizontal==2){
                posicao=cimaDireita;
            }
        }
    }
}
