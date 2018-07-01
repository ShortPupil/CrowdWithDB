package crowdtag.hibernate.entity.request;

public enum RequestType {

	//可能的值为：AreaTag，ClassTag，FrameTag（区域标注，分类标注，框标注）
	AREATAG("AreaTag"),
	CLASSTAG("ClassTag"),
	FRAMETAG("FrameTag");
	
	private String RequestType;

	RequestType(String name) {
        this.setRequestType(name);
    }


	public String getId() {
        return name();
    }

    @Override
    public String toString() {
        return this.RequestType;
    }


	/**
	 * @return the requestType
	 */
	public String getRequestType() {
		return RequestType;
	}


	/**
	 * @param requestType the requestType to set
	 */
	public void setRequestType(String requestType) {
		RequestType = requestType;
	}
}
