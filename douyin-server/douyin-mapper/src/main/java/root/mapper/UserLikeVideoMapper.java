package root.mapper;

import org.apache.ibatis.annotations.Mapper;

import root.model.UserLikeVideo;

@Mapper
public interface UserLikeVideoMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserLikeVideo record);

    int insertSelective(UserLikeVideo record);

    UserLikeVideo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserLikeVideo record);

    int updateByPrimaryKey(UserLikeVideo record);
}