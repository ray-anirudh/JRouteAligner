package GPSDataManager;

import java.io.*;
import java.util.LinkedHashMap;

public class GPSNodeReader {
    private final LinkedHashMap<Long, GPSNode> gPSNodes = new LinkedHashMap<>();

    // Read and store all the GPS nodes in the aforementioned hashmap
    private void readGPSNodes (String gPSNodesFilePath) {
        try{
            // Create a reader for the GPS nodes file
            BufferedReader gPSNodesReader = new BufferedReader(new FileReader(gPSNodesFilePath));
            String newline;

            // Find pertinent indices
            String[] gPSNodesHeaderArray = gPSNodesReader.readLine().split(",");
            int gPSNodeIdIndex = findIndexInArray(gPSNodesHeaderArray, "ID");
            int gPSNodeLongitudeIndex = findIndexInArray(gPSNodesHeaderArray, "Longitude");
            int gPSNodeLatitudeIndex = findIndexInArray(gPSNodesHeaderArray, "Latitude");

            // Read body and process data
            while ((newline = gPSNodesReader.readLine()) != null) {
                Long gPSNodeId =
            }

        } catch (FileNotFoundException fNFE) {
            System.out.println("File not found at " + gPSNodesFilePath);
        } catch (IOException iOE) {
            System.out.println("Input-output exception; please check file " + gPSNodesFilePath);
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
}
