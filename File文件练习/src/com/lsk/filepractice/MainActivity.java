package com.lsk.filepractice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;

import org.w3c.dom.Text;

import android.R.menu;
import android.R.string;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private Button btnWriteToLocal;
	private Button btnRead;
	private EditText edtWrite;
	private TextView tvShow;
	private String FILE_NAME = "text.txt";
	private Button btnWriteToSD;
	private Button btnReadFromSD;
	private EditText edtWriteToSD;
	private TextView tvShowSD;
    private Button btnDelete;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();

	}

	private void initView() {
		// TODO Auto-generated method stub
		btnWriteToLocal = (Button) findViewById(R.id.btn_write);
		btnRead = (Button) findViewById(R.id.btn_read);
		edtWrite = (EditText) findViewById(R.id.edt_main);
		tvShow = (TextView) findViewById(R.id.tv_show);
		btnWriteToLocal.setOnClickListener(this);
		btnRead.setOnClickListener(this);
		btnWriteToSD = (Button) findViewById(R.id.btn_writetosdcard);
		btnReadFromSD = (Button) findViewById(R.id.btn_read_sdcard);
		edtWriteToSD = (EditText) findViewById(R.id.edt_main_sdcard);
		tvShowSD = (TextView) findViewById(R.id.tv_show_sdcard);
		btnWriteToSD.setOnClickListener(this);
		btnReadFromSD.setOnClickListener(this);
		btnDelete=(Button) findViewById(R.id.btn_delete);
		btnDelete.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_read: {
			String readtxt = readFromFile();
			tvShow.setText(readtxt);
		}
			break;
		case R.id.btn_write: {
			writeToFile(edtWrite.getText().toString());
		}
			break;
		case R.id.btn_writetosdcard: {
			writeToSdCardFile(edtWriteToSD.getText().toString());
		}
			break;
		case R.id.btn_read_sdcard: {
			readFromSdCardFile();
		}
			break;
		case R.id.btn_delete: {
		File file=makeSdCardFile("/lsk", "me.txt")	;
		if (file.delete()) { 
			Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
		}		
		}break;
		}
	}

	// 建立SD卡文件
	private File makeSdCardFile(String ExtraFilePath, String filename) {
		String filePath = "";
		BufferedWriter bw = null;
		boolean isSDcardExit = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
		if (isSDcardExit) {
			filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + ExtraFilePath;
			System.out.println(filePath);
		} else {
			filePath = ExtraFilePath;
		}
		File file_temp = new File(filePath);
		if (!file_temp.exists()) {
			file_temp.mkdir();
		}
		File file = new File(filePath, filename);
		return file;
	}

	// 从sd中读文件
	private void readFromSdCardFile() {
		// TODO Auto-generated method stub
		File file = makeSdCardFile("/lsk", "me.txt");
		try {
			InputStream is = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				sb.append(readLine);
				sb.append("\n");
			}
			br.close();
			tvShowSD.setText(sb.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 向sd卡中写文件
	private void writeToSdCardFile(String content) {
		// TODO Auto-generated method stub
		BufferedWriter bw = null;
		File file = makeSdCardFile("/lsk", "me.txt");
		Log.d("filepath:", file.getAbsolutePath().toString());
		try {
			OutputStream outputStream = new FileOutputStream(file, true);
			OutputStreamWriter osw = new OutputStreamWriter(outputStream, "utf-8");
			bw = new BufferedWriter(osw);
			bw.write(content);
			bw.flush();
			Toast.makeText(MainActivity.this, "写入成功", Toast.LENGTH_SHORT).show();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// 读本地文件
	private String readFromFile() {
		// TODO Auto-generated method stub
		try {
			FileInputStream fis = openFileInput(FILE_NAME);
			byte[] buff = new byte[1024];
			int len = 0;
			StringBuilder sb = new StringBuilder();
			while ((len = fis.read(buff)) > 0) {
				String content = new String(buff, 0, len);
				sb.append(content);

			}
			fis.close();
			return sb.toString();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	// 向本地写文件
	private void writeToFile(String filecontent) {
		// TODO Auto-generated method stub
		File file = new File(FILE_NAME);
		if (!file.exists()) {
			file.mkdir();
		}
		Log.d("filepath:", file.getAbsoluteFile().toString());
		try {
			FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
			PrintStream ps = new PrintStream(fos);
			ps.print(filecontent);
			ps.close();
			fos.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
