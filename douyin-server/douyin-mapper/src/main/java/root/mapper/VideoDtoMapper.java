package root.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import root.dto.VideoDto;

@Mapper
public interface VideoDtoMapper {

	List<VideoDto> getAllVideos();
	
	List<VideoDto> getAllVideosByDesc(@Param("videoDesc") String videoDesc);
}
