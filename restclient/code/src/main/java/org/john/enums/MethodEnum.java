package org.john.enums;

import org.enhance.common.enums.TextedEnum;
import org.enhance.common.util.EnumUtil;

public enum MethodEnum implements TextedEnum {
	POST("POST"),

	GET("GET"),

	PUT("PUT"),

	DELETE("DELETE");

	private String method;

	MethodEnum(String method) {
		this.method = method;
	};

	public static MethodEnum get(String method) {
		return EnumUtil.getByText(MethodEnum.class, method);
	}

	@Override
	public String getText() {
		return method;
	}

	@Override
	public String getName() {
		return name();
	}
}
