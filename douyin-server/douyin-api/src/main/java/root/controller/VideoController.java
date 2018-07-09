package root.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import root.bean.JsonData;
import root.bean.PageResult;
import root.enums.VideoStatusEnum;
import root.ffmpeg.FetchVideoCover;
import root.model.Video;
import root.param.VideoParam;
import root.service.VideoService;

@RestController
@Api(value="视频相关业务处理接口", tags= {"视频相关业务的Controller"})
@RequestMapping("/video")
public class VideoController extends BasicController {
	
	@Resource
	private VideoService videoService;
	
	@ApiOperation(value="上传视频", notes="上传视频的接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId", value="用户id", required=true,
				dataType="String", paramType="form")
	})
	@PostMapping(value="/upload", headers="content-type=multipart/form-data")
	public JsonData upload(@RequestParam("file") MultipartFile file,VideoParam param) {
		if (StringUtils.isBlank(param.getUserId())) {
			return JsonData.errorMsg("用户id不能为空...");
		}
		// String FILE_SPACE = "C:/douyin/uploadresource";
		// 保存到数据库中的相对路径
		String uploadPathDB = "/" + param.getUserId() + "/video";
		String coverPathDB = "/" + param.getUserId() + "/video";
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		String videoId = null;
		try {
			if (file != null) {
				String fileName = file.getOriginalFilename();
				// abc.mp4 -> abc,mp4
				String arrayFilenameItem[] =  fileName.split("\\.");
				String fileNamePrefix = "";
				for (int i = 0 ; i < arrayFilenameItem.length-1 ; i ++) {
					// 直到连接到最后一个点为止
					fileNamePrefix += arrayFilenameItem[i];
				}
				if (StringUtils.isNotBlank(fileName)) {
					// 文件上传的最终保存路径
					String finalVideoPath = FILE_SPACE + uploadPathDB +"/"+ fileName;
					// 设置数据库保存的路径
					uploadPathDB +=("/"+ fileName);
					coverPathDB = coverPathDB + "/" + fileNamePrefix + ".jpg";
					File outFile = new File(finalVideoPath);
					if (outFile.getParentFile() !=null || !outFile.getParentFile().isDirectory()) {
						outFile.getParentFile().mkdirs();
					}
					fileOutputStream = new FileOutputStream(outFile);
					inputStream = file.getInputStream();
					IOUtils.copy(inputStream, fileOutputStream);
					// 对视频进行截图
					FetchVideoCover videoInfo = new FetchVideoCover(FFMPEG_EXE);
					videoInfo.getCover(finalVideoPath, FILE_SPACE + coverPathDB);
					
					// 保存视频信息到数据库
					Video video = new Video();
					video.setUserId(param.getUserId());
					video.setAudioId(param.getBgmId());
					video.setVideoSeconds((float)param.getVideoSeconds());
					video.setVideoDesc(param.getDesc());
					video.setVideoHeight(param.getVideoHeight());
					video.setVideoWidth(param.getVideoWidth());
					// 保存路径和封面
					video.setCoverPath(coverPathDB);
					video.setVideoPath(uploadPathDB);
					video.setStatus(VideoStatusEnum.SUCCESS.value);
					video.setCreateTime(new Date());
					videoId = videoService.saveVideo(video);
				}
			} else {
				return JsonData.errorMsg("上传出错...");
			}
		} catch(Exception e) {
			e.printStackTrace();
			return JsonData.errorMsg("上传出错...");
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.flush();
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return JsonData.ok(videoId);
	}
	
	@ApiOperation(value="上传视频封面", notes="上传视频封面的接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name="videoId", value="视频主键id", required=true,
				dataType="String", paramType="form")
	})
	@PostMapping(value="/uploadCover", headers="content-type=multipart/form-data")
	public JsonData upload(String videoId, String userId, @RequestParam("file") MultipartFile file ) {
		if (StringUtils.isBlank(videoId) || StringUtils.isBlank(userId)) {
			return JsonData.errorMsg("视频主键id和用户id不能为空...");
		}
		// String FILE_SPACE = "C:/douyin/uploadresource";
		// 保存到数据库中的相对路径
		String uploadPathDB = "/" + userId + "/video";
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		String finalCoverPath = "";
		try {
			if (file != null) {
				String fileName = file.getOriginalFilename();
				if (StringUtils.isNotBlank(fileName)) {
					// 文件上传的最终保存路径
					finalCoverPath = FILE_SPACE + uploadPathDB +"/"+ fileName;
					// 设置数据库保存的路径
					uploadPathDB +=("/"+ fileName);
					File outFile = new File(finalCoverPath);
					if (outFile.getParentFile() !=null || !outFile.getParentFile().isDirectory()) {
						outFile.getParentFile().mkdirs();
					}
					fileOutputStream = new FileOutputStream(outFile);
					inputStream = file.getInputStream();
					IOUtils.copy(inputStream, fileOutputStream);
					videoService.updateVideo(videoId, uploadPathDB);
				}
			} else {
				return JsonData.errorMsg("上传出错...");
			}
		} catch(Exception e) {
			e.printStackTrace();
			return JsonData.errorMsg("上传出错...");
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.flush();
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return JsonData.ok();
	}
	
	@PostMapping(value="/showAll")
	public JsonData uploadCover(Integer page) {
		if (page == null) {
			page = 1;
		}
		PageResult allVideos = videoService.getAllVideos(page, PAGE_SIZE);
		return JsonData.ok(allVideos);
	}
}
