package com.atelier.gestionbib;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Consultation extends Activity {
	private ListView lstLivre;
	private Button btnRetour;
	private ArrayAdapter<Livre> adpLivre;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.consultation);
		initialiser();
		ecouteurs();
		remplir();
	}

	private void initialiser() {
		lstLivre = findViewById(R.id.lstLivre);
		btnRetour = findViewById(R.id.btnRetour);
		adpLivre = new ArrayAdapter<Livre>(getApplicationContext(),
				android.R.layout.simple_list_item_1);
		lstLivre.setAdapter(adpLivre);
	}

	private void ecouteurs() {
		btnRetour.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private void remplir() {
		SQLiteBib b;
		b = new SQLiteBib(this,"bib.db",null,1);
		SQLiteDatabase db;
		db = b.getWritableDatabase();
		Cursor c = db.query("livre", new String[] {"id", "titre", "nbpage"}, "", null, null, null, null);
		adpLivre.clear();
		while (c.moveToNext()){
			int id = c.getInt(0);
			String titre = c.getString(1);
			int nbpage = c.getInt(2);
			Livre l = new Livre(id,titre,nbpage);
			adpLivre.add(l);
		}
	}
}