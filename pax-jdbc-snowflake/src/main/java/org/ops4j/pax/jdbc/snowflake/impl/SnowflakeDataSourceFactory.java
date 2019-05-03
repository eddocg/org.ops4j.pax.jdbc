package org.ops4j.pax.jdbc.snowflake.impl;

import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.XADataSource;

import org.ops4j.pax.jdbc.common.BeanConfig;
import org.osgi.service.jdbc.DataSourceFactory;

import net.snowflake.client.jdbc.SnowflakeBasicDataSource;
import net.snowflake.client.jdbc.SnowflakeDriver;

public class SnowflakeDataSourceFactory implements DataSourceFactory {

	private static final String JDBC_DATABASE_ACCOUNT = "account"; 
	
	@Override
	public DataSource createDataSource(Properties props) throws SQLException {
		
		SnowflakeBasicDataSource dataSource = new SnowflakeBasicDataSource();
        String url = props.getProperty(JDBC_URL);
        if (url == null) {
            dataSource.setUrl("jdbc:snowflake:" + props.getProperty(JDBC_DATABASE_NAME));
            props.remove(JDBC_DATABASE_NAME);
        } else {
            dataSource.setUrl(url);
            props.remove(JDBC_URL);
            dataSource.setDatabaseName(JDBC_DATABASE_NAME);
            props.remove(JDBC_DATABASE_NAME);
            dataSource.setAccount(props.getProperty(JDBC_DATABASE_ACCOUNT));
            props.remove(JDBC_DATABASE_ACCOUNT);
            
        }

        if (!props.isEmpty()) {
            BeanConfig.configure(dataSource, props);
        }
        return dataSource;
	}

	@Override
	public ConnectionPoolDataSource createConnectionPoolDataSource(Properties props) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public XADataSource createXADataSource(Properties props) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Driver createDriver(Properties props) throws SQLException {
		SnowflakeDriver driver = new SnowflakeDriver();
		return driver;
	}

}
