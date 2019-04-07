package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.cj.xdevapi.SqlDataResult;
import org.apache.log4j.Logger;


public class DBUtil {
    static Logger logger = Logger.getLogger(DBUtil.class);
    protected Connection conn = null;
//    protected String url = "jdbc:mysql://" + Config.DB_SERVER + ":" + Config.DB_PORT + "/" + Config.DB_NAME + Config.Unicode;

    public void DBUtilInit(String dbName) {
        try {
            Class.forName(Config.DRIVER);
            String url = "";
            if (dbName.equals("meteo_qq")) {
                url = "jdbc:mysql://" + Config.DB_QunQun_SERVER + ":" + Config.DB_PORT + "/" + dbName + Config.Unicode;
                conn = DriverManager.getConnection(url, Config.DB_USER, Config.DB_QunQun_PWD);

            } else {
                url = "jdbc:mysql://" + Config.DB_SERVER + ":" + Config.DB_PORT + "/" + dbName + Config.Unicode;
                conn = DriverManager.getConnection(url, Config.DB_USER, Config.DB_PASSWORD);
            }
            if (conn != null) {
                logger.info("Succeeded connecting to the Database!");
            }
        } catch (ClassNotFoundException e) {
            logger.info("Sorry,can`t find the Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet sqlQuery(String sql) {

        ResultSet rs = null;
        Statement stmt;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return rs;
        }
        return rs;

    }

    /***
     * 统计用户下注问题和下注次数和数量，按天统计
     */
    public ResultSet sqlQueryUserGuessDetail(String data){
        ResultSet rs = null;
        String sql = "SELECT g.start_time as time,u.id as userid,u.nickname as nickname,g.title as title,IF (d.option_id=0,d.`option`,o.content) AS content,COUNT(u.id) AS count,SUM(d.mount) AS summount FROM `mg_activity_guess_detail` AS d LEFT JOIN mg_service_user AS u ON u.id=d.user_id LEFT JOIN mg_activity_guess AS g ON g.id=d.guess_id LEFT JOIN mg_activity_guess_option AS o ON o.id=d.option_id WHERE g.start_time=? GROUP BY o.content,u.nickname";
        PreparedStatement pstmt;
        try{
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,data);
            rs = pstmt.executeQuery();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return rs;
    }
    /***
     * 统计用户下注的次数和下注的数量
     * @return
     */
    public ResultSet sqlQueryUserCountAndMount() {
        ResultSet rs = null;
        String sql = "SELECT u.id as userid,u.nickname as nickname,COUNT(u.id) as count, SUM(d.mount) as mount FROM `mg_activity_guess_detail` as d LEFT JOIN mg_service_user as u on d.user_id=u.id GROUP BY d.user_id";
//      String sql="SELECT start_time,city,total_amount/100 as total_amount FROM mg_activity_guess  where start_time=?";
        Statement stmt;
        try {
            rs = conn.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /****
     * @说明：查询天气宝每日盈亏
     * @param
     * @return
     */
    public ResultSet sqlQueryPre(String datetime) {
        ResultSet rs = null;
        String sql = "SELECT g.start_time as time,g.city as city,g.title as title,g.answer as answer,g.total_amount/100 as total_amount,o.total_pay/100 as total_pay,(g.total_amount-o.total_pay)/100 as cost FROM mg_activity_guess g JOIN mg_activity_guess_option o ON o.guess_id=g.id AND o.content=g.answer WHERE g.type='竞猜' AND g.start_time=?";
//      String sql="SELECT start_time,city,total_amount/100 as total_amount FROM mg_activity_guess  where start_time=?";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, datetime);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public List<Integer> sqlSumPersons() {
        ResultSet rs = null;
        ResultSet rs1 = null;
        List<Integer> list = new ArrayList<Integer>();
        String sql = "SELECT count(user_id)as count from (SELECT DISTINCT(user_id) as user_id from mg_activity_guess_detail g JOIN mg_activity_guess dd on g.guess_id = dd.id WHERE dd.type='竞猜' ) as TTT";
        String sql1 = "SELECT count(user_id)as count from (SELECT DISTINCT(user_id) as user_id from mg_activity_guess_detail g JOIN mg_activity_guess dd on g.guess_id = dd.id WHERE dd.type='预测' ) as TTT";
        Statement st;
        Statement st1;
        try {
            rs = conn.createStatement().executeQuery(sql);
            rs1 = conn.createStatement().executeQuery(sql1);
            if (rs.next()) {
                list.add(Integer.parseInt(rs.getString("count")));
            }
            if (rs1.next()) {
                list.add(Integer.parseInt(rs1.getString("count")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /***
     * 获取天气宝和城市纷争参与人数和人次
     * @param type
     * @param datetime
     * @return
     */
    public List<String> sqlPersonOfPerDay(String type, String datetime) {
        ResultSet rs = null;
        ResultSet rs1 = null;
        List<String> list = new ArrayList<String>();
        String sql = "SELECT  count(c.userid) as number FROM ( SELECT g.start_time AS time, d.user_id AS userid FROM mg_activity_guess_detail d JOIN mg_activity_guess g ON g.id = d.guess_id WHERE g.start_time = ? AND g.type =? GROUP BY d.user_id ) AS c";
        String sql1 = "SELECT count(d.id) as count FROM mg_activity_guess_detail d JOIN mg_activity_guess g ON d.guess_id = g.id WHERE g.type=? and g.start_time = ?";

        PreparedStatement pstmt, pstmt1;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, datetime);
            pstmt.setString(2, type);
            rs = pstmt.executeQuery();
            pstmt1 = conn.prepareStatement(sql1);
            pstmt1.setString(1, type);
            pstmt1.setString(2, datetime);
            rs1 = pstmt1.executeQuery();
//            System.out.println("长度"+rs.last());
            if (rs.next()) {
                list.add(datetime);
                list.add(rs.getString("number"));
                if (rs1.next()){
                    list.add(rs1.getString("count"));
                }
                /**原来的sql语句在qq数据库中不支持，先去掉一个time字段的展示，注销原来的
                if (rs.getString("time") == null) {
                    list.add(datetime);
                    list.add(rs.getString("number"));
                } else {
                    list.add(rs.getString("time"));
                    list.add(rs.getString("number"));
                }

                if (rs1.next()) {
                    list.add(rs1.getString("count"));
                }
                 */
//            } else if (rs == null) {    //添加查询到的天气宝人数为空的情况处理
//                list.add(datetime);
//                list.add("0");
//                if (rs1.next()) {
//                    list.add(rs1.getString("count"));
//                } else {
//                    list.add("0");
//                }

            }
//            pstmt.close();
//            pstmt1.close();
        } catch (SQLException e) {
            if (type.equals("竞猜")) {
                System.err.println("天气宝没有查询到人数和人次");
            } else {
                System.err.println("城市没有查询到人数和人次");
            }
            e.printStackTrace();
        }
        return list;
    }


    public ResultSet sqlQueryCityPre(String datetime) {
        ResultSet rs1 = null;
        //该Sql语句是有中奖的情况下得到的盈亏统计
        String sqlWin = "SELECT DISTINCT g.start_time  time, g.answer  answer, g.total_amount / 100 total_amount,IFNULL(sum(d.mount * d.rate),0) total_pay, (g.total_amount / 100 - IFNULL(sum(d.mount * d.rate),0)) cost\n" +
                "FROM mg_activity_guess g LEFT JOIN mg_activity_guess_detail d ON d.guess_id = g.id AND d.`status` = '恭喜中奖' \n" +
                "WHERE g.type = '预测' AND g.start_time = ? GROUP BY g.id";
        //该Sql语句是没有中奖的情况下得到的盈亏统计
        String sqlNoWin = "SELECT DISTINCT g.start_time  time, g.answer  answer, g.total_amount / 100 total_amount,IFNULL(sum(d.mount * d.rate),0) total_pay, (g.total_amount / 100 - IFNULL(sum(d.mount * d.rate),0)) cost\n" +
                "FROM mg_activity_guess g LEFT JOIN mg_activity_guess_detail d ON d.guess_id = g.id \n" +
                "WHERE g.type = '预测' AND g.start_time = ? GROUP BY g.id";
        PreparedStatement pstmt3;
        try {
            pstmt3 = conn.prepareStatement(sqlWin);
            pstmt3.setString(1, datetime);
            rs1 = pstmt3.executeQuery();
            if (rs1 == null) {
                pstmt3 = conn.prepareStatement(sqlNoWin);
                pstmt3.setString(1, datetime);
                rs1 = pstmt3.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs1;
    }

    public String sqlSelectCity(String datetime) {
        String count = null;
        ResultSet rs = null;
        String sql = "SELECT count(d.id) as count FROM mg_activity_guess_detail d JOIN mg_activity_guess g ON d.guess_id = g.id WHERE g.type='竞猜' and g.start_time = ?";
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, datetime);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getString(1);

            } else {
                System.out.println("没有查询到人次");
            }
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public Listmap select(String sql) {

        ResultSet rs = null;
        Statement stmt;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            return getResultMapList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }


    public Listmap getResultMapList(ResultSet rs) {
        List<Map<String, String>> rsl = new ArrayList<Map<String, String>>();
        try {
            while (rs.next()) {
                Map<String, String> hm = new HashMap<String, String>();
                ResultSetMetaData rsmd = rs.getMetaData();
                int count = rsmd.getColumnCount();
                for (int i = 1; i <= count; i++) {
                    String key = rsmd.getColumnLabel(i);
                    String value = rs.getString(i);
                    hm.put(key, value);
                }
                rsl.add(hm);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (new Listmap(rsl));
    }

    public int update(String sql) {
        logger.info("=============更新sql成功============");

        Statement stmt;
        try {
            stmt = (Statement) conn.createStatement();
            return stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

    public int save(String sql) {

        int rs = 0;
        Statement stmt;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            return rs;
        }

        return rs;

    }

    /**
     * get ResultSet
     *
     * @param sql
     * @return
     * @throws Exception
     */
    private ResultSet getResultSet(String sql) throws Exception {
        ResultSet resultSet = null;
        try {
            // PreparedStatement pstmt;
            // ResultSet rset;
            Statement statement = (Statement) conn.createStatement(
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            // pstmt = conn.prepareStatement(sql);
            resultSet = statement.executeQuery(sql);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        return resultSet;
    }

    /**
     * get ColumnCount
     *
     * @param resultSet
     * @return
     * @throws Exception
     */
    private int getColumnCount(ResultSet resultSet) {
        int columnCount = 0;
        try {
            columnCount = resultSet.getMetaData().getColumnCount();
            if (columnCount == 0) {
                logger.info("sql error!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        return columnCount;
    }

    /**
     * get ColumnCount
     *
     * @param conn
     * @param sql
     * @return
     * @throws Exception
     */
    public int getColumnCount(Connection conn, String sql) {
        int columnCount = 0;
        try {
            // ResultSet resultSet = this.getResultSet(conn, sql);
            columnCount = getResultSet(sql).getMetaData().getColumnCount();
            if (columnCount == 0) {
                logger.info("sql error!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        return columnCount;
    }

    /**
     * get RowCount
     *
     * @param sql
     * @return
     * @throws Exception
     */
    public int getRowCount(String sql) throws Exception {
        int rowCount = 0;
        try {
            ResultSet resultSet = getResultSet(sql);
            resultSet.last();
            rowCount = resultSet.getRow();
            if (rowCount == 0) {
                logger.info("sql query no data!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        return rowCount;
    }

    /**
     * get RowCount
     *
     * @param resultSet
     * @return
     * @throws Exception
     */
    private int getRowCount(ResultSet resultSet) throws Exception {
        int rowCount = 0;
        try {
            resultSet.last();
            rowCount = resultSet.getRow();
            if (rowCount == 0) {
                logger.info("sql query no data!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        return rowCount;
    }

    /**
     * get data by row index and col index
     *
     * @param sql
     * @param row
     * @param col
     * @return
     * @throws Exception
     */
    public String getData(String sql, int row, int col) throws Exception {
        String data = null;
        int rownum = 0;
        int rowcount = 0;
        int colcount = 0;
        try {
            ResultSet resultSet = getResultSet(sql);
            colcount = getColumnCount(resultSet);
            rowcount = getRowCount(resultSet);
            resultSet.beforeFirst();
            if (rowcount > 0) {
                if (row <= 0 || row > rowcount) {
                    logger.error("error row index!");
                } else {
                    if (col <= 0 || col > colcount) {
                        logger.error("error col index!");
                    } else {
                        while (resultSet.next()) {
                            rownum++;
                            if (rownum == row) {
                                data = resultSet.getString(col);
                                break;
                            }
                        }
                    }
                }
            } else {
                logger.info("sql query no data!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        }
        return data;
    }

    /**
     * get data by row index and col index
     *
     * @param sql
     * @param row
     * @param field
     * @return
     * @throws Exception
     */
    public String getData(String sql, int row, String field) throws Exception {
        String data = null;
        int rownum = 0;
        int rowcount = 0;
        try {
            ResultSet resultSet = getResultSet(sql);
            rowcount = getRowCount(resultSet);
            resultSet.beforeFirst();
            if (rowcount > 0) {
                if (row <= 0 || row > rowcount) {
                    logger.error("error row index!");
                } else {
                    while (resultSet.next()) {
                        rownum++;
                        if (rownum == row) {
                            data = resultSet.getString(field);
                            break;
                        }
                    }
                }
            } else {
                logger.info("sql query no data!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.toString());
        } finally {
            close();
        }
        return data;
    }

    public void close() {
        try {
            conn.close();
            logger.info("====close conn====");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
