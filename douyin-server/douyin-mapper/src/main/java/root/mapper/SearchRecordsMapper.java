package root.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import root.model.SearchRecords;

@Mapper
public interface SearchRecordsMapper {
    int deleteByPrimaryKey(String id);

    int insert(SearchRecords record);

    int insertSelective(SearchRecords record);

    SearchRecords selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SearchRecords record);

    int updateByPrimaryKey(SearchRecords record);
    
    List<String> getHotwords();
}