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
	private static int[] intervalo;
	private static int contador;

	public static List<Vertice> lerGrafo(String nomeArquivo) {

		Grafo g = new Grafo();
		Vertice v;
		File f = new File(nomeArquivo);
		String vertices[];
		String linha;
		ArrayList<String[]> s1 = new ArrayList<String[]>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(f));

			Map<String, Vertice> mapa = new HashMap<String, Vertice>();
			contador = 1;
			//for (int i = 0; i < 1; i++) {
				while ((linha = br.readLine()) != null/* && contador <= intervalo[i]*/) {
					contador++;
					Vertice vit = null;
					if (linha.contains(",")) {
						s1.add(linha.split("/"));
						vertices = s1.get(0)[0].split(",");

						v = (Vertice) mapa.get(vertices[0]);
						if (v == null)
							v = new Vertice();

						v.setDescricao(vertices[0]);
						mapa.put(vertices[0], v);

						if (linha.contains("/")) {

							String pesoArestas[] = s1.get(0)[1].split(",");

							vit = mapa.get(vertices[1]);
							if (vit == null)
								vit = new Vertice();
							vit.setDescricao(vertices[1]);

							mapa.put(vertices[1], vit);

							v.setVizinhos(vit, Integer.parseInt(pesoArestas[0]));
							v.setVizinhos(vit, Integer.parseInt(pesoArestas[0]));
						}

					}

					// Vertices finais
					else {
						v = (Vertice) mapa.get(linha);
						if (v == null)
							v = new Vertice();
						v.setDescricao(linha);
						mapa.put(linha, v);
					}
					boolean flag = true;
					for (int j = 0; j < g.getVertices().size(); j++) {
						if (g.getVertices().get(j) == vit) {
							flag = false;
						}
					}
					if (flag) {
						g.adicionarVertice(v);
					}
					flag = true;
					if (vit != null) {
						for (int j = 0; j < g.getVertices().size(); j++) {
							if (g.getVertices().get(j) == vit) {
								flag = false;
							}
						}
						if (flag) {
							g.adicionarVertice(vit);
						}
					}
					s1.clear();
				}

				// catch do BufferedReader
			//}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("Nao encontrou o arquivo");
			e.printStackTrace();
		}
		// catch do readLine
		catch (IOException e) {
			e.printStackTrace();
		}

		// Retornando os vertices
		return g.getVertices();

	}

	public static void gerador() throws IOException {
		for (int i = 1; i <= 5; i++) {
			Long timer = System.currentTimeMillis();
			int n = (int) Math.pow(5, i);
			File f = new File("src/Grafo" + n + ".txt");
			BufferedWriter br = new BufferedWriter(new FileWriter(f));
			Random aleat = new Random();
			Grafo h = new Grafo();
			for (int j = 0; j < n; j++) {
				Vertice v = new Vertice();
				v.setDescricao("v" + Integer.toString(j));
				h.adicionarVertice(v);
			}
			int limiteInferior = n - 1;
			int limiteSuperior = (n * (n - 1)) / 2;

			for (int j = 0; j < h.getVertices().size(); j++) {
				Vertice v, x;
				int u = aleat.nextInt(n), w = aleat.nextInt(n);
				while (u == w) {
					w = aleat.nextInt(n);
				}
				int ps = aleat.nextInt(200);
				while (j < h.getVertices().size()) {
					boolean flag = true;
					if (h.getVertices().get(j).getGrau() < 2) {

						for (int k = j + 1; k < h.getVertices().size() && flag; k++) {
							if (h.getVertices().get(k).getVizinhos().size() < 2
									&& !(h.getVertices().get(j).vizinhos.containsKey(h.getVertices().get(k)))) {
								flag = false;
								v = h.getVertices().get(j);
								x = h.getVertices().get(k);
								v.setVizinhos(x, ps);
								x.setVizinhos(v, ps);
								br.write(h.getVertices().get(j).getDescricao() + ","
										+ h.getVertices().get(k).getDescricao()
										+ "/" + Integer.toString(ps) + "\n");
								ps = aleat.nextInt(200);
							}
						}
						if (flag) {
							v = h.getVertices().get(j);
							do {
								while (u == w) {
									w = aleat.nextInt(n);
								}
								x = h.getVertices().get(w);
								w = aleat.nextInt(n);
							} while ((v.vizinhos.containsKey(x)));
							v.setVizinhos(x, ps);
							x.setVizinhos(v, ps);
							br.write(h.getVertices().get(j).getDescricao() + "," + x.getDescricao() + "/"
									+ Integer.toString(ps) + "\n");
							ps = aleat.nextInt(200);
							
						}
					} else {
						j += 1;
					}
				}
				for (; j < limiteSuperior-1; j++) {
					do {
						while (u == w) {
							w = aleat.nextInt(n);
						}
						v = h.getVertices().get(u);
						x = h.getVertices().get(w);
						w = aleat.nextInt(n);
						u = aleat.nextInt(n);
					} while ((v.vizinhos.containsKey(x)));
					v.setVizinhos(x, ps);
					x.setVizinhos(v, ps);
					br.write(v.getDescricao() + "," + x.getDescricao() + "/"
							+ Integer.toString(ps) + "\n");
					ps = aleat.nextInt(200);
					w = aleat.nextInt(n);
					u = aleat.nextInt(n);
					while (u == w) {
						w = aleat.nextInt(n);
					}
				}
				System.out.println("teste  " + i + "  " + (System.currentTimeMillis() - timer));
			}
			br.close();
		}

	}

	public static void main(String args[]) throws IOException {
		int input = -1;
		Scanner a = new Scanner(System.in);
		while (input != 0) {
			input = a.nextInt();
			switch (input) {
				case 1:
					Long timer = System.currentTimeMillis();
					gerador();
					System.out.println(System.currentTimeMillis() - timer);

					break;
				case 2:
					for (int i = 1; i <= 5; i++) {
						Grafo g = new Grafo();
						int n = (int) Math.pow(5, i);
						int limiteInferior = n - 1;
						int limiteSuperior = (n * (n - 1)) / 2;
						intervalo = new int[5];
						for (int j = 0; j < intervalo.length; j++) {
							intervalo[j] = limiteInferior + (j * (limiteSuperior - limiteInferior) / 4);
						}
						g.setVertices(lerGrafo("src/Grafo" + n + ".txt"));
						Vertice i1 = new Vertice();
						i1 = g.encontrarVertice("v0");

						List<Vertice> resultado = new ArrayList<Vertice>();
						resultado = Dijstra.encontrarMenorCaminhoDijkstra(g, i1);
						System.out.println("\t Esse é o menor caminho feito pelo algoritmo de dijkstra:");
						for (int j = 1; j < resultado.size() && i < 3; j++) {
							System.out.println("Esse e o teste " + n +
									"\t " + resultado.get(j).getDescricao() + " : " + resultado.get(j).getDistancia());
						}
					}

				default:
					break;
			}
			a.nextLine();
		}
		a.close();
	}

}