package root.service;

import java.util.List;

import javax.annotation.Resource;

import org.n3r.idworker.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import root.bean.PageResult;
import root.dto.VideoDto;
import root.mapper.VideoDtoMapper;
import root.mapper.VideoMapper;
import root.model.Video;

@Service
public class VideoService {
	
	@Resource
	private Sid sid;
	
	@Resource
	private VideoMapper videoMapper;
	@Resource
	private VideoDtoMapper videoDtoMapper;
	
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
	
	@Transactional
	public PageResult getAllVideos(Integer page, Integer pageSize) {
		// 使用pageHelper
		PageHelper.startPage(page, pageSize);
		// 全表查询
		List<VideoDto> list = videoDtoMapper.getAllVideos();
		// 计算总页数
		PageInfo<VideoDto> pageList = new PageInfo<>(list);
		PageResult pageResult = new PageResult();
		pageResult.setPage(page);
		// 总页数
		pageResult.setTotal(pageList.getPages());
		// 总记录数
		pageResult.setRecords(pageList.getTotal());
		pageResult.setRows(list);
		return pageResult;
	}
}
