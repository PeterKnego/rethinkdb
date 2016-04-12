package com.rethinkdb.net;

import java.util.Map;

public interface PojoConverter {

	boolean willConvert(Class clazz);

	<T> T toPojo (Class<T> clazz, Map<String, Object> properties);

	Map<String, Object> fromPojo(Object object);
}
