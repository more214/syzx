package com.cn.configlog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileHelper {
	/** 定义日期格式化格式 */
	public final static String DATE_FMT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	/** 定义线程局部变量 */
	public final static ThreadLocal<File> file = new ThreadLocal<File>();

	/** 静态方法：将当前时间按照格式格式化并返回 */
	private static String fmt() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FMT_DEFAULT);
		return sdf.format(new Date());
	}

	/**
	 * 静态方法：初始化日志文件，此方法新建日志目录，并给线程变量set一个值
	 * 
	 * @param name
	 *            文件名
	 */
	public static void initLogFile(String name) {

		String dir = "./result/log/" + getFunctionName(name) + "/";
		File base = new File(dir);
		if (!base.exists())
			base.mkdirs();
		File f = new File(base, name);
		if (f.exists())
			f.delete();
		// 将日志文件赋给线程变量
		file.set(f);
	}

	/**
	 * 输出级别为 info的，将信息写入文件
	 * 
	 * @param o
	 *            输入的内容
	 */
	public static void info(Object o) {
		// 取得文件对象，就是之前线程变量set的值
		File f = file.get();
		if (f == null)
			throw new RuntimeException("日志文件没有初始化，请确保日志文件已经生成，相关方法请参考initLogFile(String name)");
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, true), "utf-8"));
			if (o != null) {
				// 将日志写入文件中
				bw.write(String.format("%s [INFO]:%s", fmt(), o.toString()));
				bw.write("\r\n");
				// 在这里会将日志信息在控制台也打印一遍
				System.out.println(String.format("%s [INFO]:%s", fmt(), o.toString()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 输出级别为 error的，将信息写入文件
	 * 
	 * @param o
	 *            输入的内容
	 * @param t
	 *            异常信息
	 */
	public static void error(Object o, Throwable... t) {
		// 取得文件对象，就是之前线程变量set的值
		File f = file.get();
		if (f == null)
			throw new RuntimeException("日志文件没有初始化，请确保日志文件已经生成，相关方法请参考initLogFile(String name)");
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, true), "utf-8"));
			if (o != null) {
				// 将日志写入文件中
				bw.write(String.format("%s [错误]:%s", fmt(), o.toString() + "\r\n" + t));
				// 在这里会将日志信息在控制台也打印一遍
				System.out.println(String.format("%s [错误]:%s", fmt(), o.toString() + "\r\n" + t));
				bw.write("\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 取得模块名字
	 * 
	 * @param fileName
	 *            文件名称
	 */
	public static String getFunctionName(String fileName) {
		String functionName = null;
		int firstUndelineIndex = fileName.indexOf("_");
		functionName = fileName.substring(0, firstUndelineIndex - 4);
		return functionName;

	}

}
