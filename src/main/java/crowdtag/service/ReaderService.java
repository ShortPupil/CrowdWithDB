package crowdtag.service;

public class ReaderService {
	public String getChapter(int index) {
		if(index==1)return"The first Chapter";
		if(index==2)return "The second Chapter";
		if(index==3)return "The third Chapter";
		return "End";
	}
}
