package root.mapper;

import org.apache.ibatis.annotations.Mapper;

import root.model.UserFan;

@Mapper
public interface UserFanMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserFan record);

    int insertSelective(UserFan record);

    UserFan selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserFan record);

    int updateByPrimaryKey(UserFan record);
}