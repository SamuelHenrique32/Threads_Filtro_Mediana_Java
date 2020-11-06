import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {

	private static final int kQUANT_PARAMETROS = 2;

	private static final String kNOME_ARQUIVO_ENTRADA = "heavy.bmp";

	private static final String kNOME_ARQUIVO_SAIDA = "out.bmp";

	public static void main(String[] args) {
		
		int tamanhoMascara, nroThreads, deslPosMascara;

		File file = new File("../images/" + kNOME_ARQUIVO_SAIDA);
		
		BufferedImage img = null, imgCopy = null;

		Tools tools;
		Filtro[] filtro;

		if(args.length != kQUANT_PARAMETROS ){

			System.out.printf("java Main <tamanho_mascara> <nro_threads>\n");

			return;
		}

		tamanhoMascara = Integer.parseInt(args[0]);
		nroThreads = Integer.parseInt(args[1]);

		deslPosMascara = (tamanhoMascara/2);

		System.out.println("Tamanho da mascara: " + tamanhoMascara);
		System.out.println("Numero de threads: " + nroThreads);

		filtro = new Filtro[nroThreads];

		try {

			img = ImageIO.read(new File("../images/" + kNOME_ARQUIVO_ENTRADA));

		} catch (IOException e) {

			System.out.println("Erro ao ler arquivo de entrada");
		}

		System.out.println("Largura da imagem lida: " + img.getWidth());

		System.out.println("Altura da imagem lida: " + img.getHeight() + "\n");

		imgCopy = img;

		tools = new Tools(img.getWidth(), img.getHeight(), img, imgCopy);

		tools.criar_matriz_entrada();

		for(int i=0 ; i<nroThreads ; i++) {

			filtro[i] = new Filtro(i, nroThreads, tamanhoMascara, deslPosMascara, img.getWidth(), img.getHeight(), tools);
			filtro[i].start();
		}

		for(int i=0 ; i<nroThreads ; i++) {

			try {
				filtro[i].join();
			}	
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		tools.criar_imagem_saida();

		try {

			ImageIO.write(imgCopy, "BMP", file);

		} catch (IOException e) {

			System.out.println("Erro ao escrever arquivo de saida");
		}    			
	}	
}