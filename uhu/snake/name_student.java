package uhu.snake;
import java.awt.Point;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.*;

public class name_student extends Agente {

    public name_student() {
        super();
    }

    private class Nodo {
        int columna, fila;
        int g;
        int f;

        Nodo(int columna, int fila, int g, int h) {
            this.fila = fila;
            this.columna = columna;
            this.g = g;
            this.f = g + h;
        }
    }

    private int Manhattan(int x, int y) {
        return Math.abs(x - COMIDA.x) + Math.abs(y - COMIDA.y);
    }

    private boolean CaminoLibre(int x, int y) {
        Point punto = new Point(x, y);
        for (Point p : CUERPO) {
            if (p.equals(punto)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<String> pensar() {
        List<String> acciones = new LinkedList<>();
        Point cabeza = CUERPO.get(0);

        boolean found = false;
        boolean[][] visitados = new boolean[ANCHO][ALTO];
        int[][] G = new int[ANCHO][ALTO];
        for (int i = 0; i < ANCHO; i++) {
            Arrays.fill(G[i], Integer.MAX_VALUE);
        }
        G[cabeza.x][cabeza.y] = 0;

        Map<String, String> padres = new HashMap<>();
        PriorityQueue<Nodo> cola = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        int heur = Manhattan(cabeza.x, cabeza.y);
        cola.add(new Nodo(cabeza.x, cabeza.y, 0, heur));

        int[][] movimientos = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // Derecha, Izquierda, Abajo, Arriba

        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();
            if (visitados[actual.columna][actual.fila]) continue;
            visitados[actual.columna][actual.fila] = true;

            if (actual.columna == COMIDA.x && actual.fila == COMIDA.y) {
                found = true;
                break;
            }

            for (int[] mov : movimientos) {
                int nx = actual.columna + mov[0];
                int ny = actual.fila + mov[1];

                if (nx >= 0 && nx < ANCHO && ny >= 0 && ny < ALTO && CaminoLibre(nx, ny) && !visitados[nx][ny]) {
                    int nuevoG = actual.g + 1;
                    if (nuevoG < G[nx][ny]) {
                        G[nx][ny] = nuevoG;
                        int nuevaHeur = Manhattan(nx, ny);
                        cola.add(new Nodo(nx, ny, nuevoG, nuevaHeur));
                        padres.put(nx + "," + ny, actual.columna + "," + actual.fila);
                    }
                }
            }
        }

        if (found) {
            LinkedList<Point> camino = new LinkedList<>();
            Point actual = new Point(COMIDA.x, COMIDA.y);
            while (!actual.equals(cabeza)) {
                camino.addFirst(actual);
                String keyPadre = padres.get(actual.x + "," + actual.y);
                if (keyPadre == null) break;
                String[] partes = keyPadre.split(",");
                actual = new Point(Integer.parseInt(partes[0]), Integer.parseInt(partes[1]));
            }

            if (!camino.isEmpty()) {
                Point siguientePaso = camino.getFirst();
                int dx = siguientePaso.x - cabeza.x;
                int dy = siguientePaso.y - cabeza.y;

                if (dx == 1) acciones.add("DERECHA");
                else if (dx == -1) acciones.add("IZQUIERDA");
                else if (dy == 1) acciones.add("ABAJO");
                else if (dy == -1) acciones.add("ARRIBA");
            }
        }

        // Si no encuentra camino o la lista está vacía, mover en una dirección segura
        if (acciones.isEmpty()) {
            int[][] direccionesSeguras = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1}
            };
            for (int[] dir : direccionesSeguras) {
                int nx = cabeza.x + dir[0];
                int ny = cabeza.y + dir[1];
                if (nx >= 0 && nx < ANCHO && ny >= 0 && ny < ALTO && CaminoLibre(nx, ny)) {
                    if (dir[0] == 1) acciones.add("DERECHA");
                    else if (dir[0] == -1) acciones.add("IZQUIERDA");
                    else if (dir[1] == 1) acciones.add("ABAJO");
                    else if (dir[1] == -1) acciones.add("ARRIBA");
                    break;
                }
            }
        }

        return acciones;
    }
}
