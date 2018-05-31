package crowdtag.model.businesslogic;


import crowdtag.hibernate.entity.request.Images;

public class Collection {
	
	/**该数据集的一个图片*/
private Images image;
/**其余属性和RequestEntity相同 介绍*/
private String content;
private int point;
/**requestId 同时也是CollectionId*/
private long id;
public Collection(Images image,String content,int point,long id) {
	this.content=content;
	this.image=image;
	
	this.point =point;
	this.id=id;
}
public void setContent(String content) {
    this.content = content;
}
public String getContent() {
    return content;
}
public void setImages(Images images) {
    this.image = images;
}
public Images getImages() {
    return image;
}
public long getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getPoint() {
	return point;
}
public void setPoint(int point) {
	this.point = point;
}

}
