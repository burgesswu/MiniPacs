package com.carlwu.minipacs.tools.sqlite;


import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetExtractor<T> {

    public abstract T extractData(ResultSet rs) throws SQLException;

}
