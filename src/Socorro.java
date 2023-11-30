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
	public static List<Vertice> lerGrafo(String nomeArquivo, int limite) {

		Grafo g = new Grafo();
		Vertice v = null;
		File f = new File(nomeArquivo);
		String vertices[];
		String linha;
		ArrayList<String[]> s1 = new ArrayList<String[]>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(f));

			Map<String, Vertice> mapa = new HashMap<String, Vertice>();
			int contador = 0;
			while ((linha = br.readLine()) != null && limite != contador) {
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
					}

				}
				g.adicionarVertice(vit);
				g.adicionarVertice(v);
				s1.clear();
			}

			// catch do BufferedReader
			// }
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

	public static void generator() throws IOException {
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
			Grafo k = h;
			for (int j = 0; j < n; j++) {
				Vertice u = k.encontrarVertice("v" + j);
				for (int j2 = j + 1; j2 < n; j2++) {
					Vertice w = k.encontrarVertice("v" + j2);
					u.setVizinhos(w, 1);
				}
			}
			h = arvure(k, n, br);
			List<Vertice> pegaGrau = h.getVertices();
			int limiteInferior = n - 1;
			int limiteSuperior = (n * (n - 1)) / 2;
			int ale1, ale2;

			for (int j = limiteInferior; j < limiteSuperior;) {
				int peso = aleat.nextInt(1, 500);
				ale1 = aleat.nextInt(pegaGrau.size());
				ale2 = aleat.nextInt(pegaGrau.size());
				Vertice u = pegaGrau.get(ale1);
				Vertice w = pegaGrau.get(ale2);
				while (ale1 == ale2) {
					ale1 = aleat.nextInt(pegaGrau.size());
					ale2 = aleat.nextInt(pegaGrau.size());
					u = pegaGrau.get(ale1);
					w = pegaGrau.get(ale2);
				}
				while (u.vizinhos.get(w.getDescricao()) == null) {
					u.setVizinhos(w, peso);
					j++;
					if (u.getGrau() == n - 1) {
						pegaGrau.remove(u);
					}
					if (w.getGrau() == n - 1) {
						pegaGrau.remove(w);
					}
					br.write(u.getDescricao() + "," + w.getDescricao() + "/" + Integer.toString(peso) + "\n");
				}
			}
			Long tempo = (System.currentTimeMillis() - timer);
			System.out.println("\tGrafo gerado com vertice: " + n + " em: " + tempo + " ms, " + ((tempo / 1000) % 60)
					+ " segundos, " + ((tempo / 60000) % 60) + " minutos");
			br.close();
		}
	}

	private static Grafo arvure(Grafo k, int n, BufferedWriter br) throws IOException {

		Grafo g = new Grafo();
		List<Vertice> a = k.getVertices();
		List<Vertice> b = new ArrayList<Vertice>();
		Random aleat = new Random();
		int v = aleat.nextInt(a.size()), w = aleat.nextInt(a.size());

		for (; a.size() != 0;) {
			if (b.size() == 0) {
				do {
					v = aleat.nextInt(a.size());
					w = aleat.nextInt(a.size());
				} while (v == w);
				Vertice u = new Vertice();
				Vertice y = new Vertice();
				u.setDescricao(a.get(v).getDescricao());
				y.setDescricao(a.get(w).getDescricao());
				u.setVizinhos(y, aleat.nextInt(1, 500));

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
				br.write(u.getDescricao() + "," + y.getDescricao() + "/" + u.getVizinhos(y).toString() + "\n");
			} else {
				w = aleat.nextInt(b.size());
				v = aleat.nextInt(a.size());
				Vertice u = new Vertice(), y = new Vertice();
				u.setDescricao(a.get(v).getDescricao());
				y = b.get(w);
				u.setVizinhos(y, aleat.nextInt(1, 500));
				br.write(u.getDescricao() + "," + y.getDescricao() + "/" + u.getVizinhos(y).toString() + "\n");
				b.add(u);
				a.remove(v);
			}

		}
		for (int i = 0; i < b.size(); i++) {
			g.adicionarVertice(b.get(i));
		}
		return g;
	}

	public static void main(String args[]) throws IOException {
		int input = -1;
		Scanner a = new Scanner(System.in);
		while (input != 0) {
			System.out.print(
					"\tDigite: 0 : para sair\n"
							+ "\tDigite: 1 : para gerar o grafo\n"
							+ "\tDigite: 2 : para rodar o djikstra\n\t");
			input = a.nextInt();
			switch (input) {
				case 1:
					Long timer = System.currentTimeMillis();
					generator();
					Long tempo=System.currentTimeMillis()-timer;
					System.out.println("\tTempo total; gasto para gerar em: " + tempo + " ms, " + ((tempo / 1000) % 60)
							+ " segundos, " + ((tempo / 60000) % 60) + " minutos");

					break;
				case 2:
					for (int i = 1; i <= 5; i++) {
						int n = (int) Math.pow(5, i);
						int limiteInferior = n - 1;
						int limiteSuperior = (n * (n - 1)) / 2;
						for (int j = 0; j < 5; j++) {
							Grafo g = new Grafo();
							int intervalo = limiteInferior + (j * (limiteSuperior - limiteInferior) / 4);
							g.setVertices(lerGrafo("src/Grafo" + n + ".txt", intervalo));
							Vertice i1 = new Vertice();
							i1 = g.encontrarVertice("v0");
							List<Vertice> resultado = new ArrayList<Vertice>();
							resultado = Dijstra.encontrarMenorCaminhoDijkstra(g, i1,intervalo);
							/*System.out.println("\t Esse é o menor caminho feito pelo algoritmo de dijkstra:");
							for (int j2 = 0; j2 < resultado.size(); j2++) {
								String lala = "";
								lala = printPai(resultado.get(j2), lala);
								System.out.println("Esse e o teste " + n +" de "+intervalo+
										"\t " + lala + " : "
										+ resultado.get(j2).getDistancia());
							}
							*/
						}
					}

				default:
					break;
			}
			a.nextLine();
		}
		a.close();
	}

	private static String printPai(Vertice vertice, String pais) {
		if (vertice.getPai() == null) {
			return (vertice.getDescricao() + "->")+pais;
		} else {
			pais = printPai(vertice.getPai(), pais)+"->"+vertice.getDescricao();
			return pais;
		}
	}

}