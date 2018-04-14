// Czajka Kamil - grupa nr 1
import java.util.Scanner;
import java.util.Comparator;
import java.util.PriorityQueue;

class Vertex{
	public int wspX;
	public int wspY;
	public int price;
	public int pathCost;
	public Vertex prev;
//	public String path; 
	
	public Vertex(int x, int y, int p){
		wspX = x;
		wspY = y;
		price = p;
		pathCost = Integer.MAX_VALUE; // nieokreslony na poczatku
		prev = null; // nieokreslony na poczatku
//		path = ""; // poczatkowa sciezka
	}

	public void relax(Vertex v){
		if (pathCost > v.pathCost + price){
			pathCost = v.pathCost + price;
			prev = v;
//			direction(v);
			Source.queue.add(this);
		}
	}

/*	public void direction(Vertex v){
		if (v.wspX == wspX && v.wspY > wspY){	// E 
			path = "E" + v.path;
			return;
		}
		if (v.wspX == wspX && v.wspY < wspY){	// W
			path = "W" + v.path;
			return;
		}
		if (v.wspX > wspX && v.wspY == wspY){	// S
			path = "S" + v.path;
			return;
		}
		if (v.wspX < wspX && v.wspY == wspY){	// N
			path = "N" + v.path;
			return;
		}
	}
*/
	public void print(){
		System.out.print("( " + wspX + " , " + wspY + " )");
	}
} 

public class Source{
	public static Scanner sc = new Scanner(System.in);
	public static Vertex[][] vertex;	
	public static PriorityQueue<Vertex> queue;

	public static void dijkstra(int x, int y, int n, int m){
		vertex[x][y].pathCost = -5;	// sala promocyjna
		queue.add(vertex[x][y]);
		while (!queue.isEmpty()){
			Vertex current = queue.poll();
			if (current.wspX > 0){
				vertex[current.wspX - 1][current.wspY].relax(current); 
			}
			if (current.wspX + 1 < n){
				vertex[current.wspX + 1][current.wspY].relax(current);
			}
			if (current.wspY > 0){
				vertex[current.wspX][current.wspY - 1].relax(current);
			}
			if (current.wspY + 1 < m){
				vertex[current.wspX][current.wspY + 1].relax(current);
			}	
		} 
	}

	public static void reset(int n, int m){
		for (int i = 0; i < n; i++){
			for (int j = 0; j < m; j++){
				vertex[i][j].pathCost = Integer.MAX_VALUE;
				vertex[i][j].prev = null;
//				vertex[i][j].path = "";
			}
		}
	} 

	public static void main(String[] args){
		int set, n, m, p, s, x, y;	// set = zestawy, n x m tablica, p = ceny, s = promocja (-5), (x,y) gdzie promocja 	

		set = sc.nextInt();		
		while (set-- > 0){
			n = sc.nextInt();
			m = sc.nextInt();

			vertex = new Vertex[n][m];

			for (int i = 0; i < n; i++){
				for (int j = 0; j < m; j++){
					p = sc.nextInt();
					vertex[i][j] = new Vertex(i, j, p);
				}
			}

			s = sc.nextInt();
			for (int i = 0; i < s; i++){
				if (i != 0){
					reset(n, m);
				}
				queue = new PriorityQueue<Vertex>(n * m, new Comparator<Vertex>(){	// do porownywania obiektow Vertex
                    public int compare(Vertex v1, Vertex v2) {
                        return v1.pathCost - v2.pathCost;
                    }
				});				
				x = sc.nextInt();
				y = sc.nextInt();
				dijkstra(x - 1, y - 1, n, m);
				System.out.println(vertex[n - 1][0].pathCost + ", " + (vertex[0][m - 1].pathCost + 5));
				// + ", " + vertex[n - 1][0].path + ", " + vertex[0][m - 1].path);
			}			
		}
	}	
}