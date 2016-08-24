package edu.hillel.library2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.hillel.library2.fragment.BookDescFragment;
import edu.hillel.library2.fragment.BookListFragment;

public class MainActivity
        extends AppCompatActivity
        implements BookListFragment.BookListFragmentListener {

    private static final String SELECTED_BOOK_ID = "selectedBookId";
    private long mSelectedBookId = 1L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        if (savedInstanceState != null) {
            mSelectedBookId = savedInstanceState.getLong(SELECTED_BOOK_ID, 1L);
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.flContainer, new BookListFragment(), "list")
                    .commit();
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.flList, new BookListFragment(), "list")
                    .replace(R.id.flDesc, BookDescFragment.newInstance(mSelectedBookId), "desc_fr")
                    .commit();
        }
    }

    @Override
    public void onItemClick(long bookId) {
        mSelectedBookId = bookId;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            getFragmentManager().beginTransaction()
                    .replace(R.id.flContainer, BookDescFragment.newInstance(bookId))
                    .addToBackStack("desc_tr")
                    .commit();
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Fragment fragment = getFragmentManager().findFragmentByTag("desc_fr");
            if (fragment != null) {
                ((BookDescFragment) fragment).setBookDesc(bookId);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(SELECTED_BOOK_ID, mSelectedBookId);
    }
}