package KDTreeManager;

import java.util.Arrays;
import java.util.Comparator;

import OSMDataManager.NetworkNode;

public class KDTreeBuilderSearcher {
    private KDTreeNode kDTreeRootNode;    // Represents the root (highest level) node of the tree

    /**
     * BEHAVIOUR DEFINITIONS
     * For fast nearest-neighbour searches, the below methods are executed to build and query node-based KD-Trees
     */

    private KDTreeNode buildKDTreeForNetworkNodes(NetworkNode[] networkNodes, int depth) {
        if ((networkNodes == null) || (networkNodes.length == 0)) {
            return null;
        }

        int axis = depth % 2;
        Arrays.sort(networkNodes, Comparator.comparingDouble(networkNode -> (axis == 0) ? networkNode.
                        getNetworkNodeLatitude() : networkNode.getNetworkNodeLongitude()));

        int medianIndex = networkNodes.length / 2;     // Indexing for roots of new subtrees
        KDTreeNode networkNode = new KDTreeNode(networkNodes[medianIndex]);
        // Setting up node-based roots of new subtrees

        networkNode.setLeft(buildKDTreeForNetworkNodes(Arrays.copyOfRange(networkNodes, 0, medianIndex),
                depth + 1));
        networkNode.setRight(buildKDTreeForNetworkNodes(Arrays.copyOfRange(networkNodes, medianIndex + 1,
                        networkNodes.length), depth + 1));

        return networkNode;
    }

    public void buildNodeBasedKDTree(NetworkNode[] networkNodes) {
        this.kDTreeRootNode = buildKDTreeForNetworkNodes(networkNodes, 0);
        System.out.println("KD-Tree created for network nodes");
    }

    private KDTreeNode nearestNeighbourSearchForNetworkNodes(double sourceLongitude, double sourceLatitude,
                                                             KDTreeNode kDTreeNode, KDTreeNode bestKDTreeNode,
                                                             int depth) {
        if (kDTreeNode == null) {
            return bestKDTreeNode;
        }

        double distance = kDTreeNode.equiRectangularDistanceTo(sourceLongitude, sourceLatitude);
        double bestDistance = bestKDTreeNode.equiRectangularDistanceTo(sourceLongitude, sourceLatitude);

        if (distance < bestDistance) {
            bestKDTreeNode = kDTreeNode;
        }

        int axis = depth % 2;
        KDTreeNode nextKDTreeNode = ((axis == 0) ? (sourceLatitude < kDTreeNode.getNetworkNode().
                getNetworkNodeLatitude()) : (sourceLongitude < kDTreeNode.getNetworkNode().getNetworkNodeLongitude()))
                ? kDTreeNode.getLeft() : kDTreeNode.getRight();
        KDTreeNode otherKDTreeNode = (nextKDTreeNode == kDTreeNode.getLeft()) ? kDTreeNode.getRight() :
                kDTreeNode.getLeft();

        bestKDTreeNode = nearestNeighbourSearchForNetworkNodes(sourceLongitude, sourceLatitude, nextKDTreeNode,
                bestKDTreeNode, depth + 1);

        double axisDistance = (axis == 0) ?
                Math.abs(kDTreeNode.getNetworkNode().getNetworkNodeLatitude() - sourceLatitude) * 111_320 :
                Math.abs(kDTreeNode.getNetworkNode().getNetworkNodeLongitude() - sourceLongitude) * 111_320 *
                        Math.cos(Math.toRadians(kDTreeNode.getNetworkNode().getNetworkNodeLatitude()));

        // Search goes in the direction of the other node if the next node is deemed to be a suboptimal option
        if (axisDistance < bestDistance) {
            bestKDTreeNode = nearestNeighbourSearchForNetworkNodes(sourceLongitude, sourceLatitude, otherKDTreeNode,
                    bestKDTreeNode, depth + 1);
        }
        return bestKDTreeNode;
    }

    // Find the nearest node to a source point from amongst a set of nodes
    public NetworkNode findNearestNode(double sourceLongitude, double sourceLatitude) {
        if (this.kDTreeRootNode == null) {
            throw new IllegalStateException("Network node-based KD-Tree is empty.");
        }

        KDTreeNode bestKDTreeNode = nearestNeighbourSearchForNetworkNodes(sourceLongitude, sourceLatitude,
                this.kDTreeRootNode, this.kDTreeRootNode, 0);
        return bestKDTreeNode.getNetworkNode();
    }
}