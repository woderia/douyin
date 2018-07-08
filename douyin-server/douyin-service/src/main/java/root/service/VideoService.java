package root.service;

import javax.annotation.Resource;

import org.n3r.idworker.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import root.mapper.VideoMapper;
import root.model.Video;

@Service
public class VideoService {
	
	@Resource
	private Sid sid;
	
	@Resource
	private VideoMapper videoMapper;
	
	@Transactional
	public String saveVideo(Video video) {
		// 没有主键
		String id = sid.nextShort();
		video.setId(id);
		videoMapper.insertSelective(video);
		return id;
	}
	
	@Transactional
	public void updateVideo(String videoId, String coverPath ) {
		Video video = new Video();
		video.setId(videoId);
		video.setCoverPath(coverPath);
		videoMapper.updateByPrimaryKeySelective(video);
;	}
}
