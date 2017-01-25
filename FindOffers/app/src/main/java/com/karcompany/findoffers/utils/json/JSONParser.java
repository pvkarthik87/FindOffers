package com.karcompany.findoffers.utils.json;

import com.google.gson.Gson;

/**
 * Created by pvkarthik on 2017-01-25.
 */

public class JSONParser {

	private static Gson mGson = new Gson();

	public static String toJSON(Object o) {
		return mGson.toJson(o);
	}

	public static <T> T parse(String jsonInString, Class<T> clazz) {
		return mGson.fromJson(jsonInString, clazz);
	}

}
