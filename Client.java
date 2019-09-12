import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry; 
import java.rmi.registry.Registry;
import java.util.Scanner;  
import java.rmi.server.UnicastRemoteObject;

public class Client extends UnicastRemoteObject implements GraphOperations{  
   
    private static final long serialVersionUID = 1L;
    protected Client() throws RemoteException {
   }

   public boolean addEdge(int u,int v) throws RemoteException{
        return false;
   }

   public int shortestPath(int u,int v) throws RemoteException  {
        return -1;
   }
   public String getGraph() throws RemoteException {
        return "";
   }
   public String printMsg(String msg) throws RemoteException {
        System.out.println("Message from server");
        return "";
   }
   public static void main(String[] args) {  
      try 
      {  
    	  
    	Registry reg = LocateRegistry.getRegistry("localhost", 1099);
    	GraphOperations graph_ops = (GraphOperations) reg.lookup("RMIServer");
        //Registry registry = LocateRegistry.getRegistry(null); 
        //GraphOperations graph_ops= (GraphOperations) registry.lookup("RMIServer");
        System.out.println(graph_ops);
        Scanner sc=new Scanner(System.in);
         while(true)
         {
            System.out.print("command:");             
            String com=sc.nextLine();
            String[] temp=com.split(" ");
            
            String command_function=temp[0];
            if(command_function.equals("quit"))
            {
                System.out.println("Exit");
                break;
            }
            if(command_function.equals("test")){
                graph_ops.printMsg("Hello");
                continue;
            }
            if(temp[0].equals("get_graph")){
                String gr=graph_ops.getGraph();
                System.out.println(gr);
            }
            if(temp.length!=3)
            {
                 System.out.println("Invalid command structure");
                 continue;
            }

            
            int par1=Integer.parseInt(temp[1]);
            int par2=Integer.parseInt(temp[2]);
            if(temp[0].equals("add_edge"))
            {
                System.out.println(par1+" "+par2);
                boolean res=graph_ops.addEdge(par1,par2);
                if(res)
                    System.out.println("Edge added in graph");
                else
                    System.out.println("Edge could not be added in graph");
            }
            else if(temp[0].equals("shortest_path"))
            {
                int res=graph_ops.shortestPath(par1,par2);
                if(res!=-1)
                    System.out.println("Shortest path in "+par1+" "+par2+" "+res);
                else
                    System.out.println("Path does not exist in the graph");
            }
            else 
                System.out.println("No such command");
        }
        sc.close();
         
      } catch (Exception e) {
         System.err.println("Client exception: " + e.toString()); 
         e.printStackTrace(); 
      }
   }
}