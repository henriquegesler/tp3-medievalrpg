package medievalRPG;

/**
 * Created by Henrique on 22/11/2016.
 */
public class Config {
    /**
     * O tamanho do quadrado do mundo
     */
    public static int WORLD_ZOOM = 1;
    public static int WORLD_QUADRADO = 50*WORLD_ZOOM;


    /**
     * A largura do mundo de jogo.
     *
     * Todos os objetos (sprites, etc.) devem estar contidos em coordenadas x
     * que vão de 0 a WORLD_WIDTH para que apareçam na tela.
     */
    public static final int WORLD_WIDTH = 1280;

    /**
     * A altura do mundo de jogo.
     *
     * Todos os objetos (sprites, etc.) devem estar contidos em coordenadas y
     * que vão de 0 a WORLD_HEIGHT para que apareçam na tela.
     */
    public static final int WORLD_HEIGHT = 720;

    /**
     * A razão de aspecto do mundo de jogo, igual a 16:9.
     *
     * Mesmo que a janela/tela em que o jogo está sendo renderizado não tenha
     * este valor de razão de aspecto, o jogo será sempre renderizado com ela.
     *
     * Caso a razão de aspecto seja menor (e.g., 4:3), barras superiores e
     * inferiores "em branco" aparecerão e o jogo será renderizado apenas no
     * centro do espaço total.
     *
     * Caso a razão de aspecto seja maior (e.g., 16:10), as barras "em branco"
     * são laterais.
     */
    public static final float DESIRED_ASPECT_RATIO
            = (float) WORLD_WIDTH / (float) WORLD_HEIGHT;


    /**
     * Tempo em que a tela de splash fica sendo mostrada.
     */
    public static final long TIME_ON_SPLASH_SCREEN = 3750;
}
