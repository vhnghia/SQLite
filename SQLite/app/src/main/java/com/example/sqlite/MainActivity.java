package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btn_save, btn_select, btn_exit, btn_update, btn_delete;
    private EditText edt_Id, edt_Title, edt_Author;
    private GridView gv;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Anh Xa
        btn_save = (Button)findViewById(R.id.btnSave);
        btn_delete = (Button)findViewById(R.id.btnDelete);
        btn_select = (Button)findViewById(R.id.btnSelect);
        btn_exit = (Button)findViewById(R.id.btnExit);
        btn_update = (Button)findViewById(R.id.btnUpdate);
        edt_Id = (EditText)findViewById(R.id.edtMaSo);
        edt_Title = (EditText)findViewById(R.id.edtTieuDe);
        edt_Author = (EditText)findViewById(R.id.edtTenTacGia);
        gv = (GridView)findViewById(R.id.gv);
        dbHelper = new DBHelper(this);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = new Book();
                book.setId(edt_Id.getText().toString());
                book.setTitle(edt_Title.getText().toString());
                book.setAuthor(edt_Author.getText().toString());

                if(dbHelper.InsertBook(book)){
                    Toast.makeText(getApplicationContext(),"Da Luu Thanh Cong",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Luu Khong Thanh Cong",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   if(edt_Id.getText().toString().equalsIgnoreCase("")){
                       ArrayList<String> list = new ArrayList<>();
                       ArrayList<Book> booklist = new ArrayList<>();
                       booklist = dbHelper.AllBook();
                       for(Book book : booklist){
                           list.add(book.getId());
                           list.add(book.getTitle());
                           list.add(book.getAuthor());
                       }
                       ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,list);
                       gv.setAdapter(arrayAdapter);
                   }else {
                       ArrayList<String> arrayList = new ArrayList<>();
                       Book b = new Book();
                       b = dbHelper.getBook(edt_Id.getText().toString());
                       arrayList.add(b.getId());
                       arrayList.add(b.getTitle());
                       arrayList.add(b.getAuthor());
                       ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,arrayList);
                       gv.setAdapter(arrayAdapter);
                   }



            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dbHelper.Delete(edt_Id.getText().toString())==false){
                    Toast.makeText(MainActivity.this,"Xoa Khong Thanh Cong",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"Xoa Thanh Cong",Toast.LENGTH_SHORT).show();

                }
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.Update("N","124");
            }
        });
    }
}
