package edu.hillel.library2.adapter;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import edu.hillel.library2.DBContract;
import edu.hillel.library2.R;
import edu.hillel.library2.task.BookListQueryTask;

/**
 * Created by yuriy on 23.08.16.
 */
public class BookListAdapter extends CursorAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    public BookListAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = mInflater.inflate(R.layout.book_list_item, viewGroup, false);
        setBookData(cursor, view);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        setBookData(cursor, view);
    }

    void setBookData(Cursor cursor, View view) {

        int coverId = cursor.getInt(cursor.getColumnIndex(DBContract.Book.COLUMN_COVER_ID));
        String name = cursor.getString(cursor.getColumnIndex(DBContract.Book.COLUMN_NAME));
        String author = cursor.getString(cursor.getColumnIndex(DBContract.Author.COLUMN_FIRST_NAME))
                + " " + cursor.getString(cursor.getColumnIndex(DBContract.Author.COLUMN_SECOND_NAME));

        ImageView ivCover = (ImageView) view.findViewById(R.id.ivCover);
        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvAuthor = (TextView) view.findViewById(R.id.tvAuthor);

        ivCover.setImageResource(coverId);
        tvName.setText(name);
        tvAuthor.setText(author);
    }

    public void filterByCategoryId(Fragment fragment, long categoryId) {
        new BookListQueryTask(fragment).execute(categoryId);
    }
}
