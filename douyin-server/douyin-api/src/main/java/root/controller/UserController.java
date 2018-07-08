package root.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
import io.swagger.annotations.ApiOperation;
import root.bean.JsonData;
import root.dto.UserDto;
import root.model.User;
import root.service.UserService;

@RestController
@Api(value="用户相关业务的接口", tags={"用户相关业务的Controller"})
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserService userService;
	
	@ApiOperation(value="用户上传头像", notes="用户上传头像的接口")
	@ApiImplicitParam(name="userId", value="用户id", required=true,
						dataType="String", paramType="query")
	@PostMapping("/uploadFace")
	public JsonData uploadFace(String userId,@RequestParam("file") MultipartFile[] files) {
		
		if (StringUtils.isBlank(userId)) {
			return JsonData.errorMsg("用户id不能为空...");
		}
		String fileSpace = "C:/douyin/uploadresource";
		// 保存到数据库中的相对路径
		String uploadPathDB = "/" + userId + "/face";
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		try {
			if (files != null && files.length > 0) {
				String fileName = files[0].getOriginalFilename();
				if (StringUtils.isNotBlank(fileName)) {
					// 文件上传的最终保存路径
					String finalFacePath = fileSpace + uploadPathDB +"/"+ fileName;
					// 设置数据库保存的路径
					uploadPathDB +=("/"+ fileName);
					File outFile = new File(finalFacePath);
					if (outFile.getParentFile() !=null || !outFile.getParentFile().isDirectory()) {
						outFile.getParentFile().mkdirs();
					}
					fileOutputStream = new FileOutputStream(outFile);
					inputStream = files[0].getInputStream();
					IOUtils.copy(inputStream, fileOutputStream);
					User user = new User();
					user.setId(userId);
					user.setFaceImage(uploadPathDB);
					userService.updateUserInfo(user);
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
		return JsonData.ok(uploadPathDB);
	}
	
	@ApiOperation(value="查询用户信息", notes="查询用户信息的接口")
	@ApiImplicitParam(name="userId", value="用户id", required=true,
						dataType="String", paramType="query")
	@PostMapping("/query")
	public JsonData query(String userId) {
		if (StringUtils.isBlank(userId)) {
			return JsonData.errorMsg("用户id不能为空");
		}
		User user = userService.queryUserById(userId);
		UserDto dto = UserDto.adapt(user);
		return JsonData.ok(dto);
	}
}
