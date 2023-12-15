package com.atelier.gestionbib;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
public class Suppression extends Activity {
    private Spinner spLivre;
    private Button btnSupprimer;
    private Button btnRetour;
    private ArrayAdapter<Livre> adpLivre;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suppression);
        initialiser();
        ecouteurs();
        remplir();
    }

    private void initialiser() {
        spLivre = findViewById(R.id.spLivre);
        btnSupprimer = findViewById(R.id.btnSupprimer);
        btnRetour = findViewById(R.id.btnRetour);
        adpLivre = new ArrayAdapter<Livre>(this, android.R.layout.simple_spinner_item);
        //liee le spinner avec le adpter
        spLivre.setAdapter(adpLivre);
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
    private void ecouteurs() {
        btnSupprimer.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                supprimelivre();
            }
        });
        btnRetour.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });
    }

    protected void supprimelivre() {
        Livre l = (Livre) spLivre.getSelectedItem();
        if (l != null) {
            SQLiteBib b = new SQLiteBib(this, "bib.db", null, 1);
            SQLiteDatabase db = b.getWritableDatabase();
            db.delete("livre", "id=" + l.getId(), null);
            remplir();
            b.close();
        }
    }
}
