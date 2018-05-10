package crowdtag.model.temporary;

@SuppressWarnings("hiding")
public class ImagesAndCollectionId<Images, String> {

	public ImagesAndCollectionId(Images images, String id) {
		super();
		this.images = images;
		this.id = id;
	}
	public Images getImages() {
		return images;
	}
	public String getId() {
		return id;
	}
	private final Images images;
	private final String id;
	
}
