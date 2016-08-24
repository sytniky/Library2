package edu.hillel.library2.task;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.content.res.TypedArrayUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import edu.hillel.library2.DBContract;
import edu.hillel.library2.DBHelper;

/**
 * Created by yuriy on 23.08.16.
 */
public class BookListQueryTask extends AsyncTask<Long, Void, Cursor> {

    private Context mContext;
    private BookListQueryTaskListener mListener;

    public interface BookListQueryTaskListener {
        void onSetBookListCursor(Cursor cursor);
    }

    public BookListQueryTask(Fragment fragment) {
        mContext = fragment.getActivity();
        if (fragment instanceof BookListQueryTaskListener)
            mListener = (BookListQueryTaskListener) fragment;
    }

    @Override
    protected Cursor doInBackground(Long... longs) {

        // for test
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long categoryId = longs[0];
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String rawQuery = "SELECT * FROM " + DBContract.Book.TABLE_NAME
                + " INNER JOIN " + DBContract.Author.TABLE_NAME
                + " ON " + DBContract.Book.COLUMN_AUTHOR_ID + " = " + DBContract.Author.TABLE_NAME + "." + DBContract.Author._ID
                + " WHERE " + DBContract.Book.COLUMN_CATEGORY_ID + " = " + String.valueOf(categoryId);

        return database.rawQuery(rawQuery, null);
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        mListener.onSetBookListCursor(cursor);
    }
}
