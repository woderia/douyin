package root.mapper;

import org.apache.ibatis.annotations.Mapper;

import root.model.UserReport;

@Mapper
public interface UserReportMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserReport record);

    int insertSelective(UserReport record);

    UserReport selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserReport record);

    int updateByPrimaryKey(UserReport record);
}