package crowdtag.hibernate.entity.request;
import java.util.List;

public class Images {

    private String id;
    private String path;
    private List<Records> records;
    /**状态：进行中(processing)，已完成(completed),关闭（canceled）**/
	private String state;
    public void setId(String id) {
         this.id = id;
     }
     public String getId() {
         return id;
     }

    public void setRecords(List<Records> records) {
         this.records = records;
     }
     public List<Records> getRecords() {
         return records;
     }
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

}