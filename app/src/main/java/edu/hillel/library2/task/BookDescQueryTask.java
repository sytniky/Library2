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
public class BookDescQueryTask extends AsyncTask<Long, Void, Cursor> {

    private Context mContext;
    private BookDescQueryTaskListener mListener;

    public interface BookDescQueryTaskListener {
        void onSetBookDescCursor(Cursor cursor);
    }

    public BookDescQueryTask(Fragment fragment) {
        mContext = fragment.getActivity();
        if (fragment instanceof BookDescQueryTaskListener)
            mListener = (BookDescQueryTaskListener) fragment;
    }

    @Override
    protected Cursor doInBackground(Long... longs) {

        // for test
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long bookId = longs[0];
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String rawQuery = "SELECT * FROM " + DBContract.Book.TABLE_NAME
                + " INNER JOIN " + DBContract.Author.TABLE_NAME
                + " ON " + DBContract.Book.COLUMN_AUTHOR_ID + " = " + DBContract.Author.TABLE_NAME + "." + DBContract.Author._ID
                + " WHERE " + DBContract.Book.TABLE_NAME + "." + DBContract.Book._ID + " = " + String.valueOf(bookId);

        return database.rawQuery(rawQuery, null);
    }

    @Override
    protected void onPostExecute(Cursor cursor) {
        super.onPostExecute(cursor);
        mListener.onSetBookDescCursor(cursor);
    }
}
