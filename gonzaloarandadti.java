

import java.awt.Point;
import java.util.List;
import java.util.Random;

import uhu.snake.Agente;

import java.util.ArrayList;
import java.util.LinkedList;


public class gonzaloarandadti extends Agente {

		
	public gonzaloarandadti() {
		super();
	}
	

	@Override
	public List<String> pensar() {
		List<String> acciones = new LinkedList<String>();

		Point cabeza = CUERPO.get(0);
		
		while (!cabeza.equals(COMIDA)) 
		{
//			System.out.println(cabeza + " -> " + COMIDA);
			if (cabeza.x < COMIDA.x) 
			{
				cabeza.x += 1;
				acciones.add("DERECHA");
			} 
			
			else if (cabeza.x > COMIDA.x) 
			{
				cabeza.x -= 1;
				acciones.add("IZQUIERDA");
			}
			
			else if (cabeza.y > COMIDA.y)
			{
				cabeza.y -= 1;
				acciones.add("ARRIBA");
			} 
			
			else if (cabeza.y < COMIDA.y) 
			{
				cabeza.y += 1;
				acciones.add("ABAJO");	
			}
			System.out.println(acciones.get(0));
		}
//		System.out.println(cabeza + " -> " + COMIDA);
		return acciones;
	}



	
	
	
	
}
