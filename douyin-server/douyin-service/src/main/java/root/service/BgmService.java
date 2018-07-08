package root.service;

import java.util.List;

import javax.annotation.Resource;

import org.n3r.idworker.Sid;
import org.springframework.stereotype.Service;

import root.mapper.BgmMapper;
import root.model.Bgm;

@Service
public class BgmService {
	
	@Resource
	private BgmMapper bgmMapper;
	
	@Resource
	private Sid sid;
	/**
	 * 查询背景音乐列表
	 * @return
	 */
	public List<Bgm> queryBgmList() {
		return bgmMapper.getAll();
	}
}
