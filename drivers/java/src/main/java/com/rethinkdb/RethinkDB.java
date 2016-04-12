package com.rethinkdb;

import com.rethinkdb.gen.model.TopLevel;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.ConnectionPool;
import com.rethinkdb.net.PojoConverter;

import java.util.ArrayList;
import java.util.List;

public class RethinkDB extends TopLevel {

    /**
     * The Singleton to use to begin interacting with RethinkDB Driver
     */
    public static final RethinkDB r = new RethinkDB();

    public Connection.Builder connection() {
        return Connection.build();
    }

	  private static ConnectionPool globalConnectionPool;

	  public static ConnectionPool getGlobalConnectionPool() {
		return globalConnectionPool;
	}

	  public static void setGlobalConnectionPool(ConnectionPool globalConnectionPool) {
		    RethinkDB.globalConnectionPool = globalConnectionPool;
	  }

	  // a list of optional PojoConverters
	  static List<PojoConverter> pojoConverters = new ArrayList<>();

	  /**
	   * Registers a new global PojoConverter.
	   * PojoConverters are queried whenever a map of values needs to be converted to/form a Java object.
	   * @param pojoConverter
	   */
	  public static void registerPojoConverter(PojoConverter pojoConverter){
		    pojoConverters.add(pojoConverter);
	  }

	  public static List<PojoConverter> getGlobalPojoConverters(){
	  	 return pojoConverters;
	  }
}
