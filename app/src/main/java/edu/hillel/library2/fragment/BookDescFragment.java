package edu.hillel.library2.fragment;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import edu.hillel.library2.DBContract;
import edu.hillel.library2.R;
import edu.hillel.library2.task.BookDescQueryTask;

/**
 * Created by yuriy on 24.08.16.
 */
public class BookDescFragment extends Fragment implements BookDescQueryTask.BookDescQueryTaskListener {

    private static final String ARG_BOOK_ID = "bookId";

    private long mBookId;
    private ProgressBar mProgressBar;
    private ImageView mIvCover;
    private TextView mTvName;
    private TextView mTvAuthor;
    private TextView mTvAnnotation;

    public BookDescFragment() {}

    public static BookDescFragment newInstance(long bookId) {
        Bundle args = new Bundle();
        args.putLong(ARG_BOOK_ID, bookId);
        BookDescFragment fragment = new BookDescFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mBookId = getArguments().getLong(ARG_BOOK_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_desc, container, false);

        mProgressBar = (ProgressBar) v.findViewById(R.id.pbProgress);
        mIvCover = (ImageView) v.findViewById(R.id.ivCover);
        mTvName = (TextView) v.findViewById(R.id.tvName);
        mTvAuthor = (TextView) v.findViewById(R.id.tvAuthor);
        mTvAnnotation = (TextView) v.findViewById(R.id.tvAnnotation);

        setBookDesc(mBookId);

        return v;
    }

    public void setBookDesc(long bookId) {
        mProgressBar.setVisibility(View.VISIBLE);
        new BookDescQueryTask(this).execute(bookId);
    }

    @Override
    public void onSetBookDescCursor(Cursor cursor) {

        while (cursor.moveToNext()) {

            int coverId = cursor.getInt(cursor.getColumnIndex(DBContract.Book.COLUMN_COVER_ID));
            String name = cursor.getString(cursor.getColumnIndex(DBContract.Book.COLUMN_NAME));
            String author = cursor.getString(cursor.getColumnIndex(DBContract.Author.COLUMN_FIRST_NAME))
                    + " " + cursor.getString(cursor.getColumnIndex(DBContract.Author.COLUMN_SECOND_NAME));
            String annotation = cursor.getString(cursor.getColumnIndex(DBContract.Book.COLUMN_ANNOTATION));

            mIvCover.setImageResource(coverId);
            mTvName.setText(name);
            mTvAuthor.setText(author);
            mTvAnnotation.setText(annotation);
        }

        mProgressBar.setVisibility(View.GONE);
    }
}
