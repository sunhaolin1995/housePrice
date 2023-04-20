package org.haolin.mailService;




import org.haolin.dto.HousePriceDTO;
import org.haolin.getHousePrice.SendStringService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;
import java.util.Properties;


@Component
public class MailService {


    @Scheduled(cron = "0 0 10 * * ?")
    public void getHousePrice() throws Exception {

        // 配置邮件服务器
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", "smtp.qq.com"); // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true"); // 需要请求认证

        // 使用授权码登录邮箱
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                // 邮箱账号和授权码
                return new PasswordAuthentication("hlsunneu@qq.com", "zudrixnfhbmedjah");
            }
        };

        // 创建邮件会话
        Session session = Session.getInstance(props, auth);

        // 创建邮件
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("hlsunneu@qq.com")); // 发件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("2575453225@qq.com")); // 收件人

        String housingEstate = "广贤梁园";
        //1056931551@qq.com,
        message.setSubject(housingEstate + "今日二手房价格"); // 邮件主题


        HousePriceDTO housePriceDTO = SendStringService.getService(housingEstate);
        List<String> houseList = housePriceDTO.getHouseList();

        String res ="<table>"
                + "<tr><td>"+housePriceDTO.getTotalNum() +"</td></tr>"
                + "<tr><th>价格（万）</th><th>单价（元/平米）</th><th>楼层 户型 面积（平米）</th><th>关注人数/发布天数</th><th>跳转链接</th></tr>";

        for (String s: houseList) {
            res = res+s;
        }



            res = res  + "</table>";

        //构建HTML表格
        Document doc = Jsoup.parse(res);
        Element table = doc.select("table").first();



        // 邮件正文
       // String content = sendService;
        //message.setContent(content, "text/html;charset=UTF-8");

        //将HTML表格添加到邮件内容中
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(table.toString(), "text/html;charset=UTF-8");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        message.setContent(multipart);

        // 发送邮件
        Transport.send(message);

        System.out.println("邮件发送成功！");
    }


}
