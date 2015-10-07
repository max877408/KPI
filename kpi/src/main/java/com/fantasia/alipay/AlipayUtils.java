package com.fantasia.alipay;



public class AlipayUtils {

	//商户PID
	public static final String PARTNER = "2088911953287966";
	//商户收款账号
	public static final String SELLER = "chenhao@excegroup.com";
	//商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALhw5LTjuvav8IxwAJ547GmFAD361nzLRJAvX5aYwJmpuwmEDuPEIH8pOE0qeAvDYOfYZMiln7Bv0Mp5mzOHNe2bRQcS7pXYd7pD6K4VjrtPXRQQrQj2IjSoCeb18Wt7DMoYt59F2jPxmOrtXGYuMxPyFX0c8jMB3oaYV+pKMjmlAgMBAAECgYBn0Rva+ehuHKBg3FZs3RHtXzDAsmVyvxOruQ6r/PVESTZ/Z7bd27KTAQew37vMmz/7oGEyU4+sR3QV1BdZDL18OUcWcr1mwVKW6a8Ek9wBfjT26i6XzAcfZKITX3hAbwunuThSrWdI1JbjODRD+VAp1/IUMhd0tPfQifD/UvEpgQJBAOE+ciauMOqCBmbN1aiQxe70QrpJI2TS1T6me3a1U/LsQu5tv9hN8IXWE5JL9PGZYd8fglmGegDfKiz16Gg3bMUCQQDRoCcxYuGeIvKzfNxHNUJCrvFkHtUmnLTK/9zFRCgUubz6DputWiSYcrIN0lLQ2mE7d/lG7mY7DwU+gbvnICdhAkEA0fQDM0rrDaxliy4SGi3YUgQcrse91h0v6FYGf4BF4AmZ2eUzDcyYhigl1hg3GXx/XhtgxVI0Jtvd1Erjd0EygQJBAMAHul+feZdn5HVQOGpy+5QitrdFF3Ybr/MWmKONYDo8PEXGBA61uTh+OWY3+e2hX9keepjj43rOs7sjFYMRDYECQQCYJemQRKITC87mHE/5X2O24Pl/B2OOX461gRHeefEXFmRcmj8HqaOg5zi6T6XZmpxLt+HCR5mDbb87RCXPKPaY";
	//支付宝公钥
	public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	//支付宝回调地址
	//public static final String SERVER_URL = ConfigUtils.SERVER_ALIPAY;

	//支付操作
	private static final int SDK_PAY_FLAG = 1;

	//查询终端设备是否存在支付宝认证账户
	private static final int SDK_CHECK_FLAG = 2;


	private String mOrderId;

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	private String getOrderInfo(String subject, String body, String price) {
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径
		//orderInfo += "&notify_url=" + "\"" + SERVER_URL + "/notify_url.jsp"		+ "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	private String getOutTradeNo() {
		return mOrderId;

//		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
//				Locale.getDefault());
//		Date date = new Date();
//		String key = format.format(date);
//
//		Random r = new Random();
//		key = key + r.nextInt();
//		key = key.substring(0, 15);
//		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	private String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	private String getSignType() {
		return "sign_type=\"RSA\"";
	}

}
