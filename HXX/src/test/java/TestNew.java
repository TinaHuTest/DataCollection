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
        String[] dbName={"meteo_qq"};
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

                ResultSet rs = db.sqlQueryPerQuestionAllCount(date1);
                Map<String, String> map = db.selectMapShow(rs);
                ResultSet rs1 = db.sqlQueryPerQuestionPerAnswer(date1);
                Listmap listmap =db.getResultMapList(rs1);
                writeExcel.writeExcelSheet8(listmap,map,filePath);
            }catch (Exception e){
                e.printStackTrace();
                db.close();

            }
            db.close();
        }
//        sendMail.sendMail(fileSend);
    }
}
