package crowdtag.service;





import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import crowdtag.model.Result;
import crowdtag.model.requestJson.JsonJavaVO;
import crowdtag.model.user.Worker;




public class WorkerService {
	String turn(int a) {
		String s=a+"";
		int p=6-s.length();
		if(p>0) {
			for(int i=0;i<p;i++) {
				if(i==p-1) {
					s="1"+s;
				}
				else {
				s="0"+s;
				}
			}
		}
		
		return s;
	}
	/**权限确认
	 * id workerid
	 * key worker password
	 * **/
public Result login(String id,String key) {
	Result result=new Result();
	result.isright=false;
	result.error="Not exist ID";
	File file = new File("src/main/resources/static/user/worker.txt");  
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
/**工人注册
 * worker worker对象
 * **/
public Result register(Worker worker) {
	Result result=new Result();
	result.isright=false;
	ArrayList<String> s=new ArrayList<String>();
	File file =new File("src/main/resources/static/user/worker.txt");
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
           tmp+=worker.getWorkername()+" ";
           tmp+=worker.getPassword()+ " ";
           tmp+=worker.getPoint()+" ";
           tmp+=worker.getPosition()+" ";
           s.add(worker.getRequestlist());
	         
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
/**工人加分
 * id  workerid
 * point 分数
 * **/
public Result addPoint(String id,int point) {
	Result result=new Result();
	result.isright=false;
	ArrayList<String> s=new ArrayList<String>();
	File file =new File("src/main/resources/static/user/worker.txt");
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
	 ArrayList<String> rs=new ArrayList<String>();
          for(int i=0;i<s.size();i++) {
        	  String tmp=s.get(i);
        	  String[] t=tmp.split(" ");
        	  if(t[0].equals(id)) {
        		  int p=Integer.valueOf(t[3])+point;
        		  t[3]=p+"";
        		  tmp="";
        	  
        	  for(int j=0;j<t.length;j++) {
        		  if(j==t.length-1) {
        		  tmp+=t[j];
        		  }
        		  else
        		  tmp+=t[j]+" ";
        	  }
        	  }
        	  rs.add(tmp);
          }
	         
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
	        for(int i=0;i<rs.size();i++){
	        	writer1.write(rs.get(i));
	        	writer1.newLine();
	        	if(i==rs.size()-1){
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
/**工人减分
 * id  workerid
 * point 分数
 * **/
public Result subPoint(String id,int point) {
	Result result=new Result();
	result.isright=false;
	ArrayList<String> s=new ArrayList<String>();
	File file =new File("src/main/resources/static/user/worker.txt");
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
	 ArrayList<String> rs=new ArrayList<String>();
          for(int i=0;i<s.size();i++) {
        	  String tmp=s.get(i);
        	  String[] t=tmp.split(" ");
        	  if(t[0].equals(id)) {
        		  int p=Integer.valueOf(t[3])-point;
        		  t[3]=p+"";
        		  tmp="";
        	  
        	  for(int j=0;j<t.length;j++) {
        		  if(j==t.length-1) {
        		  tmp+=t[j];
        		  }
        		  else
        		  tmp+=t[j]+" ";
        	  }
        	  }
        	  rs.add(tmp);
          }
	         
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
	        for(int i=0;i<rs.size();i++){
	        	writer1.write(rs.get(i));
	        	writer1.newLine();
	        	if(i==rs.size()-1){
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
///**工人添加奖品**/
/*Result addPrize(String id,String prizekey) {
	return null;
}*/
/**获得工人排名
 * id workerid
 * **/
public int getRank(String id) {
	
	ArrayList<String> s=new ArrayList<String>();
	File file =new File("src/main/resources/static/user/worker.txt");
	 BufferedReader reader = null;  
	 try { 
	     reader = new BufferedReader(new FileReader(file));  
	     String tempString = null;  
	     while ((tempString = reader.readLine()) != null) {  
	    	 s.add(tempString);
	     } 
	     }catch (IOException e) {  
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
           int count=1;
	       int p=0;
          for(int i=0;i<s.size();i++) {
        	  String tmp=s.get(i);
        	  String[] t=tmp.split(" ");
        	  if(t[0].equals(id)) {
        		 p=Integer.valueOf(t[3]);
        		 }
        	  }
          for(int i=0;i<s.size();i++) {
        	  String tmp=s.get(i);
        	  String[] t=tmp.split(" ");
    	  if(!t[0].equals(id)) {
        		  if(Integer.valueOf(t[3])>p) {
        			  count++;
        		  }
        	  }
        	 
          }
	         
	          return count;
}
/**以比例的形式显示工人排名
 * id workerid
 * **/
public double getScaleRank(String id) {
	ArrayList<String> s=new ArrayList<String>();
	File file =new File("src/main/resources/static/user/worker.txt");
	 BufferedReader reader = null;  
	 try { 
	     reader = new BufferedReader(new FileReader(file));  
	     String tempString = null;  
	     while ((tempString = reader.readLine()) != null) {  
	    	 s.add(tempString);
	     } 
	     }catch (IOException e) {  
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
           int count=1;
           int total=s.size();
	       int p=0;
          for(int i=0;i<s.size();i++) {
        	  String tmp=s.get(i);
        	  String[] t=tmp.split(" ");
        	  if(t[0].equals(id)) {
        		 p=Integer.valueOf(t[3]);
        		 }
        	  }
         
          for(int i=0;i<s.size();i++) {
        	  String tmp=s.get(i);
        	  String[] t=tmp.split(" ");
        	  if(!t[0].equals(id)) {
        		  if(Integer.valueOf(t[3])>p) {
        			  count++;
        		  }
        	  }
        	 
          }
	         double scalerank=((count)*100/total)*0.01;
	          return scalerank;
}
/**依据所完成任务的json（完整的数据集），在工人信息中记录完成情况。
 * id workerid
 * request 完成任务的数据集
 * **/
public Result addRequest(String id,String request) {
	Result result=new Result();
	result.isright=true;
	//JsonJavaVO j=new JsonJavaVO();
	JsonTransformation tmp=new JsonTransformation();
	JsonJavaVO j=tmp.transferJsonToVO(request);
	String requestid=j.getId();
	//不测试转换函数的正确性
	
	boolean change=false;
	ArrayList<String> s=new ArrayList<String>();
	File file =new File("src/main/resources/static/user/complete.txt");
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
	         result.error="ArrayIndexOutOfBoundsException";
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
	 ArrayList<String> rs=new ArrayList<String>();
	 for(int i=0;i<s.size();i++) {
		 String t=s.get(i);
		
		 String[] lit=t.split(" ");
		 if(lit[0].equals(id)) {
			 if(lit[1].equals(requestid)) {
				 change=true;
				 lit[2]=(Integer.valueOf(lit[2])+1)+"";
			     t="";
				 for(int p=0;p<lit.length;p++) {
					 if(p==lit.length-1) {
						 t+=lit[p];
					 }else
					 t+=lit[p]+" ";
					 
				 }
				
			 }
		 }
		
		 rs.add(t);
 if(i==s.size()-1&&change==false) {
			 String ts=id+" "+requestid+" "+"1";
			 rs.add(ts);
		 }

	 }
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
         result.error="IO Exception";
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
   for(int i=0;i<rs.size();i++){
   	writer1.write(rs.get(i));
   	writer1.newLine();
   
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
    //存储到complete.txt中
     ArrayList<String> r=new ArrayList<String>();
     File file1 =new File("src/main/resources/static/user/worker.txt");
	 BufferedReader reader1 = null;  
	 try { 
	     reader1 = new BufferedReader(new FileReader(file1));  
	     String tempString = null;  
	     while ((tempString = reader1.readLine()) != null) {  
	    	 r.add(tempString);
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
	         if (reader1 != null) {  
	             try {  
	                 reader1.close();  
	             } catch (IOException e1) { 
	            	 result.isright=false;
			         result.error="IO Exception";
			         return result;
	             }  
	         }  
	     	}
	 ArrayList<String> m=new ArrayList<String>();
	 
	    if(change) {
	    	String o="";
	    	for(int i=0;i<r.size();i++) {
	    		String k=r.get(i);
	    		String[] l=k.split(" ");
	    		String[]  l1=l[5].split(";");
	    		if(l[0].equals(id)) {
	    			
	    			for(int f=0;f<l1.length;f++) {
	    				String[] l2=l1[f].split(",");
	    				if(l2[0].equals(requestid)) {
	    					l2[1]=Integer.valueOf(l2[1])+1+"";
	    				}
	    				 o+=l2[0]+","+l2[1];
	    				if(f!=l1.length-1) {
	    					o+=";";
	    				}
	    				if(f==l1.length-1) {
	    					l[5]=o;
	    				}
	    			}
	    			
	    			
	    		}
	    		String n=l[0]+" "+l[1]+" "+l[2]+" "+l[3]+" "+l[4]+" "+l[5];
	    		m.add(n);
	    	}
	    }
	    else {
	    	for(int i=0;i<r.size();i++) {
	    		String k=r.get(i);
	    		String[] l=k.split(" ");
	    		if(l[0].equals(id)) {
	    			k=k+";"+requestid+","+"1";
	    		}
	    		m.add(k);
	    	}
	    }

        BufferedWriter writer2 = null;  
        try { 
            writer2 = new BufferedWriter(new FileWriter(file1));  
            writer2.flush();
            writer2.close();
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
            if (writer2 != null) {  
                try {  
                    writer2.close();  
                } catch (IOException e1) {  
              	  result.isright=false;
				         result.error="IO Exception";
				         return result;
                }  
            }  
        }
     
        BufferedWriter writer3 = null;  
        try { 
            writer3 = new BufferedWriter(new FileWriter(file1,true));  
      for(int i=0;i<m.size();i++){
      	writer3.write(m.get(i));
      	writer3.newLine();
      	if(i==m.size()-1){
      		result.isright=true;
      		result.error=null;
      	}
      }
            writer3.close();
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
            if (writer3 != null) {  
                try {  
                    writer3.close();  
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
/**获取工人的任务完成情况
 * id workerid
 * **/
public ArrayList<String> getRequest(String id){
	ArrayList<String> s=new ArrayList<String>();
	File file =new File("src/main/resources/static/user/worker.txt");
	 BufferedReader reader = null;  
	 try { 
	     reader = new BufferedReader(new FileReader(file));  
	     String tempString = null;  
	     while ((tempString = reader.readLine()) != null) {  
	    	 s.add(tempString);
	     } 
	     }catch (IOException e) {  
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
	ArrayList<String> a=new ArrayList<String>();
	 for(int i=0;i<s.size();i++) {
   	  String tmp=s.get(i);
   	  String[] t=tmp.split(" ");
   	  if(t[0].equals(id)) {
   		  String l=t[5];
   		  String[] list=l.split(";");
   		  for(int j=0;j<list.length;j++) {
   			  a.add(list[j]);
   		  }
   	  }
  
     }
	 return a;
}
/**设置工人的阅读进度
 * id workerid
 * pos 位置
 * **/
public Result setPosition(String id,String pos) {
	Result result=new Result();
	result.isright=false;
	ArrayList<String> s=new ArrayList<String>();
	File file =new File("src/main/resources/static/user/worker.txt");
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
	 ArrayList<String> rs=new ArrayList<String>();
          for(int i=0;i<s.size();i++) {
        	  String tmp=s.get(i);
        	  String[] t=tmp.split(" ");
        	  if(t[0].equals(id)) {
        		  
        		  t[4]=pos;
        		  tmp="";
        	  
        	  for(int j=0;j<t.length;j++) {
        		  if(j==t.length-1) {
        		  tmp+=t[j];
        		  }
        		  else
        		  tmp+=t[j]+" ";
        	  }
        	  }
        	  rs.add(tmp);
          }
	         
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
	        for(int i=0;i<rs.size();i++){
	        	writer1.write(rs.get(i));
	        	writer1.newLine();
	        	if(i==rs.size()-1){
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
/**获得工人的阅读进度
 * id workerid
 * **/
public String getPosition(String id) {
	ArrayList<String> s=new ArrayList<String>();
	File file =new File("src/main/resources/static/user/worker.txt");
	 BufferedReader reader = null;  
	 try { 
	     reader = new BufferedReader(new FileReader(file));  
	     String tempString = null;  
	     while ((tempString = reader.readLine()) != null) {  
	    	 s.add(tempString);
	     } 
	     }catch (IOException e) {  
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
	 String position="";
	 for(int i=0;i<s.size();i++) {
   	  String tmp=s.get(i);
   	  String[] t=tmp.split(" ");
   	  if(t[0].equals(id)) {
   		  
   		position=t[4];
   		
   	  }
  
     }
	 return position;
}
/**获得工人的总数**/
public int getQuantity() {
	ArrayList<String> s=new ArrayList<String>();
	File file =new File("src/main/resources/static/user/worker.txt");
	 BufferedReader reader = null;  
	 try { 
	     reader = new BufferedReader(new FileReader(file));  
	     String tempString = null;  
	     while ((tempString = reader.readLine()) != null) {  
	    	 s.add(tempString);
	     } 
	     }catch (IOException e) {  
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
	         }  }
	 return s.size();
}

}
