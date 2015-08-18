package info.androidhive.materialdesign.adapter;


import com.parse.ParseObject;
import com.parse.ParseUser;

public class Task extends ParseObject {
	public Task(){
		
	}
	
	public boolean isCompleted(){
		return getBoolean("completed");
	}
	
	public void setCompleted(boolean complete){
		put("completed", complete);
	}
	
	public String getDescription(){
		return getString("description");
	}
	
	public void setDescription(String description){
		put("description", description);
	}

	public void setUser(ParseUser currentUser) {
		put("user", currentUser);
	}


}
