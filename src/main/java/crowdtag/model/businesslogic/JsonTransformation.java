package crowdtag.model.businesslogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import crowdtag.hibernate.entity.request.JsonJavaVO;

public class JsonTransformation {

	/**
	 * 由json字符串转为Java对象，第二、三种标注
	 * 
	 * @param content
	 *            json字符串信息
	 * @author 钟镇鸿
	 */
	public JsonJavaVO transferJsonToVO(String content) {
		Gson gsonBuilder = new GsonBuilder().create();
		JsonJavaVO object = gsonBuilder.fromJson(content, JsonJavaVO.class);
		return object;
	}

	/**
	 * 由Json1对象转为json字符串，第一种标注
	 * 
	 * @param one
	 *            第一种标注的json对象
	 * @author 钟镇鸿
	 */
	public String transferVOToJson(JsonJavaVO one) {
		Gson gs = new Gson();
		String res = gs.toJson(one);
		return res;
	}

}
