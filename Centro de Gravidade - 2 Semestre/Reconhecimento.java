import java.io.*;

/**
 *
 * @author bsantosdias
 */

public class Reconhecimento {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        PrintResultado(null, false);
        String entrada = LerArquivo();
        boolean valida = ValidaEntrada(entrada);
        float[][] matriz = MontaMatriz(entrada);
        if (valida == false) {
            PrintResultado(null, true);
        } else {
            float[] centroLinhaColuna = EncontraCentroGravidade(matriz);
            PrintResultado(centroLinhaColuna, true);
        }
    }

    // Abre, lê o arquivo txt e guarda seu conteudo em uma variavel String. 
    private static String LerArquivo() throws FileNotFoundException, IOException {
        String texto = "";
        FileReader arquivo = new FileReader("entrada.txt");

        // Gera um fluxo bufferizado para fazer a leitura de linhas.
        BufferedReader leBufferizado;
        leBufferizado = new BufferedReader(arquivo);

        // Le as linhas do arquivo e guarda na variavel texto, separando-as com um espaço. 
        String linha = leBufferizado.readLine();
        while (linha != null) {
            texto += linha + " ";
            linha = leBufferizado.readLine();
        }
        arquivo.close();
        return texto;
    }

    // Verificar tamanho da matriz e se tem números suficientes para enche-la. 
    private static boolean ValidaEntrada(String entrada) {
        String vetString[] = entrada.split(" ");
        int linha = 0;
        int coluna = 0;
        int tamanho = 0;

        for (int i = 0; i < vetString.length; i++) {
            float vetFloat = Float.parseFloat(vetString[i]);

            // Verifica se os dois primeiros numeros do arquivo (tamanho da matriz) são maiores do que três. 
            if (i <= 1 && vetFloat < 3) {
                return false;
            }

            //Pega o primeiro e segundo valor do arquivo. 
            if (i == 0) {
                linha = (int) vetFloat;
            }
            if (i == 1) {
                coluna = (int) vetFloat;
            }
        }

        // Multiplica o primeiro pelo segundo valor, assim conseguindo a quantidade de caracteres necessarios para enche-la.
        // - 2, pois o numero da linha e coluna tambem estão no vetString.length.
        tamanho = linha * coluna;
        if (tamanho != vetString.length - 2) {
            return false;
        }
        return true;
    }

    //Apos a verificação, monta a matriz a partir do arquivo de entrada. 
    private static float[][] MontaMatriz(String entrada) {
        String vetString[] = entrada.split(" ");
        int linha = 0;
        int coluna = 0;
        String auxiliarString = "";

        for (int i = 0; i < vetString.length; i++) {
            float vetFloat = Float.parseFloat(vetString[i]);

            // Novamente, pega primeiro e segundo números do arquivo, pois serão o tamanho da matriz. 
            if (i == 0) {
                linha = (int) vetFloat;
            } else if (i == 1) {
                coluna = (int) vetFloat;
            } // Restante dos números entram para a auxiliar, pois serão usados para preencher a matriz. 
            else {
                auxiliarString += vetFloat + " ";
            }
        }
        String auxiliarMatriz[] = auxiliarString.split(" ");

        int auxiliarContadorColuna = 0;
        float[][] matriz = new float[linha][coluna];

        // Após separar números restantes e coloca-los numa matriz auxiliar de [], números são tranferidos para uma matriz dupla. 
        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {
                matriz[i][j] = Float.parseFloat(auxiliarMatriz[auxiliarContadorColuna]);
                auxiliarContadorColuna++;
            }
        }
        return matriz;

    }

    //Com a matriz principal montada, encontra centro de gravidade. 
    private static float[] EncontraCentroGravidade(float[][] matriz) {
        float[] centros = new float[2];
        float centroLinha = 0;
        float centroColuna = 0;
        float somaLinha = 0;
        float somaColuna = 0;
        int auxiliarContadorColuna = 1;
        int auxiliarContadorLinha = 1;
        float subtrairColuna = 0;
        float subtrairLinha = 0;
        int centroColunaLinha = 0;
        int centroLinhaColuna = 0;
        boolean somarColuna = true;
        boolean somarLinha = true;

        //Encontra linha matriz.
        for (int c = 0; c < matriz[0].length; c++) {
            float auxiliarLinha = 0;
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[0].length; j++) {
                    if (i != auxiliarContadorLinha) {
                        if (somarLinha == true) {
                            somaLinha = somaLinha + matriz[i][j];
                        } else {
                            subtrairLinha = subtrairLinha + matriz[i][j];
                        }
                    } else {
                        somarLinha = false;
                    }
                }
            }
            // Se o resultado der negativo, o mesmo será multiplicado por -1 para que fique positivo. 
            // Quando positivo, resultado será enviado para a auxiliar. 
            if (somaLinha - subtrairLinha < 0) {
                auxiliarLinha = (somaLinha - subtrairLinha) * -1;
            } else {
                auxiliarLinha = (somaLinha - subtrairLinha);
            }

            // No começo do preenchimento, centros receberão auxiliares. 
            if (c == 0) {
                centroLinha = auxiliarLinha;
                centroLinhaColuna = auxiliarContadorLinha;
            } //Se não for o começo, centros serão comparados com as auxiliares para saber qual tem o menor valor. 
            //Caso centros forem maiores que a auxiliares do momento, os mesmos serão substituidos pelas auxiliares.
            else {
                if (centroLinha > auxiliarLinha) {
                    centroLinha = auxiliarLinha;
                    centroLinhaColuna = auxiliarContadorLinha;
                }
            }
            somarLinha = true;
            auxiliarContadorLinha++;
            subtrairLinha = 0;
            somaLinha = 0;

        }

        // Encontra coluna matriz.
        for (int c = 0; c < matriz.length; c++) {
            float auxiliarColuna = 0;
            for (int i = 0; i < matriz[0].length; i++) {
                for (int j = 0; j < matriz.length; j++) {
                    if (i != auxiliarContadorColuna) {
                        if (somarColuna == true) {
                            somaColuna = somaColuna + matriz[j][i];
                        } else {
                            subtrairColuna = subtrairColuna + matriz[j][i];
                        }
                    } else {
                        somarColuna = false;
                    }
                }
            }

            // Se o resultado der negativo, o mesmo será multiplicado por -1 para que fique positivo. 
            // Quando positivo, resultado será enviado para a auxiliar. 
            if (somaColuna - subtrairColuna < 0) {
                auxiliarColuna = (somaColuna - subtrairColuna) * -1;
            } else {
                auxiliarColuna = (somaColuna - subtrairColuna);
            }

            // No começo do preenchimento, centros receberão auxiliares.
            if (c == 0) {
                centroColuna = auxiliarColuna;
                centroColunaLinha = auxiliarContadorColuna;
            } //Se não for o começo, centros serão comparados com as auxiliares para saber qual tem o menor valor. 
            //Caso centros forem maiores que a auxiliares do momento, os mesmos serão substituidos pelas auxiliares.
            else {
                if (centroColuna > auxiliarColuna) {
                    centroColuna = auxiliarColuna;
                    centroColunaLinha = auxiliarContadorColuna;
                }
            }
            somarColuna = true;
            auxiliarContadorColuna++;
            subtrairColuna = 0;
            somaColuna = 0;

        }
        //Com centros encontrados, os mesmo são jogados num vetor para facilitar o transporte para o main. 
        //A adição do "+1" ocorre para que o usuário veja os valores começando a partir do 1 e não do 0.
        centros[0] = centroLinhaColuna + 1;
        centros[1] = centroColunaLinha + 1;
        return centros;
    }

    //Por fim, imprime resultados (se forem encontrados). 
    private static void PrintResultado(float[] centros, boolean scaneado) {
        if (scaneado == false) {
            System.out.println("Scaneando");
        } else {
            if (centros != null) {
                //Não consegui colocar uma função que tirasse as casas decimais. Todas que tentei não funcionaram. 
                System.out.println("Centro (" + centros[0] + "," + centros[1] + ")");
            } else {
                System.out.println("Dados insuficientes. Matriz não montada.");
            }
        }
    }
}
