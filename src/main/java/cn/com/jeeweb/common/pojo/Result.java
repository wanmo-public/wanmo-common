package cn.com.jeeweb.common.pojo;

/**
 * 简单的Java对象<br>
 * 结果消息, 默认 SUCCESS
 */
public class Result {

	public static final int SUCCESS = 1;
	public static final int ERROR = 0;

	/** 服务端的响应状态 */
	private int state;
	/** 信息(给用户的提示) */
	private String message;
	/** 具体业务数据 */
	private Object data;

	public Result() {
		this.state = SUCCESS;
		this.message = "操作成功！";
	}

	public Result(Object data) {
		this();
		this.data = data;
	}

	public Result(String message) {
		this();
		this.message = message;
	}

	public Result(int state, String message) {
		super();
		this.state = state;
		this.message = message;
	}

	public Result(Object data, String message) {
		this();
		this.data = data;
		this.message = message;
	}

	public Result(Throwable e) {
		this.state = ERROR;
		this.message = e.getMessage();
	}

	public String getMessage() {
		return message;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public int getState() {
		return state;
	}
}
