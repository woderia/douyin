package root.mapper;

import root.model.SearchRecords;

public interface SearchRecordsMapper {
    int deleteByPrimaryKey(String id);

    int insert(SearchRecords record);

    int insertSelective(SearchRecords record);

    SearchRecords selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SearchRecords record);

    int updateByPrimaryKey(SearchRecords record);
}