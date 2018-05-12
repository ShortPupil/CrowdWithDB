package crowdtag.service;





import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import crowdtag.hibernate.entity.RequesterEntity;
import crowdtag.model.Result;







public class RequesterService {
	
	
	
	String turn(int a) {
		String s=a+"";
		int p=6-s.length();
		if(p>0) {
			for(int i=0;i<p;i++) {
				s="0"+s;
			}
		}
		
		return s;
	}
	/**登录 
	 * @param id 发起者id
	 * @param key 发起者密码
	 * @return result 结果信息*/   
	public Result login(String id,String key) {  
		
		Result result=new Result();
		result.isright=false;
		result.error="Not exist ID";
		File file = new File("src/main/resources/static/user/requester.txt");  
        BufferedReader reader = null;  
        try { 
            reader = new BufferedReader(new FileReader(file));  
            String tempString = null;  
            while ((tempString = reader.readLine()) != null) {  
          String [] arr= tempString.split(" ");
          if(arr[0].equals(id)) {
        	  result.error="Password is wrong";
        	  if(arr[2].equals(key)) {
        		  result.isright=true;
        		  break;
        	  }
          }
          
            }  
            
            reader.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
        catch(ArrayIndexOutOfBoundsException e){
        	e.printStackTrace();
        }
        	finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }      
        if(result.isright==true) {
        	result.error=null;
        }
        return result;
	}
	
	/** 注册
	 * @param requester 发起者类
	 * @return result 结果信息*/
    public Result register(RequesterEntity requester) {  
    	Result result=new Result();
    	result.isright=false;
    	ArrayList<String> s=new ArrayList<String>();
    	File file =new File("src/main/resources/static/user/requester.txt");
    	 BufferedReader reader = null;  
		 try { 
		     reader = new BufferedReader(new FileReader(file));  
		     String tempString = null;  
		     while ((tempString = reader.readLine()) != null) {  
		    	 s.add(tempString);
		     } 
		     }catch (IOException e) {  
		         e.printStackTrace();
		         result.isright=false;
		         result.error="IO Exception";
		         return result;
		     }
		     catch(ArrayIndexOutOfBoundsException e){
		     	e.printStackTrace();
		     	result.isright=false;
		         result.error="ArrayIndexOutOfBounds Exception";
		         return result;
		     }
		     	finally {  
		         if (reader != null) {  
		             try {  
		                 reader.close();  
		             } catch (IOException e1) { 
		            	 result.isright=false;
				         result.error="IO Exception";
				         return result;
		             }  
		         }  
		     	}
               String tmp="";
               tmp=tmp+turn(s.size()+1)+" ";
               tmp+=requester.getName()+" ";
               tmp+=requester.getPassword()+ " ";             
               s.add(requester.getRequestlist());
		         
		          BufferedWriter writer = null;  
		          try { 
		              writer = new BufferedWriter(new FileWriter(file));  
		              writer.flush();
		              writer.close();
		          } catch (IOException e) {  
		              e.printStackTrace();  
		              result.isright=false;
				         result.error="IO Exception";
				         return result;
		          }
		          catch(ArrayIndexOutOfBoundsException e){
		          	e.printStackTrace();
		          	result.isright=false;
			         result.error="ArrayIndexOutOfBounds Exception";
			         return result;
		          }
		          	finally {  
		              if (writer != null) {  
		                  try {  
		                      writer.close();  
		                  } catch (IOException e1) {  
		                	  result.isright=false;
						         result.error="IO Exception";
						         return result;
		                  }  
		              }  
		          }
		       
		          BufferedWriter writer1 = null;  
		          try { 
		              writer1 = new BufferedWriter(new FileWriter(file,true));  
		        for(int i=0;i<s.size();i++){
		        	writer1.write(s.get(i));
		        	writer1.newLine();
		        	if(i==s.size()-1){
		        		result.isright=true;
		        		result.error=null;
		        	}
		        }
		              writer1.close();
		          } catch (IOException e) {  
		              e.printStackTrace();  
		              result.isright=false;
				         result.error="IO Exception";
				         return result;
		          }
		          catch(ArrayIndexOutOfBoundsException e){
		          	e.printStackTrace();
		          	result.isright=false;
			         result.error="ArrayIndexOutOfBounds Exception";
			         return result;
		          }
		          	finally {  
		              if (writer1 != null) {  
		                  try {  
		                      writer1.close();  
		                  } catch (IOException e1) { 
		                	  result.isright=false;
						         result.error="IO Exception";
						         return result;
		                  }  
		              }  
		          }
		          if(result.isright=true) {
		        	  result.error=null;
		          }
		 return result;
    }
    
    /**获得发起者的所有任务集的id
     * @param requesterid 发起者id
     * @return 发起者的所有任务集的id*/
   public  ArrayList<String> getAllRequestByRequesterId(String requesterid){
    	ArrayList<String> a=new ArrayList<String>();
    	File file = new File("src/main/resources/static/user/requester.txt");  
        BufferedReader reader = null;  
        try { 
            reader = new BufferedReader(new FileReader(file));  
            String tempString = null;  
            while ((tempString = reader.readLine()) != null) {  
          String [] arr= tempString.split(" ");
          if(arr[0].equals(requesterid)) {
        	  String[] t=arr[3].split(";");
        	  for(int i=0;i<t.length;i++) {
        		  a.add(t[i]);
        	  }
          }
          
            }  
            
            reader.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }
        catch(ArrayIndexOutOfBoundsException e){
        	e.printStackTrace();
        }
        	finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        } 
        return a;
    }
}
