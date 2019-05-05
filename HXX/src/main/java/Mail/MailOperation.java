package Mail;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;

public class MailOperation {
    /**
     * 发送邮件
     * @param user 发件人邮箱
     * @param password 授权码（注意不是邮箱登录密码）
     * @param host
     * @param from 发件人
     * @param to 接收者邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     * @return success 发送成功 failure 发送失败
     * @throws Exception
     */
    public String sendMail(String user, String password, String host,
                           String from, String to, String subject, String content,String[] filename)
            throws Exception {
        if (to != null){
            Properties props = System.getProperties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.auth", "true");
            MailAuthenticator auth = new MailAuthenticator();
            MailAuthenticator.USERNAME = user;
            MailAuthenticator.PASSWORD = password;
            Session session = Session.getInstance(props, auth);
            session.setDebug(true);
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                if (!to.trim().equals(""))
//                    message.addRecipient(Message.RecipientType.TO,
//                            new InternetAddress(to.trim()));
                    message.addRecipients(Message.RecipientType.TO,to);
                message.setSubject(subject);

                MimeBodyPart mbp1 = new MimeBodyPart(); // 正文
                mbp1.setContent(content, "text/html;charset=utf-8");
                Multipart multipart = new MimeMultipart("mixed");
                // 设置文本消息部分
                multipart.addBodyPart(mbp1);
                //添加附件文件
//
//                for(int i=0;i<filename.length;i++){
//                    MimeBodyPart mbp = new MimeBodyPart();
//                    System.out.println(filename[i]);
//                    DataSource source = new FileDataSource(filename[i]);
//                    mbp.setDataHandler(new DataHandler(source));
//                    // 处理附件名称中文（附带文件路径）乱码问题
//                    mbp.setFileName(MimeUtility.encodeText(filename[i]));
//                    multipart.addBodyPart(mbp);
//                }
                MimeBodyPart mbp2 = new MimeBodyPart();
//                System.out.println(filename[0]);
                DataSource source = new FileDataSource(filename[0]);
                mbp2.setDataHandler(new DataHandler(source));
                // 处理附件名称中文（附带文件路径）乱码问题
                mbp2.setFileName(MimeUtility.encodeText(filename[0]));
                multipart.addBodyPart(mbp2);

//
                MimeBodyPart mbp3 = new MimeBodyPart();
//                System.out.println(filename[1]);
                DataSource source1 = new FileDataSource(filename[1]);
                mbp3.setDataHandler(new DataHandler(source1));
                // 处理附件名称中文（附带文件路径）乱码问题
                mbp3.setFileName(MimeUtility.encodeText(filename[1]));
                multipart.addBodyPart(mbp3);


                MimeBodyPart mbp4 = new MimeBodyPart();
//                System.out.println(filename[2]);
                DataSource source2 = new FileDataSource(filename[2]);
                mbp4.setDataHandler(new DataHandler(source2));
                // 处理附件名称中文（附带文件路径）乱码问题
                mbp4.setFileName(MimeUtility.encodeText(filename[2]));
                multipart.addBodyPart(mbp4);


                message.setContent(multipart);
                message.setSentDate(new Date());
                message.saveChanges();
                Transport trans = session.getTransport("smtp");
                trans.send(message);
//                System.out.println(message.toString());
            } catch (Exception e){
                e.printStackTrace();
                return "failure";
            }
            return "success";
        }else{
            return "failure";
        }
    }
}
