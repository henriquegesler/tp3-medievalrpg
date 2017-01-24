package medievalRPG.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import medievalRPG.Config;
import medievalRPG.Item.Arma;
import medievalRPG.characters.Inimigo;
import medievalRPG.characters.Personagem;
import medievalRPG.maps.Mapa;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Henrique on 22/11/2016.
 */
public class BattleScreen extends BaseScreen {
    protected TextureRegion[][] quadrosAtaque;
    protected Animation atacando, defendendo, dano;
    private int zoomX, zoomY, contador;
    private float tamanhoMenuLateral, tempoDaAnimacao;
    private Texture spriteSheetAtaques, barraLateral, barra, quadradoMovimento, turn;
    private Personagem personagemAtivo;
    private Inimigo personagemInimigoAtivo;
    private ArrayList<Personagem> perDoJogador, listaTurno;
    private ArrayList<Personagem> alvosInimigos;
    private ArrayList<Inimigo> perInimigos, alvos;
    private ArrayList<Arma> armas;
    private Mapa mapa;
    private boolean novoTurno, grade, pause;

    public BattleScreen(Game game, BaseScreen previous,
                        ArrayList<Arma> armas, ArrayList<Personagem> personagens, ArrayList<Inimigo> inimigos,
                        Mapa m) {
        super(game, previous);
        this.perDoJogador=personagens;
        this.perInimigos=inimigos;
        this.mapa=m;
        this.armas=armas;
        turn = new Texture("nextTurn.png");
        spriteSheetAtaques = new Texture("spriteSheetAtaques.png");
        quadrosAtaque = TextureRegion.split(spriteSheetAtaques,100,100);
        dano = animacaoAtaque(0);
        atacando = animacaoAtaque(1);
        defendendo = animacaoAtaque(2);
        contador=0;
        novoTurno=true;
        tamanhoMenuLateral= Config.WORLD_WIDTH*0.2f;
        zoomX =0;
        zoomY =0;
        grade=false;
        pause=true;
        personagemInimigoAtivo=null;
        personagemAtivo=null;
        //Define a música tema
        //menuMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/menu.mp3"));
    }

    @Override
    public void appear() {
        Config.WORLD_QUADRADO=50;
        barraLateral = new Texture("barraLateral.png");
        barra = new Texture("barra.png");
        quadradoMovimento = new Texture("quadradoMovimento.png");
        //criação do vetor de lista do turno
        listaTurno = new ArrayList<Personagem>();
        for(int i=0; i<perInimigos.size(); i++){
            Personagem teste;
            teste = perInimigos.get(i);
            listaTurno.add(teste);
        }
        listaTurno.addAll(perDoJogador);
        Collections.sort(listaTurno);
        for(int i=0;i<listaTurno.size();i++){
           mapa.createEspaco(listaTurno.get(i).posJogadorX,listaTurno.get(i).posJogadorY);
        }

    }

    @Override
    public void cleanUp() {

    }

    @Override
    public void update(float dt) {
        mapa.atualizaEspaco(contador,listaTurno.get(contador).posJogadorX,listaTurno.get(contador).posJogadorY);
        //encontra o personagem ativo controlado pelo computador
        if(listaTurno.get(contador).inimigo){
            Inimigo teste = null;
            for(int i=0;i<perInimigos.size();i++){
                if(perInimigos.get(i).id == listaTurno.get(contador).id){
                    teste=perInimigos.get(i);
                    i=perInimigos.size();
                }
            }
            personagemInimigoAtivo=teste;
            // verifica se o personagem ativo já esgotou suas açoes
            if(personagemInimigoAtivo.acoes==0){
                //gerenciaTurno();
            }
        }

        //encontra o personagem ativo controlado pelo jogador
        else if(!listaTurno.get(contador).inimigo){
            Personagem teste = null;
            for(int i=0;i<perDoJogador.size();i++){
                if(perDoJogador.get(i).id == listaTurno.get(contador).id){
                    teste=perDoJogador.get(i);
                    i=perDoJogador.size();
                }
            }
            personagemAtivo=teste;
            // verifica se o personagem ativo já esgotou suas açoes
            //if(personagemAtivo!=null){
                if(personagemAtivo.acoes==0){
                    //gerenciaTurno();
                }
            //}
        }

        //ativa o update do personagem de jogador
        if(personagemAtivo!=null){
            personagemAtivo.update();
            if (!personagemAtivo.andandoX && !personagemAtivo.andandoY){
                alvos= personagemAtivo.verificandoArredores(perInimigos);
            }
        }
        //ativa o update dos inimigos
        if(personagemInimigoAtivo!=null){
            personagemInimigoAtivo.update();
            if(personagemInimigoAtivo.acoes>0){
                alvosInimigos=personagemInimigoAtivo.verificandoArredores2(perDoJogador);
                personagemInimigoAtivo.tomarDecisao(alvosInimigos, perDoJogador, mapa.colidiveis);
            }
        }
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
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            zoomX++;
            if(zoomX >=0){
                zoomX--;
            }
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            zoomX--;
            if(zoomX <=-(mapa.tamanhoMapX-(Config.WORLD_WIDTH-tamanhoMenuLateral))){
                zoomX++;
            }
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            zoomY--;
            if(zoomY <=-(mapa.tamanhoMapY-Config.WORLD_HEIGHT)){
                zoomY++;
            }
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            zoomY++;
            if(zoomY >=0){
                zoomY--;
            }
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.F1)){
            if(grade){
                grade=false;
            }
            else{
                grade=true;
            }
        }
        else if(Gdx.input.isKeyJustPressed(Input.Keys.F2)){
            gerenciaTurno();
        }
        if(personagemAtivo !=null){
            personagemAtivo.handleInput(alvos, mapa.colidiveis, zoomX, zoomY);
        }
    }

    @Override
    public void draw() {
        batch.begin();
        mapa.render(batch, zoomX, zoomY);
        if(grade){
            for(int x=0; x<mapa.tamanhoMapX; x+=Config.WORLD_QUADRADO){
                for(int y=0;y<mapa.tamanhoMapY;y+=Config.WORLD_QUADRADO){
                    batch.draw(quadradoMovimento, x+ zoomX, y+ zoomY, Config.WORLD_QUADRADO, Config.WORLD_QUADRADO);
                }
            }
        }
        if (personagemAtivo !=null){
            //chamada para desenhar as zonas de alcanceAtaque dos jogadores
            personagemAtivo.renderZonas(batch, zoomX, zoomY);
            /*if(alvos!=null){
                for (int i=0;i<alvos.size();i++){
                    drawCenterAlignedText("Número do alvo:"+i,
                            1f, viewport.getWorldHeight() * (0.9f-(i*0.1f)));
                }
            }*/
            if(personagemAtivo.atacando){
                renderAnimacao(batch,personagemAtivo,atacando);
                if(personagemAtivo.acertou){
                    renderAnimacao(batch,personagemAtivo.alvo,dano);
                }
                else{
                    renderAnimacao(batch,personagemAtivo.alvo,defendendo);
                }
            }
            /*
            isso aqui serve para testar se ele está andando direito
            drawCenterAlignedText("Clique X: "+personagemAtivo.posDeslocaX+"pX: "+(personagemAtivo.posJogadorX+(Config.WORLD_QUADRADO/2)),
                    1f, viewport.getWorldHeight() * 0.2f);
            drawCenterAlignedText("Clique Y: "+personagemAtivo.posDeslocaY+"pY: "+(personagemAtivo.posJogadorY+(Config.WORLD_QUADRADO/2)),
                    1f, viewport.getWorldHeight() * 0.1f);
            */
        }
        if(personagemInimigoAtivo!=null){
            if(personagemInimigoAtivo.atacando){
                renderAnimacao(batch,personagemInimigoAtivo,atacando);
                if(personagemInimigoAtivo.acertou){
                    renderAnimacao(batch,personagemInimigoAtivo.alvo,dano);
                }
                else{
                    renderAnimacao(batch,personagemInimigoAtivo.alvo,defendendo);
                }
            }
        }
        for (int i=0;i<perDoJogador.size();i++){
            if(perDoJogador.get(i).estado==2){
                perDoJogador.get(i).render(batch, zoomX, zoomY);
            }
        }
        for (int i=0;i<perInimigos.size();i++){
            if(perInimigos.get(i).estado==2){
                perInimigos.get(i).render(batch, zoomX, zoomY);
            }
        }
        /*
        * ******************************************************************************************************
        * Tela ao lado
        * */
        //barra lateral
        batch.draw(barraLateral, Config.WORLD_WIDTH-tamanhoMenuLateral, 0,
                tamanhoMenuLateral, Config.WORLD_HEIGHT);
        int parametro;
        if((listaTurno.size()-contador)>6){
            parametro=6;
        }
        else{
            parametro=listaTurno.size();
        }
        for (int i=contador;i<parametro;i++){
            drawText(listaTurno.get(i).nome, 0.5f,
                    viewport.getWorldHeight() * (0.99f-((i-contador)*0.04f)), viewport.getWorldWidth()-tamanhoMenuLateral+5,0);
        }
        //barra dividindo a lista de turno pra descricao de personagem
        batch.draw(barra, Config.WORLD_WIDTH-tamanhoMenuLateral, Config.WORLD_HEIGHT*(0.99f-((6*0.04f)))-5,
                tamanhoMenuLateral, 5);
        //nome do personagem ativo
        drawText(listaTurno.get(contador).nome, 0.6f,
                Config.WORLD_HEIGHT*(0.99f-((6*0.04f)))-15, viewport.getWorldWidth()-tamanhoMenuLateral+5,0);
        //HP do personagem ativo
        drawText("HP: "+listaTurno.get(contador).vidaAtual, 0.5f,
                Config.WORLD_HEIGHT*(0.99f-((6*0.04f)))-45, viewport.getWorldWidth()-tamanhoMenuLateral+5,0);
        //classe de armadura do personagem ativo
        drawText("Acoes: "+listaTurno.get(contador).acoes, 0.5f,
                Config.WORLD_HEIGHT*(0.99f-((6*0.04f)))-45, viewport.getWorldWidth()-(tamanhoMenuLateral*0.5f),0);
        //dano provavel do personagem ativo
        drawText("Ataque: 1-"+listaTurno.get(contador).ataque.danoMaximo, 0.5f,
                Config.WORLD_HEIGHT*(0.99f-((6*0.04f)))-70, viewport.getWorldWidth()-tamanhoMenuLateral+5,0);
        //barra dividindo a descricao de personagem e
        batch.draw(barra, Config.WORLD_WIDTH-tamanhoMenuLateral, Config.WORLD_HEIGHT*(0.99f-((6*0.04f)))-95,
                tamanhoMenuLateral, 5);
        if(listaTurno.get(contador)!=null && listaTurno.get(contador).alvo!=null){
            drawText(listaTurno.get(contador).nome+" atacou ", 0.5f,
                    Config.WORLD_HEIGHT*(0.99f-((6*0.04f)))-100, viewport.getWorldWidth()-tamanhoMenuLateral+5,0);
            drawText(listaTurno.get(contador).alvo.nome, 0.5f,
                    Config.WORLD_HEIGHT*(0.99f-((6*0.04f)))-125, viewport.getWorldWidth()-tamanhoMenuLateral+5,0);
            if(listaTurno.get(contador).acertou && listaTurno.get(contador).atacando){
                drawText(listaTurno.get(contador).nome+" acertou ", 0.5f,
                        Config.WORLD_HEIGHT*(0.99f-((6*0.04f)))-150, viewport.getWorldWidth()-tamanhoMenuLateral+5,0);
                drawText(listaTurno.get(contador).alvo.nome, 0.5f,
                        Config.WORLD_HEIGHT*(0.99f-((6*0.04f)))-175, viewport.getWorldWidth()-tamanhoMenuLateral+5,0);
                drawText("com "+listaTurno.get(contador).dano+" dano.", 0.5f,
                        Config.WORLD_HEIGHT*(0.99f-((6*0.04f)))-200, viewport.getWorldWidth()-tamanhoMenuLateral+5,0);
                drawText("Vida restante:", 0.5f,
                        Config.WORLD_HEIGHT*(0.99f-((6*0.04f)))-225, viewport.getWorldWidth()-tamanhoMenuLateral+5,0);
                drawText(""+listaTurno.get(contador).alvo.vidaAtual, 0.5f,
                        Config.WORLD_HEIGHT*(0.99f-((6*0.04f)))-250, viewport.getWorldWidth()-tamanhoMenuLateral+5,0);
            }
            else if(!listaTurno.get(contador).acertou && listaTurno.get(contador).atacando){
                drawText(listaTurno.get(contador).nome+" errou  ", 0.5f,
                        Config.WORLD_HEIGHT*(0.99f-((6*0.04f)))-150, viewport.getWorldWidth()-tamanhoMenuLateral+5,0);
                drawText(listaTurno.get(contador).alvo.nome, 0.5f,
                        Config.WORLD_HEIGHT*(0.99f-((6*0.04f)))-175, viewport.getWorldWidth()-tamanhoMenuLateral+5,0);
            }
        }
        //terminar aqui.
        //batch.draw(turn,viewport.getWorldWidth()-tamanhoMenuLateral+10,Config.WORLD_HEIGHT*(0.99f-((6*0.04f)))-250,
          //      150,100);
        batch.end();
    }

    public void gerenciaTurno(){
        boolean removeu;
        removeu = false;
        listaTurno.get(contador).atacando=false;
        //verifica se algum jogador está inconsciente ou morto e o retira da lista de jogadores ativos
        System.out.println("Verificando se elimina alguem da lista");
        for(int i=0;i<listaTurno.size();i++){
            listaTurno.get(i).atualizaEstado();
            if(listaTurno.get(i).estado != 2){
                System.out.println("Removeu "+listaTurno.get(i).nome);
                listaTurno.remove(i);
                mapa.removeEspaco(i);
                removeu=true;
            }
        }
        /*
                Verifica se após a remoção da lista, o personagem ativo é igual ao ultimo personagem que jogou
                caso seja, adiciona +1 ao contador, caso não seja, o contador se mantem na posição atual.
                Essa verificação é necessária pois ao excluir um personagem da listaTurno, caso esse personagem já
                tenha jogado no turno de sua eliminação, haveria confusão na lista a partir daquele momento.
                */
        if(removeu && contador<listaTurno.size()){
            if(personagemAtivo!=null){
                if(listaTurno.get(contador).id==personagemAtivo.id){
                    contador++;
                }
            }
            if(personagemInimigoAtivo!=null){
                if(listaTurno.get(contador).id==personagemInimigoAtivo.id){
                    contador++;
                }
            }
        }
        else{
            contador++;
        }
        System.out.println("Alterando personagem");
        //caso atinga o tamanho da lista, reseta a lista e atualiza o turno
        if (contador>=listaTurno.size()){
            contador=0;
            for (int i=0;i<listaTurno.size();i++){
                if(listaTurno.get(i).estado==2){
                    listaTurno.get(i).atualizaTurno();
                }
            }
        }
        personagemAtivo=null;
        personagemInimigoAtivo=null;
    }

    public void renderAnimacao(SpriteBatch batch, Personagem p, Animation estate) {
        tempoDaAnimacao += Gdx.graphics.getDeltaTime();
        TextureRegion quadroCorrente = estate.getKeyFrame(tempoDaAnimacao);
        batch.draw(quadroCorrente,
                p.posJogadorX, p.posJogadorY,50,50);
    }

    public Animation animacaoAtaque(int linha){
        Animation movimento;
        movimento = new Animation(1.25f, new TextureRegion[] {
                quadrosAtaque[linha][0],
                quadrosAtaque[linha][1],
                quadrosAtaque[linha][2],
                quadrosAtaque[linha][3],
        });
        movimento.setPlayMode(Animation.PlayMode.LOOP);
        return(movimento);
    }
}
