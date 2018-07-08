package root.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
import root.param.VideoParam;

@RestController
@Api(value="视频相关业务处理接口", tags= {"视频相关业务的Controller"})
@RequestMapping("/video")
public class VideoController {
	
	
	@ApiOperation(value="上传视频", notes="上传视频的接口")
	@ApiImplicitParams({
		@ApiImplicitParam(name="userId", value="用户id", required=true,
				dataType="String", paramType="query")
	})
	@PostMapping(value="/upload", headers="content-type=multipart/form-data")
	public JsonData upload(@RequestParam("file") MultipartFile file,VideoParam param) {
		if (StringUtils.isBlank(param.getUserId())) {
			return JsonData.errorMsg("用户id不能为空...");
		}
		String fileSpace = "C:/douyin/uploadresource";
		// 保存到数据库中的相对路径
		String uploadPathDB = "/" + param.getUserId() + "/video";
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		try {
			if (file != null) {
				String fileName = file.getOriginalFilename();
				if (StringUtils.isNotBlank(fileName)) {
					// 文件上传的最终保存路径
					String finalVideoPath = fileSpace + uploadPathDB +"/"+ fileName;
					// 设置数据库保存的路径
					uploadPathDB +=("/"+ fileName);
					File outFile = new File(finalVideoPath);
					if (outFile.getParentFile() !=null || !outFile.getParentFile().isDirectory()) {
						outFile.getParentFile().mkdirs();
					}
					fileOutputStream = new FileOutputStream(outFile);
					inputStream = file.getInputStream();
					IOUtils.copy(inputStream, fileOutputStream);
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
}
