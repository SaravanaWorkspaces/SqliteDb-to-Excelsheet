package com.example.sqlit2excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	Button create_But,export_But;
	EditText ed;
	ArrayList<String> globalArycolm=new ArrayList<String>();
	
	String fname;
		
	DbClass obj;
	
	HSSFSheet firstSheet;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		create_But=(Button)findViewById(R.id.create_But);
		export_But=(Button)findViewById(R.id.exp_But);
	
		ed=(EditText)findViewById(R.id.editText1);
		export_But.setVisibility(View.INVISIBLE);
		
		obj=new DbClass(MainActivity.this, "test", null, 1);
	
		//insert data into sqlite
		create_But.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				obj.insert();
				export_But.setVisibility(View.VISIBLE);
			}
	});
		
		
		//Creates XL file by user defined Name
		export_But.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				fname=ed.getText().toString();
				if(fname.toString().equals(""))
				{
					Toast.makeText(getApplicationContext(),"Please enter the name into Edit text",1000).show();;
				}
				else
				{
				ArrayList<String> aryList=obj.getColumn();
				globalArycolm=aryList;
				
				exporting_sqlite(fname);
				}
			}
		});
		
	}
	

	public void exporting_sqlite(String fileName)
	{
		//fileName="saranTest";
		HSSFWorkbook workbook = new HSSFWorkbook();

		firstSheet= workbook.createSheet("Sheet1");
		
		create_columns();
		insert_item();
		
		 FileOutputStream fos = null;
		            try 
		            {

		                String str_path = Environment.getExternalStorageDirectory().toString();
		                File file ;
		                file = new File(str_path, fileName + ".xls");
		                fos = new FileOutputStream(file);

		                workbook.write(fos);
		            }
		            catch (IOException e) 
		            {
		                e.printStackTrace();
		            }
		            finally 
		            {
		                if (fos != null) 
		                {
		                    try 
		                    {
		                        fos.flush();
		                        fos.close();
		                    }
		                    catch (IOException e) 
		                    {
		                        e.printStackTrace();
		                    }
		                }
		                Toast.makeText(MainActivity.this, "Excel Document Exported", Toast.LENGTH_SHORT).show();

		            }
	 }


	public void create_columns() 
	{
		HSSFRow rowA = firstSheet.createRow(0);
		for(int i=0;i<globalArycolm.size();i++)
		{
			
			HSSFCell cellA = rowA.createCell(i);
			cellA.setCellValue(new HSSFRichTextString(""+globalArycolm.get(i)));
		}
	}
	
	
	public void insert_item()
	{
		Cursor cursor=obj.export_allItems();
		cursor.moveToFirst();
		int n=1;
		while(!cursor.isAfterLast())
		{
			HSSFRow rowA = firstSheet.createRow(n);
			for(int j=0;j<globalArycolm.size();j++)
			{
			HSSFCell cellA = rowA.createCell(j);
			cellA.setCellValue(new HSSFRichTextString(cursor.getString(j)));
			}
			n++;
			cursor.moveToNext();
		}
	}
}
