由于该接口继承了jpaRepository接口，所以简单的增删改查方法没有显示，显示了的属于我自定义的sql语句的方法
以下为未显示的增删改差方法的
举例调用 以AdministratorEntity为例

	@Autowired //注意这个注解不能少
	private AdministratorRepository administratorRepository;
	
	/*返回所有该entity的总数*/
	administratorRepository.count();
	
	/*找到所有AdministratorEntity, 返回值为List<AdministratorEntity>*/
	administratorRepository.findAll();  
	
	/*根据id找到单个AdministratorEntity, 返回值为AdministratorEntity*/
	administratorRepository.findById(id);
	
	/*保存传入的entity并更新，该entity的id若为新的则增加一行，若为已存在表中的则修改原entity*/
	administratorRepository.saveAndFlush(entity);
	
	/*通过id删除某entity*/
    administratorRepository.deleteById(id);
    
以上均为基本的方法，jpaRepository里还有一些实用的方法，可自看注释调用
以及我写的自定义的sql语句的方法
