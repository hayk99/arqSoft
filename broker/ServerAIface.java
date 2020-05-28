import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ServerAIface extends Remote {
    //metodos de la interface
    String dar_hora()
        throws RemoteException;
    
    String dar_fecha()
        throws RemoteException;
    Boolean ping() throws RemoteException;
}
