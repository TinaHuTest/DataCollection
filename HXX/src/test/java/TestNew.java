import Mail.MainTestMail;
import db.DBUtil;
import db.Listmap;
import db.VeDate;
import excel.WriteExcel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TestNew {

    public static void main(String[] args) {
        MainTestMail sendMail = new MainTestMail();
        String[] dbName={"meteo_bkb"};
        String  date1 = VeDate.getParmDateString();
        String[] fileSend = new String[dbName.length];
        for (int dateBase= 0;dateBase<dbName.length;dateBase++){
            DBUtil db = new DBUtil();
            try {

                db.DBUtilInit(dbName[dateBase]);
                WriteExcel writeExcel = new WriteExcel();
                String filePath = "D:\\" +dbName[dateBase].toUpperCase()  +"数据需求模板.xlsx";
                System.out.println(filePath);
                writeExcel.initExcel(dbName[dateBase],filePath);

                Listmap userCountAndMount ;
                ResultSet userResultSet = db.sqlQueryUserCountAndMount(date1);
                userCountAndMount = db.getResultMapList(userResultSet);
                writeExcel.writeExcelSheet6(userCountAndMount,filePath);
                System.out.println(dbName[dateBase]+"数据库下用户下注次数和数量统计完成");
            }catch (Exception e){
                e.printStackTrace();
                db.close();

            }
            db.close();
        }
//        sendMail.sendMail(fileSend);
    }
}
