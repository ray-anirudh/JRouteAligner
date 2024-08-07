package OSMDataManager;

public class NetworkNode {
    private double networkNodeLongitude;
    private double networkNodeLatitude;

    NetworkNode(double networkNodeLongitude, double networkNodeLatitude) {
        this.networkNodeLongitude = networkNodeLongitude;
        this.networkNodeLatitude = networkNodeLatitude;
    }

    public double getNetworkNodeLongitude() {
        return this.networkNodeLongitude;
    }

    public double getNetworkNodeLatitude() {
        return this.networkNodeLatitude;
    }
}