import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class Tools {

    private int larguraImagem, alturaImagem;

    private int [][]redEntrada;
	private int [][]greenEntrada;
    private int [][]blueEntrada;
    private int [][]redSaida;
	private int [][]greenSaida;
    private int [][]blueSaida;

    private BufferedImage img, imgCopy;

    private Raster raster;
    private WritableRaster wraster;
    
    public Tools(int larguraImagem, int alturaImagem, BufferedImage img, BufferedImage imgCopy) {
        this.larguraImagem = larguraImagem;
        this.alturaImagem = alturaImagem;
        this.img = img;
        this.imgCopy = imgCopy;
        this.raster = img.getRaster();
        this.wraster = imgCopy.getRaster();
        this.redEntrada = new int[this.larguraImagem][this.alturaImagem];
        this.greenEntrada = new int[this.larguraImagem][this.alturaImagem];
        this.blueEntrada = new int[this.larguraImagem][this.alturaImagem];
        this.redSaida = new int[this.larguraImagem][this.alturaImagem];
        this.greenSaida = new int[this.larguraImagem][this.alturaImagem];
        this.blueSaida = new int[this.larguraImagem][this.alturaImagem];
    }

    public void criar_matriz_entrada() {

        for(int i=0 ; i<this.larguraImagem ; i++) {
            for(int j=0 ; j<this.alturaImagem ; j++) {
                redEntrada[i][j] = raster.getSample(i,j,0);
                greenEntrada[i][j] = raster.getSample(i,j,1);
                blueEntrada[i][j] = raster.getSample(i,j,2);
            }
        }
    }

    public void criar_imagem_saida() {

        for(int i=0 ; i<this.larguraImagem ; i++) {
            for(int j=0 ; j<this.alturaImagem ; j++) {
                this.wraster.setSample(i, j, 0, redSaida[i][j]);
                this.wraster.setSample(i, j, 1, greenSaida[i][j]);
                this.wraster.setSample(i, j, 2, blueSaida[i][j]);
            }
        }        
    }

    public int get_red_entrada(int x, int y) {
        return this.redEntrada[x][y];
    }

    public int get_green_entrada(int x, int y) {
        return this.greenEntrada[x][y];
    }

    public int get_blue_entrada(int x, int y) {
        return this.blueEntrada[x][y];
    }

    public void set_red_saida(int x, int y, int value) {
        this.redSaida[x][y] = value;
    }

    public void set_green_saida(int x, int y, int value) {
        this.greenSaida[x][y] = value;
    }

    public void set_blue_saida(int x, int y, int value) {
        this.blueSaida[x][y] = value;
    }

    public BufferedImage get_img_saida() {
        return this.imgCopy;
    }
}