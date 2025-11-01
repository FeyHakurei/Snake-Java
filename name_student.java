
import java.awt.Point;
import java.util.List;
import java.util.Random;

import uhu.snake.Agente;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.*;



public class name_student extends Agente {

	
	public name_student() {
		super();
	}

	 private class Nodo
	    {
	        int columna,fila;
	        int g; // Coste real desde el inicio
	        int f; // f = g + h

	        Nodo(int columna, int fila, int g, int h) 
	        {
	            this.fila = fila;
	            this.columna = columna;
	            this.g = g;
	            this.f = g + h;
	        }
	    }
	 
	 private int Manhattan(int x,int y)      //|x1 - x2| + |y1 - y2|
	    {
	       return Math.abs(x - COMIDA.x) + Math.abs(y - COMIDA.y);   
	    }
	 
	 private boolean CaminoLibre(int x, int y)
	 {
		 //boolean ser = true;
		 //int longo = CUERPO.size();
		 Point punto = new Point(x,y);
		 
		 if (x < 0 || y < 0 || x >= ANCHO || y >= ALTO) 
		 {
	            return false;
	     }
		 
		 for(Point p:CUERPO)
		 {
			 
			 if(p.equals(punto))
			 {
				 return false;
			 }
 
		 }
		 
		 return true;
	 }
	 
	 
	 
	 private List<String> Mov_Emergencia(Point cabeza)
	 {
		 
	     List<String> acciones = new LinkedList<String>();
		 
		 try {
			 
			// System.out.println("Posición de comida: " + COMIDA.x + "," + COMIDA.y);
	        int[][] movimientos = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
	        String[] direcciones = {"DERECHA", "IZQUIERDA", "ABAJO", "ARRIBA"};
	        boolean found= false;
	      
	        Map<String,String> papa = new HashMap<>();
	        String mejorDireccion = "DERECHA";
	        Queue<Point> queue = new LinkedList<>();
            boolean[][] visitaos = new boolean[ANCHO][ALTO];
            
	       for(int i=0; i<ANCHO; i++)
	       {
	    	   for(int j=0; j<ALTO;j++)
	    	   {
	    		   visitaos[i][j] = false;
	    	   }
	       }
	       
            queue.add(cabeza);
            visitaos[cabeza.x][cabeza.y]=true;
             
	             while(!queue.isEmpty())
	             {
	            	// System.out.println("HEROES");
	            	 Point V = queue.poll();
	            	 
	            	 if( V.equals(COMIDA))
	            	 {
	            		found=true;
	            		//System.out.println("SILENCIO");
	            		break;
	            		
	            	 }
	            	 
	            	 for (int[] dir : movimientos)
	                 {
	                     int nuevaFila = V.y + dir[1];
	                     int nuevaCol = V.x + dir[0];
	                     
	                     if(CaminoLibre(nuevaCol,nuevaFila) && !visitaos[nuevaCol][nuevaFila])
	                     {
	                    	// System.out.println("DEL");
	                    	// System.out.println(queue.size());
	                    	 Point vecino = new Point(nuevaCol,nuevaFila);
	                    	 queue.add(vecino);
	                    	 String keyHijo = nuevaCol + "," + nuevaFila;
	                         String keyPadre = V.x + "," + V.y;
	                         papa.put(keyHijo, keyPadre);
	                         visitaos[nuevaCol][nuevaFila] = true;
	                     }
	                     
	                 }
	            	 
	             }  
	             
	             if(found)
	     		{
	     			// Reconstruir el camino
	            	// System.out.println("GALLAGHER");
	     			LinkedList<Point> camino = new LinkedList<>();
	                 Point actual = new Point(COMIDA.x, COMIDA.y);

	                 while (!actual.equals(cabeza))
	                 {
	                     camino.addFirst(actual);
	                     String keyActual = actual.x + "," + actual.y;
	                     String keyPadre = papa.get(keyActual);
	                     
	                     if (keyPadre == null) 
	                     	{
	                     	  break; // Por seguridad
	                     	}

	                     String[] partes = keyPadre.split(",");
	                     actual = new Point(Integer.parseInt(partes[0]), Integer.parseInt(partes[1]));
	                 }

	                 // Traducir puntos a acciones
	                 Point anterior = cabeza;
	                 for (Point paso : camino) 
	                 {
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

	                     anterior = paso;;
	     			
	     		    }
	                 
	     	    }
	     		
	     		} 
	     		catch (Exception e) 
	     		{
	     	        acciones.clear();
	     	        acciones.add("DERECHA"); // movimiento por defecto si todo falla
	     	    }
		 
		 if (acciones.isEmpty())
			{
	           //System.out.println("CREMA_CATALANA");
	        }
	     	

	     		return acciones;
	   
		/* List<String> acciones = new LinkedList<String>();
		 
		 while (!cabeza.equals(COMIDA)) 
			{
//				System.out.println(cabeza + " -> " + COMIDA);
				if (cabeza.x < COMIDA.x && CaminoLibre(cabeza.x+1,cabeza.y)) 
				{
					cabeza.x += 1;
					acciones.add("DERECHA");
				} 
				
				else if (cabeza.x > COMIDA.x && CaminoLibre(cabeza.x-1,cabeza.y)) 
				{
					cabeza.x -= 1;
					acciones.add("IZQUIERDA");
				}
				
				else if (cabeza.y > COMIDA.y && CaminoLibre(cabeza.x,cabeza.y-1))
				{
					cabeza.y -= 1;
					acciones.add("ARRIBA");
				} 
				
				else if (cabeza.y < COMIDA.y && CaminoLibre(cabeza.x,cabeza.y+1)) 
				{
					cabeza.y += 1;
					acciones.add("ABAJO");	
				}
				System.out.println(acciones.get(0));
			}
//			System.out.println(cabeza + " -> " + COMIDA);
			return acciones;*/
	 }

	 
	 
	@Override
	public List<String> pensar() //Astra
	{
		List<String> acciones = new LinkedList<String>();
		try {

		/*
		
		ESCRIBE TU CÓDIGO AQUI
		============================
	
		De la clase Agent puedes usar las atributos
	
		List<Point> CUERPO;	// Lista puntos que describen a la serpiente.
		 					// El primero es la cabeza y el último es la cola
		Point COMIDA;		// Punto donde está la comida
		int ANCHO;			// Ancho del tablero del entorno
		int ALTO;			// Alto del tablero del entorno
		
		pintarTablero()		// Método que pinta el tablero con la serpiente y la comida 

	**************/
		
		
		// Si no hay camino posible, al menos intentamos movernos en dirección a la comida
				if (CUERPO.isEmpty()) 
				{
					acciones.add("DERECHA");
					return acciones;
				}

				Point cabeza = CUERPO.get(0);
				
				// Verificamos si estamos fuera de los límites del tablero
				if (cabeza.x < 0 || cabeza.y < 0 || cabeza.x >= ANCHO || cabeza.y >= ALTO) 
				{
					acciones.add("DERECHA"); // Movimiento por defecto
					return acciones;
				}
		
		
		
		boolean found =false;
		Point entrada = CUERPO.get(0);
		boolean[][] visitados = new boolean[ANCHO][ALTO];
		
		int[][] G = new int[ANCHO][ALTO];
		
		for(int i=0; i<ANCHO; i++)
		{
			for(int j=0; j<ALTO;j++)
			{
				G[i][j] = Integer.MAX_VALUE;
			}
		}
		
		G[entrada.x][entrada.y]=0; 
		
		Map<String,String> padres = new HashMap<>();
		
		PriorityQueue<Nodo> cola = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
		int heur = Manhattan(entrada.x,entrada.y);
		cola.add(new Nodo(entrada.x, entrada.y, 0, heur));
		
		int[][] movimientos = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}}; // DASW
		String[] direcciones = {"DERECHA", "IZQUIERDA", "ABAJO", "ARRIBA"};
		
		while(!cola.isEmpty())
		{
			Nodo V = cola.poll();
			
			if(visitados[V.columna][V.fila])
			{
				continue;
			}
			
			visitados[V.columna][V.fila] = true;
			
			if((V.columna == COMIDA.x) &&(V.fila == COMIDA.y) )
			{	
				found = true;
				break;	
			}
			
			for(int i = 0; i < movimientos.length; i++)
			{
				int nuevaCol = V.columna + movimientos[i][0];
				int nuevaFila = V.fila + movimientos[i][1];
	                
	                if(CaminoLibre(nuevaCol,nuevaFila) && !visitados[nuevaCol][nuevaFila])
	                {
	                	int nuevoG = V.g + 1;
	                	
	                	if(nuevoG < G[nuevaCol][nuevaFila])
	                	{
	                		
	                    G[nuevaCol][nuevaFila] = nuevoG;
	                    
	                    int novoHeur = Manhattan(nuevaCol,nuevaFila) ;
	                	
	                    cola.add(new Nodo(nuevaCol, nuevaFila, nuevoG, novoHeur));
	                    
	                    String keyHijo = nuevaCol + "," + nuevaFila;
                        String keyPadre = V.columna + "," + V.fila;
                        padres.put(keyHijo, keyPadre);
	                    
                        
	                	}
	                }
	            }

			//System.out.println("LONGO ="+cola.size());     
			
			
		}
		
		//System.out.println("CHE, EL ESTADO DE FOUND ES "+found );
		
		
		
		if(found)
		{
			// Reconstruir el camino
			
			LinkedList<Point> camino = new LinkedList<>();
            Point actual = new Point(COMIDA.x, COMIDA.y);

            while (!actual.equals(entrada))
            {
                camino.addFirst(actual);
                String keyActual = actual.x + "," + actual.y;
                String keyPadre = padres.get(keyActual);
                
                if (keyPadre == null) 
                	{
                	  break; // Por seguridad
                	}

                String[] partes = keyPadre.split(",");
                actual = new Point(Integer.parseInt(partes[0]), Integer.parseInt(partes[1]));
            }

            // Traducir puntos a acciones
            Point anterior = entrada;
            for (Point paso : camino) 
            {
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

                anterior = paso;;
			
		    }
            
	    }
		if (acciones.isEmpty())
		{
            acciones = (Mov_Emergencia(cabeza));
        }
		
		} 
		catch (Exception e) 
		{
	        acciones.clear();
	        acciones.add("DERECHA"); // movimiento por defecto si todo falla
	    }
	

		return acciones;
}
	
}
