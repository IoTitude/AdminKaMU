package adminkamu;

import com.datastax.driver.core.Cluster;  
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;  
import com.datastax.driver.core.exceptions.NoHostAvailableException;
import java.util.LinkedHashMap;
import java.util.Map;


 
public class CassandraConnector {
 
    public static Map getDevices() {
        
        Cluster cluster;
        Session session;
        
        try {
            cluster = Cluster.builder().addContactPoint("").withCredentials("cassandra", "cassandra").build();
            session = cluster.connect("kaa");
            
            
            Map<String, String> map = new LinkedHashMap<>();
            
            ResultSet results = session.execute("SELECT mac, hash FROM admin1 where isadmin = false ALLOW FILTERING");
            
            for (Row row : results){
            map.put(row.getString("hash"), row.getString("mac"));
            }

            
            
            cluster.close();
            return map;
        }
        catch (NoHostAvailableException e){
            System.out.println(e.getErrors());
            return null;
        }
    }
}
