import Mail.MainTestMail;
import db.DBUtil;
import db.Listmap;
import db.VeDate;
import excel.WriteExcel;
import java.sql.ResultSet;
import java.util.List;

public class TestXSSF {

    public static void main(String[] args) {
        MainTestMail sendMail = new MainTestMail();
        String[] dbName={"meteo_slt","meteo_bkb"};
        String  date1 = VeDate.getParmDateString();
        String[] fileSend = new String[2];
        for (int dateBase= 0 ;dateBase<dbName.length;dateBase++){
            DBUtil db = new DBUtil();
            db.DBUtilInit(dbName[dateBase]);
            WriteExcel writeExcel = new WriteExcel();
            String filePath = "D:\\" +dbName[dateBase].toUpperCase()  +"数据需求模板.xlsx";
            System.out.println(filePath);
            fileSend[dateBase]=filePath;
            writeExcel.initExcel(dbName[dateBase],filePath);

            String[] typeArray = { "预测","竞猜"};
            Listmap sqlMap ;
            ResultSet resultSet = db.sqlQueryPre(date1);
            sqlMap = db.getResultMapList(resultSet);
            writeExcel.writeExcel(sqlMap,filePath);
            System.out.println(dbName[dateBase]+"数据库下天气宝每日盈亏统计完成");
            List sumCount;
            sumCount =db.sqlSumPersons();
            writeExcel.writeExcelSheet4(VeDate.getNowDate(),sumCount,filePath);
            System.out.println(dbName[dateBase]+"数据库下至今参与活动总人数的统计完成！！！！");
            if(dbName[dateBase].equals("meteo_slt")) {
                for (int i = 0; i < typeArray.length; i++) {
                    List dd1;
                    dd1 = db.sqlPersonOfPerDay(typeArray[i], date1);
                    writeExcel.writeExcelSheet2(typeArray[i], dd1, filePath);
                    System.out.println(dbName[dateBase]+"数据库下"+typeArray[i] + "人数和人次统计完成！！！！");
                }
                Listmap sqlMap2 ;
                ResultSet resultSet2 = db.sqlQueryCityPre(date1);
                sqlMap2 = db.getResultMapList(resultSet2);
                writeExcel.writeExcelSheet3(sqlMap2,filePath);
                System.out.println(dbName[dateBase]+"数据库下城市纷争盈亏统计完成！");
            }
            else {
                List dd1;
                dd1 = db.sqlPersonOfPerDay(typeArray[1], date1);
                writeExcel.writeExcelSheet2(typeArray[1], dd1, filePath);
                System.out.println(dbName[dateBase]+"数据库下城市纷争的每日参与人数和人次统计完成");
            }
            db.close();
        }
        sendMail.sendMail(fileSend);
    }
}
