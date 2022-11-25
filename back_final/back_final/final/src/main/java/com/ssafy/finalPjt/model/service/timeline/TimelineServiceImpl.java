package com.ssafy.finalPjt.model.service.timeline;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.finalPjt.model.dao.TimelineDao;
import com.ssafy.finalPjt.model.dto.Timeline;

@Service
public class TimelineServiceImpl implements TimelineService {
	
	@Autowired
	private TimelineDao timelineDao;
	

	@Override
	public int registTimeLine(Timeline timeline) {
		return timelineDao.insertTimeline(timeline);
	}

	@Override
	public int modifyTimeLine(Timeline timeline) {
		return timelineDao.updateTimeline(timeline);
	}

	@Override
	public int removeTimeline(@Param("u_id") String u_id, @Param("reg_date") String reg_date) {
		return timelineDao.deleteTimeline(u_id, reg_date);
	}

	@Override
	public List<Timeline> getTimelineList(String u_id) {
		return timelineDao.selectAllTimeline(u_id);
	}
	
	
	
	
}
