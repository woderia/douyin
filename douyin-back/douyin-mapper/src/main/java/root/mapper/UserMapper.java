package root.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import root.model.User;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    /**
     * 是否存在相同用户名
     * @param username
     * @return
     */
	int countByUsername(@Param("username") String username);
	/**
	 * 根据用户名密码获取用户
	 * @param username
	 * @param password
	 * @return
	 */
	User getUserByUsernameAndPassword(@Param("username") String username,@Param("password") String password);
}