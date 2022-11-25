package com.ssafy.finalPjt.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.finalPjt.model.dto.Timeline;
import com.ssafy.finalPjt.model.service.timeline.TimelineService;


@RestController
@RequestMapping("/api/timelineApi")
public class TimeLineRestController {
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private TimelineService timelineService;
	
	
	// 해당 아이디 타임라인 가져오기
	@GetMapping("/")
	public ResponseEntity<List<Timeline>> getTimeLine(String u_id){
		System.out.println(u_id);
		List<Timeline> timeList = timelineService.getTimelineList(u_id);
		for(Timeline t : timeList) System.out.println(t);
		return new ResponseEntity<List<Timeline>>(timeList, HttpStatus.OK); 
	}
	
	//  등록
	@PostMapping("/")
	public ResponseEntity<String> registTimeLine(Timeline timeline, @RequestPart(required = false) MultipartFile Videofile){
		System.out.println("---------------------------------------");
		System.out.println(timeline.getU_id());
		System.out.println("Timeline 객체 : " + timeline);
		System.out.println("파일영상 업로드 : " + Videofile.getOriginalFilename());
		System.out.println("---------------------------------------");
		// 저장 경로 지정(로컬)
		String uploadPath = "C:\\final_upload\\video";
		if(!new File(uploadPath).exists()) {
			new File(uploadPath).mkdir();
		}
		// 중복 이름 방지
		String fileName = Videofile.getOriginalFilename();
		String prefixName = System.currentTimeMillis() + fileName;
		
		// 새로 만든 이름과 경로를 target객체로 만듬
		File target = new File(uploadPath, prefixName);
		try {
			// 파일 복사
			FileCopyUtils.copy(Videofile.getBytes(), target);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		timeline.setV_url(prefixName);
		
		timelineService.registTimeLine(timeline);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK); 
	}
	
	// 수정
	@PutMapping("/")
	public ResponseEntity<String> updateTimeLine(Timeline timeline){
		timelineService.modifyTimeLine(timeline);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK); 
	}
	
	// 삭제
	@DeleteMapping("/")
	public ResponseEntity<String> DeleteTimeLine(String u_id, String reg_date){
		timelineService.removeTimeline(u_id, reg_date);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK); 
	}
	
	
}
