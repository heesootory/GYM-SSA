package com.ssafy.finalPjt.model.dto;

public class Timeline {

    private String u_id;

    private String reg_date;

    private String content;

    private String lesson;

    private String v_url;

    private Double from_lat;

    private Double from_lng;

    private Double to_lat;

    private Double to_lng;

  

	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLesson() {
		return lesson;
	}

	public void setLesson(String lesson) {
		this.lesson = lesson;
	}

	public String getV_url() {
		return v_url;
	}

	public void setV_url(String v_url) {
		this.v_url = v_url;
	}

	public Double getFrom_lat() {
		return from_lat;
	}

	public void setFrom_lat(Double from_lat) {
		this.from_lat = from_lat;
	}

	public Double getFrom_lng() {
		return from_lng;
	}

	public void setFrom_lng(Double from_lng) {
		this.from_lng = from_lng;
	}

	public Double getTo_lat() {
		return to_lat;
	}

	public void setTo_lat(Double to_lat) {
		this.to_lat = to_lat;
	}

	public Double getTo_lng() {
		return to_lng;
	}

	public void setTo_lng(Double to_lng) {
		this.to_lng = to_lng;
	}

	@Override
	public String toString() {
		return "Timeline [u_id=" + u_id + ", reg_date=" + reg_date + ", content=" + content + ", lesson=" + lesson
				+ ", v_url=" + v_url + ", from_lat=" + from_lat + ", from_lng=" + from_lng + ", to_lat=" + to_lat
				+ ", to_lng=" + to_lng + "]";
	}


}

