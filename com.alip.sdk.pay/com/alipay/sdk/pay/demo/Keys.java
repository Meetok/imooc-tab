/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 * 
 *  提示：如何获取安全校验码和合作身份者id
 *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *  2.点击“商家服务”(https://b.alipay.com/order/myorder.htm)
 *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
 */

package com.alipay.sdk.pay.demo;

//
// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
// 这里签名时，只需要使用生成的RSA私钥。
// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
public final class Keys {

	// 合作身份者id，以2088开头的16位纯数字
	public static final String DEFAULT_PARTNER = "2088911966502027";

	// 收款支付宝账号
	public static final String DEFAULT_SELLER = "fw@meetok.com";

	// 商户私钥，自助生成
	public static final String PRIVATE = "MIICXQIBAAKBgQCg4o092k/j9j2pidEKW9XTBaPoS2UiSoRMo8GpjCoZphHWY8RvFwyPLA/V/ydjNpGPxJFSFVihPNCo974AQbOR/MKHE22PzxQh87UB3d8qFKafTudF8Ge7U8tUOpaQ61v6wgpZXap79+bRzi0oY7EVgFreU1dATSJJ9imaUxui9QIDAQABAoGANnqAuWEjjF3lo3M3hfpy/XH7fr2JEYOKKSmKeo8r8mN5xs8hxTxY1GJnOl+cRsxDnhtB2jnAOVkOjyjU/eFBnLQEsG7tFtqs3SBSlYSaNpJdnyuftikM1+IzLkyFMbMkeDT6EqWAgBbt9yxL1dXj4yyyv27v929dUFBgoZDLwgECQQDWTbmp3D0Zb6zcCNmJ6MWUH3DG+u9FiVSV7vbLZiNtnJcTObpLbmqx0bopnb5N7VrCjWFD1TAiItNC8E4WGeYJAkEAwDAXMvaV/QdMJnEHBbUcMI94QLESFy1jD67FKqxLnlhz0O8sFqDHXBGbfiJ+LvBKg/axRlUjjj4Jde9ybZRwjQJAPipP2GFF7BIEH9QFXscCQYSZT29NZ2t9+PxX5Qrc7hgFjxMo4mbGVozTJu1WXf6jXFpsw6OdEdnuVLWmwSyEGQJBAJJJyM7dDawtStSUAlFg21VHT7SGVAvaW2YLlT73KRQdpgKqmzeaKEW3jVFEtiHKfB3YwHWVxtu18wOXQNWSQN0CQQC10HsBqxZJYyvAiB8n/tunPqO4HkSxokxvJ0Y7/mCGK2peaqYVwxsA7lniP/9lMHJ9gLvOmrUVxoo7PDm5SVg6";

//	public static final String PUBLIC = "xxxxxxxxxxxxx";
	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC8rPqGGsar+BWI7vAtaaDOqphy41j5186hCU9DcchV4HWiv0HvQ3KXAEqHfZiAHZSyMSRMmDZVnqJwCVWFvKUPqU1RsCPZ9Imk+9ZXVkM3DDdw74v/s6YMNx8cTuxybRCJUfOKbyC79cnHgmQqqkODv+EnprBtNKE4k8g90jNmbwIDAQAB";
}