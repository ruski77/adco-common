package adcowebsolutions.beans;

import java.io.Serializable;

public class SearchPage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private boolean currentPage;
	private Integer number;
	private Integer startPage;

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public boolean isCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(boolean currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getStartPage() {
		return startPage;
	}

	public void setStartPage(Integer startPage) {
		this.startPage = startPage;
	}
}
