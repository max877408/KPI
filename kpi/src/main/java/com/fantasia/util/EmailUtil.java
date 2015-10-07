package com.fantasia.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Controller;

/**
 * 邮件发送工具类
 * @author chenhao
 * @date 2015-5-28
 */
@Controller
public class EmailUtil {

	private MimeMessage mimeMsg; // MIME邮件对象
	private Session session; // 邮件会话对象
	private Properties props; // 系统属性
	@SuppressWarnings("unused")
	private boolean needAuth = false; // smtp是否需要认证
	// smtp认证用户名和密码
	private static String username;
	private static String password;
	private Multipart mp; // Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象
	private static String smtp;
	private static String fromEmailAddr;
	private static String isLoad = "";

	public EmailUtil() {
		if (isLoad.length() == 0) {
			Properties p = new Properties();
			InputStream is = EmailUtil.class
					.getResourceAsStream("/email.properties");
			try {
				p.load(is);
				smtp = p.getProperty("smtp");
				fromEmailAddr = p.getProperty("fromEmailAddr");
				username = p.getProperty("username");
				password = p.getProperty("password");
				isLoad = "load";
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 检测邮箱地址是否合法
	 * 
	 * @param email
	 * @return true合法 false不合法
	 */
	public static boolean isEmail(String email) {
		if (null == email || "".equals(email))
			return false;
		// Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
		Pattern p = Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");// 复杂匹配
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * Constructor
	 * 
	 * @param smtp
	 *            邮件发送服务器
	 */
	public EmailUtil(String smtp) {
		setSmtpHost(smtp);
		createMimeMessage();
	}

	/**
	 * 设置邮件发送服务器
	 * 
	 * @param hostName
	 *            String
	 */
	public void setSmtpHost(String hostName) {
		System.out.println("设置系统属性：mail.smtp.host = " + hostName);
		if (props == null)
			props = System.getProperties(); // 获得系统属性对象
		props.put("mail.smtp.host", hostName); // 设置SMTP主机
	}

	/**
	 * 创建MIME邮件对象
	 * 
	 * @return
	 */
	public boolean createMimeMessage() {
		try {
			System.out.println("准备获取邮件会话对象！");
			session = Session.getDefaultInstance(props, null); // 获得邮件会话对象
		} catch (Exception e) {
			System.err.println("获取邮件会话对象时发生错误！" + e);
			return false;
		}

		System.out.println("准备创建MIME邮件对象！");
		try {
			mimeMsg = new MimeMessage(session); // 创建MIME邮件对象
			mp = new MimeMultipart();

			return true;
		} catch (Exception e) {
			System.err.println("创建MIME邮件对象失败！" + e);
			return false;
		}
	}

	/**
	 * 设置SMTP是否需要验证
	 * 
	 * @param need
	 */
	public void setNeedAuth(boolean need) {
		System.out.println("设置smtp身份认证：mail.smtp.auth = " + need);
		if (props == null)
			props = System.getProperties();
		if (need) {
			props.put("mail.smtp.auth", "true");
			props.put("mail.debug", "true");
		} else {
			props.put("mail.smtp.auth", "false");
		}
	}

	/**
	 * 设置用户名和密码
	 * 
	 * @param name
	 * @param pass
	 */
	public void setNamePass(String name, String pass) {
		username = name;
		password = pass;
	}

	/**
	 * 设置邮件主题
	 * 
	 * @param mailSubject
	 * @return
	 */
	public boolean setSubject(String mailSubject) {
		System.out.println("设置邮件主题！");
		try {
			mimeMsg.setSubject(mailSubject);
			return true;
		} catch (Exception e) {
			System.err.println("设置邮件主题发生错误！");
			return false;
		}
	}

	/**
	 * 设置邮件正文
	 * 
	 * @param mailBody
	 *            String
	 */
	public boolean setBody(String mailBody) {
		try {
			BodyPart bp = new MimeBodyPart();
			bp.setContent("" + mailBody, "text/html;charset=UTF-8");
			mp.addBodyPart(bp);

			return true;
		} catch (Exception e) {
			System.err.println("设置邮件正文时发生错误！" + e);
			return false;
		}
	}
	
	/**
	 * 设置邮件正文
	 * 
	 * @param mailBody
	 *            String
	 */
	public boolean setBodyWithImg(String mailBody, String file) {
		try {
			// 创建邮件的正文 
			BodyPart text = new MimeBodyPart(); 
	        // setContent(“邮件的正文内容”,”设置邮件内容的编码方式”) 
	        text.setContent(mailBody + "<br/><img src='cid:a'>", "text/html;charset=UTF-8");
	        
	        MailcapCommandMap mc = (MailcapCommandMap) CommandMap
					.getDefaultCommandMap();
			mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
			mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
			mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
			mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
			mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
			CommandMap.setDefaultCommandMap(mc);
			
			// 创建图片 
	        MimeBodyPart img = new MimeBodyPart(); 
	        /*
	         * JavaMail API不限制信息只为文本,任何形式的信息都可能作茧自缚MimeMessage的一部分.
	         * 除了文本信息,作为文件附件包含在电子邮件信息的一部分是很普遍的. JavaMail
	         * API通过使用DataHandler对象,提供一个允许我们包含非文本BodyPart对象的简便方法.
	         */ 
	        DataHandler dh = new DataHandler(new URL(file));//图片路径 
	        img.setDataHandler(dh); 
	        // 创建图片的一个表示用于显示在邮件中显示 
	        img.setContentID("a"); 
	   
	        // 关系 正文和图片的 
	        MimeMultipart mm = new MimeMultipart(); 
	        mm.addBodyPart(text); 
	        mm.addBodyPart(img); 
	        mm.setSubType("related");// 设置正文与图片之间的关系 
	        // 图班与正文的 body 
	        MimeBodyPart all = new MimeBodyPart(); 
	        all.setContent(mm); 
	        // 附件与正文（text 和 img）的关系 
	        MimeMultipart mm2 = new MimeMultipart(); 
	        mm2.addBodyPart(all); 
	        mm2.setSubType("mixed");// 设置正文与附件之间的关系 
	   
	        mimeMsg.setContent(mm2); 
	        mimeMsg.saveChanges();

			return true;
		} catch (Exception e) {
			System.err.println("设置邮件正文时发生错误！" + e);
			return false;
		}
	}

	/**
	 * 添加附件
	 * 
	 * @param filename
	 *            String
	 */
	public boolean addFileAffix(String filename) {

		System.out.println("增加邮件附件：" + filename);
		try {
			BodyPart bp = new MimeBodyPart();
			FileDataSource fileds = new FileDataSource(filename);
			bp.setDataHandler(new DataHandler(fileds));
			bp.setFileName(fileds.getName());

			mp.addBodyPart(bp);

			return true;
		} catch (Exception e) {
			System.err.println("增加邮件附件：" + filename + "发生错误！" + e);
			return false;
		}
	}

	/**
	 * 设置发信人
	 * 
	 * @param from
	 *            String
	 */
	public boolean setFrom(String from) {
		System.out.println("设置发信人！");
		try {
			mimeMsg.setFrom(new InternetAddress(from)); // 设置发信人
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 设置收信人
	 * 
	 * @param to
	 *            String
	 */
	public boolean setTo(String to) {
		if (to == null)
			return false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 设置抄送人
	 * 
	 * @param copyto
	 *            String
	 */
	public boolean setCopyTo(String copyto) {
		if (copyto == null)
			return false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.CC,
					InternetAddress.parse(copyto));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 发送邮件
	 */
	public boolean sendOut() {
		try {
			
			MailcapCommandMap mc = (MailcapCommandMap) CommandMap
					.getDefaultCommandMap();
			mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
			mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
			mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
			mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
			mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
			CommandMap.setDefaultCommandMap(mc);
			
			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();
			System.out.println("正在发送邮件....");

			Session mailSession = Session.getInstance(props, null);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect((String) props.get("mail.smtp.host"), username,
					password);

			if (mimeMsg.getRecipients(Message.RecipientType.TO) != null)
				transport.sendMessage(mimeMsg,
						mimeMsg.getRecipients(Message.RecipientType.TO));
			if (mimeMsg.getRecipients(Message.RecipientType.CC) != null)
				transport.sendMessage(mimeMsg,
						mimeMsg.getRecipients(Message.RecipientType.CC));
			// transport.send(mimeMsg);

			System.out.println("发送邮件成功！");
			transport.close();

			return true;
		} catch (Exception e) {
			System.err.println("邮件发送失败！" + e);
			return false;
		}
	}
	
	/**
	 * 发送邮件
	 */
	public boolean sendOutImg() {
		try {
			Session mailSession = Session.getInstance(props, null);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect((String) props.get("mail.smtp.host"), username,
					password);

			if (mimeMsg.getRecipients(Message.RecipientType.TO) != null)
				transport.sendMessage(mimeMsg,
						mimeMsg.getRecipients(Message.RecipientType.TO));
			if (mimeMsg.getRecipients(Message.RecipientType.CC) != null)
				transport.sendMessage(mimeMsg,
						mimeMsg.getRecipients(Message.RecipientType.CC));

			System.out.println("发送邮件成功！");
			transport.close();

			return true;
		} catch (Exception e) {
			System.err.println("邮件发送失败！" + e);
			return false;
		}
	}

	/**
	 * 调用sendOut方法完成邮件发送
	 * 
	 * @param smtp
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @return boolean
	 */
	public static boolean send(String smtp, String from, String to,
			String subject, String content, String username, String password) {
		EmailUtil theMail = new EmailUtil(smtp);
		theMail.setNeedAuth(true); // 需要验证

		if (!theMail.setSubject(subject))
			return false;
		if (!theMail.setBody(content))
			return false;
		if (!theMail.setTo(to))
			return false;
		if (!theMail.setFrom(from))
			return false;
		theMail.setNamePass(username, password);

		if (!theMail.sendOut())
			return false;
		return true;
	}
	
	/**
	 * 调用sendImg方法完成邮件发送
	 * 
	 * @param smtp
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @return boolean
	 */
	public static boolean sendImg(String smtp, String from, String to,
			String subject, String content, String file, String username, String password) {
		EmailUtil theMail = new EmailUtil(smtp);
		theMail.setNeedAuth(true); // 需要验证

		if (!theMail.setSubject(subject))
			return false;
		if (!theMail.setBodyWithImg(content, file))
			return false;
		if (!theMail.setTo(to))
			return false;
		if (!theMail.setFrom(from))
			return false;
		theMail.setNamePass(username, password);

		if (!theMail.sendOutImg())
			return false;
		return true;
	}

	/**
	 * 调用sendOut方法完成邮件发送,带抄送
	 * 
	 * @param smtp
	 * @param from
	 * @param to
	 * @param copyto
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @return boolean
	 */
	public static boolean sendAndCc(String smtp, String from, String to,
			String copyto, String subject, String content, String username,
			String password) {
		EmailUtil theMail = new EmailUtil(smtp);
		theMail.setNeedAuth(true); // 需要验证

		if (!theMail.setSubject(subject))
			return false;
		if (!theMail.setBody(content))
			return false;
		if (!theMail.setTo(to))
			return false;
		if (!theMail.setCopyTo(copyto))
			return false;
		if (!theMail.setFrom(from))
			return false;
		theMail.setNamePass(username, password);

		if (!theMail.sendOut())
			return false;
		return true;
	}

	/**
	 * 调用sendOut方法完成邮件发送,带附件
	 * 
	 * @param smtp
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @param filename
	 *            附件路径
	 * @return
	 */
	public static boolean send(String smtp, String from, String to,
			String subject, String content, String username, String password,
			String filename) {
		EmailUtil theMail = new EmailUtil(smtp);
		theMail.setNeedAuth(true); // 需要验证

		if (!theMail.setSubject(subject))
			return false;
		if (!theMail.setBody(content))
			return false;
		if (!theMail.addFileAffix(filename))
			return false;
		if (!theMail.setTo(to))
			return false;
		if (!theMail.setFrom(from))
			return false;
		theMail.setNamePass(username, password);

		if (!theMail.sendOut())
			return false;
		return true;
	}

	/**
	 * 调用sendOut方法完成邮件发送,带附件和抄送
	 * 
	 * @param smtp
	 * @param from
	 * @param to
	 * @param copyto
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @param filename
	 * @return
	 */
	public static boolean sendAndCc(String smtp, String from, String to,
			String copyto, String subject, String content, String username,
			String password, String filename) {
		EmailUtil theMail = new EmailUtil(smtp);
		theMail.setNeedAuth(true); // 需要验证

		if (!theMail.setSubject(subject))
			return false;
		if (!theMail.setBody(content))
			return false;
		if (!theMail.addFileAffix(filename))
			return false;
		if (!theMail.setTo(to))
			return false;
		if (!theMail.setCopyTo(copyto))
			return false;
		if (!theMail.setFrom(from))
			return false;
		theMail.setNamePass(username, password);

		if (!theMail.sendOut())
			return false;
		return true;
	}

	/**
	 * 发送注册验证码
	 * @param email
	 *            收件箱
	 * @param number
	 *            随机码
	 * @return true 表示发送成功 false 表示发送失败
	 */
	@SuppressWarnings("static-access")
	public static boolean emailSend(String email, String title ,  String content) {
		//status：true 表示发送成功    false 表示发送失败 
		boolean status = new EmailUtil().send(smtp, fromEmailAddr, email, title, content, username, password);
		return status;
	}
	
	/**
	 * 发送邀请访客图片
	 * @param email 收件箱
	 * @param file 邀请访客图片
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static boolean emailSendWithImg(String email, String file) {
		String title = "访客通行码";
		String content = "";
		//status：true 表示发送成功    false 表示发送失败 
		boolean status = new EmailUtil().sendImg(smtp, fromEmailAddr, email, title, content, file, username, password);
 
		return status;
	}

	public static void main(String[] args) {

//		String smtp = "smtp.qq.com";
//		String from = "441313190@qq.com";
//		String to = "chcc1023@sina.com";
//		String subject = "马上行注册验证邮件码";
//		String content = "您本次操作的验证码为："+ StringUtils.game(6) +"【马上行】";
//		String username = "441313190";
//		String password = "chenhao";
		//String filename = "d:/1.txt";
		//EmailUtil.sendAndCc(smtp, from, to, copyto, subject, content, username, password, filename);
		//EmailUtil.send(smtp, from, to, subject, content, username, password);
		//EmailUtil e = new EmailUtil();
		 
		EmailUtil.emailSend("chcc1023@vip.qq.com", "title" , StringUtils.randomNumber(6));
		//EmailUtil.emailSendWithImg("chcc1023@vip.qq.com", "http://www.fwjia.com/d/file/2013-08-05/d95cb9e0e3f0d2a3f2b66ebfa69cc98a.jpg");
	}

}
