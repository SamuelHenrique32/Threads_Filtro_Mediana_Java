public class Filtro extends Thread {

    private int tid, nthreads, tamanhoMascara, deslPosMascara;
    private int larguraImagem, alturaImagem;

    private Tools tools;

    public Filtro(int tid, int nthreads, int tamanhoMascara, int deslPosMascara, int larguraImagem, int alturaImagem, Tools tools) {

        this.tid = tid;
        this.nthreads = nthreads;
        this.tamanhoMascara = tamanhoMascara;
        this.deslPosMascara = deslPosMascara;
        this.larguraImagem = larguraImagem;
        this.alturaImagem = alturaImagem;
        this.tools = tools;
    }

    private void debug_delay() {

        try { 
            Thread.sleep(1000);
        } 
        catch (InterruptedException ex) {

        }
    }

    private void bubble_sort(int v[], int size) {

        int k, j, aux;
    
        for(k=(size - 1) ; k>0 ; k--) {
            for(j=0 ; j<k ; j++) {
    
                if(v[j] > v[j + 1]) {
                    aux = v[j];
                    v[j] = v[j+1];
                    v[j+1] = aux;
                }
            }
        }
    }

    int median(int v[], int tamanhoMascara) {

        int posElemento = 0, val1 = 0, val2 = 0;
    
        if((tamanhoMascara*tamanhoMascara)%2 == 0) {
    
            val1 = v[(tamanhoMascara*tamanhoMascara/2) - 1];
    
            val2 = v[tamanhoMascara*tamanhoMascara/2];
    
            val1 += val2;
    
            return (val1/2);
        }
        else {
    
            val1 = v[(tamanhoMascara*tamanhoMascara-1)/2];
  
            return val1;
        }
    }
    
    public void run() {

        int posVetMascaraRed = 0, posVetMascaraGreen = 0, posVetMascaraBlue = 0, posX, posY, startX, startY, i, j;
        int medianRed, medianGreen, medianBlue;
        int vetmascaraRedInt[];
        int vetmascaraGreenInt[]; 
        int vetmascaraBlueInt[];
        int mascaraVet[] = new int[this.tamanhoMascara*this.tamanhoMascara];
        int currentColor = 0, colorValueToStore = 0;

        // Posicao atual na matriz
        posX = deslPosMascara;
        posY = deslPosMascara+this.tid;

        // Posicao inicial de deslocamento em X para mascara
        startX = 0;

        // Posicao corrente de deslocamento em X para mascara
        j = startX;

        // Posicao inicial de deslocamento em Y para mascara
        startY = posY-deslPosMascara;

        // Posicao corrente de deslocamento em Y para mascara
        i = startY;

        //System.out.printf("Thread id: %d", this.tid);
        //System.out.printf(" posX = %d posY = %d\n\n", posX, posY);

        /*for(int y=0 ; y<this.larguraImagem ; y++) {
            for(int z=0 ; z<this.alturaImagem ; z++) {
                System.out.printf("%d|%d|%d\t\t\t\t", raster.getSample(y,z,0), raster.getSample(y,z,1), raster.getSample(y,z,2));
            }
            System.out.printf("\n");
        }

        System.out.printf("\n\n");*/

        // Para cada pixel da imagem
        while((posX<this.larguraImagem) && (posY<this.alturaImagem)) {

            if((startX>=0) && (startY>=0)) {

                vetmascaraRedInt = new int[this.tamanhoMascara*this.tamanhoMascara];
                vetmascaraGreenInt = new int[this.tamanhoMascara*this.tamanhoMascara]; 
                vetmascaraBlueInt = new int[this.tamanhoMascara*this.tamanhoMascara];

                // Processamento para pixel atual
                while(true) {

                    vetmascaraRedInt[posVetMascaraRed] = this.tools.get_red_entrada(j,i);
                    vetmascaraGreenInt[posVetMascaraGreen] = this.tools.get_green_entrada(j,i);
                    vetmascaraBlueInt[posVetMascaraBlue] = this.tools.get_blue_entrada(j,i);
    
                    medianRed = 0;
                    medianGreen = 0;
                    medianBlue = 0;

                    posVetMascaraRed++;
                    posVetMascaraGreen++;
                    posVetMascaraBlue++;

                    // Incrementa coluna
                    if((j+1-startX) <= (deslPosMascara*2)) {
                        j++;
                    }
                    else {

                        // Troca linha
                        i++;
                        j = startX;
                    }

                    // Proximo pixel
                    if(posVetMascaraRed >= (tamanhoMascara*tamanhoMascara)) {

                        //System.out.print("Mascara Red antes ordenacao:");                    

                        for(int x=0 ; x<tamanhoMascara*tamanhoMascara ; x++) {
                            mascaraVet[x] = vetmascaraRedInt[x];
                            //System.out.printf("%d ", mascaraVet[x]);
                        }
                        //System.out.println();

                        bubble_sort(mascaraVet, tamanhoMascara*tamanhoMascara);

                        //System.out.print("Mascara Red apos ordenacao:");                    

                        /*for(int x=0 ; x<tamanhoMascara*tamanhoMascara ; x++) {
                            System.out.printf("%d ", mascaraVet[x]);
                        }*/

                        medianRed = median(mascaraVet, tamanhoMascara);

                        for(int x=0 ; x<tamanhoMascara*tamanhoMascara ; x++) {
                            mascaraVet[x] = vetmascaraGreenInt[x];
                            //System.out.printf("%d ", mascaraVet[x]);
                        }

                        bubble_sort(mascaraVet, tamanhoMascara*tamanhoMascara);

                        medianGreen = median(mascaraVet, tamanhoMascara);

                        for(int x=0 ; x<tamanhoMascara*tamanhoMascara ; x++) {
                            mascaraVet[x] = vetmascaraBlueInt[x];
                            //System.out.printf("%d ", mascaraVet[x]);
                        }

                        bubble_sort(mascaraVet, tamanhoMascara*tamanhoMascara);

                        medianBlue = median(mascaraVet, tamanhoMascara);
                        
                        this.tools.set_red_saida(posX, posY, medianRed);
                        this.tools.set_green_saida(posX, posY, medianGreen);
                        this.tools.set_blue_saida(posX, posY, medianBlue);

                        posVetMascaraRed = 0;
                        posVetMascaraGreen = 0;
                        posVetMascaraBlue = 0;

                        break;
                    }
                }    
            }


            // Pixel a direita
            if((posX+1) <= ((this.larguraImagem-1)-deslPosMascara)) {

                posX++;

                // Posicao inicial de deslocamento em X para mascara
                startX = posX-deslPosMascara;

                // Posicao corrente de deslocamento em X para mascara
                j = startX;

                // Posicao inicial de deslocamento em Y para mascara
                startY = posY-deslPosMascara;

                // Posicao corrente de deslocamento em Y para mascara
                i = startY;

                //System.out.printf("\n\nThread id: %d pixel a direita\n", id);
                //System.out.printf("posX = %d posY = %d\n", posX, posY);
            }
            // Pixel abaixo
            else if((posY+this.nthreads) <= ((this.alturaImagem-1)-deslPosMascara)) {

                posX = deslPosMascara;

                posY += this.nthreads;

                // Posicao inicial de deslocamento em X para mascara
                startX = posX-deslPosMascara;

                // Posicao corrente de deslocamento em X para mascara
                j = startX;

                // Posicao inicial de deslocamento em Y para mascara
                startY = posY-deslPosMascara;

                // Posicao corrente de deslocamento em Y para mascara
                i = startY;

                //System.out.printf("\n\nThread id: %d pixel abaixo\n", id);
                //System.out.printf("posX = %d posY = %d\n", posX, posY);
            }
            else {

                //System.out.printf("\n\nThread id: %d fim\n", id);

                break;
            }
        }
    }
}