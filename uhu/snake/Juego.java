package uhu.snake;
import java.awt.Point;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Juego 
{
	private int ALTO, ANCHO;
	private Random r;

	private List<Point> snake;
	private Point food;
	private int score;
	private boolean fin;
	
	
	public Juego(int _ancho, int _alto) {
		r = new Random();
		ANCHO = _ancho;
		ALTO  = _alto;
		
		// Inicializar el juego
		snake = new ArrayList<Point>();
		food = new Point(r.nextInt(ANCHO), r.nextInt(ALTO));
		snake.add(new Point(r.nextInt(ANCHO), r.nextInt(ALTO)));

		score = 0;
		fin = false;

		
		
		
		
		
	}
	   
    public String serializeGameState() {
        StringBuilder sb = new StringBuilder();
    
        sb.append(ANCHO).append(",").append(ALTO).append(",");
        sb.append(food.x).append(",").append(food.y).append(",");
        for (Point p : snake) {
            sb.append(p.x).append(",").append(p.y).append(",");
        }
        return sb.toString();
    }

    public void moveSnake(String direction) {
        Point head = snake.get(0);
        Point newHead = null;

        switch (direction) {
            case "ARRIBA":
                newHead = new Point(head.x, head.y - 1);
                break;
            case "ABAJO":
                newHead = new Point(head.x, head.y + 1);
                break;
            case "DERECHA":
                newHead = new Point(head.x + 1, head.y);
                break;
            case "IZQUIERDA":
                newHead = new Point(head.x - 1, head.y);
                break;
            default:
            	System.out.println("ORDEN NO ENTENDIDA: " + direction);
            	fin=true;
        }

        // Mover la serpiente (agregar nuevo punto en la cabeza)
        snake.add(0, newHead);
        if (!snake.get(0).equals(food))
        	snake.remove(snake.size() - 1);

        // Comprobar si la serpiente ha colisionado con las paredes o consigo misma
        if (       (newHead.x < 0) 
        		|| (newHead.x >= ANCHO)
        		|| (newHead.y < 0 )
        		|| (newHead.y >= ALTO)
        		|| (snake.subList(1, snake.size()).contains(newHead))) {
        	fin = true;
        }

    }

    public void generaComida() {
        // Generar una nueva posición aleatoria para la comida
        food= new Point(r.nextInt(ANCHO), r.nextInt(ALTO));
    }

	public void gameOver(boolean b) {
		fin = b;
	}
	public boolean gameOver() {
		return fin;
	}

	public boolean estaComida() {
		return snake.get(0).equals(food);
	}

	public void sumaPuntos() {
		score++;
	}
	public int getPuntos() {
		return score;
	}
	public void pintarTablero() {
		char _BLOQUE = '#';
		char _COMIDA = 'o';
		char _SERP   = '*';
		char _CAB    = '@';
		
		char[][] tabla = new char[ANCHO][ALTO];
		
		for (Point c: snake) {
			tabla[c.x][c.y] = _SERP;
		}
		tabla[snake.get(0).x][snake.get(0).y] = _CAB;
		tabla[food.x][food.y] = _COMIDA;
		
		for (int i=0;i<ANCHO+2;i++)
			System.out.print(_BLOQUE);
		System.out.println();
		
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<ANCHO;i++) {
			for (int j=0;j<ALTO;j++) {
				if (tabla[i][j] ==0)
					System.out.print(" ");
				else
					System.out.print("" + tabla[i][j]);
			}
			System.out.println();
		}
		
	}

	

}
