package com.karcompany.findoffers.config;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pvkarthik on 2017-01-25.
 *
 * Indicates different states of UI states.
 */

public enum ViewType {
	LIST("list"),
	GRID("grid"),
	STAGGERED("staggered");

	private static final Map<String, ViewType> lookup
			= new HashMap<>();

	static {
		for(ViewType s : EnumSet.allOf(ViewType.class))
			lookup.put(s.getCode(), s);
	}

	private String mIdName;

	public String getCode() { return mIdName; }

	public static ViewType get(String code) {
		return lookup.get(code);
	}

	ViewType(String tableName) {
		mIdName = tableName;
	}
}
