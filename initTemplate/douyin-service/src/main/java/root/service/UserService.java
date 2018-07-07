package root.service;

import javax.annotation.Resource;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import root.mapper.UserMapper;
import root.model.User;


@Service
public class UserService {

	@Resource
	private UserMapper userMapper;
	@Resource
	private Sid sid;

	@Transactional(propagation = Propagation.SUPPORTS)
	public boolean queryUsernameIsExist(String username) {
		User user = new User();
		user.setUsername(username);
		
		int result = userMapper.countByUsername(username);
		
		return result == 0 ? false : true;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveUser(User user) {
		 String userId = sid.nextShort();
		 user.setId(userId);
		userMapper.insert(user);
	}
	
}
