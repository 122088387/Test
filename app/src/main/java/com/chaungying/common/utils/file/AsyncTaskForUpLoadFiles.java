package com.chaungying.common.utils.file;

import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/** 提交多个文件的异步线程 */
public class AsyncTaskForUpLoadFiles extends AsyncTask<Object, Object, Object> {

	public String actionUrl;// 接收数据的Url
	public Map<String, String> params;// 文本参数
	public Map<String, File> files;// 文件参数
	public String fileParamName;// 接收文件的参数名
	public Message m;
	public Gson g = new Gson();

	public AsyncTaskForUpLoadFiles(String actionUrl, Map<String, String> params, Map<String, File> files,
								   String fileParamName, Message m) {
		this.actionUrl = actionUrl;
		this.files = files;
		this.fileParamName = fileParamName;
		this.m = m;
		this.params = params;
		// CommonUtils.log(params.toString());
	}

	@Override
	protected String doInBackground(Object... paramss) {
		return post(actionUrl, params, files);
	}

	@Override
	protected void onPostExecute(Object result) {
		Base base = null;// 附件上传 服务器响应的json
		String response = (String) result;
		if (response.equals("-1")) {
			base = new Base();
			// base.message = "提交失败";
		} else {
			Log.i("response", response);
//			base = CommonUtils.gson.fromJson(response, Base.class);
//
//			 base = g.fromJson(response, Base.class);
//			 base = JSON.parseObject(response, Base.class);
		}
//		m.obj = base;
		m.sendToTarget();
	}

	/**
	 * 附件上传
	 * 
	 * @param actionUrl
	 *            附件上传目标地址url
	 * @param params
	 *            表单内容 包括文本内容
	 * @param files
	 *            附件集合 可以上传多个图片
	 * @return 成功与否
	 * @throws IOException
	 */
	public String post(String actionUrl, Map<String, String> params, Map<String, File> files) {

		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--";// 起始标示
		String LINEND = "\r\n"; // 换行
		String CHARSET = "UTF-8";// 字符集
		String MULTIPART_FROM_DATA = "multipart/form-data";// 表单提交 标示

		try {
			URL uri = new URL(actionUrl);
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setConnectTimeout(60 * 1000);
			conn.setReadTimeout(60 * 1000);
			conn.setDoInput(true);// 允许输入
			conn.setDoOutput(true);// 允许输出
			conn.setUseCaches(false);
			conn.setRequestMethod("POST"); // Post方式
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);
			// 首先组拼文本类型的参数
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINEND);
				sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
				sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
				sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
				sb.append(LINEND);
				sb.append(entry.getValue());
				sb.append(LINEND);
			}
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			BufferedOutputStream bos = new BufferedOutputStream(dos);
			bos.write(sb.toString().getBytes());
			// 发送文件数据
			if (files != null && files.size() > 0) {
				for (Map.Entry<String, File> file : files.entrySet()) {
					StringBuilder sb1 = new StringBuilder();
					sb1.append(PREFIX);
					sb1.append(BOUNDARY);
					sb1.append(LINEND);
					sb1.append("Content-Disposition: form-data; name=\"" + fileParamName + "\"; filename=\""
							+ file.getKey() + "\"" + LINEND);
					sb1.append("Content-Type: multipart/form-data; charset=" + CHARSET + LINEND);
					sb1.append(LINEND);
					bos.write(sb1.toString().getBytes("utf-8"));
					InputStream is = new FileInputStream(file.getValue());
					byte[] buffer = new byte[10 * 1024];
					int len = 0;
					while ((len = is.read(buffer)) != -1) {
						bos.write(buffer, 0, len);
					}
					is.close();
					bos.write(LINEND.getBytes());
				}
			}
			// 请求结束标志
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
			bos.write(end_data);
			bos.flush();
			// 得到响应码
			int code =conn.getResponseCode();
			Log.i("code", code + "");
			if (code == 200) {
				InputStream in = conn.getInputStream();
				String json = convertStreamToString(in);
				// CommonUtil.log(json);
				bos.close();
				dos.close();
				conn.disconnect();
				return json;
			} else {
				return "-1";
			}
		} catch (IOException e) {
			e.printStackTrace();
			return "-1";
		}
	}

	private String convertStreamToString(InputStream is) {
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			// CommonUtil.log(e);
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				// CommonUtil.log(e);
			}
		}
		return sb.toString();
	}
}
