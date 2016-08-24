package edu.hillel.library2;

import android.provider.BaseColumns;

/**
 * Created by yuriy on 22.08.16.
 */
public final class DBContract {

    public static final String DB_NAME = "library2";

    public static final class Category implements BaseColumns {
        public static final String TABLE_NAME = "category";
        public static final String COLUMN_NAME = "name";
        public static final String[] DEFAULT_PROJECTION = {_ID, COLUMN_NAME};
    }

    public static final class Author implements BaseColumns {
        public static final String TABLE_NAME = "author";
        public static final String COLUMN_FIRST_NAME = "first_name";
        public static final String COLUMN_SECOND_NAME = "second_name";
        public static final String[] DEFAULT_PROJECTION = {_ID, COLUMN_FIRST_NAME, COLUMN_SECOND_NAME};
    }

    public static final class Book implements BaseColumns {
        public static final String TABLE_NAME = "book";
        public static final String COLUMN_CATEGORY_ID = "category_id";
        public static final String COLUMN_AUTHOR_ID = "author_id";
        public static final String COLUMN_COVER_ID = "cover_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_ANNOTATION = "annotation";
        public static final String[] DEFAULT_PROJECTION = {_ID, COLUMN_CATEGORY_ID, COLUMN_AUTHOR_ID, COLUMN_COVER_ID, COLUMN_NAME, COLUMN_ANNOTATION};
    }
}
