package com.hendiaome.gen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class GeneCode {
	private static File file;
	private static File xmlFile;
	private static File ServiceFile;
	private static File serviceImplFile;
	
	static {
		String path = GeneCode.class.getResource("/").toString();
		file = new File((path+"/template/controller.txt").substring(6));
		ServiceFile = new File((path+"/template/service.txt").substring(6));
		serviceImplFile = new File((path+"/template/serviceImpl.txt").substring(6));
		xmlFile = new File((path+"/template/generatorConfig.xml").substring(6));
	}

	public static void genController(JProgressBar bar, JTable table, String packageName, String packageName2) {
		System.out.println("生成controller");
		
		try {
			//System.out.println(getResourceConten(xmlFile));
			String content = getResourceConten(file);
			List<Map<String, Object>> list = dealTables(table);
			int pro = 90/list.size();
			int n = 0;
			
			//导入需要的表
			for(Map<String, Object> map: list) {
				n++; 
				String modelUpName = (String) map.get("modelUpName");
				String modelName = (String) map.get("modelName");
				String tableName = (String) map.get("tableName");
				String type = (String) map.get("type");
				
				//controller文件
				String out = content.toString()
						.replace("${packageName}", packageName)
						.replace("${modelUpName}", modelUpName)
						.replace("${modelName}", modelName);
				if("".equals(packageName2)) {
					out =  out.replace("${packageName2}", packageName2);
				} else {
					out =  out.replace("${packageName2}", "."+packageName2);
				}
				
				//System.out.println(out);
				
				//写文件
				String str = GeneCode.class.getResource("").toString();
				String path = str.substring(6, str.indexOf("com")+4).replace("bin", "src")+packageName;
				if(!"".equals(packageName2)) {
					path = path+"/"+packageName2;
				}
				
				System.out.println(path+"/controller");
				File conFileDir = new File(path+"/controller/");
				conFileDir.mkdirs();
				File conFile = new File(path+"/controller/"+modelUpName+"Controller.java");
				//conFile.createNewFile();
				
				FileWriter fw = new FileWriter(conFile);
				fw.write(out);
				fw.flush();
				fw.close();
				Main1.pro = pro*n;
				Thread.sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Main1.pro = 100;
	}
	
	public static void genService(JProgressBar bar, JTable table, String packageName, String packageName2) {
		System.out.println("生成Service");
		try {
			//System.out.println(getResourceConten(xmlFile));
			String contentService = getResourceConten(ServiceFile);
			String contentServiceImpl = getResourceConten(serviceImplFile);
			List<Map<String, Object>> list = dealTables(table);
			int pro = 90/list.size();
			int n = 0;
			
			//导入需要的表
			for(Map<String, Object> map: list) {
				n++;
				String modelUpName = (String) map.get("modelUpName");
				String modelName = (String) map.get("modelName");
				String tableName = (String) map.get("tableName");
				String type = (String) map.get("type");
				
				//service文件
				String serviceOut = contentService.toString()
						.replace("${packageName}", packageName)
						.replace("${modelUpName}", modelUpName);
				if("".equals(packageName2)) {
					serviceOut =  serviceOut.replace("${packageName2}", packageName2);
				} else {
					serviceOut =  serviceOut.replace("${packageName2}", "."+packageName2);
				}
				if(-1 != type.indexOf("VARCHAR")) {
					serviceOut =  serviceOut.replace("${keyType}", "String");
				} else if(-1 != type.indexOf("BIGINT")) {
					serviceOut =  serviceOut.replace("${keyType}", "Long");
				} else {
					serviceOut =  serviceOut.replace("${keyType}", "Integer");
				}
				
				
				//System.out.println(serviceOut);
				
				//写文件
				String str = GeneCode.class.getResource("").toString();
				String path = str.substring(6, str.indexOf("com")+4).replace("bin", "src")+packageName;
				if(!"".equals(packageName2)) {
					path = path+"/"+packageName2;
				}
				
				System.out.println(path+"/service");
				File serviceFileDir = new File(path+"/service/");
				serviceFileDir.mkdirs();
				File serviceFile = new File(path+"/service/"+modelUpName+"Service.java");
				//conFile.createNewFile();
				
				FileWriter sfw = new FileWriter(serviceFile);
				sfw.write(serviceOut);
				sfw.flush();
				sfw.close();
				
				//serviceImpl文件
				String serviceImplOut = contentServiceImpl.toString()
						.replace("${packageName}", packageName)
						.replace("${modelUpName}", modelUpName)
						.replace("${modelName}", modelName);
				if("".equals(packageName2)) {
					serviceImplOut =  serviceImplOut.replace("${packageName2}", packageName2);
				} else {
					serviceImplOut =  serviceImplOut.replace("${packageName2}", "."+packageName2);
				}
				if(-1 != type.indexOf("VARCHAR")) {
					serviceImplOut =  serviceImplOut.replace("${keyType}", "String");
				} else if(-1 != type.indexOf("BIGINT")) {
					serviceImplOut =  serviceImplOut.replace("${keyType}", "Long");
				} else {
					serviceImplOut =  serviceImplOut.replace("${keyType}", "Integer");
				}
				//System.out.println(serviceImplOut);
				
				System.out.println(path+"/server/impl");
				File serviceImplFileDir = new File(path+"/service/impl");
				serviceImplFileDir.mkdirs();
				File serviceImplFile = new File(path+"/service/impl/"+modelUpName+"ServiceImpl.java");
				//conFile.createNewFile();
				
				FileWriter sifw = new FileWriter(serviceImplFile);
				sifw.write(serviceImplOut);
				sifw.flush();
				sifw.close();
				Main1.pro = pro*n;
				Thread.sleep(100);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Main1.pro = 100;
	}

	public static void genMapper(JProgressBar bar,String packageName, String packageName2, JTable table, String url, String user, String password) {
		try {
			//System.out.println(getResourceConten(xmlFile));
			String content = getResourceConten(xmlFile);
			List<Map<String, Object>> list = dealTables(table);
			StringBuilder tableConfig = new StringBuilder();
			int pro = 90/list.size();
			int n = 0;
			
			//导入需要的表
			for(Map<String, Object> map: list) {
				n++;
				String modelUpName = (String) map.get("modelUpName");
				String modelName = (String) map.get("modelName");
				String tableName = (String) map.get("tableName");
				
				tableConfig.append(
						"\n<table tableName=\""+tableName+"\" enableCountByExample=\"false\" enableUpdateByExample=\"false\" enableDeleteByExample=\"false\"  enableSelectByExample=\"false\"  selectByExampleQueryId=\"false\" />"
						);
				Main1.pro = pro*n;
				Thread.sleep(100);
			}
			
			//生成xml
			String conf =
					content.replace("${tableConfig}", tableConfig)
					.replace("${url}", url)
					.replace("${user}", user)
					.replace("${password}", password)
					.replace("${packageName}", packageName);
			if("".equals(packageName2)) {
				conf =  conf.replace("${packageName2}", packageName2);
			} else {
				conf =  conf.replace("${packageName2}", "."+packageName2);
			}		
					
			
			//生成文件
			String str = GeneCode.class.getResource("").toString();
			String path = str.substring(6, str.indexOf("com"))+"config/generatorConfig.xml";
			
			File file = new File(path);
			//conFile.createNewFile();
			FileWriter fw = new FileWriter(file);
			fw.write(conf);
			fw.flush();
			fw.close();
		
			//开始生成
			List<String> warnings = new ArrayList<String>();
			boolean overwrite = true;
			//指定 逆向工程配置文件
			//File configFile = new File(path); 
			ConfigurationParser cp = new ConfigurationParser(warnings);
			Configuration config = cp.parseConfiguration(file);
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,callback, warnings);
			myBatisGenerator.generate(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Main1.pro = 100;
		
	}
	
	private static String getResourceConten(File file) throws Exception {
		StringBuilder template = new StringBuilder();
		
		//加载模板
		String tmp;
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		while(null != (tmp=reader.readLine())) {
			//System.err.println(tmp);
			template.append(tmp+"\t\n");
		}
		
		return template.toString();
	} 
	
	private static List<Map<String, Object>> dealTables(JTable table) {
		//替换变量
		List<Map<String, Object>> list = new ArrayList<>();
		int[] rows = table.getSelectedRows();
		boolean flag = false;
		
		for(int row: rows) {
			String tableName = (String) table.getValueAt(row, 0);
			String type = (String) table.getValueAt(row, 1);
			StringBuilder sb = new StringBuilder();
			StringBuilder sbUp = new StringBuilder();
			
			//表名
			for(int i=0; i<tableName.length(); i++) {
				char s = tableName.charAt(i);
				
				if(0 == i) {
					sb.append(s);
					sbUp.append(Character.toUpperCase(s));
				} else {
					if(95 == s) {
						flag = true;
						continue;
					}
					if(true == flag) {
						sb.append(Character.toUpperCase(s));
						sbUp.append(Character.toUpperCase(s));
						flag = false;
					} else {
						sb.append(s);
						sbUp.append(s);
					}
				}
			}
			
			String modelUpName = sbUp.toString();
			String modelName = sb.toString();
			Map<String, Object> map = new HashMap<>();
			map.put("modelUpName", modelUpName);
			map.put("modelName", modelName);
			map.put("tableName", tableName);
			map.put("type", type);
			list.add(map);
		}
		return list;
	}

}
