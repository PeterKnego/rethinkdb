package com.rethinkdb;

import com.rethinkdb.net.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CustomConverterTest {

	private FixedConnectionPool pool;

	@Before
	public void before() {

		Connection.Builder connBuilder1 = Connection.build().hostname("localhost").port(28015);
		Connection.Builder connBuilder2 = Connection.build().hostname("localhost").port(28016);
		Connection.Builder connBuilder3 = Connection.build().hostname("localhost").port(28017);
		Connection.Builder connBuilder4 = Connection.build().hostname("localhost").port(28018); // this one should not exist

		pool = new FixedConnectionPool(connBuilder1, connBuilder2, connBuilder3, connBuilder4);
		RethinkDB.setGlobalConnectionPool(pool);

		RethinkDB.r.db("test").tableCreate("pooltest").run();
	}

	@After
	public void after() {
		RethinkDB.r.db("test").tableDrop("pooltest").run();
		pool.close();
	}

	@Test
	public void simpleConverterTest() {

		SimplePojo simplePojo1 = new SimplePojo();
		simplePojo1.one = "One";
		simplePojo1.two = 2;
		RethinkDB.r.db("test").table("pooltest").insert(simplePojo1).run();  // insert

		SimplePojo simplePojo2 = new SimplePojo();
		simplePojo2.one = "Two";
		simplePojo2.two = 4;
		RethinkDB.r.db("test").table("pooltest").insert(simplePojo2).run();  // insert

		Cursor<SimplePojo> res = RethinkDB.r.db("test").table("pooltest").run(SimplePojo.class);  // load all

		List<SimplePojo> simplePojos = res.toList();
		Assert.assertEquals(2, simplePojos.size());
	}

	@CustomConverter(converter = SimplePojoConverter.class)
	public static class SimplePojo {
		public String one;
		public int two;
	}

	public static class SimplePojoConverter implements PojoConverter<SimplePojo> {

		@Override
		public SimplePojo toPojo(Class<SimplePojo> clazz, Map<String, Object> properties) {
			SimplePojo simplePojo = new SimplePojo();
			simplePojo.one = (String) properties.get("one");
			simplePojo.two = ((Long) properties.get("two")).intValue();
			return simplePojo;
		}

		@Override
		public Map<String, Object> fromPojo(SimplePojo object) {
			Map<String, Object> props = new HashMap<>();
			SimplePojo simplePojo = (SimplePojo) object;
			props.put("one", simplePojo.one);
			props.put("two", simplePojo.two);
			return props;
		}

	}
}
