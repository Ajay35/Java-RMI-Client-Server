import java.rmi.registry.Registry; 
import java.rmi.registry.LocateRegistry;
import java.util.List;
import java.rmi.Naming;
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue; 
import java.util.LinkedList;


public class Server extends UnicastRemoteObject implements GraphOperations {
    
    private static final long serialVersionUID = 1L;
    public HashMap<Integer,List<Integer>> adjList;
    final private int vertices=100005;
    
    
    protected Server() throws RemoteException {
        System.out.println("New connection");
        adjList= new HashMap<Integer,List<Integer>>();
    }

    public boolean addEdge(int u,int v) throws RemoteException{

        if((adjList.containsKey(u) && adjList.get(u).contains(v)) || (adjList.containsKey(v) && adjList.get(v).contains(u))){
            return false;
        }
        if(!adjList.containsKey(u))
            adjList.put(u,new ArrayList<Integer>());
        if(!adjList.containsKey(v))
            adjList.put(v,new ArrayList<Integer>());
        adjList.get(u).add(v);
        adjList.get(v).add(u);
        return true;
    }

    public int shortestPath(int source,int destination) throws RemoteException{
       
        if(!adjList.containsKey(source))
            return -1;
        if(!adjList.containsKey(destination))
            return -1;
        boolean[] visited = new boolean[vertices];
        visited[source] = true;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        int d=0;
        while(!queue.isEmpty()){
            
            int s=queue.size();
            while(s!=0)
            {
                int u=queue.poll();
                if(u==destination)
                {
                    System.out.println("Shortest Path:"+d);
                    return d;
                }
                for(int v:adjList.get(u))
                {
                    if(!visited[v])
                    {
                        queue.add(v);
                        visited[v]=true;
                    }
                }
                s--;
            }
            d++;
        }
        System.out.println("Shortest path Not found:"+"-1");
        return -1;
    }
    public String getGraph(){
        
        String gr="";
        for(int i=1;i<=100005;i++)
        {
           if(adjList.containsKey(i))
           {
               gr+=i+" \n";
               for(int v:adjList.get(i))
                    gr+=v+" ";
               gr+="\n";
           }
        }
        return gr;
    }
    public String printMsg(String hello) throws RemoteException  {
        System.out.println("testing "+hello);
        return hello;
    }
   public static void main(String args[]) { 
      try
      { 
    	  System.setProperty("java.rmi.server.RMIServer", "localhost");  
    	  Registry reg = LocateRegistry.createRegistry(1099);  
    	  Server serv = new Server();        
    	  reg.rebind("RMIServer", serv);
      } 
      catch(Exception e)
      { 
        System.err.println("Server exception: " + e.toString()); 
        e.printStackTrace(); 
      } 
   } 
} 