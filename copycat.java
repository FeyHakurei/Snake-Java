
package uhu.snake;

import java.awt.Point;
import java.util.*;

public class name_student extends Agente {

    public name_student() {
        super();
    }

    private class Nodo {
        int columna, fila;
        int g; // Coste real desde el inicio
        int f; // f = g + h

        Nodo(int columna, int fila, int g, int h) {
            this.fila = fila;
            this.columna = columna;
            this.g = g;
            this.f = g + h;
        }
    }

    // Heurística Manhattan: distancia desde (x,y) hasta COMIDA
    private int Manhattan(int x, int y) {
        return Math.abs(x - COMIDA.x) + Math.abs(y - COMIDA.y);
    }

    // Comprueba que la casilla (x,y) no está ocupada por la serpiente
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
    public List<String> pensar() { // Astra
        List<String> acciones = new LinkedList<>();

        Point entrada = CUERPO.get(0);

        // Matrices visitados y G con dimensiones ALTO x ANCHO (fila x columna)
        boolean[][] visitados = new boolean[ALTO][ANCHO];
        int[][] G = new int[ALTO][ANCHO];

        // Inicializar G con infinito
        for (int i = 0; i < ALTO; i++) {
            for (int j = 0; j < ANCHO; j++) {
                G[i][j] = Integer.MAX_VALUE;
            }
        }

        G[entrada.y][entrada.x] = 0;

        // Cola prioridad con Comparator por f = g + h
        PriorityQueue<Nodo> cola = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        int heur = Manhattan(entrada.x, entrada.y);
        cola.add(new Nodo(entrada.x, entrada.y, 0, heur));

        // Mapa padres: clave "x,y" para evitar problemas de equals y hashCode
        Map<String, String> padres = new HashMap<>();

        int[][] movimientos = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } }; // Derecha, Izquierda, Abajo, Arriba

        boolean found = false;

        while (!cola.isEmpty()) {
            Nodo V = cola.poll();

            if (visitados[V.fila][V.columna]) {
                continue;
            }
            visitados[V.fila][V.columna] = true;

            if (V.columna == COMIDA.x && V.fila == COMIDA.y) {
                found = true;
                break;
            }

            for (int[] mov : movimientos) {
                int nuevaCol = V.columna + mov[0];
                int nuevaFila = V.fila + mov[1];

                // Comprobar límites y si casilla libre y no visitada
                if (nuevaCol >= 0 && nuevaFila >= 0 && nuevaCol < ANCHO && nuevaFila < ALTO && CaminoLibre(nuevaCol, nuevaFila)
                        && !visitados[nuevaFila][nuevaCol]) {

                    int nuevoG = V.g + 1;

                    if (nuevoG < G[nuevaFila][nuevaCol]) {
                        G[nuevaFila][nuevaCol] = nuevoG;
                        int nuevoHeur = Manhattan(nuevaCol, nuevaFila);

                        cola.add(new Nodo(nuevaCol, nuevaFila, nuevoG, nuevoHeur));

                        // Guardar el padre con claves string
                        String keyHijo = nuevaCol + "," + nuevaFila;
                        String keyPadre = V.columna + "," + V.fila;
                        padres.put(keyHijo, keyPadre);
                    }
                }
            }
        }

        if (found) {
            // Reconstruir camino desde comida hasta la cabeza
            LinkedList<Point> camino = new LinkedList<>();
            Point actual = new Point(COMIDA.x, COMIDA.y);

            while (!actual.equals(entrada)) {
                camino.addFirst(actual);
                String keyActual = actual.x + "," + actual.y;
                String keyPadre = padres.get(keyActual);
                if (keyPadre == null) break; // Por seguridad

                String[] partes = keyPadre.split(",");
                actual = new Point(Integer.parseInt(partes[0]), Integer.parseInt(partes[1]));
            }

            // Traducir puntos a acciones
            Point anterior = entrada;
            for (Point paso : camino) {
                int dx = paso.x - anterior.x;
                int dy = paso.y - anterior.y;

                if (dx == 1)
                    acciones.add("DERECHA");
                else if (dx == -1)
                    acciones.add("IZQUIERDA");
                else if (dy == 1)
                    acciones.add("ABAJO");
                else if (dy == -1)
                    acciones.add("ARRIBA");

                anterior = paso;
            }
        }

        return acciones;
    }
}
