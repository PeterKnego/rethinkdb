package com.rethinkdb.net;

import java.util.Map;

public interface PojoConverter<T> {

	T toPojo(Class<T> clazz, Map<String, Object> properties);

	Map<String, Object> fromPojo(T object);
}
