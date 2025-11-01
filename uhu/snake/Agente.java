package uhu.snake;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Agente {

	protected List<Point> CUERPO;
	protected Point COMIDA;
	protected int ANCHO;
	protected int ALTO;
	protected Random r;

	
	public String[] ACCIONES= {"DERECHA","IZQUIERDA","ARRIBA","ABAJO"};
	
	public Agente() 
	{
		r = new Random();
		System.out.println("Serpiente Cargada");
	}
		
	public final List<String> decidir(String Tablero)
	{
		List<String> acciones =new ArrayList<String>();
		analizarEntrada(Tablero);

		acciones = pensar();
		
		
		return acciones;
	}
	
	public abstract List<String> pensar();

	/***************************/
	
	public void pintarTablero() 
	{
		char _BLOQUE = '#';
		char _COMIDA = '@';
		char _SERP   = '*';
		
		char[][] tabla = new char[ANCHO][ALTO];
		
		for (Point c: CUERPO) 
		{
			tabla[c.x][c.y] = _SERP;
		}
		
		tabla[COMIDA.x][COMIDA.y] = _COMIDA;
		
		for (int i=0;i<ANCHO+2;i++)
					System.out.print(_BLOQUE);
		
		for (int i=0;i<ANCHO;i++) 
		{
			for (int j=0;j<ALTO;j++) 
			{
				if (tabla[i][j] ==0)
					System.out.print(" ");
				else
					System.out.print("" + tabla[i][j]);
			}
			System.out.println();
		}
		
	}

	
	private void analizarEntrada(String tablero)
	{
		String[] ordenes = tablero.split(",");
		ANCHO = Integer.parseInt(ordenes[0]);
		ALTO  = Integer.parseInt(ordenes[1]);
		
		COMIDA = new Point(Integer.parseInt(ordenes[2]),Integer.parseInt(ordenes[3]));

		CUERPO = new ArrayList<Point>();
		for(int i=4;i<ordenes.length;i+=2) {
			Point n = new Point(Integer.parseInt(ordenes[i]),Integer.parseInt(ordenes[i+1]));
			CUERPO.add(n);
		}
		
	}

}
