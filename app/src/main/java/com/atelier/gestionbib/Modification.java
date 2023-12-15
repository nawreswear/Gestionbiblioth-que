package com.atelier.gestionbib;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Modification extends AppCompatActivity {
	private Spinner spLivre;
	private EditText edTitre;
	private EditText edNbPage;
	private Button btnModifier;
	private Button btnRetour;
	private ArrayAdapter<Livre> adpLivre;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.modification);
		initialiser();
		ecouteurs();
		remplir();
	}

	private void initialiser() {
		spLivre = findViewById(R.id.spLivre);
		edTitre = findViewById(R.id.edTitre);
		edNbPage = findViewById(R.id.edNbPage);
		btnModifier = findViewById(R.id.btnModifier);
		btnRetour = findViewById(R.id.btnRetour);
		adpLivre = new ArrayAdapter<Livre>(this, android.R.layout.simple_spinner_item);
		spLivre.setAdapter(adpLivre);
	}

	private void ecouteurs() {
		//Listener sur les boutons
		btnRetour.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		btnModifier.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				modifielivre();
			}
		});

		spLivre.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
									   int arg2, long arg3) {
				// TODO Auto-generated method stub
				actualiser();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	private void remplir() {
		SQLiteBib b;
		b = new SQLiteBib(this,"bib.db",null,1);
		SQLiteDatabase db;
		db = b.getWritableDatabase();
		Cursor c = db.query("Livre", new String[] {"id", "titre", "nbpage"}, "", null, null, null, null);
		adpLivre.clear();
		while (c.moveToNext()){
			int id = c.getInt(0);
			String titre = c.getString(1);
			int nbpage = c.getInt(2);
			Livre l = new Livre(id,titre,nbpage);
			adpLivre.add(l);
		}
	}

	protected void actualiser() {
		Livre l = (Livre) spLivre.getSelectedItem();
		if(l != null){
			edTitre.setText(l.getTitre());
			edNbPage.setText(l.getNbpage()+"");
		}
	}

	protected void modifielivre() {
		try {//selectionne
			Livre l = (Livre) spLivre.getSelectedItem();
			ContentValues v = new ContentValues();
			v.put("titre", edTitre.getText().toString());
			v.put("nbpage", Integer.parseInt(edNbPage.getText().toString()));
			SQLiteBib b = new SQLiteBib(this, "bib.db", null, 1);
			SQLiteDatabase db = b.getWritableDatabase();
			db.update("livre", v, "id=" + l.getId(), null);
			remplir();
			b.close();
			edTitre.requestFocus();
		}catch (NumberFormatException e){
			Toast t=Toast.makeText(getApplicationContext(),"taper le nombre de page SVP!",Toast.LENGTH_LONG);
			t.show();
			edNbPage.requestFocus();
		}
	}
}