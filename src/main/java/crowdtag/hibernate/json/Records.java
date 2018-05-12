package crowdtag.hibernate.json;
import java.util.List;

public class Records {

    private String user;
    private Description description;
    private List<Tags> tags;
    public void setUser(String user) {
         this.user = user;
     }
     public String getUser() {
         return user;
     }

    public void setTags(List<Tags> tags) {
         this.tags = tags;
     }
     public List<Tags> getTags() {
         return tags;
     }
	public Description getDescription() {
		return description;
	}
	public void setDescription(Description description) {
		this.description = description;
	}

}