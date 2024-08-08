import GPSDataManager.GPSNode;
import GPSDataManager.GPSNodeReader;
import OSMDataManager.NetworkNode;
import OSMDataManager.NetworkNodeReader;

import java.util.LinkedHashMap;

public class RouteAligningAlgorithm {
    public static void main(String[] args) {
        // Load all GPS nodes to be snapped
        GPSNodeReader gPSNodeReader = new GPSNodeReader();
        String gPSNodesFilePath = "";
        gPSNodeReader.readGPSNodes(gPSNodesFilePath);
        LinkedHashMap<Long, GPSNode> gPSNodes = gPSNodeReader.getGPSNodes();

        // Load all network nodes to be snapped to
        NetworkNodeReader networkNodeReader = new NetworkNodeReader();
        String networkNodesFilePath = "";
        networkNodeReader.readNetworkNodes(networkNodesFilePath);
        LinkedHashMap<Long, NetworkNode> networkNodes = networkNodeReader.getNetworkNodes();
    }
}
