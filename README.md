O trabalho consiste na implementação (em Java, C, ou C++, usando somente bibliotecas padrão) e avaliação de vários métodos de cômputo de caminhos mínimos de um grafo não direcionado de pesos positivos. Mais especificamente:

Algoritmo de Dijkstra;
Algoritmo de Bellman-Ford;
Algoritmo de Floyd-Warshall;
Algoritmo da Floresta de Caminhos Ótimos* com a função fmax;
Algoritmo de Johnson*;

onde aqueles indicados por *, se implementados e analisados, acarretarão em pontos extra à nota do aluno. Além disso, o aluno deverá entregar um relatório escrito em LaTeX com os seguintes itens:

Introdução e Referencial Teórico
Descrição e Pseudocódigo de cada algoritmo;
Análise quantitativa e comparativa (de tempo médio e desvio padrão) de todos algoritmos (variando a quantidade de vértices e arestas), e discussão dos resultados;
Para o ponto 3, a quantidade de vértices é determinada por 5x , dado x em {1,...,5}. Para as arestas, deve-se selecioná-las aleatoriamente de tal forma a obter cada uma das quantias equidistantes descritas no intervalo [|V| - 1, |V|(|V| - 1)/2]. Ou seja, o primeiro valor seria |V| - 1, o último valor, |V|(|V| - 1)/2, e os 3 valores restantes devem estar espaçados de maneira equidistante no intervalo descrito pelos dois anteriores.
