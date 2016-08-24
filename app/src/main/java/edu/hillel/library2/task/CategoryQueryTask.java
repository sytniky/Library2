package edu.hillel.library2.task;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import edu.hillel.library2.DBContract;
import edu.hillel.library2.DBHelper;

/**
 * Created by yuriy on 24.08.16.
 */
public class CategoryQueryTask extends AsyncTask<Void, Void, Cursor> {

    private Context mContext;
    private CategoryQueryTaskListener mListener;

    public interface CategoryQueryTaskListener {
        void onSetCategoryCursor(Cursor cursor);
    }

    public CategoryQueryTask(Fragment fragment) {
        mContext = fragment.getActivity();
        if (fragment instanceof CategoryQueryTaskListener) {
            mListener = (CategoryQueryTaskListener) fragment;
        }
    }

    @Override
    protected Cursor doInBackground(Void... voids) {

        // for test
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DBHelper dbHelper = new DBHelper(mContext);

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        return database.query(DBContract.Category.TABLE_NAME,
                DBContract.Category.DEFAULT_PROJECTION,
                null,
                null,
                null,
                null,
                null);
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        mListener.onSetCategoryCursor(cursor);
    }
}
