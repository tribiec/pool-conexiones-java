package Pool;
public class programa{
	/*
	 * @author: Carlos Jose Tribiec Universidad Rafael Urdaneta
	 */
	poolManager poolManager;

	public programa() {
		System.out.println("Pool de Conexiones a Base de Datos");	
		System.out.println("Hecho por Carlos Tribiec");
		System.out.println("2019-A");
		System.out.println("----------------------------------");
		poolManager = new poolManager();
		try {
		while(true) {
			poolManager.getConn();
			
		}
		}catch(Exception exc) {
			System.err.println(exc);
		}
	}
	
	public static void main(String[] args) {
		new programa();
	}
	
}
