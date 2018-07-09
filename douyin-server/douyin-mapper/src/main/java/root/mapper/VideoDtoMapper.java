package root.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import root.dto.VideoDto;

@Mapper
public interface VideoDtoMapper {

	public List<VideoDto> getAllVideos();
}
