package src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Socorro {
	/**
	 * Método para ler um grafo a partir de um arquivo.
	 *
	 * @param nomeArquivo Nome do arquivo contendo as informações do grafo.
	 * @param limite      Limite de linhas a serem lidas no arquivo.
	 * @return Grafo lido a partir do arquivo.
	 */
	public static Grafo lerGrafo(String nomeArquivo, int limite) {
		// Criação de um novo grafo
		Grafo g = new Grafo();

		// Leitura do arquivo
		File f = new File(nomeArquivo);

		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			// Mapa para rastrear vértices já criados
			Map<String, Vertice> mapa = new HashMap<>();

			String linha;
			// Loop para ler as linhas do arquivo até atingir o limite ou chegar ao final do
			// arquivo
			while ((linha = br.readLine()) != null && limite > 0) {
				limite--;

				// Divide a linha em partes, separando vértices e pesos
				String[] partes = linha.split("/");
				String[] vertices = partes[0].split(",");

				// Obtém ou cria os vértices do mapa
				Vertice v1 = mapa.get(vertices[0]);
				if (v1 == null) {
					v1 = new Vertice();
					v1.setDescricao(vertices[0]);
					mapa.put(vertices[0], v1);
				}

				Vertice v2 = mapa.get(vertices[1]);
				if (v2 == null) {
					v2 = new Vertice();
					v2.setDescricao(vertices[1]);
					mapa.put(vertices[1], v2);
				}

				// Adiciona os vértices ao grafo
				g.adicionarVertice(v1);
				g.adicionarVertice(v2);

				// Se houver pesos na linha, cria arestas entre os vértices
				if (partes.length > 1) {
					String[] pesos = partes[1].split(",");
					int pesoAresta = Integer.parseInt(pesos[0]);

					// Adiciona arestas entre os vértices com o peso informado
					v1.setArestas(g, v2, pesoAresta);
					v1.vizinhos.add(v2);
					v2.vizinhos.add(v1);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Não encontrou o arquivo");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Retorna o grafo lido a partir do arquivo
		return g;
	}

	/**
	 * Método para gerar grafos aleatórios e salvar em arquivos.
	 *
	 * @throws IOException Exceção de entrada/saída ao lidar com arquivos.
	 */
	public static void generator() throws IOException {
		for (int i = 1; i <= 5; i++) {
			Long timer = System.currentTimeMillis();
			int n = (int) Math.pow(5, i);
			File f = new File("src/Grafo" + n + ".txt");
			BufferedWriter br = new BufferedWriter(new FileWriter(f));
			Random aleat = new Random();
			Grafo h = new Grafo();

			// Adiciona vértices ao grafo
			for (int j = 0; j < n; j++) {
				Vertice v = new Vertice();
				v.setDescricao("v" + Integer.toString(j));
				h.adicionarVertice(v);
			}

			Grafo k = h;

			// Adiciona arestas entre os vértices formando uma árvore

			for (Vertice uVertice : k.getVertices()) {
				for (Vertice wVertice : k.getVertices()) {
					if (!(uVertice.equals(wVertice) && uVertice.vizinhos.contains(wVertice))) {
						uVertice.setArestas(k, wVertice, 1);
						uVertice.vizinhos.add(wVertice);
						wVertice.vizinhos.add(uVertice);
					}
				}
			}

			// Cria um grafo arvore com base no grafo original
			h = arvure(k, n, br);
			List<Vertice> pegaGrau = h.getVertices();
			int limiteInferior = n - 1;
			int limiteSuperior = (n * (n - 1)) / 2;
			int ale1, ale2;

			// Adiciona arestas aleatórias com pesos entre os vértices
			for (int j = limiteInferior; j < limiteSuperior;) {
				int peso = aleat.nextInt(1, 500);
				ale1 = aleat.nextInt(pegaGrau.size());
				ale2 = aleat.nextInt(pegaGrau.size());
				Vertice u = pegaGrau.get(ale1);
				Vertice w = pegaGrau.get(ale2);

				// Garante que os vértices escolhidos não sejam iguais
				while (ale1 == ale2) {
					ale1 = aleat.nextInt(pegaGrau.size());
					ale2 = aleat.nextInt(pegaGrau.size());
					u = pegaGrau.get(ale1);
					w = pegaGrau.get(ale2);
				}

				// Garante que não exista aresta entre os vértices escolhidos
				while (!u.vizinhos.contains(w)) {
					u.setArestas(h, w, peso);
					u.vizinhos.add(w);
					w.vizinhos.add(u);
					j++;

					// Remove vértices do conjunto de vértices de alto grau
					if (u.getGrau() == n - 1) {
						pegaGrau.remove(u);
					}
					if (w.getGrau() == n - 1) {
						pegaGrau.remove(w);
					}

					// Escreve a aresta no arquivo
					br.write(u.getDescricao() + "," + w.getDescricao() + "/" + peso + "\n");
				}
			}

			// Calcula e exibe o tempo de execução
			Long tempo = (System.currentTimeMillis() - timer);
			System.out.println("\tGrafo gerado com vértice: " + n + " em: " + tempo + " ms, " + ((tempo / 1000) % 60)
					+ " segundos, " + ((tempo / 60000) % 60) + " minutos");
			br.close();
		}
	}

	/**
	 * Método para criar uma árvore aleatória com base em um grafo existente.
	 *
	 * @param k  Grafo base para criar a árvore.
	 * @param n  Número de vértices da árvore.
	 * @param br BufferedWriter para escrever no arquivo.
	 * @return Grafo representando uma árvore.
	 * @throws IOException Exceção de entrada/saída ao lidar com arquivos.
	 */
	private static Grafo arvure(Grafo k, int n, BufferedWriter br) throws IOException {
		Grafo g = new Grafo();
		List<Vertice> a = k.getVertices();
		List<Vertice> b = new ArrayList<Vertice>();
		Random aleat = new Random();
		int v = aleat.nextInt(a.size()), w = aleat.nextInt(a.size());

		// Loop até que todos os vértices sejam processados
		for (; a.size() != 0;) {
			// Se o conjunto 'b' estiver vazio, cria uma nova aresta entre vértices
			// aleatórios
			if (b.size() == 0) {
				do {
					v = aleat.nextInt(a.size());
					w = aleat.nextInt(a.size());
				} while (v == w);

				Vertice u = new Vertice();
				Vertice y = new Vertice();
				u.setDescricao(a.get(v).getDescricao());
				y.setDescricao(a.get(w).getDescricao());
				int peso = aleat.nextInt(1, 500);
				u.setArestas(k, y, peso);
				u.vizinhos.add(y);
				y.vizinhos.add(u);
				// Adiciona vértices ao conjunto 'b' e remove do conjunto 'a'
				if (v < w) {
					b.add(y);
					a.remove(w);
					b.add(u);
					a.remove(v);
				} else {
					b.add(u);
					a.remove(v);
					b.add(y);
					a.remove(w);
				}

				// Escreve a aresta no arquivo
				br.write(u.getDescricao() + "," + y.getDescricao() + "/" + peso + "\n");
			} else {
				// Escolhe vértices aleatórios de 'b' e 'a' para criar novas arestas
				w = aleat.nextInt(b.size());
				v = aleat.nextInt(a.size());
				Vertice u = new Vertice(), y = new Vertice();
				u.setDescricao(a.get(v).getDescricao());
				y = b.get(w);
				int peso = aleat.nextInt(1, 500);
				u.setArestas(k, y, peso);
				u.vizinhos.add(y);
				y.vizinhos.add(u);

				// Escreve a aresta no arquivo
				br.write(u.getDescricao() + "," + y.getDescricao() + "/" + peso + "\n");

				// Adiciona vértice ao conjunto 'b' e remove do conjunto 'a'
				b.add(u);
				a.remove(v);
			}
		}

		// Adiciona os vértices do conjunto 'b' ao grafo final
		for (int i = 0; i < b.size(); i++) {
			g.adicionarVertice(b.get(i));
		}

		// Retorna o grafo que representa a árvore
		return g;
	}

	public static void rodarDijkstra() throws IOException, CloneNotSupportedException {
		for (int i = 1; i <= 5; i++) {
			int n = (int) Math.pow(5, i);
			int limiteInferior = n - 1;
			int limiteSuperior = (n * (n - 1)) / 2;
			File f = new File("src/ResultadoDjikstra.txt");
			BufferedWriter br = new BufferedWriter(new FileWriter(f, true));
			br.write("Djikstra com quantidade de vertice: " + n + "\n");
			br.close();
			for (int j = 0; j < 5; j++) {
				Grafo g = new Grafo();
				int intervalo = limiteInferior + (j * (limiteSuperior - limiteInferior) / 4);
				g = lerGrafo("src/Grafo" + n + ".txt", intervalo);
				Vertice i1 = new Vertice();
				i1 = g.encontrarVertice("v0");
				List<Vertice> result = Dijstra.encontrar(g, i1, intervalo);
				File p = new File("src/SaidaDijkstra" + n + ".txt");
				BufferedWriter bw = new BufferedWriter(new FileWriter(p, true));
				bw.write("\n Intervalo: " + intervalo + "\n");
				bw.close();
				for (Vertice v : result) {
					bw = new BufferedWriter(new FileWriter(p, true));
					bw.write("Vertices: " + v.getDescricao() + " Com o a distancia: "
							+ v.getDistancia() + "\n");
					bw.close();
				}
			}
		}
	}

	public static void rodarBellmanFord() throws IOException, CloneNotSupportedException {
		for (int i = 1; i <= 5; i++) {
			int n = (int) Math.pow(5, i);
			int limiteInferior = n - 1;
			int limiteSuperior = (n * (n - 1)) / 2;
			File f = new File("src/ResultadoBellmanFord.txt");
			BufferedWriter br = new BufferedWriter(new FileWriter(f, true));
			br.write("\tCom quantidade de Vertice: " + n + "\n");
			br.close();
			for (int j = 0; j < 5; j++) {
				Grafo g = new Grafo();
				int intervalo = limiteInferior + (j * (limiteSuperior - limiteInferior) / 4);
				g = (lerGrafo("src/Grafo" + n + ".txt", intervalo));
				File p = new File("src/SaidaBellmanFord" + n + ".txt");
				BufferedWriter bw = new BufferedWriter(new FileWriter(p, true));
				bw.write("\n Intervalo: " + intervalo + "\n");
				bw.close();
				Vertice i1 = new Vertice();
				i1 = g.encontrarVertice("v0");
				List<Vertice> result = BellmanFord.encontrar(g, i1, intervalo);
				for (Vertice v : result) {
					bw = new BufferedWriter(new FileWriter(p, true));
					bw.write("Vertices: " + v.getDescricao() + " Com o a distancia: "
							+ v.getDistancia() + "\n");
					bw.close();
				}
			}
		}
	}

	public static void rodarFloydWarshall() throws IOException, CloneNotSupportedException {
		for (int i = 1; i <= 5; i++) {
			int n = (int) Math.pow(5, i);
			int limiteInferior = n - 1;
			int limiteSuperior = (n * (n - 1)) / 2;
			File f = new File("src/ResultadoFloyd.txt");
			BufferedWriter br = new BufferedWriter(new FileWriter(f, true));
			br.write("Floyd com quantidade de vertice= " + n + "\n");
			br.close();
			for (int j = 0; j < 5; j++) {
				Grafo g = new Grafo();
				int intervalo = limiteInferior + (j * (limiteSuperior - limiteInferior) / 4);
				g = (lerGrafo("src/Grafo" + n + ".txt", intervalo));
				Vertice i1 = new Vertice();
				i1 = g.encontrarVertice("v0");
				List<Vertice> result = Floyd.encontrar(g, i1, intervalo, g.gerarMatrizAdjacencia());
				File p = new File("src/SaidaFloyd" + n + ".txt");
				BufferedWriter bw = new BufferedWriter(new FileWriter(p, true));
				bw.write("\n Intervalo: " + intervalo + "\n");
				bw.close();
				for (Vertice v : result) {
					bw = new BufferedWriter(new FileWriter(p, true));
					bw.write("Vertices: " + v.getDescricao() + " Com o a distancia: "
							+ v.getDistancia() + "\n");
					bw.close();
				}
			}
		}
	}

	public static void rodarOPF() throws IOException, CloneNotSupportedException {
		for (int i = 1; i <= 5; i++) {
			int n = (int) Math.pow(5, i);
			int limiteInferior = n - 1;
			int limiteSuperior = (n * (n - 1)) / 2;
			File f = new File("src/ResultadoOPF.txt");
			BufferedWriter br = new BufferedWriter(new FileWriter(f, true));
			br.write("OPF com quantidade de vertice= " + n + "\n");
			br.close();
			for (int j = 0; j < 5; j++) {
				Grafo g = new Grafo();
				int intervalo = limiteInferior + (j * (limiteSuperior - limiteInferior) / 4);
				g = (lerGrafo("src/Grafo" + n + ".txt", intervalo));
				Vertice i1 = new Vertice();
				i1 = g.encontrarVertice("v0");
				List<Vertice> result = OPF.encontrar(g, i1, intervalo);
				File p = new File("src/SaidaOPF" + n + ".txt");
				BufferedWriter bw = new BufferedWriter(new FileWriter(p, true));
				bw.write("\n Intervalo: " + intervalo + "\n");
				bw.close();
				for (Vertice v : result) {
					bw = new BufferedWriter(new FileWriter(p, true));
					bw.write("Vertices: " + v.getDescricao() + " Com o a distancia: "
							+ v.getDistancia() + "\n");
					bw.close();
				}
			}
		}
	}

	public static void rodarJohnson() throws IOException, CloneNotSupportedException {
		for (int i = 1; i <= 5; i++) {
			int n = (int) Math.pow(5, i);
			int limiteInferior = n - 1;
			int limiteSuperior = (n * (n - 1)) / 2;
			File f = new File("src/ResultadoJhonson.txt");
			BufferedWriter br = new BufferedWriter(new FileWriter(f, true));
			br.write("Jhonson com quantidade de vertice= " + n + "\n");
			br.close();
			for (int j = 0; j < 5; j++) {
				Grafo g = new Grafo();
				int intervalo = limiteInferior + (j * (limiteSuperior - limiteInferior) / 4);
				g = (lerGrafo("src/Grafo" + n + ".txt", intervalo));
				Vertice i1 = new Vertice();
				i1 = g.encontrarVertice("v0");
				List<Vertice> result = Jhonson.encontrar(g, i1, intervalo);
				File p = new File("src/SaidaJhonson" + n + ".txt");
				BufferedWriter bw = new BufferedWriter(new FileWriter(p, true));
				bw.write("\n Intervalo: " + intervalo + "\n");
				bw.close();
				for (Vertice v : result) {
					bw = new BufferedWriter(new FileWriter(p, true));
					bw.write("Vertices: " + v.getDescricao() + " Com o a distancia: "
							+ v.getDistancia() + "\n");
					bw.close();
				}
			}
		}
	}

	public static void main(String args[]) throws IOException, CloneNotSupportedException {
		int input = -1;
		Scanner a = new Scanner(System.in);
		while (input != 0) {
			System.out.print(
					"\tDigite 0 : para sair\n"
							+ "\tDigite 1 : para gerar grafo\n"
							+ "\tDigite 2 : para rodar djikstra\n" + "\tDigite 3 : para rodar BellmanFord\n"
							+ "\tDigite 4 : para rodar Floyd-Warshall\n" + "\tDigite 5 : para rodar OPF\n"
							+ "\tDigite 6 : para rodar Johnson\n" + "\tDigite 7 : para rodar todos\n"
							+ "\tOpção escolhida : ");
			input = a.nextInt();
			switch (input) {
				case 1:
					Long timer = System.currentTimeMillis();
					generator();
					Long tempo = System.currentTimeMillis() - timer;
					System.out.println("\tTempo total; gasto para gerar em: " + tempo + " ms, " + ((tempo / 1000) % 60)
							+ " segundos, " + ((tempo / 60000) % 60) + " minutos");

					break;
				case 2:
					rodarDijkstra();
					break;
				case 3:
					rodarBellmanFord();
					break;
				case 4:
					rodarFloydWarshall();
					break;
				case 5:
					rodarOPF();
					break;
				case 6:
					rodarJohnson();
					break;
				case 7:
					rodarTd();
					break;
				default:
					break;
			}
			a.nextLine();
		}
		a.close();
	}

	private static void rodarTd() throws IOException, CloneNotSupportedException {
		for (int i = 1; i <= 5; i++) {
			int n = (int) Math.pow(5, i);
			int limiteInferior = n - 1;
			int limiteSuperior = (n * (n - 1)) / 2;

			File f = new File("src/ResultadoJhonson.txt");
			BufferedWriter br = new BufferedWriter(new FileWriter(f, true));
			br.write("Jhonson com quantidade de vertice= " + n + "\n");
			br.close();

			f = new File("src/ResultadoOPF.txt");
			br = new BufferedWriter(new FileWriter(f, true));
			br.write("OPF com quantidade de vertice= " + n + "\n");
			br.close();

			f = new File("src/ResultadoFloyd.txt");
			br = new BufferedWriter(new FileWriter(f, true));
			br.write("Floyd com quantidade de vertice= " + n + "\n");
			br.close();

			f = new File("src/ResultadoBellmanFord.txt");
			br = new BufferedWriter(new FileWriter(f, true));
			br.write("BellmanFord com quantidade de Vertice: " + n + "\n");
			br.close();

			f = new File("src/ResultadoDjikstra.txt");
			br = new BufferedWriter(new FileWriter(f, true));
			br.write("Djikstra com quantidade de vertice: " + n + "\n");
			br.close();
			for (int j = 0; j < 5; j++) {
				Grafo g = new Grafo();
				int intervalo = limiteInferior + (j * (limiteSuperior - limiteInferior) / 4);
				g = (lerGrafo("src/Grafo" + n + ".txt", intervalo));
				Vertice i1 = new Vertice();
				Grafo h=new Grafo();
				h=g.clone();
				i1 = h.encontrarVertice("v0");
				
				List<Vertice> resultOPF = OPF.encontrar(g.clone(), i1, intervalo);
				File p = new File("src/SaidaOPF" + n + ".txt");
				BufferedWriter bw = new BufferedWriter(new FileWriter(p, true));
				bw.write("\n Intervalo: " + intervalo + "\n");
				bw.close();
				for (Vertice v : resultOPF) {
					bw = new BufferedWriter(new FileWriter(p, true));
					bw.write("Vertices: " + v.getDescricao() + " Com o a distancia: "
							+ v.getDistancia() + "\n");
					bw.close();
				}

				List<Vertice> resultJhonson = Jhonson.encontrar(g.clone(), i1, intervalo);
				 p = new File("src/SaidaJhonson" + n + ".txt");
				 bw = new BufferedWriter(new FileWriter(p, true));
				bw.write("\n Intervalo: " + intervalo + "\n");
				bw.close();
				for (Vertice v : resultJhonson) {
					bw = new BufferedWriter(new FileWriter(p, true));
					bw.write("Vertices: " + v.getDescricao() + " Com o a distancia: "
							+ v.getDistancia() + "\n");
					bw.close();
				}

				List<Vertice> resultDijkstra = Dijstra.encontrar(g.clone(), i1, intervalo);
				p = new File("src/SaidaDijkstra" + n + ".txt");
				bw = new BufferedWriter(new FileWriter(p, true));
				bw.write("\n Intervalo: " + intervalo + "\n");
				bw.close();
				for (Vertice v : resultDijkstra) {
					bw = new BufferedWriter(new FileWriter(p, true));
					bw.write("Vertices: " + v.getDescricao() + " Com o a distancia: "
							+ v.getDistancia() + "\n");
					bw.close();
				}

				List<Vertice> resultBellman = BellmanFord.encontrar(g.clone(), i1, intervalo);
				p = new File("src/SaidaBellmanFord" + n + ".txt");
				bw = new BufferedWriter(new FileWriter(p, true));
				bw.write("\n Intervalo: " + intervalo + "\n");
				bw.close();
				for (Vertice v : resultBellman) {
					bw = new BufferedWriter(new FileWriter(p, true));
					bw.write("Vertices: " + v.getDescricao() + " Com o a distancia: "
							+ v.getDistancia() + "\n");
					bw.close();
				}

				List<Vertice> resultFloyd = Floyd.encontrar(g, i1, intervalo, g.gerarMatrizAdjacencia());
				 p = new File("src/SaidaFloyd" + n + ".txt");
				 bw = new BufferedWriter(new FileWriter(p, true));
				bw.write("\n Intervalo: " + intervalo + "\n");
				bw.close();
				for (Vertice v : resultFloyd) {
					bw = new BufferedWriter(new FileWriter(p, true));
					bw.write("Vertices: " + v.getDescricao() + " Com o a distancia: "
							+ v.getDistancia() + "\n");
					bw.close();
				}
			}
		}
	}
}