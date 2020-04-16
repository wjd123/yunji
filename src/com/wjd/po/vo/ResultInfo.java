package com.wjd.po.vo;

/**
 * 封装的响应对象
 * 		状态码		成功=1；失败=0
 * 		提示信息		提示用户失败/成功的原因
 * 		响应的对象		需要响应给前台的对象（集合、JavaBean、Map等）
 * @author Lisa Li
 * 
 	"{\"code\":0,\"msg\":\"xxxx\"}"
 * @param <T>
 *
 */
public class ResultInfo<T> {

	private Integer code; // 状态码  成功=1，失败=0
	private String msg; // 提示信息
	private T result; // 需要响应给前台的对象（集合、JavaBean、Map等）
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
}
