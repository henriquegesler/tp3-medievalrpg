package medievalRPG.characters;

import medievalRPG.Config;
import medievalRPG.maps.Colidiveis;

import java.util.ArrayList;

/**
 * Created by Henrique on 25/11/2016.
 */
public class Inimigo extends Personagem{
    public boolean possivelAndar;

    public Inimigo(String n, float jogadorX, float jogadorY, int id) {
        super(n,jogadorX, jogadorY, id);
        iniciativa=gerador.nextInt(21);
        nome="Inimigo "+id;
        inimigo=true;
        possivelAndar=false;
        posicao=parado;
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
                //System.out.println("Possui "+acoes+" acoes");
                andou=false;
                possivelAndar=false;
            }
        }
    }


    public ArrayList<Personagem> verificandoArredores2(ArrayList<Personagem> list){
        ArrayList<Personagem> alcancados = new ArrayList<Personagem>();
        Personagem candidato;
        float dX, dY;
        double dist;
        possuiInimigo=false;
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

    public void tomarDecisao(ArrayList<Personagem> alvos, ArrayList<Personagem> inimigos, ArrayList<Colidiveis> espacos){
        if(possuiInimigo){
            if(!andandoY && !andandoX && !andou) {
                //System.out.println(nome);
                //System.out.println("Possuia "+acoes+" acoes.");
                atacando=true;
                alvo =alvos.get(0);
                dano = atacarInimigo(alvos.get(0));
                //System.out.println("Vida antes do ataque: " + alvos.get(0).vidaAtual);
                alvos.get(0).vidaAtual -= dano;
                //System.out.println("Vida depois do ataque: " + alvos.get(0).vidaAtual);
                acoes = 0;
                //System.out.println("Possui "+acoes+" acoes.");
            }
        }
        else{
            if(!andandoY && !andandoX && !andou){
                Personagem alvo;
                float distX, distY, tentativa;
                double distancia;
                alvo=acharInimigo(inimigos);
                tentativa=1;
                posDeslocaX=alvo.posJogadorX;
                posDeslocaY=alvo.posJogadorY;
                //System.out.println(nome);
                //System.out.println("Possuia "+acoes+" acoes.");
                while(possivelAndar==false){
                    vX=0;
                    vY=0;
                    distX=calculaDistanciaParcial(posDeslocaX+(Config.WORLD_QUADRADO/2),posJogadorX+(Config.WORLD_QUADRADO/2));
                    distY=calculaDistanciaParcial(posDeslocaY+(Config.WORLD_QUADRADO/2),posJogadorY+(Config.WORLD_QUADRADO/2));
                    distancia=calcularDistancia(distX, distY);
                    //System.out.println(distancia);
                    /*Caso a distancia seja maior que a movimentacao precisamos saber até onde ele deve andar*/
                    if(distancia>limiteDeMovimentacao){
                        double parametro;
                        //parametro é um parametro de reduçao dos vetores distancia para distancia que pode ser percorrida
                        parametro=limiteDeMovimentacao/distancia;
                        distX*=parametro;
                        limiteX=limiteMovimentacao(distX);
                        distX/=parametro;

                        distY*=parametro;
                        limiteY=limiteMovimentacao(distY);
                        distY/=parametro;
                    }
                    /*Caso a distancia esteja dentro do tamanho, apenas recebe a distancia de cada ordenada*/
                    else if(distancia<=limiteDeMovimentacao){
                        limiteX=limiteMovimentacao(distX);
                        limiteY=limiteMovimentacao(distY);
                    }
                    /*Aqui começa a setar a posição X para onde o jogador vai*/
                    posDeslocaX=posJogadorX;
                    //verifica se vai para direita ou esquerda
                    if(distX>0){
                        posDeslocaX+=limiteX;
                    }
                    else if(distX<0){
                        posDeslocaX-=limiteX;
                    }
                    /*Posição X está escolhida*/
                    /*Aqui começa a setar a posição Y para onde o jogador vai*/
                    posDeslocaY=posJogadorY;
                    //verifica se vai para cima ou para baixo
                    if(distY>0){
                        posDeslocaY+=limiteY;
                    }
                    else if(distY<0){
                        posDeslocaY-=limiteY;
                    }
                    /*Posição Y está escolhida*/
                    if(limiteX!=0){
                        if(distX>0){
                            vX=2;
                            horizontal=2;
                            andandoX=true;
                        }
                        else if(distX<0){
                            vX=-2;
                            horizontal=0;
                            andandoX=true;
                        }
                    }
                    if(limiteY!=0){
                        if(distY>0){
                            vY=2;
                            vertical=2;
                            andandoY=true;
                        }
                        else if(distY<0){
                            vY=-2;
                            vertical=0;
                            andandoY=true;
                        }
                    }
                    orientacao();
                    //System.out.println(""+tentativa);
                    //System.out.println(""+posDeslocaX);
                    //System.out.println(""+posDeslocaY);
                    if(tentativa<9) {
                        for (int i = 0; i <= espacos.size(); i++) {
                            if (i == espacos.size()) {
                                possivelAndar = true;
                            } else if (espacos.get(i).x == posDeslocaX) {
                                if (espacos.get(i).y == posDeslocaY) {
                                    posDeslocaX = alvo.posJogadorX;
                                    posDeslocaY = alvo.posJogadorY;
                                    if (distX <= 0) {
                                        if (tentativa == 1 || tentativa == 2 || tentativa == 4) {
                                            posDeslocaX += Config.WORLD_QUADRADO;
                                        } else if (tentativa == 5 || tentativa == 7 || tentativa == 8) {
                                            posDeslocaX -= Config.WORLD_QUADRADO;
                                        }
                                    } else {
                                        if (tentativa == 1 || tentativa == 2 || tentativa == 4) {
                                            posDeslocaX -= Config.WORLD_QUADRADO;
                                        } else if (tentativa == 5 || tentativa == 7 || tentativa == 8) {
                                            posDeslocaX += Config.WORLD_QUADRADO;
                                        }
                                    }
                                    if (distY >= 0) {
                                        if (tentativa == 1 || tentativa == 3 || tentativa == 5) {
                                            posDeslocaY -= Config.WORLD_QUADRADO;
                                        } else if (tentativa == 4 || tentativa == 6 || tentativa == 8) {
                                            posDeslocaY += Config.WORLD_QUADRADO;
                                        }
                                    } else {
                                        if (tentativa == 1 || tentativa == 3 || tentativa == 5) {
                                            posDeslocaY += Config.WORLD_QUADRADO;
                                        } else if (tentativa == 4 || tentativa == 6 || tentativa == 8) {
                                            posDeslocaY -= Config.WORLD_QUADRADO;
                                        }
                                    }
                                    possivelAndar = false;
                                    i = 100;
                                }
                            }
                        }
                    }
                    tentativa++;
                    if(tentativa==9){
                        //System.out.println("Olar");
                        posDeslocaX=alvo.posJogadorX;
                        //System.out.println(""+posDeslocaX);
                        posDeslocaY=alvo.posJogadorY;
                    }
                    else if(tentativa>9){
                        if(distX<=0){
                            //System.out.println(""+posDeslocaX);
                            //System.out.println("Passou aqui 1");
                            posDeslocaX+=(Config.WORLD_QUADRADO);
                        }
                        else{
                            //System.out.println("Passou aqui 2");
                            posDeslocaX-=Config.WORLD_QUADRADO;
                        }
                        possivelAndar=true;
                    }
                }
                andou=true;
            }
        }
    }

    public Personagem acharInimigo(ArrayList<Personagem> alvos){
        int parametro=0, numeroAlvo=0;
        Personagem alvo = null;
        for(int i=0;i<alvos.size();i++){
            alvo=alvos.get(i);
            if(alvo.vida>parametro){
                if(alvo.estado==2){
                    parametro=alvo.vida;
                    numeroAlvo=i;
                }

            }
        }
        return(alvos.get(numeroAlvo));
    }

    public int atacarInimigo(Personagem alvo){
        int dadoDeAtaque, d=0;
        dadoDeAtaque=1+gerador.nextInt(20);
        acertou=false;
        //System.out.println(nome+" atacou com "+dadoDeAtaque+
          //      " contra "+alvo.nome+" com "+alvo.classeDeArmadura);
        if(dadoDeAtaque>alvo.classeDeArmadura){
            d=1+gerador.nextInt(ataque.danoMaximo);
            acertou=true;
            //System.out.println("Ataque com sucesso causara "+dano);
        }
        else{
           // System.out.println("Ataque falho e causara: "+dano);
        }
        return(d);
    }
}
