package uhu.snake;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class SnakeIA2025 {

	public static void main(String[] args) {
		
		boolean visual = true;
		
		welcome();
		
		int ancho=20, alto=20;
		String alumno = "name_student";
		
		switch(args.length) {
		
		case 1:
			alumno = args[0];
			break;
			
		case 3:
			alumno = args[0];
			ancho  = Integer.parseInt(args[1]);
			alto   = Integer.parseInt(args[2]);
		
		default:
			System.out.println("Error en los argumentos");
			System.out.println("Debe de escribir la clase.");
			System.exit(-1);
		}

		// Creamos el juego
		Juego JUEGO = new Juego(ancho, alto);
		
		// Carga de la clase del alumno
		Agente JUGADOR = CargarJugador(alumno);
		
		
		while (!JUEGO.gameOver()) {
			
			List<String> acciones = JUGADOR.decidir(JUEGO.serializeGameState());
			
			for(String acc: acciones) {
				JUEGO.moveSnake(acc);
				if (visual) { 
					JUEGO.pintarTablero();
					try {
						Thread.sleep(100); // espera 1000 milisegundos = 1 segundo
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (JUEGO.gameOver())
					break;
			}
			
			if (JUEGO.estaComida()) {
				JUEGO.sumaPuntos();
				JUEGO.generaComida();
			} else {
				JUEGO.gameOver(true);
			}			
			
			
			
			
		}
		
		System.out.println("Puntuación final: " + JUEGO.getPuntos());
		
		
		
		
		
	}

	private static void welcome() {
		System.out.println("Bienvenidos al Snake-UHU");
	}


	private static Agente CargarJugador(String alumno) {
		boolean fallo = false;
		Class<?> agente;
		try {
			agente = Class.forName(alumno);
			// Crear una instancia de la clase (requiere un constructor sin parámetros)
            Object obj = agente.getDeclaredConstructor().newInstance();

			return (Agente)obj;
			
		} catch (ClassNotFoundException e) {
			fallo = true;
		} catch (InstantiationException e) {
			fallo = true;
		} catch (IllegalAccessException e) {
			fallo = true;
		} catch (IllegalArgumentException e) {
			fallo = true;
		} catch (InvocationTargetException e) {
			fallo = true;
		} catch (NoSuchMethodException e) {
			fallo = true;
		} catch (SecurityException e) {
			fallo = true;
		}
		System.out.println("Error al cargar la clase del Alumno:" + alumno);
		System.exit(-1);
		
		return null;
	}


}
