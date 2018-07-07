package root.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import root.mapper.UserMapper;

@Service
public class UserService {

	@Resource
	private UserMapper userMapper;
	
}
