import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface BrokerIface extends Remote{
    //metodos de la interface
    void registar_servidor(final String nombre_servidor, final String host_remoto_IP_puerto)
        throws RemoteException;
    
    String ejecutar_servicio(final String nom_servicio, final List<String> params)
        throws RemoteException;

    void baja_servicio(String server_name, String service_name)
        throws RemoteException;

        
    void registrar_servicio (String server_name, String service_name, 
        List<String> param, String tipo_retorno) throws RemoteException;

    List<String> mostrar_servicios ()
        throws RemoteException;

}
