调用谨记 加@Autowired 不要new 不要手动实例化 
例如
	@Autowired
	Requester r;
这是spring做的事 不然会报null错误