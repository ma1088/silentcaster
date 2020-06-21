/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alosom.dal.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@SuppressFBWarnings(
    value = {"HARD_CODE_PASSWORD", "WEM_WEAK_EXCEPTION_MESSAGING"},
    justification = "Extracted from environment, Exception message adds context.")
@WebListener("Creates a connection pool that is stored in the Servlet's context for later use.")
public class ConnectionPool {

  // Saving credentials in environment variables is convenient, but not secure - consider a more
  // secure solution such as https://cloud.google.com/kms/ to help keep secrets safe.
  private static final String CLOUD_SQL_CONNECTION_NAME = System.getenv("CLOUD_SQL_CONNECTION_NAME");
  private static final String DB_USER = System.getenv("DB_USER");
  private static final String DB_PASS = System.getenv("DB_PASS");
  private static final String DB_NAME = System.getenv("DB_NAME");
  
  private static DataSource pool;

  @SuppressFBWarnings(
      value = "USBR_UNNECESSARY_STORE_BEFORE_RETURN",
      justification = "Necessary for sample region tag.")
  private static DataSource createConnectionPool() {
    // [START cloud_sql_postgres_servlet_create]
    // The configuration object specifies behaviors for the connection pool.
    HikariConfig config = new HikariConfig();

    // Configure which instance and what database user to connect with.
    config.setJdbcUrl(String.format("jdbc:postgresql:///%s", DB_NAME));
    config.setUsername(DB_USER); // e.g. "root", "postgres"
    config.setPassword(DB_PASS); // e.g. "my-password"

    // For Java users, the Cloud SQL JDBC Socket Factory can provide authenticated connections.
    // See https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory for details.
    config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.postgres.SocketFactory");
    config.addDataSourceProperty("cloudSqlInstance", CLOUD_SQL_CONNECTION_NAME);

    // [START cloud_sql_postgres_servlet_limit]
    // maximumPoolSize limits the total number of concurrent connections this pool will keep. Ideal
    // values for this setting are highly variable on app design, infrastructure, and database.
    config.setMaximumPoolSize(5);
    // minimumIdle is the minimum number of idle connections Hikari maintains in the pool.
    // Additional connections will be established to meet this value unless the pool is full.
    config.setMinimumIdle(5);
    // [END cloud_sql_postgres_servlet_limit]

    // [START cloud_sql_postgres_servlet_timeout]
    // setConnectionTimeout is the maximum number of milliseconds to wait for a connection checkout.
    // Any attempt to retrieve a connection from this pool that exceeds the set limit will throw an
    // SQLException.
    config.setConnectionTimeout(10000); // 10 seconds
    // idleTimeout is the maximum amount of time a connection can sit in the pool. Connections that
    // sit idle for this many milliseconds are retried if minimumIdle is exceeded.
    config.setIdleTimeout(600000); // 10 minutes
    // [END cloud_sql_postgres_servlet_timeout]

    // [START cloud_sql_postgres_servlet_backoff]
    // Hikari automatically delays between failed connection attempts, eventually reaching a
    // maximum delay of `connectionTimeout / 2` between attempts.
    // [END cloud_sql_postgres_servlet_backoff]

    // [START cloud_sql_postgres_servlet_lifetime]
    // maxLifetime is the maximum possible lifetime of a connection in the pool. Connections that
    // live longer than this many milliseconds will be closed and reestablished between uses. This
    // value should be several minutes shorter than the database's timeout value to avoid unexpected
    // terminations.
    config.setMaxLifetime(1800000); // 30 minutes
    // [END cloud_sql_postgres_servlet_lifetime]

    // [END_EXCLUDE]

    // Initialize the connection pool using the configuration object.
    return new HikariDataSource(config);
    // [END cloud_sql_postgres_servlet_create]
  }

  private static void teste(DataSource pool) throws SQLException {
    // Safely attempt to create the table schema.
    try (Connection conn = pool.getConnection()) {
      String stmt = "select 1;";
      try (PreparedStatement createTableStatement = conn.prepareStatement(stmt); ) {
        createTableStatement.execute();
      }
    }
  }

  public static void disconnectPool() {
    if (pool != null) {
    	((HikariDataSource)pool).close();
    }
  }

  public static DataSource getConnection() {
    // This function is called when the application starts and will safely create a connection pool
    // that can be used to connect to.
    if (pool == null ) {
    	pool = createConnectionPool();
    } else if (((HikariDataSource)pool).isClosed()) {
    	pool = createConnectionPool();
    }
    try {
      teste(pool);
    } catch (SQLException ex) {
      throw new RuntimeException(
          "Unable to verify table schema. Please double check the steps"
              + "in the README and try again.",
          ex);
    }
    return pool;
  }
}