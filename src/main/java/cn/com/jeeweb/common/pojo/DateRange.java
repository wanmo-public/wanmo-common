package cn.com.jeeweb.common.pojo;

import cn.com.jeeweb.common.util.StringUtil;

import java.io.Serializable;

/**
 * 简单的Java对象<br>
 * 日期范围
 */
public class DateRange implements Serializable {

	private static final long serialVersionUID = 1L;
	private String startDate;
	private String endDate;
	private String currentDate;

	public DateRange() {
		super();
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	@Override
	public String toString() {
		if (StringUtil.isBlank(startDate) || StringUtil.isBlank(endDate)) {
			return StringUtil.EMPTY;
		}
		return String.format("%s ~ %s", startDate, endDate);
	}

}
