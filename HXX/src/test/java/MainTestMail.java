import Mail.MailOperation;
import db.VeDate;

public class MainTestMail {
    public  void sendMail(String[] fileSend) {
        MailOperation operation = new MailOperation();
        String user = "huyanna16935@163.com";             //"你的邮箱地址";
        String password = "SHUNLI@8899";    //"你的邮箱授权码";
        String host = "smtp.163.com";
        String from = "huyanna16935@163.com";   //"你的邮箱地址
        String to = "machengkai@mlogcn.com";// 收件人
        String subject = VeDate.getNowDateString()+ "数据整理统计情况汇报";           //"输入邮件主题";
        //邮箱内容
        StringBuffer sb = new StringBuffer();
//        String yzm = RandomUtil.getRandomString(6);
        sb.append("<!DOCTYPE>" + "<div bgcolor='#f1fcfa'   style='border:1px solid #d9f4ee; font-size:14px; line-height:22px; color:#005aa0;padding-left:1px;padding-top:5px;   padding-bottom:5px;'><span style='font-weight:bold;'>温馨提示：</span>"
                + "<div style='width:950px;font-family:arial;'><h3 style='color:green'>" + "今天的数据已经整理好，请查阅附件内容！" + "</h3><br/>本邮件由代码自动执行，请勿回复<br/>Thanks and best wishes！<br/>胡艳娜</div>"
                + "</div>");
//        String[] fileName = {"D:\\METEO_SLT数据需求模板20190310.xlsx", "D:\\METEO_BKB数据需求模板20190310.xlsx"};
        try {
            String res = operation.sendMail(user, password, host, from, to,
                    subject, sb.toString(), fileSend);
            System.out.println(res);
        } catch (Exception e) {
            // TODO Auto-generated catch block""
            e.printStackTrace();
        }
    }
}
