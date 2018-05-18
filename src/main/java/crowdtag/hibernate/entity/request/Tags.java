package crowdtag.hibernate.entity.request;
import java.util.List;

public class Tags {

    private List<Nodes> nodes;
    public void setNodes(List<Nodes> nodes) {
         this.nodes = nodes;
     }
     public List<Nodes> getNodes() {
         return nodes;
     }

}