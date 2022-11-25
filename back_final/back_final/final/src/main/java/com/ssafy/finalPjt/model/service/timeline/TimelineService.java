package com.ssafy.finalPjt.model.service.timeline;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ssafy.finalPjt.model.dto.Timeline;

public interface TimelineService {

	// 등록
	int registTimeLine(Timeline timeline);
	
	// 수정
	int modifyTimeLine(Timeline timeline);
	
	// 삭제
	int removeTimeline(@Param("u_id") String u_id, @Param("reg_date") String reg_date);
	
	// 전체 조회 
	List<Timeline> getTimelineList(String u_id);
}
