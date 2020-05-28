import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


public class ServerB extends UnicastRemoteObject implements ServerBIface {
    private static final long serialVersionUID = -5938174545515179377L;
    // Private member variables
    private int m_number_of_books;
    private String m_name_of_collection;
    private static String ip_server;
    private static String nombre_server;

    // Constructor
    public ServerB(String ip, String nombre)
            throws RemoteException, MalformedURLException, NotBoundException {
        super();//Llama al constructor de UnicastRemoteObject
        ip_server = ip;
        nombre_server = nombre;
        m_number_of_books = 10;
        m_name_of_collection = "CollectionBroker";
    }

    @Override
    public Integer number_of_books() throws RemoteException  {
        //notTODO: Implementar todos los metodos de la interface remota
        return m_number_of_books;
    }

    @Override
    public String name_of_collection() throws RemoteException {

        return m_name_of_collection;
    }


    @Override
    public Boolean ping() throws RemoteException{
        return true;
    }

    public static void main(String args[]) {
        //Fijar el directorio donde se encuentra el java.policy
        //El segundo argumento es la ruta al java.policy
        System.setProperty("java.security.policy", "./java.policy");
        //Crear administrador de seguridad System.setSecurityManager(new SecurityManager());
        System.setSecurityManager(new SecurityManager());
        //nombre o IP del host donde reside el objeto servidor
        String hostName = "155.210.154.192"; //se puede usar "IPhostremoto:puerto"
        //Por defecto RMI usa el puerto 1099
        String brokerIP = "155.210.154.192"; //Rellenar 
        try {
            // Crear objeto remoto
            ServerB obj = new ServerB(hostName, "serverBib");
            System.out.println("Creado!");
            //Registrar el objeto remoto
            Naming.rebind("//" + hostName + "/" + nombre_server, obj); 
            System.out.println("rebind donde");
            BrokerIface broker = (BrokerIface) Naming.lookup("//" + brokerIP + "/BrokerJH");
            // broker.registar_servidor(nombre_server, ip_server);
            broker.registar_servidor(nombre_server, ip_server);
            System.out.println("registrado");
            List<String> empty = new ArrayList<>();
            broker.baja_servicio(nombre_server, "name_of_collection");
            broker.baja_servicio(nombre_server, "number_of_books");
            broker.registrar_servicio(nombre_server, "name_of_collection", empty, "");
            broker.registrar_servicio(nombre_server, "number_of_books", empty, "");
            System.out.println(broker.mostrar_servicios());
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }
}
