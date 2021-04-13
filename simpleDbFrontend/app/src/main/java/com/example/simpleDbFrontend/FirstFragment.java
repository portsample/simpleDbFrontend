package com.example.simpleDbFrontend;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class FirstFragment extends Fragment {
    private static SQLiteDatabase db;
    Cursor queryAll;
    Button btnSaveRecord, btnUpdateRecord, btnDeleteRecord, btnResetForm, btnFirstRecord, btnPreviousRecord,btnNextRecord, btnLastRecord, btnHelp, btnEditComment;
EditText edRowid, edSpecies, edArea, edSampler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//Buttons
        btnSaveRecord = (Button) view.findViewById(R.id.btnSaveRecordxml);
        btnUpdateRecord = (Button) view.findViewById(R.id.btnUpdateRecordxml);
        btnDeleteRecord = (Button) view.findViewById(R.id.btnDeleteRecordxml);
        btnResetForm = (Button) view.findViewById(R.id.btnResetFormxml);
        btnFirstRecord = (Button) view.findViewById(R.id.btnFirstRecordxml);
        btnPreviousRecord = (Button) view.findViewById(R.id.btnPreviousRecordxml);
        btnNextRecord = (Button) view.findViewById(R.id.btnNextRecordxml);
        btnLastRecord = (Button) view.findViewById(R.id.btnLastRecordxml);

        instantiateDb();
        fetchButtonClicks();
    }//end OnViewCreated

    private void instantiateDb(){
        DBAdapter msdb= new DBAdapter(getActivity().getApplicationContext(),MainActivity.szDbName, null);
        db=msdb.getWritableDatabase();
    }

    private void fetchButtonClicks() {
        btnSaveRecord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity(), "btnSaveRecord", Toast.LENGTH_SHORT).show();
                saveRecord(v);
            }
        });
        btnUpdateRecord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity(), "btnUpdateRecord", Toast.LENGTH_SHORT).show();
                updateRecord(v);
            }
        });
        btnDeleteRecord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity(), "btnDeleteRecord", Toast.LENGTH_SHORT).show();
                deleteRecord(v);
            }
        });
        btnResetForm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity(), "btnResetForm", Toast.LENGTH_SHORT).show();
                resetForm(v);
            }
        });
        btnFirstRecord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity(), "btnFirstRecord", Toast.LENGTH_SHORT).show();
                firstRecord(v);
            }
        });
        btnPreviousRecord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity(), "btnPreviousRecord", Toast.LENGTH_SHORT).show();
                previousRecord(v);
            }
        });
        btnNextRecord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity(), "btnNextRecord", Toast.LENGTH_SHORT).show();
                nextRecord(v);
            }
        });
        btnLastRecord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getActivity(), "btnLastRecord", Toast.LENGTH_SHORT).show();
                lastRecord(v);
            }
        });
    }//end fetchButtonClicks()

    /*******************************************************************
     * Save, Update, Delete, Clear functions for the four screen buttons
     *******************************************************************/
    public void saveRecord(View v) {//"SAVE RECORD"
        EditText edRowid = (EditText) getView().findViewById(R.id.edRowid);
        String szRowid = edRowid.getText().toString();
        EditText edSpecies = (EditText) getView().findViewById(R.id.edSpecies);
        String szSpecies = edSpecies.getText().toString();
        EditText edArea = (EditText) getView().findViewById(R.id.edArea);
        String szArea = edArea.getText().toString();
        EditText edSampler = (EditText) getView().findViewById(R.id.edSampler);
        String szSampler = edSampler.getText().toString();
        db.execSQL("INSERT INTO surveyDB (species, area, sampler) VALUES ('" + szSpecies + "','" + szArea + "','" + szSampler + "')");
        resetForm(v);
        Toast.makeText(getActivity(), "SAVE Successful." + szRowid + ".", Toast.LENGTH_LONG).show();
    }

    public void updateRecord(View v) {//"UPDATE RECORD"
        EditText edRowid = (EditText) getView().findViewById(R.id.edRowid);
        String szRowid = edRowid.getText().toString();
        EditText edSpecies = (EditText) getView().findViewById(R.id.edSpecies);
        String szSpecies = edSpecies.getText().toString();
        EditText edArea = (EditText) getView().findViewById(R.id.edArea);
        String szArea = edArea.getText().toString();
        EditText edSampler = (EditText) getView().findViewById(R.id.edSampler);
        String szSampler = edSampler.getText().toString();
        db.execSQL("UPDATE surveyDB SET species = '" + szSpecies + "', area ='" + szArea + "', sampler ='" + szSampler + "'WHERE rowid =" + szRowid);
        resetForm(v);
        Toast.makeText(getActivity(), "SAVE Successful." + szRowid + ".", Toast.LENGTH_LONG).show();
    }

    public void deleteRecord(View v) {  //"DELETE RECORD"
        EditText edRowid = (EditText) getView().findViewById(R.id.edRowid);
        String szRowid = edRowid.getText().toString();
        db.execSQL("DELETE FROM surveyDB WHERE rowid=" + szRowid);
        resetForm(v);
        Toast.makeText(getActivity(), "Count " + szRowid + " Removed Successfully", Toast.LENGTH_LONG).show();
    }

    public void resetForm(View v) {//aka "CLEAR FORM"
        ((EditText) getView().findViewById(R.id.edSampler)).setText("");
        ((EditText) getView().findViewById(R.id.edArea)).setText("");
        ((EditText) getView().findViewById(R.id.edSpecies)).setText("");
        ((EditText) getView().findViewById(R.id.edRowid)).setText("");
        ((EditText) getView().findViewById(R.id.edSpecies)).requestFocus();
    }

    public void firstRecord(View v) {//"FIRST RECORD. ||<<"
        Cursor c = db.rawQuery("SELECT * FROM surveyDB WHERE rowid = (SELECT MIN(rowid) FROM surveyDB)", null);
        if (c.moveToFirst()) {////a record exists (table is not blank)
            ((EditText) getView().findViewById(R.id.edRowid)).setText(c.getString(0));
            ((EditText) getView().findViewById(R.id.edSpecies)).setText(c.getString(1));
            ((EditText) getView().findViewById(R.id.edArea)).setText(c.getString(2));
            ((EditText) getView().findViewById(R.id.edSampler)).setText(c.getString(3));
        } else {//no record here...table is blank.
        }
    }

    public void previousRecord(View v) {//"PREVIOUS RECORD, <<"
        EditText edRowid = (EditText) getView().findViewById(R.id.edRowid);
        String szRowid = edRowid.getText().toString();
        if (szRowid.equals("")) {//if edRowid is blank, nothing happens.
        } else {
            int iCurrentRowid;
            iCurrentRowid = Integer.parseInt(edRowid.getText().toString());
            Cursor c = db.rawQuery("SELECT * FROM surveyDB WHERE rowid < " + iCurrentRowid + " ORDER BY rowid DESC LIMIT 1", null);
            if (c.moveToFirst()) {//a previous record exists
                //Toast.makeText(getApplicationContext(), "Event triggered. Value exists ", Toast.LENGTH_LONG).show();
                ((EditText) getView().findViewById(R.id.edRowid)).setText(c.getString(0));
                ((EditText) getView().findViewById(R.id.edSpecies)).setText(c.getString(1));
                ((EditText) getView().findViewById(R.id.edArea)).setText(c.getString(2));
                ((EditText) getView().findViewById(R.id.edSampler)).setText(c.getString(3));
            } else {//doesn't exist, no prior records...at tippy top of table.
                //Toast.makeText(getApplicationContext(), "Event triggered. Value is null. Do nothing. ", Toast.LENGTH_LONG).show();
            }
        }
        //Toast.makeText(getApplicationContext(), "Event triggered value is: "+szRowid+". Thanks.", Toast.LENGTH_LONG).show();
    }

    public void nextRecord(View v) {//"NEXT RECORD, >>"
        EditText edRowid = (EditText) getView().findViewById(R.id.edRowid);
        String szRowid = edRowid.getText().toString();
        if (szRowid.equals("")) {//if edRowid is blank, nothing happens.
        } else {
            int iCurrentRowid;
            iCurrentRowid = Integer.parseInt(edRowid.getText().toString());
            Cursor c = db.rawQuery("SELECT * FROM surveyDB WHERE rowid > " + iCurrentRowid + " ORDER BY rowid ASC LIMIT 1", null);
            if (c.moveToFirst()) {//a next record exists
                //Toast.makeText(getApplicationContext(), "Event triggered. Value exists ", Toast.LENGTH_LONG).show();
                ((EditText) getView().findViewById(R.id.edRowid)).setText(c.getString(0));
                ((EditText) getView().findViewById(R.id.edSpecies)).setText(c.getString(1));
                ((EditText) getView().findViewById(R.id.edArea)).setText(c.getString(2));
                ((EditText) getView().findViewById(R.id.edSampler)).setText(c.getString(3));
            } else {//doesn't exist, no prior records...at very bottom (max rowid) of table.
            }
        }
    }

    public void lastRecord(View v) {//"LAST RECORD, >>||"
        Cursor c = db.rawQuery("SELECT * FROM surveyDB WHERE rowid = (SELECT MAX(rowid) FROM surveyDB)", null);
        if (c.moveToFirst()) {//a record exists (table is not blank)
            ((EditText) getView().findViewById(R.id.edRowid)).setText(c.getString(0));
            ((EditText) getView().findViewById(R.id.edSpecies)).setText(c.getString(1));
            ((EditText) getView().findViewById(R.id.edArea)).setText(c.getString(2));
            ((EditText) getView().findViewById(R.id.edSampler)).setText(c.getString(3));
        } else {//no record here...table is blank.
        }
    }
    /***************************************************************************
     *                        End of pager button coding
     ***************************************************************************/
    public void getDetails(View v) {
        EditText edRowid = (EditText) getView().findViewById(R.id.edRowid);
        String szRowid = edRowid.getText().toString();
        Cursor c = db.query("surveyDB", new String[]{"species", "area", "sampler"}, "rowid=?", new String[]{szRowid}, null, null, null);

        if (c.moveToNext()) {
            ((EditText) getView().findViewById(R.id.edSpecies)).setText(c.getString(0));
            ((EditText) getView().findViewById(R.id.edArea)).setText(c.getString(1));
            ((EditText) getView().findViewById(R.id.edSampler)).setText(c.getString(2));
        } else {
            Toast.makeText(getActivity(), "Count record not found", Toast.LENGTH_LONG).show();
        }
    }

    public void queryAll(View v) {
        queryAll = db.rawQuery("select * from surveyDB", null);

        if (queryAll.moveToNext()) {
            ((EditText) getView().findViewById(R.id.edRowid)).setText(queryAll.getInt(0) + "");
            ((EditText) getView().findViewById(R.id.edSpecies)).setText(queryAll.getString(1));
            ((EditText) getView().findViewById(R.id.edArea)).setText(queryAll.getString(2));
            ((EditText) getView().findViewById(R.id.edSampler)).setText(queryAll.getString(3));
        } else {
            Toast.makeText(getActivity(), "No Rowid records found", Toast.LENGTH_LONG).show();
        }
    }//end queryAll()
} //end FirstFragment()