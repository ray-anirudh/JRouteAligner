package OSMDataManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

public class NetworkNodeReader {
    private final LinkedHashMap<Long, NetworkNode> networkNodes = new LinkedHashMap<>();

    // Read and store all the network nodes in the aforementioned hashmap
    private void readNetworkNodes(String networkNodesFilePath) {
        try {
            // Create a reader for the "nodes.csv" file
            BufferedReader networkNodesReader = new BufferedReader(new FileReader(networkNodesFilePath));
            String newline;

            // Find pertinent indices
            String[] networkNodesHeaderArray = networkNodesReader.readLine().split(",");
            int networkNodeLongitudeIndex = findIndexInArray(networkNodesHeaderArray, "node_longitude");
            int networkNodeLatitudeIndex = findIndexInArray(networkNodesHeaderArray, "node_latitude");

            // Read body and process data
            long networkNodeId = 0;
            while ((newline = networkNodesReader.readLine()) != null) {
                String[] networkNodeDataRecord = newline.split(",");
                networkNodeId++;

                double networkNodeLongitude = Double.parseDouble(networkNodeDataRecord[networkNodeLongitudeIndex]);
                double networkNodeLatitude = Double.parseDouble(networkNodeDataRecord[networkNodeLatitudeIndex]);
                NetworkNode networkNode = new NetworkNode(networkNodeLongitudeIndex, networkNodeLatitudeIndex);
                this.networkNodes.put(networkNodeId, networkNode);
            }
        } catch (FileNotFoundException fNFE) {
            System.out.println("File not found at " + networkNodesFilePath);
        } catch (IOException iOE) {
            System.out.println("Input-output exception; please check file " + networkNodesFilePath);
            iOE.printStackTrace();
        }
    }

    // Read array and find index
    private int findIndexInArray(String[] stringArray, String string) {
        int index = -1;
        for(int i = 0; i < stringArray.length; i++) {
            if(stringArray[i].equalsIgnoreCase(string)) {
                index = i;
                break;
            }
        }
        return index;
    }

    LinkedHashMap<Long, NetworkNode> getNetworkNodes() {
        return this.networkNodes;
    }
}
