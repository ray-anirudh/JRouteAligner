package GPSDataManager;

public class GPSNode {
    private long gPSNodeId;
    private double gPSNodeLongitude;
    private double gPSNodeLatitude;

    GPSNode(long gPSNodeId, double gPSNodeLongitude, double gPSNodeLatitude) {
        this.gPSNodeId = gPSNodeId;
        this.gPSNodeLongitude = gPSNodeLongitude;
        this.gPSNodeLatitude = gPSNodeLatitude;
    }

    public long getGPSNodeId() {
        return this.gPSNodeId;
    }   // todo check redundance

    public double getGPSNodeLongitude() {
        return this.gPSNodeLongitude;
    }

    public double getGPSNodeLatitude() {
        return this.gPSNodeLatitude;
    }
}