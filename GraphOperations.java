import java.rmi.Remote; 
import java.rmi.RemoteException;  

public interface GraphOperations extends Remote {  
   
   public String printMsg(String msg) throws RemoteException; 
   public boolean addEdge(int u,int v) throws RemoteException;
   public int shortestPath(int u,int v) throws RemoteException;
   public String getGraph() throws RemoteException; 

}
