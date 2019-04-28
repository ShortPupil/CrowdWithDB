package crowdtag.model.businesslogic.aliyunoss;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

/**
 * @class:AliyunOSSClientUtil
 * @descript:java使用阿里云OSS存储对象上传图片
 */
public class AliyunOSSClientUtil {
	//阿里云API的内或外网域名
	private static String ENDPOINT;
	//阿里云API的密钥Access Key ID
	private static String ACCESS_KEY_ID;
	//阿里云API的密钥Access Key Secret
	private static String ACCESS_KEY_SECRET;
	//阿里云API的bucket名称
	@SuppressWarnings("unused")
	private static String BACKET_NAME;
	//初始化属性
	static{
		ENDPOINT = OSSClientConstants.ENDPOINT;
		ACCESS_KEY_ID = OSSClientConstants.ACCESS_KEY_ID;
		ACCESS_KEY_SECRET = OSSClientConstants.ACCESS_KEY_SECRET;
		BACKET_NAME = OSSClientConstants.BACKET_NAME;
	}

	/**
	 * 获取阿里云OSS客户端对象
	 * @return ossClient
	 */
	@SuppressWarnings("deprecation")
	public static  OSSClient getOSSClient(){
		return new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
	}

	/**
	 * 创建存储空间
	 * @param ossClient      OSS连接
	 * @param bucketName 存储空间
	 * @return
	 */
	public  static String createBucketName(OSSClient ossClient,String bucketName){
		//存储空间
		final String bucketNames=bucketName;
		if(!ossClient.doesBucketExist(bucketName)){
			//创建存储空间
			Bucket bucket=ossClient.createBucket(bucketName);
			System.out.println("创建存储空间成功");
			return bucket.getName();
		}
		return bucketNames;
	}

	/**
	 * 删除存储空间buckName
	 * @param ossClient  oss对象
	 * @param bucketName  存储空间
	 */
    public static  void deleteBucket(OSSClient ossClient, String bucketName){
    	ossClient.deleteBucket(bucketName);
    	System.out.println("删除" + bucketName + "Bucket成功");
    }

	/**
	 * 创建模拟文件夹
	 * @param ossClient oss连接
	 * @param bucketName 存储空间
	 * @param folder   模拟文件夹名如"qj_nanjing/"
	 * @return  文件夹名
	 */
	public  static String createFolder(OSSClient ossClient,String bucketName,String folder){
		//文件夹名
		final String keySuffixWithSlash =folder;
		//判断文件夹是否存在，不存在则创建
		if(!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)){
			//创建文件夹
			ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
			System.out.println("创建文件夹成功");
			//得到文件夹名
			OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
			String fileDir=object.getKey();
			return fileDir;
		}
		return keySuffixWithSlash;
	}

	 /**
	    * 根据key删除OSS服务器上的文件
	    * @param ossClient  oss连接
	    * @param bucketName  存储空间
	    * @param folder  模拟文件夹名 如"qj_nanjing/"
	    * @param key Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
	    */
	   public static void deleteFile(OSSClient ossClient, String bucketName, String folder, String key){
		    ossClient.deleteObject(bucketName, folder + key);
		    System.out.println("删除" + bucketName + "下的文件" + folder + key + "成功");
	   }

	   /**
	     * 对.zip文件进行解压缩
	     * @param zipFile  解压缩文件
	     * @param descDir  压缩的目标地址，如：D:\\测试 或 /mnt/d/测试
	     * @return
	     */
	    public static ArrayList<File> upzipFile(File zipFile, String descDir) {
	    	ArrayList<File> _list = new ArrayList<File>() ;
	        try {
				ZipFile _zipFile = new ZipFile(zipFile);
	            for( Enumeration<? extends ZipEntry> entries = _zipFile.entries() ; entries.hasMoreElements() ; ){
	                ZipEntry entry = (ZipEntry)entries.nextElement() ;
	                File _file = new File(descDir + File.separator + entry.getName()) ;
	                if( entry.isDirectory() ){
	                    _file.mkdirs() ;
	                }else{
	                    File _parent = _file.getParentFile() ;
	                    if( !_parent.exists() ){
	                        _parent.mkdirs() ;
	                    }
	                    InputStream _in = _zipFile.getInputStream(entry);
	                    FileOutputStream _out = new FileOutputStream(_file) ;
	                    int len = 0 ;
	                    byte[] _byte = new byte[4096] ;
						while( (len = _in.read(_byte)) > 0){
	                        _out.write(_byte, 0, len);
	                    }
	                    _in.close();
	                    _out.flush();
	                    _out.close();
	                    _list.add(_file) ;
	                }
	            }
	        } catch (IOException e) {
	        }
	        return _list ;
	    }

	/**
	 * 上传图片至OSS
	 * @param ossClient  oss连接
	 * @param file 上传文件（文件全路径如：D:\\image\\cake.jpg）
	 * @param bucketName  存储空间
	 * @param folder 此为图片所在的文件夹，将它设置为requestid，如requestid为1——设为"1/"
	 * @return ArrayList<String> 返回的图片网络地址组成的list
	 * */
	public static  ArrayList<String> uploadObject2OSS(OSSClient ossClient, ArrayList<File> files, String bucketName, String folder) {
		ArrayList<String> resultPath =  new ArrayList<String>();
        try {
        	for(int i=0 ; i<files.size() ; i++) {
        		File file = files.get(i);
        		//以输入流的形式上传文件
                InputStream is = new FileInputStream(file);
                //文件名
                String fileName = file.getName();
                //文件大小
                Long fileSize = file.length();
                //创建上传Object的Metadata
                ObjectMetadata metadata = new ObjectMetadata();
                //上传的文件的长度
                metadata.setContentLength(is.available());
                //指定该Object被下载时的网页的缓存行为
                metadata.setCacheControl("no-cache");
                //指定该Object下设置Header
                metadata.setHeader("Pragma", "no-cache");
                //指定该Object被下载时的内容编码格式
                metadata.setContentEncoding("utf-8");
                //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
                //如果没有扩展名则填默认值application/octet-stream
                metadata.setContentType(getContentType(fileName));
                //指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
                metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
                //上传文件   (上传文件流的形式)
                @SuppressWarnings("unused")
				PutObjectResult putResult = ossClient.putObject(bucketName, folder + fileName, is, metadata);
                //解析结果
                //resultStr = putResult.getETag();
                String path = "https://songzi-picture.oss-cn-shenzhen.aliyuncs.com/" + folder + fileName;
                resultPath.add(path);
        	}

        } catch (Exception e) {
            e.printStackTrace();
             System.out.println("上传阿里云OSS服务器异常." + e.getMessage());
        }
        return resultPath;
	}

	/**
	 * 通过文件名判断并获取OSS服务文件上传时文件的contentType
	 * @param fileName 文件名
	 * @return 文件的contentType
	 */
	public static  String getContentType(String fileName){
		//文件的后缀名
		String fileExtension = fileName.substring(fileName.lastIndexOf("."));
		if(".bmp".equalsIgnoreCase(fileExtension)) {
			return "image/bmp";
		}
		if(".gif".equalsIgnoreCase(fileExtension)) {
			return "image/gif";
		}
		if(".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension)  || ".png".equalsIgnoreCase(fileExtension) ) {
			return "image/jpeg";
		}
		if(".html".equalsIgnoreCase(fileExtension)) {
			return "text/html";
		}
		if(".txt".equalsIgnoreCase(fileExtension)) {
			return "text/plain";
		}
		if(".vsd".equalsIgnoreCase(fileExtension)) {
			return "application/vnd.visio";
		}
		if(".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
			return "application/vnd.ms-powerpoint";
		}
		if(".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
			return "application/msword";
		}
		if(".xml".equalsIgnoreCase(fileExtension)) {
			return "text/xml";
		}
		//默认返回类型
		return "image/jpeg";
	}

	//方法使用实例
	public static void main(String[] args) {
		//初始化OSSClient
		OSSClient ossClient=getOSSClient();
		//要上传的zip文件，本地地址
		File zipFile = new File("/home/songzi/se_file/1.zip");
		//解压
		ArrayList<File> files = upzipFile(zipFile,"/home/songzi/se_file/1");
		//上传
		ArrayList<String> md5key = uploadObject2OSS(ossClient, files, BACKET_NAME, "1/");
		System.out.println("上传后的文件MD5数字唯一签名:" + md5key);

		OSSClient ossClient2=getOSSClient();
		File zipFile2 = new File("/home/songzi/se_file/2.zip");
		ArrayList<File> files2 = upzipFile(zipFile2,"/home/songzi/se_file/2");
		ArrayList<String> path = uploadObject2OSS(ossClient2, files2, BACKET_NAME, "2/");
		System.out.println(path.get(0));
	}


}
