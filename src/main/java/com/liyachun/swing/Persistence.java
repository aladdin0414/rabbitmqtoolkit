/**
 * 
 */
package com.liyachun.swing;

import com.google.gson.Gson;
import com.liyachun.j2se.util.IOUtil;

/**
 * @author liyc
 * @date 2017年9月14日 下午5:40:10
 */
public class Persistence {
	
	static final String filename = "rmptooldata.json";
	
	public void save(DataModel model) {
		Gson gson=new Gson();
		String element = gson.toJson(model);
		IOUtil.writeText2Path(filename, element);
	}
	
	public DataModel getDataModel() {
		String jsonStr = IOUtil.readtxt(filename);
		Gson gson=new Gson();
		DataModel dm = gson.fromJson(jsonStr, DataModel.class);
		if(dm == null) {
			return new DataModel();
		}
		return dm;
	}
}
