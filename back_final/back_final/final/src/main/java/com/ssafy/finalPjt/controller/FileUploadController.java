package com.ssafy.finalPjt.controller;

import java.io.File;
import java.io.IOException;
//import java.net.http.HttpHeaders;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/fileApi")
public class FileUploadController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	
	@Autowired
	ResourceLoader resLoader;

	
	@PostMapping("/profile")
	public ResponseEntity<String> profileUpload(@RequestPart(required = false) MultipartFile file) throws IOException
	{
		System.out.println("파일 업로드 : " + file.getOriginalFilename());
		
		// 업로드할 디렉토리
//		String uploadPath = resLoader.getResource("classpath:/static/").getURI().getPath();
//		String uploadPath = resLoader.getResource("resources/upload").getURI().getPath();
		String uploadPath = "C:\\final_upload\\Image";
		
		System.out.println(uploadPath);
//		
		String fileName = file.getOriginalFilename();
		String prefixName = System.currentTimeMillis() + fileName;
		
		System.out.println(fileName);
		
		if(!new File(uploadPath).exists()) {
			new File(uploadPath).mkdir();
		}
		
		File target = new File(uploadPath, prefixName);
		
		try {
			FileCopyUtils.copy(file.getBytes(), target);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
//	
//	@GetMapping(value = "/video/{name}")
//    public ResponseEntity<ResourceRegion> getVideo(@RequestHeader HttpHeaders headers, @PathVariable String name) throws IOException {
//		System.out.println("영상 스트리밍");
//		
//		name = "1668847622351맛있겠다";
//		String uploadPath = resLoader.getResource("resources/upload").getURI().getPath();
//		
//        UrlResource video = new UrlResource(uploadPath+name+".mp4");
//        ResourceRegion region = resourceRegion(video, headers);
//
//        
//        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
//                .contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
//                .body(region);
//    }
//	
//	private ResourceRegion resourceRegion(UrlResource video, HttpHeaders headers) throws IOException {
//
//        final long chunkSize = 1000000L;
//        long contentLength = video.contentLength();
//
//        HttpRange httpRange = headers.getRange().stream().findFirst().get();
//        if(httpRange != null) {
//            long start = httpRange.getRangeStart(contentLength);
//            long end = httpRange.getRangeEnd(contentLength);
//            long rangeLength = Long.min(chunkSize, end - start + 1);
//            return new ResourceRegion(video, start, rangeLength);
//        } else {
//            long rangeLength = Long.min(chunkSize, contentLength);
//            return new ResourceRegion(video, 0, rangeLength);
//        }
//    }
	
}
