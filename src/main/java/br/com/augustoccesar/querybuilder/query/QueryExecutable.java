package br.com.augustoccesar.querybuilder.query;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by augustoccesar on 5/3/16.
 */
public class QueryExecutable {
    private DataSource dataSource;

    public QueryExecutable(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void execute(QueryBuilder queryBuilder, ResultMapper resultMapper){
        try(
            PreparedStatement stmt = this.dataSource.getConnection().prepareStatement(queryBuilder.build());
            ResultSet rs = stmt.executeQuery()
        ){
            while(rs.next()){
                resultMapper.map(rs);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public interface ResultMapper {
        void map(ResultSet rs);
    }
}
