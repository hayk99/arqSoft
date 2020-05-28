import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ServerA extends UnicastRemoteObject implements ServerAIface {
    private static final long serialVersionUID = 1L;
    // Private member variables
    private static String ip_server;
    private static String nombre_server;
    //Constructor
    public ServerA(String ip, String nombre) throws RemoteException {
        super();//Llama al constructor de UnicastRemoteObject
        ip_server = ip;
        nombre_server = nombre;
    }

    @Override
    public String dar_fecha() throws RemoteException{
        final DateFormat formatoDia = new SimpleDateFormat ("dd-MM-yyyy");
        final Date date = new Date();
        return formatoDia.format(date);
    }

    @Override
    public String dar_hora() throws RemoteException {
        final DateFormat formatoDia = new SimpleDateFormat ("HH:mm:ss");
        final Date date = new Date();
        return formatoDia.format(date);
    }
    @Override
    public Boolean ping() throws RemoteException{
        return true;
    }

    public static void main(final String args[]) {
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
            ServerA obj = new ServerA(hostName, "serverNTP");
            System.out.println("Creado!");
            System.out.println(nombre_server);
            Naming.rebind("//" + hostName + "/" + nombre_server, obj);
            System.out.println("rebind donde");
            BrokerIface broker = (BrokerIface) Naming.lookup("//" + brokerIP + "/BrokerJH");            
            broker.registar_servidor(nombre_server, ip_server);
            System.out.println("registrado");
            List<String> empty = new ArrayList<>();
            broker.baja_servicio(nombre_server, "dar_hora");
            broker.baja_servicio(nombre_server, "dar_fecha");
            broker.registrar_servicio(nombre_server, "dar_hora", empty, "");
            broker.registrar_servicio(nombre_server, "dar_fecha", empty, "");
            System.out.println(broker.mostrar_servicios());
            
        }
        catch (final Exception ex){
            System.out.println(ex);
        }
    }
}
