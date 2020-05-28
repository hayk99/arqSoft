import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ServerBIface extends Remote{
    //metodos de la interface
    Integer number_of_books() 
        throws RemoteException;
    
    String name_of_collection()
        throws RemoteException;

    Boolean ping() throws RemoteException;
}
