package Pool;
import java.sql.*;
public class Conexion extends Thread{
	/*
	 * @author: Carlos Jose Tribiec Universidad Rafael Urdaneta
	 */
	public static Connection Conn;
	public static boolean activo = true; /* Status de la conexion, activo (true) o libre */
	public int pool;
	
	public Conexion(int pool) {
		// Constructor
		this.start();
		this.pool = pool; /* ID de la Conexion en el Pool */
	}
	
	public void run() {
		// Comenzar conexion
		try {
		Conectar();
		}catch(Exception exc) {
			System.err.println("ERROR ->" + exc);
		}
	}
	
	public synchronized void Conectar(){
		// Parametros de conexion a la base de datos
		try{
			Class.forName("org.postgresql.Driver");
			Conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/pool", "postgres", "1234");
			activo = true;
			} catch(Exception e){e.printStackTrace(); } 
	}
	
	public synchronized boolean getStatus() {
		// Devolver Status de la conexion
		return activo;
	}
	
	public synchronized void ejecutarQuery(String Query) {
		// Ejecuta funcion
			activo = false;
			try {
				PreparedStatement ps = Conn.prepareStatement(
				"INSERT INTO pool (mensaje) VALUES (?)");
		 		ps.setString(1, Query);
	           	ps.executeUpdate();
	           	System.out.println("ID Pool en Uso: ->" + this.pool);
				ps.close();
			} catch (SQLException e) {e.printStackTrace();}
			activo = true;
	}
	
	
	public void Desconectar(){
		try{
			Conn.close();
		}catch(Exception e){ e.printStackTrace(); }	
	}
	
}
