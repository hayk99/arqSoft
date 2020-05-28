import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
//TODO: imports necesarios

public class CollectionImpl extends UnicastRemoteObject implements Collection {
  //Private member variables
    private int m_number_of_books;
    private String m_name_of_collection;

    //Constructor
    public CollectionImpl(int number, String name) throws RemoteException {
        super();//Llama al constructor de UnicastRemoteObject
        m_number_of_books = number;
        m_name_of_collection = name;
        //notTODO: inicializar las variables privadas
    }

    @Override
    public int number_of_books() throws RemoteException  {
        //notTODO: Implementar todos los metodos de la interface remota
        return m_number_of_books;
    }

    @Override
    public String name_of_collection() throws RemoteException {

        return m_name_of_collection;
    }

    @Override
    public void name_of_collection(String _new_value) throws RemoteException {
        m_name_of_collection = _new_value;
    }

    public static void main(String args[]) {
        //Fijar el directorio donde se encuentra el java.policy
        //El segundo argumento es la ruta al java.policy
        System.setProperty("java.security.policy", "./java.policy");
        //Crear administrador de seguridad System.setSecurityManager(new SecurityManager());
        System.setSecurityManager(new SecurityManager());
        //nombre o IP del host donde reside el objeto servidor
        String hostName = "192.168.0.30:32000"; //se puede usar "IPhostremoto:puerto"
        //Por defecto RMI usa el puerto 1099
        try {
            // Crear objeto remoto
            CollectionImpl obj = new CollectionImpl(2, "biblio");
            System.out.println("Creado!");
            //Registrar el objeto remoto
            Naming.rebind("//" + hostName + "/MyCollection", obj); System.out.println("Estoy registrado!");
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }
}
