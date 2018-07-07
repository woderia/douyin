package root.mapper;

import org.apache.ibatis.annotations.Mapper;

import root.model.Video;

@Mapper
public interface VideoMapper {
    int deleteByPrimaryKey(String id);

    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);
}