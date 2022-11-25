package com.ssafy.finalPjt.model.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ssafy.finalPjt.model.dto.Timeline;

public interface TimelineDao {

	// 등록
	int insertTimeline(Timeline timeline);
	
	// 수정
	int updateTimeline(Timeline timeline);
	
	// 전체 조회
	List<Timeline> selectAllTimeline(String u_id);
	
	// 삭제
	int deleteTimeline(@Param("u_id") String u_id, @Param("reg_date") String reg_date);
}
