package crowdtag.service;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;

import crowdtag.model.Result;
import crowdtag.model.request.RequestModel;
import crowdtag.model.requestJson.Images;
import crowdtag.model.requestJson.JsonJavaVO;
import crowdtag.model.temporary.ImagesAndCollectionId;


public class RequestService {
	JsonTransformation transformation = new JsonTransformation();
	WorkerService workerService = new WorkerService();
	
	String FilePath = "src/main/resources/static/";
	
	/**获得一个没有做过的未完成任务的images对象表示
	 * @return **/
	public ImagesAndCollectionId<Images, String> getOne(String workerID) {	
		ArrayList<JsonJavaVO> allUnfinishedRequestID =  getUnfinished();
		ArrayList<String> allRequestFormOneWorker = workerService.getRequest(workerID);
		for(int i=0 ; i<allUnfinishedRequestID.size() ; i++) {
			JsonJavaVO oneUnfinishedRequest = allUnfinishedRequestID.get(i);
			if(!allRequestFormOneWorker.contains(oneUnfinishedRequest.getId())) {
				List<Images> is = oneUnfinishedRequest.getImages();
				for(int j=0; j<is.size() ; j++) {
					if(is.get(j).getState().equals("processing")) {
						ImagesAndCollectionId<Images, String> res = new ImagesAndCollectionId<Images, String>(is.get(j), oneUnfinishedRequest.getId());
						return res;
					}
				}
			}
		}
		return null;
	}

	/**提交一个request任务的完成，一张图片,现在用的是json字符串
	 * @param request是json,这里是images对应的json信息 详情见model.request.json.Records
	 * @param RequestCollectionID，该任务所在的数据集id
	 **/
	public Result finishOne(String RequestCollectionID,String imagesContent) {	
		Result result = new Result();
		result.isright = true;
			
		JsonJavaVO requestCollection = transformation.transferJsonToVOById(RequestCollectionID);
		Gson gsonBuilder = new Gson();
		Images images = gsonBuilder .fromJson(imagesContent, Images.class);
		requestCollection.getImages().add(images);
	
		String content = transformation.transferVOToJson(requestCollection);
		
		result = writeRequestCollection(RequestCollectionID,content);
		return result;
	}

	/**提交一个新的任务集,并保存起来
	 * @param RequestModel 存储新任务集的信息
	 * @return Result**/
	public Result create(RequestModel model) {	
		Result result = new Result();
		
		ArrayList<String> requestList = getAllRequestID();
		String id = (Integer.valueOf(Collections.max(requestList))+1)+"";  //具体,逐加
		//System.out.println(requestList.get(requestList.size()-1)+ " " +id);
		JsonTransformation j = new JsonTransformation();
		ArrayList<Images> images = new ArrayList<Images>();
		JsonJavaVO request = new JsonJavaVO(id,model.getRequesterId(), model.getDescription(), 
				model.getStandard(),model.getPoint(),model.getType(),"processing",
				model.getImagePath(),images);
		
		String content = j.transferVOToJson(request);
		
		result = writeRequestCollection(id,content);
		return result;
	}

	/**获得某任务集中的一个已完成任务的对象，即images对象
	 * @param RequestCollectionID 数据集id
	 * @return Images 图片id 属于json转过去的一部分**/
	public Images checkOne(String RequestCollectionID) {	
		JsonJavaVO request = transformation.transferJsonToVOById(RequestCollectionID);
		List<Images> images = request.getImages();
		for(int i=0 ; i<images.size() ; i++) {
			if(images.get(i).getState().equals("completed")) {
				return 	images.get(i);
			}
		}
		return null;	
	}

	/**发起者关闭任务集（自己关闭或者已经完成）
	 * 加一个state——conpleted、processing、canceled**/
	public Result close(String RequestCollectionID) {	
		Result result = new Result();
		result.isright = false;
		
		return result;
	}

	/**删除某任务集**/
	public Result delete(String RequestCollectionID) {	
		Result result = new Result();
		result.isright = true;
		File file =new File(FilePath+"request/"+RequestCollectionID+".json");
		file.delete();
		return result;
	}

	/**获得某数据集中用户标注的标记次数**/
	public int getFinishedTagQuantity(String RequestCollectionID) {	
		int count = 0;
		JsonJavaVO requestCollection = transformation.transferJsonToVOById(RequestCollectionID);
		for(int i=0 ; i<requestCollection.getImages().size() ; i++) {
			//System.out.println(requestCollection.getImages().get(i).getRecords().size());
			count+=requestCollection.getImages().get(i).getRecords().size();
		}
		return count;
	}

	/**获得某任务集所需要的总标记次数**/
	public int getTagQuantity(String RequestCollectionID) {
		int count = 0;
		JsonJavaVO requestCollection = transformation.transferJsonToVOById(RequestCollectionID);
		count = requestCollection.getStandard()*requestCollection.getImagePath().size();
		return count;
	}

	/**获得某个数据集的所有json对象表示**/
	public JsonJavaVO getRequestVOByID(String RequestCollectionID){	
		String content = "";
		try {
			File file = new File(FilePath +"request/"+RequestCollectionID+".json");
	        BufferedReader reader = null;
	        reader = new BufferedReader(new FileReader(file));
	        String tempString = null;
	        while ((tempString = reader.readLine()) != null) {        
	        	content = content+tempString;   
	        }
	        reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		JsonJavaVO res = transformation.transferJsonToVO(content);
		return res;
	}

	/**获得任务集数量**/
	public int getQuantity() {	
		return getAllRequestID().size();
	}

	/**获得未完成任务集的json对象表示,任务集指图片or整个数据集??**/
	public ArrayList<JsonJavaVO> getUnfinished(){	
		ArrayList<JsonJavaVO> allUnfinishedRequest = new ArrayList<JsonJavaVO>();
		ArrayList<String> allRequestID = getAllRequestID();
		for(int i=0 ; i<allRequestID.size() ; i++) {
			String requestID = allRequestID.get(i);
			if(getTagQuantity(requestID) > getFinishedTagQuantity(requestID)) {
				JsonJavaVO json = getRequestVOByID(requestID);
				allUnfinishedRequest.add(json);
			}
		}
		return allUnfinishedRequest;
	}

	/**得到所有已完成任务的json对象集合*/
	public ArrayList<JsonJavaVO> getFinished(){	
		ArrayList<JsonJavaVO> allUnfinishedRequest = new ArrayList<JsonJavaVO>();
		ArrayList<String> allRequestID = getAllRequestID();
		for(int i=0 ; i<allRequestID.size() ; i++) {
			String requestID = allRequestID.get(i);
			if(getTagQuantity(requestID) == getFinishedTagQuantity(requestID)) {
				JsonJavaVO json = getRequestVOByID(requestID);
				allUnfinishedRequest.add(json);
			}
		}
		return allUnfinishedRequest;
	}
	
	/**得到所有JsonJavaVO*/
	public ArrayList<JsonJavaVO> getAllRequest(){
		ArrayList<String> requestIds = getAllRequestID();
		ArrayList<JsonJavaVO> jsons = new ArrayList<JsonJavaVO>();
		for(int i=0 ; i<requestIds.size() ; i++) {
			JsonJavaVO json = transformation.transferJsonToVOById(requestIds.get(i));
			jsons.add(json);
		}
		return jsons;
	}
	
	private ArrayList<String> getAllRequestID(){
		ArrayList<String> allRequestID = new ArrayList<String>();
		File file = new File(FilePath + "request");   
        // 获得该文件夹内的所有文件   
        File[] array = file.listFiles();   
        for(int i=0;i<array.length;i++){   
        	String temp = array[i].getName();
        	//int len = temp.length();
        	if(temp.endsWith(".json")) {
        		allRequestID.add(temp.substring(0,temp.length()-5));
        		//System.out.println(temp.substring(0,temp.length()-5));
        	}
        }
		return allRequestID;		
	}
	
	private Result writeRequestCollection(String requestCollectionID, String requestCollection) {
		Result res = new Result();
		File writename = new File(FilePath + "request/"+requestCollectionID+".json"); 
        try {
			if(!writename.exists())
				writename.createNewFile();        
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));          
			out.write(requestCollection); // \r\n即为换行          
			out.flush(); // 把缓存区内容压入文件       
			out.close(); // 最后记得关闭文件  
        } catch (IOException e) {
			// TODO Auto-generated catch block
        	res.isright = false;
        	res.error = "写入文件失败";
			e.printStackTrace();
		}
		return res;
	}
}
