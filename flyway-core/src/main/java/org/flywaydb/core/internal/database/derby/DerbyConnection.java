package org.flywaydb.core.internal.database.derby;

import org.flywaydb.core.api.configuration.FlywayConfiguration;
import org.flywaydb.core.internal.database.Connection;
import org.flywaydb.core.internal.database.Schema;

import java.sql.SQLException;

/**
 * Derby connection.
 */
public class DerbyConnection extends Connection<DerbyDatabase> {
    DerbyConnection(FlywayConfiguration configuration, DerbyDatabase database, java.sql.Connection connection, int nullType) {
        super(configuration, database, connection, nullType);
    }

    @Override
    protected String doGetCurrentSchemaName() throws SQLException {
        return jdbcTemplate.queryForString("SELECT CURRENT SCHEMA FROM SYSIBM.SYSDUMMY1");
    }

    @Override
    public void doChangeCurrentSchemaTo(String schema) throws SQLException {
        jdbcTemplate.execute("SET SCHEMA " + database.quote(schema));
    }

    @Override
    public Schema getSchema(String name) {
        return new DerbySchema(jdbcTemplate, database, name);
    }
}