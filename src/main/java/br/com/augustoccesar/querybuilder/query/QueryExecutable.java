package br.com.augustoccesar.querybuilder.query;

import javax.sql.DataSource;
import java.security.InvalidParameterException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by augustoccesar on 5/3/16.
 */
@SuppressWarnings("unchecked")
public class QueryExecutable<T> {
    private DataSource dataSource;

    public QueryExecutable(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public T execute(QueryBuilder queryBuilder, List objectList, ResultMapper resultMapper, Object... params){
        try(
                PreparedStatement stmt = this.dataSource.getConnection().prepareStatement(queryBuilder.build())
        ){
            for (int i = 0; i < params.length; i++) {
                Object param = params[i];
                int pos = i + 1;
                putParameterInPreparedStatement(stmt, pos, param);
            }
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                objectList.add(resultMapper.map(rs));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return (T)objectList;
    }

    public T execute(QueryBuilder queryBuilder, Object object, ResultMapper resultMapper, Object... params){
        String sql = queryBuilder.build();
        try(
                PreparedStatement stmt = this.dataSource.getConnection().prepareStatement(sql)
        ){
            for (int i = 0; i < params.length; i++) {
                Object param = params[i];
                int pos = i + 1;
                putParameterInPreparedStatement(stmt, pos, param);
            }
            ResultSet rs = stmt.executeQuery();
            System.out.println("EXECUTING JDBC SQL: " + sql);
            while(rs.next()){
                object = resultMapper.map(rs);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return (T)object;
    }

    private void putParameterInPreparedStatement(PreparedStatement stmt, int pos, Object param) throws SQLException {
        if(param instanceof Short){
            stmt.setShort(pos, (short) param);
        }else if(param instanceof Integer){
            stmt.setInt(pos, (int) param);
        }else if(param instanceof Long){
            stmt.setLong(pos, (long) param);
        }else if(param instanceof String){
            stmt.setString(pos, param.toString());
        }else if(param instanceof Boolean){
            stmt.setBoolean(pos, (boolean) param);
        }else if(param instanceof Date){
            stmt.setDate(pos, new Date(((Date) param).getTime()));
        }else{
            throw new InvalidParameterException();
        }
    }

    public interface ResultMapper<T> {
        T map(ResultSet rs) throws SQLException;
    }
}