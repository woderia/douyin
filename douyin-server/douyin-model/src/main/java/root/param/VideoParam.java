package root.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="上传视频参数", description="这是上传视频参数对象")
public class VideoParam {
	
	@ApiModelProperty(value="用户id", name="userId", example="userId", required=true)
	private String userId;
	
	@ApiModelProperty(value="背景音乐id", name="bgmId", example="bgmId", required=false)
	private String bgmId;
	
	@ApiModelProperty(value="视频时间长度", name="videoSeconds", example="videoSeconds", required=true)
	private double videoSeconds;
	
	@ApiModelProperty(value="视频宽度", name="videoWidth", example="videoWidth", required=true)
	private int videoWidth;
	
	@ApiModelProperty(value="视频高度", name="videoHeight", example="videoHeight", required=true)
	private int videoHeight;	
	
	@ApiModelProperty(value="视频描述", name="desc", example="desc", required=false)
	private String desc;

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBgmId() {
		return bgmId;
	}

	public void setBgmId(String bgmId) {
		this.bgmId = bgmId;
	}

	public double getVideoSeconds() {
		return videoSeconds;
	}

	public void setVideoSeconds(double videoSeconds) {
		this.videoSeconds = videoSeconds;
	}

	public int getVideoWidth() {
		return videoWidth;
	}

	public void setVideoWidth(int videoWidth) {
		this.videoWidth = videoWidth;
	}

	public int getVideoHeight() {
		return videoHeight;
	}

	public void setVideoHeight(int videoHeight) {
		this.videoHeight = videoHeight;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
