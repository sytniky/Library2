package edu.hillel.library2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yuriy on 22.08.16.
 */
public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase mDb;

    public DBHelper(Context context) {
        super(context, DBContract.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + DBContract.Category.TABLE_NAME + " ("
                + DBContract.Category._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DBContract.Category.COLUMN_NAME + " TEXT);");

        sqLiteDatabase.execSQL("CREATE TABLE " + DBContract.Author.TABLE_NAME + " ("
                + DBContract.Author._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DBContract.Author.COLUMN_FIRST_NAME + " TEXT,"
                + DBContract.Author.COLUMN_SECOND_NAME + " TEXT);");

        sqLiteDatabase.execSQL("CREATE TABLE " + DBContract.Book.TABLE_NAME + " ("
                + DBContract.Book._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DBContract.Book.COLUMN_CATEGORY_ID + " INTEGER,"
                + DBContract.Book.COLUMN_AUTHOR_ID + " INTEGER,"
                + DBContract.Book.COLUMN_COVER_ID + " INTEGER,"
                + DBContract.Book.COLUMN_NAME + " TEXT,"
                + DBContract.Book.COLUMN_ANNOTATION + " TEXT,"
                + "FOREIGN KEY (" + DBContract.Book.COLUMN_CATEGORY_ID + ") REFERENCES "
                + DBContract.Category.TABLE_NAME + "(" + DBContract.Category._ID + ") ON UPDATE CASCADE ON DELETE CASCADE,"
                + "FOREIGN KEY (" + DBContract.Book.COLUMN_AUTHOR_ID + ") REFERENCES "
                + DBContract.Author.TABLE_NAME + "(" + DBContract.Author._ID + ") ON UPDATE CASCADE ON DELETE CASCADE);");

        mDb = sqLiteDatabase;

        long categoryId1 = insertCategory("Programming");
        long categoryId2 = insertCategory("Detective");
        long categoryId3 = insertCategory("Novel");
        long categoryId4 = insertCategory("Fantastic");

        long authorId1 = insertAuthor("Bruce", "Eckel");
        long authorId2 = insertAuthor("Eric", "Freeman");
        long authorId3 = insertAuthor("Arthur", "Conan Doyle");

        insertBook(categoryId1, authorId1, R.drawable.ic_thincking_in_java, "Thinking in Java", "Thinking in Java should be read cover to cover by every Java programmer, then kept close at hand for frequent reference. The exercises are challenging, and the chapter on Collections is superb! Not only did this book help me to pass the Sun Certified Java Programmer exam; it’s also the first book I turn to whenever I have a Java question.");
        insertBook(categoryId1, authorId2, R.drawable.ic_head_first_design_patterns, "Head First Design Patterns", "At any given moment, somewhere in the world someone struggles with the same software design problems you have. You know you don't want to reinvent the wheel (or worse, a flat tire), so you look to Design Patterns--the lessons learned by those who've faced the same problems. With Design Patterns, you get to take advantage of the best practices and experience of others, so that you can spend your time on...something else. Something more challenging. Something more complex. Something more fun.");
        insertBook(categoryId2, authorId3, R.drawable.ic_the_new_annotated_sherlock_holmes, "The Adventures of Sherlock Holmes", "Collects two volumes of Doyle's short stories starring Sherlock Holmes, each of which is annotated to provide definitions and further explanations of Sherlock's theories, as well as literary and cultural details about Victorian society.");
        insertBook(categoryId1, authorId1, R.drawable.ic_thinking_in_c_plus_plus, "Thinking in C++", "In the first edition ofThinking in C++, Bruce Eckel synthesized years of C++ teaching and programming experience into a beautifully structured course in making the most of the language. The book became an instant classic, winning the 1995Software Development Jolt ColaAward for best book of the year. Now, inThinking in C++, Volume I, Second Edition, Eckel has thoroughly rewritten his masterpiece to reflect all the changes introduced in C++ by the final ANSI/ISO C++ standard. Every page has been revisited and rethought, with many new examples and exercises throughout -- all with a single goal: to help you understand C++ \"down to the bare metal,\" so you can solve virtually any development problem you encounter. Eckel begins with a detailed look at objects and their rationale, then shows how C++ programs can be constructed from off-the-shelf object libraries. This edition includes a new, chapter-length overview of the C features that are used in C++ -- plus a new CD-ROM containing an outstanding C seminar that covers all the foundations developers need before they can truly take advantage of C++. Eckel next introduces key object-oriented techniques such as data abstraction and implementation hiding. He then walks through initialization and cleanup; function overloading and default arguments; constants; inline functions; name control; references and the copy constructor; operator overloading; and more. There are chapters on dynamic object creation; inheritance and composition; polymorphism and virtual functions, and templates. (Bonus coverage of string, templates, and the Standard Template Library, can be found at Eckel's web site.) Every chapter contains many modular, to-the-point examples, plus exercises based on Eckel's extensive experience teaching C++ seminars. Put simply, Eckel has made an outstanding book on C++ even better. For all C++ programmers, and for programmers experienced in other languages who want to strengthen their C++ and object development skills.");
        insertBook(categoryId1, authorId1, R.drawable.ic_mastering_python_design_patterns, "Mastering Python Design Patterns", "This book is for Python programmers with an intermediate background and an interest in design patterns implemented in idiomatic Python.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private long insertCategory(String name) {
        ContentValues cv = new ContentValues();
        cv.put(DBContract.Category.COLUMN_NAME, name);
        return mDb.insert(DBContract.Category.TABLE_NAME, null, cv);
    }

    private long insertAuthor(String firstName, String secondName) {
        ContentValues cv = new ContentValues();
        cv.put(DBContract.Author.COLUMN_FIRST_NAME, firstName);
        cv.put(DBContract.Author.COLUMN_SECOND_NAME, secondName);
        return mDb.insert(DBContract.Author.TABLE_NAME, null, cv);
    }

    private long insertBook(long categoryId,
                            long authorId,
                            int coverId,
                            String name,
                            String annotation) {

        ContentValues cv = new ContentValues();
        cv.put(DBContract.Book.COLUMN_CATEGORY_ID, categoryId);
        cv.put(DBContract.Book.COLUMN_AUTHOR_ID, authorId);
        cv.put(DBContract.Book.COLUMN_COVER_ID, coverId);
        cv.put(DBContract.Book.COLUMN_NAME, name);
        cv.put(DBContract.Book.COLUMN_ANNOTATION, annotation);
        return mDb.insert(DBContract.Book.TABLE_NAME, null, cv);
    }
}