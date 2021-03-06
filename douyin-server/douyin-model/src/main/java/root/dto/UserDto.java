package root.dto;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import root.model.User;

public class UserDto {
	private String id;
	
	private String userToken;
	
	private boolean isFollow;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 我的头像，如果没有默认给一张
     */
    private String faceImage;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 我的粉丝数量
     */
    private Integer fansCounts;

    /**
     * 我关注的人总数
     */
    private Integer followCounts;

    /**
     * 我接受到的赞美/收藏 的数量
     */
    private Integer receiveLikeCounts;

    public static UserDto adapt(User user) {
    	UserDto dto = new UserDto();
    	BeanUtils.copyProperties(user, dto);
    	return dto;
    }
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public boolean isFollow() {
		return isFollow;
	}

	public void setFollow(boolean isFollow) {
		this.isFollow = isFollow;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFaceImage() {
		return faceImage;
	}

	public void setFaceImage(String faceImage) {
		this.faceImage = faceImage;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getFansCounts() {
		return fansCounts;
	}

	public void setFansCounts(Integer fansCounts) {
		this.fansCounts = fansCounts;
	}

	public Integer getFollowCounts() {
		return followCounts;
	}

	public void setFollowCounts(Integer followCounts) {
		this.followCounts = followCounts;
	}

	public Integer getReceiveLikeCounts() {
		return receiveLikeCounts;
	}

	public void setReceiveLikeCounts(Integer receiveLikeCounts) {
		this.receiveLikeCounts = receiveLikeCounts;
	}
}
