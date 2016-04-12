package com.rethinkdb;

import com.rethinkdb.gen.exc.ReqlError;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;
import com.rethinkdb.net.CustomConverter;
import com.rethinkdb.net.PojoConverter;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PojoConverterTest {

	public static final RethinkDB r = RethinkDB.r;
	Connection conn;
	public static final String dbName = "javatests";
	public static final String tableName = "convertertest";

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	@BeforeClass
	public static void oneTimeSetUp() throws Exception {
		Connection conn = TestingFramework.createConnection();
		try {
			r.dbCreate(dbName).run(conn);
		} catch (ReqlError e) {
		}
		try {
			r.db(dbName).wait_().run(conn);
			r.db(dbName).tableCreate(tableName).run(conn);
			r.db(dbName).table(tableName).wait_().run(conn);
		} catch (ReqlError e) {
		}
		conn.close();
	}

	@AfterClass
	public static void oneTimeTearDown() throws Exception {
		Connection conn = TestingFramework.createConnection();
		try {
			r.db(dbName).tableDrop(tableName).run(conn);
			r.dbDrop(dbName).run(conn);
		} catch (ReqlError e) {
		}
		conn.close();
	}

	@Before
	public void setUp() throws Exception {
		conn = TestingFramework.createConnection();
		r.db(dbName).table(tableName).delete().run(conn);
	}

	@After
	public void tearDown() throws Exception {
		conn.close();
	}

	@Test
	public void simpleConverterTest() {

		RethinkDB.registerPojoConverter(new SimplePojoConverter());

		SimplePojo simplePojo1 = new SimplePojo();
		simplePojo1.one = "One";
		simplePojo1.two = 10;
		RethinkDB.r.db(dbName).table(tableName).insert(simplePojo1).run(conn);  // insert

		SimplePojo simplePojo2 = new SimplePojo();
		simplePojo2.one = "Two";
		simplePojo2.two = 10;
		RethinkDB.r.db(dbName).table(tableName).insert(simplePojo2).run(conn);  // insert

		Cursor<SimplePojo> res = RethinkDB.r.db(dbName).table(tableName).run(conn, SimplePojo.class);  // load all

		List<SimplePojo> simplePojos = res.toList();
		for (SimplePojo simplePojo : simplePojos) {
			Assert.assertEquals(10, simplePojo.two);
		}
		Assert.assertEquals(2, simplePojos.size());
	}

	public static class SimplePojo {
		public String one;
		public int two;
	}

	public static class SimplePojoConverter implements PojoConverter {

		@Override
		public boolean willConvert(Class clazz) {
			return clazz.isAssignableFrom(SimplePojo.class);
		}

		@Override
		public <T> T toPojo(Class<T> clazz, Map<String, Object> properties) {
			SimplePojo simplePojo = new SimplePojo();
			simplePojo.one = (String) properties.get("one");
			simplePojo.two = ((Long) properties.get("two")).intValue();
			return (T) simplePojo;
		}

		@Override
		public Map<String, Object> fromPojo(Object object) {
			Map<String, Object> props = new HashMap<>();
			SimplePojo simplePojo = (SimplePojo) object;
			props.put("one", simplePojo.one);
			props.put("two", simplePojo.two);
			return props;
		}
	}
}
