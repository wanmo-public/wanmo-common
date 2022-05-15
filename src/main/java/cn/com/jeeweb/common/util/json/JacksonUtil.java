package cn.com.jeeweb.common.util.json;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

/**
 * Jackson可以轻松的将Java对象转换成json对象和xml文档，同样也可以将json、xml转换成Java对象..
 * 如果需要转换xml，那么还需要stax2-api.jarb<br>
 *
 * Jackson提供了一系列注解，方便对JSON序列化和反序列化进行控制，下面介绍一些常用的注解。
 * @JsonIgnore 此注解用于属性上，作用是进行JSON操作时忽略该属性。
 * @JsonFormat 此注解用于属性上，作用是把Date类型直接转化为想要的格式，如@JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")。
 * @JsonProperty 此注解用于属性上，作用是把该属性的名称序列化为另外一个名称，如把trueName属性序列化为name，@JsonProperty("name")。
 */
// http://blog.csdn.net/jesse621/article/details/22698565
public class JacksonUtil {
	private static ObjectMapper mapper = null;

	/**
	 * 返回mapper，忽略未知参数、使用long解析时间
	 * @return
	 */
	public static ObjectMapper getMapper() {
		if (mapper == null) {
			mapper = new ObjectMapper();
			//设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
	        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	        mapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
	        //禁止使用int代表Enum的order()來反序列化Enum,非常危險
//	        mapper.configure(DeserializationConfig.Feature.FAIL_ON_NUMBERS_FOR_ENUMS,true);
		}
		return mapper;
	}

	/**
	 * java对象转换成JSON字符串<br>
	 * java对象、List、Map
	 */
	public static String toJsonString(Object obj) {
		String json = "";
		try {
			json = getMapper().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			json = "";
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 读取JSON数据: 从 byte[]
	 * @param json
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object readJson(byte[] json, Class clazz) {
		try {
			return getMapper().readValue(json, clazz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 读取JSON数据: 从 InputStream
	 * @param json
	 * @return
	 */
	public static <T> T readJson(InputStream json, Class<T> valueType) {
		try {
			return getMapper().readValue(json, valueType);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 读取JSON数据: 从字符串
	 * @param json
	 * @return
	 */
	public static <T> T readJson(String json, Class<T> valueType) {
		try {
			return getMapper().readValue(json, valueType);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 读取JSON数据: 从文件
	 * @param json
	 * @return
	 */
	public static <T> T readJson(File json, Class<T> valueType) {
		try {
			return getMapper().readValue(json, valueType);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 写入JSON数据：到 文件
	 */
	public static <T> void writeJson(File resultFile, T value) {
		try {
			getMapper().writeValue(resultFile, value);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将 json 转为 List, 该方法有时有问题
	 * @param json
	 * @return
	 * @throws Exception
	 */
	public static <T> T jsonToList(String json, Class<T> valueType) throws Exception {
		JavaType javaType = getCollectionType(ArrayList.class, valueType);
		return mapper.readValue(json, javaType);
	}

	/**
	 * 该方法有时有问题
	 * @param json
	 * @param javaType
	 * @return
	 * @throws Exception
	 */
	public static <T> T jsonCollectionType(String json, JavaType javaType) throws Exception {
		return mapper.readValue(json, javaType);
	}

	/**
	 * 获取泛型的Collection Type
	 *
	 * @param collectionClass 泛型的Collection
	 * @param elementClasses 元素类
	 * @return JavaType Java类型
	 * @since 1.0
	 */
	public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
//		return mapper.getTypeFactory().constructParametrizedType(collectionClass, collectionClass, elementClasses);
//		TypeFactory.defaultInstance().constructParametrizedType(parametrized, parametersFor, parameterClasses)
		return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
//		mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses)
    }

	/**
	 * JSON 字符串解析为 JsonNode
	 * @param content
	 * @return
	 * @throws IOException
	 */
    public static JsonNode readTree(String content) throws IOException {
		return getMapper().readTree(content);
	}

	/**
	 * 创建 节点 对象
	 * @param map
	 * @return
	 */
	public static ObjectNode createObjectNode(Map<String, String> map) {
		ObjectNode jsonObject = getMapper().createObjectNode();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			jsonObject.put(key, value);
		}
		return jsonObject;
	}

	public static void main(String[] args) {
		String json = "{\"access_token\":\"8x_u-fYL2ZrmiitGIQTRxb6UpZr8uAWQHEd-Cp4NQB1_P_nrw3kLyDAklwbWK3W7SIgHcc1_DZ2paxz8DR34I3UYy66IcPDlqOSpNOpjN1gzvy4nIeDxF3_W8Zq6DTZ_IBOaAIAEXK\",\"expires_in\":7200}";
		Object parseJson = readJson(json, Map.class);
		System.out.println(parseJson);


//		AccessToken accessToken = AccessTokenUtil.getAccessToken("wxa6ca97be37890e52", "e7c0b3f8807771a41ca75652169b9aa3");
//
//		System.out.println(BeanUtil.print(accessToken));
	}

}
