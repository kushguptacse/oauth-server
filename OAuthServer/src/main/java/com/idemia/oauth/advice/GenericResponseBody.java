package com.idemia.oauth.advice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.core.MethodParameter;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.idemia.oauth.response.GenericResponse;


@RestControllerAdvice("com.idemia.epf.controller")
public class GenericResponseBody implements ResponseBodyAdvice<Object> {

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType,
			org.springframework.http.MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		if (body instanceof GenericResponse || body instanceof UrlResource) {
			return body;
		}
		return getResponse(body);
	}

	private GenericResponse<Object> getResponse(Object data) {
		GenericResponse<Object> response = new GenericResponse<>();
		response.setMessage("success");
		response.setStatusCode(String.valueOf(HttpStatus.OK.value()));
		response.setTimeStamp(getCurrenDateTime());
		response.setData(data);
		response.setSuccess(true);
		return response;
	}

	private String getCurrenDateTime() {
		final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		return dtf.format(LocalDateTime.now());
	}

	@Override
	public boolean supports(MethodParameter arg0, Class<? extends HttpMessageConverter<?>> arg1) {
		return true;
	}

}
