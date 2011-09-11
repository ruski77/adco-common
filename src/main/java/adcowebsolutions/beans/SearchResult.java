package adcowebsolutions.beans;

import java.io.Serializable;

public class SearchResult implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String title;
	private String detail;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDetail() {
		return detail;
	}
	
	public void setDetail(String detail) {
		this.detail = detail;
	}
}
