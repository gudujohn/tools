package com.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class Detect {

	public static boolean positiveAndEquals(long x, long y) {
		return isPositive(x) && isPositive(y) && x == y;
	}

	public static boolean notEmpty(String string) {
		return StringUtils.isNotEmpty(string);
	}

	public static boolean notEmpty(byte[] bytes) {
		return ArrayUtils.isNotEmpty(bytes);
	}

	public static boolean notEmpty(List<?> list) {
		return CollectionUtils.isNotEmpty(list);
	}

	public static boolean notEmpty(Map<?, ?> map) {
		return MapUtils.isNotEmpty(map);
	}

	public static boolean notEmpty(Collection<?> collection) {
		return CollectionUtils.isNotEmpty(collection);
	}

	public static boolean notEmpty(short[] array) {
		return ArrayUtils.isNotEmpty(array);
	}

	public static boolean notEmpty(int[] array) {
		return ArrayUtils.isNotEmpty(array);
	}

	public static boolean notEmpty(long[] array) {
		return ArrayUtils.isNotEmpty(array);
	}

	public static boolean notEmpty(String[] array) {
		return ArrayUtils.isNotEmpty(array);
	}

	public static <T extends Object> boolean notEmpty(T[] array) {
		return ArrayUtils.isNotEmpty(array);
	}

	public static boolean isEmpty(String string) {
		return StringUtils.isEmpty(string);
	}

	public static boolean isEmpty(byte[] bytes) {
		return ArrayUtils.isEmpty(bytes);
	}

	public static boolean isEmpty(List<?> list) {
		return CollectionUtils.isEmpty(list);
	}

	public static boolean isEmpty(Map<?, ?> map) {
		return MapUtils.isEmpty(map);
	}

	public static boolean isEmpty(Collection<?> collection) {
		return CollectionUtils.isEmpty(collection);
	}

	public static boolean isEmpty(short[] array) {
		return ArrayUtils.isEmpty(array);
	}

	public static boolean isEmpty(int[] array) {
		return ArrayUtils.isEmpty(array);
	}

	public static boolean isEmpty(long[] array) {
		return ArrayUtils.isEmpty(array);
	}

	public static boolean isEmpty(String[] array) {
		return ArrayUtils.isEmpty(array);
	}

	public static <T extends Object> boolean isEmpty(T[] array) {
		return ArrayUtils.isEmpty(array);
	}

	public static boolean isNegative(short value) {
		return value < 0;
	}

	public static boolean isPositive(short value) {
		return value > 0;
	}

	public static boolean isPositive(Byte value) {
		return value != null && value > 0;
	}

	public static boolean isNegative(int value) {
		return value < 0;
	}

	public static boolean isNegative(Byte value) {
		return value != null && value < 0;
	}

	public static boolean isPositive(int value) {
		return value > 0;
	}

	public static boolean isNegative(long value) {
		return value < 0;
	}

	public static boolean isPositive(long value) {
		return value > 0;
	}

	public static boolean isNegative(float value) {
		return value < 0;
	}

	public static boolean isPositive(float value) {
		return value > 0;
	}

	public static boolean isNegative(double value) {
		return value < 0;
	}

	public static boolean isPositive(double value) {
		return value > 0;
	}

	public static boolean contains(long[] values, long value) {
		return ArrayUtils.contains(values, value);
	}

	public static boolean containsAll(long[] values, long[] subValues) {
		if (ArrayUtils.isEmpty(values) && ArrayUtils.isEmpty(subValues)) {
			return true;
		}

		if (ArrayUtils.isEmpty(values)) {
			return false;
		}

		if (ArrayUtils.isEmpty(subValues)) {
			return true;
		}

		for (long subValue : subValues) {
			if (!contains(values, subValue)) {
				return false;
			}
		}

		return true;
	}

	public static <E> boolean contains(List<E> list, E one) {
		return list.indexOf(one) != -1;
	}

	public static boolean onlyOne(List<?> list) {
		return notEmpty(list) && list.size() == 1;
	}

	public static boolean between(long value, long floor, long ceil) {
		return value >= floor && value <= ceil;
	}

	public static boolean bizIdEquals(long bizId1, long bizId2) {
		return bizId1 > 0 && bizId2 > 0 && equals(bizId1, bizId2);
	}

	public static boolean equals(Object a, Object b) {
		return Objects.equals(a, b);
	}

	public static boolean notEquals(Object a, Object b) {
		return !equals(a, b);
	}

}
