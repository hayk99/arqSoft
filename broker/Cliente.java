
//notTODO: imports necesarios
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;
import java.util.*;


public class Cliente{
    public static void main(String[] args){

        if (System.getSecurityManager() == null) {
            System.setProperty("java.security.policy", "./java.policy");
            System.setSecurityManager(new SecurityManager());
        }
        try {
            //Paso 1 - Obtener una referencia al objeto servidor creado anteriormente
            //Nombre del host servidor o su IP. Es d ́onde se buscar ́a al objeto remoto
            String ip_broker = "155.210.154.192"; //IP DEL Broker
            BrokerIface broker =
            (BrokerIface) Naming.lookup("//"+ ip_broker + "/BrokerJH");
            //Paso 2 - Invocar remotamente los metodos del objeto servidor:
            int chosen_option;
            List<String> params = new ArrayList<String>();
            List<String> list_services = broker.mostrar_servicios();
            while(true){
                System.out.println("Los servicios que ofrecemos son los siguientes: ");
                list_services = broker.mostrar_servicios();
                int i = 1;
                System.out.println("0- " + "Salir");
                for(String service : list_services){
                    System.out.println( i++ + "- " + service);
                }
                System.out.println("Escoja su servicio[0-" + (i-1) + "]: ");
                Scanner S = new Scanner(System.in);
                chosen_option = S.nextInt();
                i=1;
                if(chosen_option == 0){
                    System.exit(1);
                }
                else{
                    System.out.println("El resultado de la ejecución de su servicio es: "+ broker.ejecutar_servicio(list_services.get(chosen_option-1), params));
                }
                
                // System.out.println("Dar hora "+ broker.ejecutar_servicio("dar_hora", params));
                // System.out.println("Name of collection : "+ broker.ejecutar_servicio("name_of_collection", params));
                // System.out.println("Numero de elementos "+ broker.ejecutar_servicio("number_of_books", params));
    
            }
        }
        catch (Exception ex){
            System.out.println(ex);
        }
    }
}
