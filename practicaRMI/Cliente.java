//notTODO: imports necesarios
import java.rmi.Naming;
import java.rmi.RMISecurityManager;

public class Cliente{
    public static void main(String[] args){
        //TODO: Fijar el directorio donde se encuentra el java.policy
        if (System.getSecurityManager() == null) {
            //TODO: Crear administrador de seguridad
            System.setProperty("java.security.policy", "./java.policy");
            System.setSecurityManager(new SecurityManager());
        }
        try {
            //Paso 1 - Obtener una referencia al objeto servidor creado anteriormente
            //Nombre del host servidor o su IP. Es d ́onde se buscar ́a al objeto remoto
            String hostname = "155.210.152.177"; //IP DEL PROFESOR
                Collection server =
            (Collection) Naming.lookup("//"+ hostname + "/MyCollection");
            //Paso 2 - Invocar remotamente los metodos del objeto servidor:
            //TODO: obtener el nombre de la colecci ́on y el n ́umero de libros
            System.out.println("Nombre de la colección: "+ server.name_of_collection());
            //TODO: cambiar el nombre de la coleccion y ver que ha funcionado
            System.out.println("Número de libros: "+ server.number_of_books());


            //Entrega de la practica
            //Las Xs son tu NIA
            String resultado = server.practica_hecha("a757715", "/home/a757715/RMI/"); 
            System.out.println("El profesor me responde: " + resultado);
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }
}
