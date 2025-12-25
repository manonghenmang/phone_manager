package org.example.phonemanager.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PhoneDatabaseHelper extends SQLiteOpenHelper {
    // 数据库名称
    private static final String DATABASE_NAME = "phone_manager.db";
    // 数据库版本
    private static final int DATABASE_VERSION = 2;

    // 手机信息表
    public static final String TABLE_PHONE_INFO = "phone_info";
    
    // 表字段
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PHONE_ID = "phone_id";
    public static final String COLUMN_OWNER = "owner";
    public static final String COLUMN_BRAND = "brand";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_BORROWER = "borrower";
    public static final String COLUMN_LENDER = "lender";
    public static final String COLUMN_LEND_DATE = "lend_date";
    public static final String COLUMN_RETURN_DATE = "return_date";
    public static final String COLUMN_RETURN_PERSON = "return_person";

    // 创建表的SQL语句
    private static final String CREATE_TABLE_PHONE_INFO = "CREATE TABLE " + TABLE_PHONE_INFO + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PHONE_ID + " TEXT UNIQUE NOT NULL, " +
            COLUMN_OWNER + " TEXT, " +
            COLUMN_BRAND + " TEXT, " +
            COLUMN_MODEL + " TEXT, " +
            COLUMN_STATUS + " TEXT, " +
            COLUMN_BORROWER + " TEXT, " +
            COLUMN_LENDER + " TEXT, " +
            COLUMN_LEND_DATE + " INTEGER, " +
            COLUMN_RETURN_DATE + " INTEGER, " +
            COLUMN_RETURN_PERSON + " TEXT" +
            ");";

    public PhoneDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建数据库表
        db.execSQL(CREATE_TABLE_PHONE_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 数据库升级时的操作
        if (oldVersion < newVersion) {
            // 添加owner字段
            if (oldVersion < 2) {
                db.execSQL("ALTER TABLE " + TABLE_PHONE_INFO + " ADD COLUMN " + COLUMN_OWNER + " TEXT");
            }
        }
    }
}