package cn.com.jeeweb.common.util.http;

import cn.com.jeeweb.common.util.Exceptions;
import cn.com.jeeweb.common.util.MapUtil;
import cn.com.jeeweb.common.util.CharsetUtil;
import cn.com.jeeweb.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

/**
 * Http 请求工具类
 */
public class HttpURLConnectionUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpURLConnectionUtil.class);

	public static String METHOD_GET = "GET";
	public static String METHOD_POST = "POST";

	/**
	 * 向指定URL发送GET方法的请求
	 *
	 * @param url 发送请求的URL
	 * @return 远程资源的响应结果
	 */
	public static String sendGet(String url) {
		return sendGet(url, null, null);
	}

	/**
	 * 向指定URL发送GET方法的请求
	 *
	 * @param url   发送请求的URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @return 远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		return sendGet(url, param, null);
	}

	/**
	 * 向指定URL发送GET方法的请求
	 *
	 * @param url    发送请求的URL
	 * @param header 通用的请求属性
	 * @return 远程资源的响应结果
	 */
	public static String sendGet(String url, Map<String, String> header) {
		return sendGet(url, null, header);
	}

	/**
	 * 向指定URL发送GET方法的请求
	 *
	 * @param url    发送请求的URL
	 * @param param  请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @param header 通用的请求属性
	 * @return 远程资源的响应结果
	 */
	public static String sendGet(String url, String param, Map<String, String> header) {
		return requestAsString(url, METHOD_GET, param, header);
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url 发送请求的 URL
	 * @return 远程资源的响应结果
	 */
	public static String sendPost(String url) {
		return sendPost(url, null, null);
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url   发送请求的 URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @return 远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		return sendPost(url, param, null);
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url    发送请求的 URL
	 * @param header 通用的请求属性
	 * @return 远程资源的响应结果
	 */
	public static String sendPost(String url, Map<String, String> header) {
		return sendPost(url, null, header);
	}

	/**
	 * 向指定 URL 发送POST方法的请求，且请求体是 JSON 格式的字符串
	 *
	 * @param url    发送请求的 URL
	 * @param param  请求参数，请求参数应该是JSON 格式字符串： {name1:value1,name2:value2}
	 * @return 远程资源的响应结果
	 */
	public static String sendPostForJson(String url, String param) {
		Map<String, String> header = new HashMap<>();
		// 代表发送端（客户端|服务器）发送的实体数据的数据类型
		header.put("Content-Type","application/json; charset=UTF-8");
		// 代表发送端（客户端）希望接受的数据类型
		header.put("accept","application/json");

		return sendPost(url, param, header);
	}



	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url    发送请求的 URL
	 * @param param  请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @param header 通用的请求属性
	 * @return 远程资源的响应结果
	 */
	public static String sendPost(String url, String param, Map<String, String> header) {
		return requestAsString(url, METHOD_POST, param, header);
	}

	/**
	 * 发起 HTTP 请求并获取结果
	 *
	 * @param url           请求地址
	 * @param params        请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @param file 响应存储的文件
	 * @return 响应的文件
	 */
	public static File sendPostAsFile(String url, String params, File file) {
		return requestAsFile(url, METHOD_POST, params, null, file);
	}

	/**
	 * 发起 HTTP 请求并获取结果
	 *
	 * @param url           请求地址
	 * @param params        请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @param header 通用的请求属性
	 * @param file 响应存储的文件
	 * @return 响应的文件
	 */
	public static File sendPostAsFile(String url, String params, Map<String, String> header, File file) {
		return requestAsFile(url, METHOD_POST, params, header, file);
	}

	/**
	 * 发起 HTTP 请求并获取结果
	 *
	 * @param url           请求地址
	 * @param params        请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @param file 响应存储的文件
	 * @return 响应的文件
	 */
	public static File sendPostAsFileForJson(String url, String params, File file) {
		Map<String, String> header = new HashMap<>();
		// 设置文件类型:
		header.put("Content-Type","application/json; charset=UTF-8");

		return requestAsFile(url, METHOD_POST, params, null, file);
	}

	/**
	 * 发起 HTTP 请求并获取结果
	 *
	 * @param url           请求地址
	 * @param method        请求方式（GET、POST）
	 * @param params        请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @param requestHeader 通用的请求属性
	 * @param file 响应存储的文件
	 * @return 响应的文件
	 */
	public static File requestAsFile(String url, String method, String params, Map<String, String> requestHeader, File file) {
		try {
			HttpURLConnection conn = getURLConnection(url, method, params, requestHeader);
			logger.debug("响应状态码是：{}", conn.getResponseCode());
			InputStream inputStream = conn.getInputStream(); // 请求后远程返回的数据

			BufferedInputStream bis = new BufferedInputStream(inputStream);
			OutputStream os = new FileOutputStream(file);
			int len;
			byte[] arr = new byte[1024];
			while ((len = bis.read(arr)) != -1) {
				os.write(arr, 0, len);
				os.flush();
			}
			os.close();
			bis.close();

			inputStream.close();
			conn.disconnect();
		} catch (Exception e) {
			throw Exceptions.unchecked(e);
		}

		logger.debug("响应的文件大小是：{}", file.length());
		return file;
	}

	/**
	 * 发起 HTTP 请求并获取结果
	 *
	 * @param url           请求地址
	 * @param method        请求方式（GET、POST）
	 * @param params        请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @param requestHeader 通用的请求属性
	 * @return 响应的字符串
	 */
	public static String requestAsString(String url, String method, String params, Map<String, String> requestHeader) {
		return requestAsString_1(url, method, params, requestHeader);
	}


	/**
	 * 发起 HTTP 请求并获取结果
	 *
	 * @param url           请求地址
	 * @param method        请求方式（GET、POST）
	 * @param params        请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @param requestHeader 通用的请求属性
	 * @return 响应的字符串
	 */
	public static String requestAsString_1(String url, String method, String params, Map<String, String> requestHeader) {
		String responseStr = "";

		try {
			HttpURLConnection conn = getURLConnection(url, method, params, requestHeader);
			logger.debug("响应状态码是：{}", conn.getResponseCode());
			InputStream inputStream = conn.getInputStream(); // 请求后远程返回的数据

			Scanner scanner = new Scanner(inputStream, CharsetUtil.DEFAULT_CHARSET_NAME);
			StringBuffer buffer = new StringBuffer();
			while (scanner.hasNextLine()) {
				buffer.append(scanner.nextLine());
			}
			responseStr = buffer.toString();
			scanner.close();

			inputStream.close();
			conn.disconnect();
		} catch (Exception e) {
			throw Exceptions.unchecked(e);
		}

		logger.debug("response_str: {}", responseStr);
		return responseStr;
	}

	/**
	 * 发起 HTTP 请求并获取结果
	 *
	 * @param url           请求地址
	 * @param method        请求方式（GET、POST）
	 * @param params        请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @param requestHeader 通用的请求属性
	 * @return 响应的字符串
	 */
	public static String requestAsString_2(String url, String method, String params, Map<String, String> requestHeader) {
		StringBuffer buffer = new StringBuffer();

		try {
			HttpURLConnection conn = getURLConnection(url, method, params, requestHeader);
			InputStream inputStream = conn.getInputStream();

			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, CharsetUtil.DEFAULT_CHARSET_NAME);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}

			bufferedReader.close();
			inputStreamReader.close();
			inputStream.close();
			conn.disconnect();
		} catch (Exception e) {
			throw Exceptions.unchecked(e);
		}

		return buffer.toString();
	}



	/**
	 * getURLConnection
	 *
	 * @param httpUrl 请求地址
	 * @return 返回 UrlConnection
	 * @throws Exception
	 */
	public static HttpURLConnection getURLConnection(String httpUrl) throws Exception {
		return getURLConnection(httpUrl, METHOD_GET, null, null);
	}

	/**
	 * getURLConnection
	 *
	 * @param httpUrl 请求地址
	 * @param method  请求方式（GET、POST）
	 * @return 返回 UrlConnection
	 * @throws Exception
	 */
	public static HttpURLConnection getURLConnection(String httpUrl, String method) throws Exception {
		return getURLConnection(httpUrl, method, null, null);
	}

	/**
	 * getURLConnection
	 *
	 * @param httpUrl       请求地址
	 * @param method        请求方式（GET、POST）
	 * @param requestHeader 通用的请求属性
	 * @return 返回 UrlConnection
	 * @throws Exception
	 */
	public static HttpURLConnection getURLConnection(String httpUrl, String method, Map<String, String> requestHeader)
			throws Exception {
		return getURLConnection(httpUrl, method, null, requestHeader);
	}

	/**
	 * getURLConnection
	 *
	 * @param httpUrl 请求地址
	 * @param method  请求方式（GET、POST）
	 * @param params  请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @return 返回 UrlConnection
	 * @throws Exception
	 */
	public static HttpURLConnection getURLConnection(String httpUrl, String method, String params) throws Exception {
		return getURLConnection(httpUrl, method, params, null);
	}

	/**
	 * 根据 Protocol 判断返回 HttpURLConnection
	 *
	 * @param httpUrl       请求地址
	 * @param method        请求方式（GET、POST）
	 * @param params        请求参数，请求参数应该是 name1=value1&name2=value2 的形式
	 * @param requestHeader 通用的请求属性
	 * @return 返回 HTTP 或 HTTPS 的 HttpURLConnection
	 * @throws Exception
	 */
	public static HttpURLConnection getURLConnection(String httpUrl, String method, String params, Map<String, String> requestHeader) throws Exception {
		if (StringUtil.equalsIgnoreCase(METHOD_GET, method) && StringUtil.isNotBlank(params)) {
			httpUrl = httpUrl + "?" + params;
		}
		URL url = new URL(httpUrl);

		HttpURLConnection conn = null;
		if ("HTTPS".equals(url.getProtocol().toUpperCase())) { // 如果是安全连接则需要
			javax.net.ssl.HttpsURLConnection connectionHttps = (javax.net.ssl.HttpsURLConnection) url.openConnection();
			connectionHttps.setSSLSocketFactory(getSSLSocket());
			conn = connectionHttps;
		} else { // 如果是普通链接
			conn = (HttpURLConnection) url.openConnection();
		}

		conn.setConnectTimeout(30000); // 设置连接主机超时（单位：毫秒）
		conn.setReadTimeout(30000); // 设置从主机读取数据超时（单位：毫秒）

		/*
		 * 设置请求头或响应头:HTTP请求允许一个key带多个用逗号分开的values，但是HttpURLConnection只提供了单个操作的方法：
		 * setRequestProperty(key,value):会覆盖已经存在的key的所有values，有清零重新赋值的作用
		 * addRequestProperty(key,value):是在原来key的基础上继续添加其他value
		 */
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("accept", "*/*"); // 设置接受的文件类型，*表示一切可以接受的
		conn.setRequestProperty("Accept-Charset", "UTF-8"); // 新添加的请求头编码
		conn.setRequestProperty("connection", "Keep-Alive"); // 设置维持长连接
		conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");

		// 设定传送的内容类型是可序列化的java对象
		// (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
//		conn.setRequestProperty("Content-type", "application/x-java-serialized-object");

		if (MapUtil.isNotEmpty(requestHeader)) {
			for (Entry<String, String> entry : requestHeader.entrySet()) {
				conn.setRequestProperty(entry.getKey(), entry.getValue());
			}
		}

		// 设定请求的方法为"POST"，默认是GET
		conn.setRequestMethod(method);
		if (StringUtil.equalsIgnoreCase(METHOD_GET, method)) {
			// 设置是否使用缓存 默认是true
			conn.setUseCaches(true);
		}
		if (StringUtil.equalsIgnoreCase(METHOD_POST, method)) {
			// 设置是否从httpUrlConnection允许读入，默认情况下是true;
			conn.setDoInput(true);

			// 设置是否向httpUrlConnection允许输出，因为这个是post请求，参数要放在 HTTP 正文内，因此需要设为true,
			// 默认情况下是false;
			conn.setDoOutput(true);

			// Post 请求不能使用缓存
			conn.setUseCaches(false);

			// 当有数据需要提交时，请求参数传给服务器
			if (StringUtil.isNotBlank(params)) {
				// 此处getOutputStream会隐含的进行connect(即：如同调用上面的connect()方法，
				// 所以在开发中不调用上述的connect()也可以)。
				PrintWriter out = new PrintWriter(conn.getOutputStream());
				out.print(params);
				// 刷新对象输出流，将任何字节都写入潜在的流中
				out.flush();
				// 关闭流对象。此时，不能再向对象输出流写入任何数据，先前写入的数据存在于内存缓冲区中
				// 在调用下边的getInputStream()函数时才把准备好的 HTTP 请求正式发送到服务器
				out.close();
			}
		}

		conn.connect();

		return conn;
	}

	/**
	 * @return HTTPS 协议需要的 SSLSocket
	 */
	private static SSLSocketFactory getSSLSocket()
			throws NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
		// 创建SSLContext对象，并使用我们指定的信任管理器初始化
		TrustManager[] tm = {

				/**
				 * 证书信任管理器（用于https请求） 这个证书管理器的作用就是让它信任我们指定的证书，上面的代码意味着信任所有证书，不管是否权威机构颁发
				 */
				new X509TrustManager() {
					public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
					};

					public void checkServerTrusted(X509Certificate[] chain, String authType)
							throws CertificateException {
					};

					public X509Certificate[] getAcceptedIssuers() {
						return null;
					};
				} };

		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init(null, tm, new java.security.SecureRandom());
		// 从上述SSLContext对象中得到SSLSocketFactory对象
		SSLSocketFactory ssf = sslContext.getSocketFactory();
		return ssf;
	}

	/**
	 * 将map转为 参数字符串：name1=value1&name2=value2 的形式
	 *
	 * @param param
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String toParamStr(Map<String, String> param) throws UnsupportedEncodingException {
		String reStr = "";

		if (MapUtil.isEmpty(param)) {
			return reStr;
		}

		Set<Entry<String, String>> entrySet = param.entrySet();

		for (Entry<String, String> o : entrySet) {
			if (o.getValue() == null || "null".equals(o.getValue()) || "class".equals(o.getKey())) {
				continue;
			}
			String s = o.getKey() + "=" + o.getValue();
			reStr += (s + "&");
		}

		return StringUtil.isBlank(reStr) ? "" : reStr.substring(0, reStr.length() - 1);
	}

	/**
	 * 将字符串 （name1=value1&name2=value2）的形式，转为 Map 形式
	 *
	 * @param param
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, String> toParamMap(String param) throws UnsupportedEncodingException {
		Map<String, String> paramMap = new HashMap<>();
		if (StringUtil.isBlank(param)) {
			return paramMap;
		}

		String[] paramPairsArr = param.split("&");
		for (String paramPair : paramPairsArr) {
			String[] paramPairArr = paramPair.split("=");
			if (paramPairArr.length == 2) {
				paramMap.put(paramPairArr[0].trim(), paramPairArr[1].trim());
			} else {
				paramMap.put(paramPairArr[0].trim(), "");
			}

		}

		return paramMap;
	}

	/**
	 * 判断是否是 HTTP URL
	 *
	 * @param url
	 * @return
	 */
	public static Boolean isHttpUrl(String url) {
		boolean flag = false;

		try {
			flag = url.indexOf("://") != -1;
		} catch (Exception e) {
		}

		return flag;
	}



}
