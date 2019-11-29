package Pool;

import java.util.ArrayList;

public class poolManager {
	/*
	 * @author: Carlos Jose Tribiec Universidad Rafael Urdaneta
	 */
	ArrayList<Conexion> poolDisponible;
	ArrayList<Conexion> poolEnUso;
	public static int poolCap = 50;
	public static int poolRate = 10;
	public static int poolMax = 90;

	public poolManager() {

		System.out.println("Iniciando Pool...");
		System.out.println("----------------------------------");
		poolDisponible = new ArrayList<Conexion>();
		poolEnUso = new ArrayList<Conexion>();

		// Crear (poolCap) conexiones iniciales para esperar solicitudes y asignar
		// dichas conexiones
		for (int i = 1; i <= poolCap; i++) {
			Conexion c = new Conexion(i);
			poolDisponible.add(c);
		}
		System.out.println("Conexiones listas y a la espera...");

	}

	public void addConn() {
		// Aumentar el numero de conexiones a una razon de (poolRate)
		try {
			for (int i = 1; i <= poolRate; i++) {
				Conexion c = new Conexion(poolCap + i);
				poolDisponible.add(c);
			}
			poolCap = poolCap + poolRate;
			System.out.println("Aumentando capacidad del pool a -> " + poolCap);
		} catch (Exception exc) {
			System.err.println(exc);
		}
	}

	public void limpiarConn() {
		// Verifica todas las conexiones en uso que ya estan libres para asignarlas como
		// disponibles
		System.err.println("Liberando conexiones...");
		Integer cuenta = 0;
		for (int i = 0; i < poolEnUso.size(); i++) {
			if (poolEnUso.get(i).getStatus() == true) {
				Conexion c = poolEnUso.remove(i);
				poolDisponible.add(c);
				cuenta++;
			}
		}
		System.out.println(cuenta + " conexiones liberadas");
	}

	public void getConn() {
		// Intentar obtener una conexiones de las que estan disponibles en la lista
		try {
			if (poolDisponible.size() > 0) {
				// Si hay disponibles, le enviamos a la conexion disponible que obtuvimos y
				// enviamos el siguiente Query como parametro para que este lo ejecute
				String Query = "askdnoauhguhegahguahjigfafiogyauotynqdrtyasdaupasu"
						+ "pasdhugfyigfiyasgfiahsdfghasjkdfgsfghsgfhshddfsgfh"
						+ "askdnoauhguhegahguahjigfafddgyauotynqdrtyasdaupasu"
						+ "pasdhugfyigfiyasgfiahsdfghasjkddgsfghsgfhshddfsgfh"
						+ "aksndjahnduabdihasdasdaggisdhhsadsdasdasdasdasdasd";
				// Removemos el status de la conexion como disponible y lo movemos a la lista de
				// Conexions ocupadas
				Conexion c = poolDisponible.remove(poolDisponible.size() - 1);
				poolEnUso.add(c);
				// Procedemos a ejecutar el query de la clase conexion
				c.ejecutarQuery(Query);
			} else if ((poolEnUso.size() <= poolCap) && (poolEnUso.size() + poolRate) <= poolMax) {
				// Si no hay conexiones disponibles, intentar aumentar si aun estamos dentro de
				// los limites
				addConn();
				// Volvemos a intentar obtener una conexion ya que se han agregado nuevas
				this.getConn();
			} else {
				// La base de datos tiene su limite maximo de conexiones y no tiene
				// disponibles...
				System.err.println("Base de datos ocupada, intente mas tarde.....");
				// Procedemos a verificar que conexiones ya estan disponibles para asignarla en
				// la lista de Conexiones disponibles
				limpiarConn();
			}
		} catch (Exception exc) {
			// Manejo de Errores
			System.err.println("Error al obtener conexion (getConn) ->" + exc);
		}
	}

}