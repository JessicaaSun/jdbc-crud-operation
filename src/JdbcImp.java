import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class JdbcImp {
    public DataSource dataSource () {
        PGSimpleDataSource data = new PGSimpleDataSource();
        data.setUser("postgres");
        data.setDatabaseName("postgres");
        data.setPassword("jessie123");
        return data;
    }

}
