package com.atelier.gestionbib;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Ajout extends Activity {
	private EditText edTitre;
	private EditText edNbPage;
	private Button btnAjouter;
	private Button btnRetour;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ajout);
		initialiser();
		ecouteurs();
	}

	private void initialiser() {

		edTitre = findViewById(R.id.edTitre);
		edNbPage = findViewById(R.id.edNbPage);
		btnAjouter = findViewById(R.id.btnAjouter);
		btnRetour = findViewById(R.id.btnRetour);
	}

	private void ecouteurs() {
		btnRetour.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		btnAjouter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			ajoutLivre();
			}
		});
	}

	private void ajoutLivre() {
		try{
		//class de type class open halper
		SQLiteBib b = new SQLiteBib(this, "bib.db", null, 1);
		SQLiteDatabase db;
		db = b.getWritableDatabase();
		ContentValues v = new ContentValues();
		v.put("titre", edTitre.getText().toString());
		v.put("nbpage ", Integer.parseInt(edNbPage.getText().toString()));
		db.insert("livre", null, v);
		db.close();
		edNbPage.setText("");
		edTitre.setText("");
		edTitre.requestFocus();
	}catch (NumberFormatException e){
			Toast t=Toast.makeText(getApplicationContext(),"Taper le Nombre de page SVP!",Toast.LENGTH_LONG);
			t.show();
		}
	}
}
