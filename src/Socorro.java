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
	public static Grafo lerGrafo(String nomeArquivo, int limite) {
		Grafo g = new Grafo();
		File f = new File(nomeArquivo);

		try (BufferedReader br = new BufferedReader(new FileReader(f))) {
			Map<String, Vertice> mapa = new HashMap<>();

			String linha;
			while ((linha = br.readLine()) != null && limite > 0) {
				limite--;

				String[] partes = linha.split("/");
				String[] vertices = partes[0].split(",");

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
				g.adicionarVertice(v1);
				g.adicionarVertice(v2);

				if (partes.length > 1) {
					String[] pesos = partes[1].split(",");
					int pesoAresta = Integer.parseInt(pesos[0]);

					v1.setArestas(g, v2, pesoAresta);
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Não encontrou o arquivo");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return g;
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
					u.setArestas(k, w, 1);
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
				while (u.getarestas(w) == -1) {
					u.setArestas(h, w, peso);
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
				u.setArestas(k, y, aleat.nextInt(1, 500));

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
				br.write(u.getDescricao() + "," + y.getDescricao() + "/" + Integer.toString(u.getarestas(y)) + "\n");
			} else {
				w = aleat.nextInt(b.size());
				v = aleat.nextInt(a.size());
				Vertice u = new Vertice(), y = new Vertice();
				u.setDescricao(a.get(v).getDescricao());
				y = b.get(w);
				u.setArestas(k, y, aleat.nextInt(1, 500));
				br.write(u.getDescricao() + "," + y.getDescricao() + "/" + Integer.toString(u.getarestas(y)) + "\n");
				b.add(u);
				a.remove(v);
			}

		}
		for (int i = 0; i < b.size(); i++) {
			g.adicionarVertice(b.get(i));
		}
		return g;
	}

	public static void rodarDijkstra() throws IOException {
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
				List<Vertice> result=Dijstra.encontrarMenorCaminhoDijkstra(g, i1, intervalo);
				File p = new File("src/SaidaDijkstra" + n + ".txt");
				BufferedWriter bw = new BufferedWriter(new FileWriter(p, true));
				bw.write("\n Intervalo: " + intervalo + "\n");
				bw.close();
				for (Vertice v : result) {
					bw = new BufferedWriter(new FileWriter(p, true));
					bw.write("Antecessores: " + printaPai(v, g) + " Com o a distancia: "
							+ v.getDistancia() + "\n");
					bw.close();
				}
			}
			br = new BufferedWriter(new FileWriter(f, true));
			br.write("\n");
			br.close();
		}
	}

	public static void rodarBellmanFord() throws IOException {
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
					bw.write("Antecessores: " + printaPai(v, g) + " Com o a distancia: "
							+ v.getDistancia() + "\n");
					bw.close();
				}
			}
			br = new BufferedWriter(new FileWriter(f, true));
			br.write("\n");
			br.close();
		}
	}

	private static String printaPai(Vertice v, Grafo g) {
		if (v.getDescricao().equals("v0")) {
			return "v0 ";
		} else {
			String a = printaPai(g.encontrarVertice(v.getPai()), g) + " -> " + v.getDescricao();
			return a;
		}
	}

	public static void rodarFloydWarshall() throws IOException {
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
				List<Vertice> result=Floyd.encontrar(g, i1, intervalo);
				File p = new File("src/SaidaFloyd" + n + ".txt");
				BufferedWriter bw = new BufferedWriter(new FileWriter(p, true));
				bw.write("\n Intervalo: " + intervalo + "\n");
				bw.close();
				for (Vertice v : result) {
					bw = new BufferedWriter(new FileWriter(p, true));
					bw.write("Antecessores: " + v.getDescricao() + " Com o a distancia: "
							+ v.getDistancia() + "\n");
					bw.close();
				}
			}
			br = new BufferedWriter(new FileWriter(f, true));
			br.write("\n");
			br.close();
		}
	}

	public static void rodarOPF() throws IOException {
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
				List<Vertice> result=OPF.encontrar(g, i1, intervalo);
				File p = new File("src/SaidaOPF" + n + ".txt");
				BufferedWriter bw = new BufferedWriter(new FileWriter(p, true));
				bw.write("\n Intervalo: " + intervalo + "\n");
				bw.close();
				for (Vertice v : result) {
					bw = new BufferedWriter(new FileWriter(p, true));
					bw.write("Antecessores: " + printaPai(v, g) + " Com o a distancia: "
							+ v.getDistancia() + "\n");
					bw.close();
				}
			}
			br = new BufferedWriter(new FileWriter(f, true));
			br.write("\n");
			br.close();
		}
	}

	public static void rodarJohnson() throws IOException {
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
				List<Vertice> result=Jhonson.encontrar(g, i1, intervalo);
				File p = new File("src/SaidaJhonson" + n + ".txt");
				BufferedWriter bw = new BufferedWriter(new FileWriter(p, true));
				bw.write("\n Intervalo: " + intervalo + "\n");
				bw.close();
				for (Vertice v : result) {
					bw = new BufferedWriter(new FileWriter(p, true));
					bw.write("Antecessores: " + printaPai(v, g) + " Com o a distancia: "
							+ v.getDistancia() + "\n");
					bw.close();
				}
			}
		}
	}

	public static void main(String args[]) throws IOException {
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
					rodarDijkstra();
					rodarBellmanFord();
					rodarFloydWarshall();
					rodarOPF();
					rodarJohnson();
					break;
				default:
					break;
			}
			a.nextLine();
		}
		a.close();
	}
}