package root.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.n3r.idworker.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import root.bean.PageResult;
import root.dto.VideoDto;
import root.mapper.CommentsMapper;
import root.mapper.SearchRecordsMapper;
import root.mapper.VideoDtoMapper;
import root.mapper.VideoMapper;
import root.model.Comments;
import root.model.SearchRecords;
import root.model.Video;

@Service
public class VideoService {
	
	@Resource
	private Sid sid;
	
	@Resource
	private VideoMapper videoMapper;
	@Resource
	private VideoDtoMapper videoDtoMapper;
	@Resource
	private SearchRecordsMapper searchRecordsMapper;
	@Resource
	private CommentsMapper commentMapper; 
	
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
	
	@Transactional
	public PageResult getAllVideos(Video video, Integer isSaveRecord, Integer page, Integer pageSize) {
		// 保存热搜词
		String desc = video.getVideoDesc();
		if (isSaveRecord != null && isSaveRecord == 1) {
			SearchRecords search = new SearchRecords();
			search.setId(Sid.next());
			search.setContent(desc);
			searchRecordsMapper.insert(search);
		}
		// 使用pageHelper
		PageHelper.startPage(page, pageSize);
		// 全表查询
		List<VideoDto> list = videoDtoMapper.getAllVideosByDesc(desc);
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
	
	@Transactional
	public List<String> getHotwords() {
		return searchRecordsMapper.getHotwords();
	}

	@Transactional
	public void saveComment(Comments comment) {
		String id = sid.nextShort();
		comment.setId(id);
		comment.setCreateTime(new Date());
		commentMapper.insert(comment);
	}
}
