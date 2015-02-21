package com.hmkcode.android.sqlite;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		
		
		MySQLiteHelper db = new MySQLiteHelper(this);
        
        /**
         * CRUD Operations
         * */
        // add Books
        db.addMot(new MotDb("Android Application Development Cookbook", "Wei Meng Lee"));   
        db.addMot(new MotDb("Android Programming: The Big Nerd Ranch Guide", "Bill Phillips and Brian Hardy"));       
        db.addMot(new MotDb("Learn Android App Development", "Wallace Jackson"));
        
        // get all books
        List<MotDb> list = db.getAllMots();
        
        // delete one book
        db.deleteMot(list.get(0));
        
        // get all books
        db.getAllMots();

        
	}

}
