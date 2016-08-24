package edu.hillel.library2.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import edu.hillel.library2.DBContract;
import edu.hillel.library2.R;
import edu.hillel.library2.adapter.BookListAdapter;
import edu.hillel.library2.task.BookListQueryTask;
import edu.hillel.library2.task.CategoryQueryTask;

/**
 * Created by yuriy on 24.08.16.
 */
public class BookListFragment extends Fragment
        implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener, BookListQueryTask.BookListQueryTaskListener, CategoryQueryTask.CategoryQueryTaskListener {

    private BookListFragmentListener mListener;
    private Spinner mSpCategory;
    private SimpleCursorAdapter mCategoryAdapter;
    private ProgressBar mProgressBar;
    private ListView mLvBooks;
    private BookListAdapter mBookListAdapter;

    public interface BookListFragmentListener {
        void onItemClick(long bookId);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof BookListFragmentListener)
            mListener = (BookListFragmentListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCategoryAdapter = new SimpleCursorAdapter(
                getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                null,
                new String[]{DBContract.Category.COLUMN_NAME},
                new int[]{android.R.id.text1},
                CursorAdapter.FLAG_AUTO_REQUERY
        );

        mBookListAdapter = new BookListAdapter(getActivity(), null, true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_book_list, container, false);

        mSpCategory = (Spinner) v.findViewById(R.id.spCategory);
        mProgressBar = (ProgressBar) v.findViewById(R.id.pbProgress);
        mLvBooks = (ListView) v.findViewById(R.id.lvBooks);

        mSpCategory.setAdapter(mCategoryAdapter);
        mLvBooks.setAdapter(mBookListAdapter);

        mSpCategory.setOnItemSelectedListener(this);
        mLvBooks.setOnItemClickListener(this);

        mProgressBar.setVisibility(View.VISIBLE);
        new CategoryQueryTask(this).execute();

        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long bookId) {
        Log.d("<BOOK_ID>", String.valueOf(bookId));
        mListener.onItemClick(bookId);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long categoryId) {
        mProgressBar.setVisibility(View.VISIBLE);
        mBookListAdapter.filterByCategoryId(this, categoryId);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void onSetCategoryCursor(Cursor cursor) {
        mCategoryAdapter.changeCursor(cursor);
        mSpCategory.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    public void onSetBookListCursor(Cursor cursor) {
        mBookListAdapter.changeCursor(cursor);
        mLvBooks.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }
}
