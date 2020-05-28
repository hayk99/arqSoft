import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BrokerB extends UnicastRemoteObject implements BrokerIface {
    private static final long serialVersionUID = -1936626453871368601L;
    private ServerAIface serverA;
    private ServerBIface serverB;
    private String ip_serverA;
    private String ip_serverB;
    private String name_serverA;
    private String name_serverB;
    private List<String> services_serverA = new ArrayList<>();
    private List<String> services_serverB = new ArrayList<>();

    BrokerB() throws RemoteException{
        super();
    }

    // Add new serverA
    public void addServerA(String name, String ip ){
        try {
            System.out.println("Registro en A: "+ name + "con ip: "+ ip);
            ip_serverA = ip;
            name_serverA = name;
            serverA = (ServerAIface) Naming.lookup("//" + ip_serverA + "/" + name_serverA);
        }
        catch(final Exception ex) {
            System.out.println(ex);
        }
    }

    //  Add new serverB
    public void addServerB(String name, String ip ){
        try {
            System.out.println("Registro en A: "+ name + "con ip: "+ ip);
            ip_serverB = ip;
            name_serverB = name;
            serverB = (ServerBIface) Naming.lookup("//" + ip_serverB + "/" + name_serverB);
        }
        catch(final Exception ex) {
            System.out.println(ex);
        }
    }

    //  Check if serverB is alive
    public void checkA(){
        try {
            boolean kk = serverA.ping();
        }
        catch(final Exception ex) {
            System.out.println(ex);
            ip_serverA =null;
        }
    }

    //  Check if serverB is alive
    public void checkB(){
        try {
            boolean kk =  serverB.ping();
        }
        catch(final Exception ex) {
            System.out.println(ex);
            ip_serverB =null;
        }
    }

    @Override
    public void registar_servidor( String nombre_servidor,  String ip_host) 
        throws RemoteException {
            checkA();
            checkB();
            System.out.println("ipA: "+ip_serverA+" ipB : "+ip_serverB);
             if (ip_serverA == null){
                System.out.println("entro if");
                addServerA(nombre_servidor, ip_host);
            }
            else if (ip_serverB == null){
                System.out.println("entro else if");
                addServerB(nombre_servidor, ip_host);
            }
            else {
                System.out.println("-----CUIDADO------");
            }
            System.out.println(ip_serverA + " " + ip_serverB + " " + name_serverA + " " + name_serverB + " ");
    }

    @Override
    public String ejecutar_servicio(final String nom_servicio, final List<String> params) 
        throws RemoteException{
        String resul = "";
        try {
            if (services_serverA.contains(nom_servicio)) {
                if (nom_servicio.equals("dar_fecha")) {
                    resul = serverA.dar_fecha();
                } else if (nom_servicio.equals("dar_hora")) {
                    resul = serverA.dar_hora();
                }

            } else if (services_serverB.contains(nom_servicio)) {
                if (nom_servicio.equals("number_of_books")) {
                    resul = serverB.number_of_books().toString();
                } else if (nom_servicio.equals("name_of_collection")) {
                    resul = serverB.name_of_collection();
                }
            } else {
                System.out.println("Error en busqueda de servicio");
            }

        } catch (final Exception ex) {
            System.out.println(ex);
            resul="El servicio solicitado no esta disponible debido a que el servidor ha caido";
        }
        return resul;
    }

    //Dar de baja un servicio
    public void baja_servicio(String server_name, String service_name)
        throws RemoteException {
        // baja de servicio de A
        if (server_name.equals(name_serverA)){
            if (services_serverA.contains(server_name)){
                services_serverA.remove(server_name);
            }
            else{
                System.out.println("--LISTA DE A NO CONTIENE ESTE SERVICIO");
            }
        }
        // baja de servicio de B
        else if (server_name.equals(name_serverB)){
            if (services_serverB.contains(server_name)){
                services_serverB.remove(server_name);
            }
            else{
                System.out.println("--LISTA DE B NO CONTIENE ESTE SERVICIO");
            }
        }
        else{
            System.out.println("!!!llamada con nombre server raro");
        }
    }

    //Registrar un servicio 
    public void registrar_servicio (String server_name, String service_name, 
        List<String> param, String tipo_retorno) throws RemoteException{
        if (server_name.equals(name_serverA)){
            if ( services_serverA.contains(service_name)){
                System.out.println("ya existe el servicio " + service_name + "en A");
            }
            else {
                services_serverA.add(service_name);
                System.out.println("servicio " + service_name + " anyadido en A");
            }
        }
        else if (server_name.equals(name_serverB)){
            if (services_serverB.contains(service_name)){
                System.out.println("ya existe el servicio " + service_name + "en B");
            }
            else{
                services_serverB.add(service_name);
                System.out.println("servicio " + service_name + " anyadido en B");
            }
        }
        else{
            System.out.println("!!!llamada con nombre server raro");
        }
    }

    public List<String> mostrar_servicios ()
        throws RemoteException{
            List<String> union = new ArrayList<String>();
            union.addAll(services_serverA);
            union.addAll(services_serverB);
            return union;
    }

    public static void main(final String args[]) {
        //Fijar el directorio donde se encuentra el java.policy
        //El segundo argumento es la ruta al java.policy
        System.setProperty("java.security.policy", "./java.policy");
        //Crear administrador de seguridad System.setSecurityManager(new SecurityManager());
        System.setSecurityManager(new SecurityManager());
        //nombre o IP del host donde reside el objeto servidor
        String brokerIP = "155.210.154.192"; //se puede usar "IPhostremoto:puerto"
        //Por defecto RMI usa el puerto 1099
        try {
            // Crear objeto remoto
            BrokerB obj = new BrokerB();
            System.out.println("Creado!");
            //Registrar el objeto remoto
            Naming.rebind("//" + brokerIP +"/BrokerJH", obj);
            System.out.println("Estoy registrado!");
            // Broker b = (Broker) Naming.lookup("//" + brokerIP + "/BrokerJH");
            System.out.println("lookupbroker ok!");
        }
        catch (final Exception ex){
            System.out.println(ex);
        }
    }
}
