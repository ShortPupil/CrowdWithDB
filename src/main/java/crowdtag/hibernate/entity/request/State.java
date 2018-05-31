package crowdtag.hibernate.entity.request;

public enum State {

	/**该数据集的状态 completed 完成,processing 进行中,canceled 取消（暂停）*/
	COMPLETED("completed"),
	PROCESSING("processing"),
	CANCELED("canceled");
	
	private String state;

	State(String name) {
        this.setState(name);
    }

    public String getId() {
        return name();
    }

    @Override
    public String toString() {
        return getState();
    }

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
}
