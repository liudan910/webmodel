package com.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.io.IOException;
import java.lang.Class;import java.lang.Exception;import java.lang.Object;import java.lang.String;import java.lang.SuppressWarnings;import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * jsonת������Ĺ�����,ȫ��Ϊ��̬����
 * @author zhaoyunxiao
 *
 */
public class JsonUtil {
    private static final Log log = LogFactory.getLog(JsonUtil.class);
    private static ObjectMapper mapper = new ObjectMapper();
    static{
        mapper.setSerializationInclusion(Include.NON_NULL);//�������л����ã�Ϊnull�����Բ����뵽json��
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);//���ݵ����� �������Ų�����json��׼ ������ʹ��
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);//������������ַ���ת���
    }
    /**
     * ������ת����json�ַ���,���ת��ʧ���򷵻�null
     * @author zhaoyunxiao
     * @param o ��Ҫת��Ϊjson�Ķ���
     * @return String ת�����json�ַ���
     *
     *
     * */
    public static String write2JsonStr(Object o){
        String jsonStr = "";
        try {
            jsonStr = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("write2JsonStr() exception: " + e.getMessage());
        }
        return jsonStr;
    }

    /**
     * ��jsonת��Ϊ���� �������ģ��Ϊ�ڲ����������⣬���Բ�Ҫʹ���ڲ���
     * @author zhaoyunxiao
     * @param json Ҫת����json
    //     * @param Ҫӳ�������
     * @return ת���ɵ�Ŀ��������ת��ʧ�ܷ���null
     * */
    public static Object json2Object(String json,Class<?> clazz){
        try {
            if (StringUtils.isNotEmpty(json)) {
                return mapper.readValue(json, clazz);
            }
        } catch (JsonParseException e) {
            log.error("json2Object() parseException: " + e.getMessage());
        } catch (JsonMappingException e) {
            log.error("json2Object() mappingException: " + e.getMessage());
        } catch (IOException e) {
            log.error("json2Object() IOException: " + e.getMessage());
        } catch (Exception e) {
            log.error(json, e);
        }
        return null;
    }

    /**
     * ��json�ַ���ת��ΪMap
     * @author zhaoyunxiao
     * @param  json ��Ҫת��ΪMap��json�ַ��� {}��ͷ��β��
     * @return ת�����map ���ת��ʧ�ܷ���null
     * */
    @SuppressWarnings("unchecked")
    public static Map<String,Object> json2Map(String json){
        try {
            if(StringUtils.isBlank(json)) {
                return new HashMap<String,Object>() ;
            }
            return mapper.readValue(json,Map.class);
        } catch (JsonParseException e) {
            log.error("json2Map(), �����json���ݣ�"  + " ,JsonParseException: " + e.getMessage());
        } catch (JsonMappingException e) {
            log.error("json2Map(), �����json���ݣ�"  + " ,JsonMappingException: " + e.getMessage());
        } catch (IOException e) {
            log.error("json2Map(), �����json����Ϊ��"  + " ,IOException: " + e.getMessage());
        }
        return new HashMap<String,Object>() ;
    }


    /**
     * ��json����ת��ΪList<Map<String,Object>> json�����ʽ[{},{}]
     * @author zhaoyunxiao
    //     * @param  ��Ҫת����json����
     * @return ת������б�   ���ת��ʧ�ܷ���null
     * */
    @SuppressWarnings("unchecked")
    public static List<Map<String,Object>> jsonArray2List(String jsonArray){
        try {
            if (StringUtils.isNotEmpty(jsonArray)) {
                return mapper.readValue(jsonArray, List.class);
            }
        } catch (JsonParseException e) {
            log.error("jsonArray2List() exception, �쳣�ַ���: " + jsonArray, e);
        } catch (JsonMappingException e) {
            log.error("jsonArray2List() exception, �쳣�ַ���: " + jsonArray, e);
        } catch (IOException e) {
            log.error("jsonArray2List() exception",e);
        } catch (Exception e) {
            log.error(jsonArray, e);
        }
        return new ArrayList<Map<String,Object>>();
    }

    /**
     * @param jsonArray
     * @param clazz
     * @author wutong
     * @return
     */
    public static List<Map<String,Object>> jsonStr2Array(String jsonArray, Class clazz){
        try {
            JavaType jt = mapper.getTypeFactory().constructParametricType(List.class, clazz);
            return mapper.readValue(jsonArray,jt);
        } catch (JsonParseException e) {
            log.error("jsonArray2List() exception, �쳣�ַ���: " + jsonArray, e);
        } catch (JsonMappingException e) {
            log.error("jsonArray2List() exception, �쳣�ַ���: " + jsonArray, e);
        } catch (IOException e) {
            log.error("jsonArray2List() exception",e);
        }
        return new ArrayList<Map<String,Object>>();
    }

    /**
     * ��json����ת��ΪList<Map<String,Object>> json�����ʽ[{},{}]
     * @author zhaoyunxiao
    //     * @param  ��Ҫת����json����
     * @return ת������б�   ���ת��ʧ�ܷ���null
     * */
    @SuppressWarnings("unchecked")
    public static List<Map<String,Object>> jsonArray2List(String jsonArray,String keyword){
        try {
            return mapper.readValue(jsonArray, List.class);
        } catch (JsonParseException e) {
            log.error("JsonUtil exception, keyword: "+keyword+", �쳣�ַ���: " + jsonArray, e);
        } catch (JsonMappingException e) {
            log.error("JsonUtil exception, keyword: "+keyword+", �쳣�ַ���: " + jsonArray, e);
        } catch (IOException e) {
            log.error("JsonUtil exception",e);
        }
        return new ArrayList<Map<String,Object>>();
    }


    /**
     * for example��
     * TypeReference type = new TypeReference<Map<String, Integer>>() {}
     *
     * @see com.fasterxml.jackson.core.type.TypeReference
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String jsonString, TypeReference<T> typeReference) {
        if (org.apache.commons.lang.StringUtils.isBlank(jsonString)) {
            return null;
        }
        try {
            return (T) mapper.readValue(jsonString, typeReference);
        } catch (IOException e) {
            log.error("parse json string error:" + jsonString, e);
        }
        return null;
    }

   /* public static void main(String[] args) {
    	String json = "{'name':'zhaoyunxiao','age':'12'}";
    	String jsonArray = "[{\"name\":\"zhaoyunxiao\",\"age\":\"12\"},{\"name\":\"dengzhengping\",\"age\":\"13\"}]";
    	System.out.println(json);
    	Map p = (Map)json2Object(json, Map.class);
    	//System.out.println(p.getName());
    	Map<String,Object> res = json2Map(json);
    	System.out.println(res);
    	List<Map<String,Object>> l = jsonArray2List(jsonArray);
    	System.out.println(l);
		
	}*/
}
