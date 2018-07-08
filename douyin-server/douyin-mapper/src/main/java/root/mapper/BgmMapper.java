package root.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import root.model.Bgm;

@Mapper
public interface BgmMapper {
    int deleteByPrimaryKey(String id);

    int insert(Bgm record);

    int insertSelective(Bgm record);

    Bgm selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Bgm record);

    int updateByPrimaryKey(Bgm record);
    /**
     * 获得bgm列表
     * @return
     */
	List<Bgm> getAll();
}