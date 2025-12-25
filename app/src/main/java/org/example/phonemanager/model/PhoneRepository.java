package org.example.phonemanager.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhoneRepository {
    private PhoneDatabaseHelper dbHelper;

    public PhoneRepository(Context context) {
        this.dbHelper = new PhoneDatabaseHelper(context);
    }

    // 添加手机
    public long addPhone(Phone phone) {
        Log.d("PhoneRepository", "Adding phone to database: PhoneID=" + phone.getPhoneId());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PhoneDatabaseHelper.COLUMN_PHONE_ID, phone.getPhoneId());
        values.put(PhoneDatabaseHelper.COLUMN_OWNER, phone.getOwner());
        values.put(PhoneDatabaseHelper.COLUMN_BRAND, phone.getBrand());
        values.put(PhoneDatabaseHelper.COLUMN_MODEL, phone.getModel());
        values.put(PhoneDatabaseHelper.COLUMN_STATUS, phone.getStatus());

        Log.d("PhoneRepository", "Inserting into table: " + PhoneDatabaseHelper.TABLE_PHONE_INFO);
        long id = db.insert(PhoneDatabaseHelper.TABLE_PHONE_INFO, null, values);
        Log.d("PhoneRepository", "Insert result: ID=" + id);
        db.close();
        return id;
    }

    // 根据手机ID查找手机
    public Phone getPhoneByPhoneId(String phoneId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Phone phone = null;

        Cursor cursor = db.query(
                PhoneDatabaseHelper.TABLE_PHONE_INFO,
                null,
                PhoneDatabaseHelper.COLUMN_PHONE_ID + "=?",
                new String[]{phoneId},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            phone = cursorToPhone(cursor);
            cursor.close();
        }

        db.close();
        return phone;
    }

    // 借出手机
    public boolean lendPhone(String phoneId, String borrower, String lender) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PhoneDatabaseHelper.COLUMN_STATUS, "lent");
        values.put(PhoneDatabaseHelper.COLUMN_BORROWER, borrower);
        values.put(PhoneDatabaseHelper.COLUMN_LENDER, lender);
        values.put(PhoneDatabaseHelper.COLUMN_LEND_DATE, new Date().getTime());
        values.put(PhoneDatabaseHelper.COLUMN_RETURN_DATE, (Long) null);
        values.put(PhoneDatabaseHelper.COLUMN_RETURN_PERSON, (String) null);

        int rowsAffected = db.update(
                PhoneDatabaseHelper.TABLE_PHONE_INFO,
                values,
                PhoneDatabaseHelper.COLUMN_PHONE_ID + "=?",
                new String[]{phoneId}
        );

        db.close();
        return rowsAffected > 0;
    }

    // 归还手机
    public boolean returnPhone(String phoneId, String returnPerson) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PhoneDatabaseHelper.COLUMN_STATUS, "available");
        values.put(PhoneDatabaseHelper.COLUMN_RETURN_DATE, new Date().getTime());
        values.put(PhoneDatabaseHelper.COLUMN_RETURN_PERSON, returnPerson);

        int rowsAffected = db.update(
                PhoneDatabaseHelper.TABLE_PHONE_INFO,
                values,
                PhoneDatabaseHelper.COLUMN_PHONE_ID + "=?",
                new String[]{phoneId}
        );

        db.close();
        return rowsAffected > 0;
    }

    // 获取所有手机
    public List<Phone> getAllPhones() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Phone> phones = new ArrayList<>();

        Cursor cursor = db.query(
                PhoneDatabaseHelper.TABLE_PHONE_INFO,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Phone phone = cursorToPhone(cursor);
                phones.add(phone);
                // 调试：输出数据库中的手机信息
                Log.d("PhoneRepository", "Database phone: ID=" + phone.getId() + ", PhoneID=" + phone.getPhoneId() + ", Brand=" + phone.getBrand() + ", Model=" + phone.getModel() + ", Status=" + phone.getStatus());
            }
            cursor.close();
        }

        db.close();
        return phones;
    }

    // 获取指定状态的手机
    public List<Phone> getPhonesByStatus(String status) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Phone> phones = new ArrayList<>();

        Cursor cursor = db.query(
                PhoneDatabaseHelper.TABLE_PHONE_INFO,
                null,
                PhoneDatabaseHelper.COLUMN_STATUS + "=?",
                new String[]{status},
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                phones.add(cursorToPhone(cursor));
            }
            cursor.close();
        }

        db.close();
        return phones;
    }

    // 将Cursor转换为Phone对象
    private Phone cursorToPhone(Cursor cursor) {
        Phone phone = new Phone();
        phone.setId(cursor.getInt(cursor.getColumnIndexOrThrow(PhoneDatabaseHelper.COLUMN_ID)));
        phone.setPhoneId(cursor.getString(cursor.getColumnIndexOrThrow(PhoneDatabaseHelper.COLUMN_PHONE_ID)));
        phone.setOwner(cursor.getString(cursor.getColumnIndexOrThrow(PhoneDatabaseHelper.COLUMN_OWNER)));
        phone.setBrand(cursor.getString(cursor.getColumnIndexOrThrow(PhoneDatabaseHelper.COLUMN_BRAND)));
        phone.setModel(cursor.getString(cursor.getColumnIndexOrThrow(PhoneDatabaseHelper.COLUMN_MODEL)));
        phone.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(PhoneDatabaseHelper.COLUMN_STATUS)));
        phone.setBorrower(cursor.getString(cursor.getColumnIndexOrThrow(PhoneDatabaseHelper.COLUMN_BORROWER)));
        phone.setLender(cursor.getString(cursor.getColumnIndexOrThrow(PhoneDatabaseHelper.COLUMN_LENDER)));

        int lendDateColumnIndex = cursor.getColumnIndexOrThrow(PhoneDatabaseHelper.COLUMN_LEND_DATE);
        if (!cursor.isNull(lendDateColumnIndex)) {
            long lendDateMillis = cursor.getLong(lendDateColumnIndex);
            phone.setLendDate(new Date(lendDateMillis));
        }

        int returnDateColumnIndex = cursor.getColumnIndexOrThrow(PhoneDatabaseHelper.COLUMN_RETURN_DATE);
        if (!cursor.isNull(returnDateColumnIndex)) {
            long returnDateMillis = cursor.getLong(returnDateColumnIndex);
            phone.setReturnDate(new Date(returnDateMillis));
        }

        phone.setReturnPerson(cursor.getString(cursor.getColumnIndexOrThrow(PhoneDatabaseHelper.COLUMN_RETURN_PERSON)));

        return phone;
    }

    // 根据手机ID删除手机
    public boolean deletePhone(String phoneId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsAffected = db.delete(
                PhoneDatabaseHelper.TABLE_PHONE_INFO,
                PhoneDatabaseHelper.COLUMN_PHONE_ID + "=?",
                new String[]{phoneId}
        );
        db.close();
        return rowsAffected > 0;
    }

    // 更新手机信息
    public boolean updatePhone(Phone phone) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PhoneDatabaseHelper.COLUMN_OWNER, phone.getOwner());
        values.put(PhoneDatabaseHelper.COLUMN_BRAND, phone.getBrand());
        values.put(PhoneDatabaseHelper.COLUMN_MODEL, phone.getModel());
        values.put(PhoneDatabaseHelper.COLUMN_STATUS, phone.getStatus());
        values.put(PhoneDatabaseHelper.COLUMN_BORROWER, phone.getBorrower());
        values.put(PhoneDatabaseHelper.COLUMN_LENDER, phone.getLender());
        values.put(PhoneDatabaseHelper.COLUMN_LEND_DATE, phone.getLendDate() != null ? phone.getLendDate().getTime() : null);
        values.put(PhoneDatabaseHelper.COLUMN_RETURN_DATE, phone.getReturnDate() != null ? phone.getReturnDate().getTime() : null);
        values.put(PhoneDatabaseHelper.COLUMN_RETURN_PERSON, phone.getReturnPerson());

        int rowsAffected = db.update(
                PhoneDatabaseHelper.TABLE_PHONE_INFO,
                values,
                PhoneDatabaseHelper.COLUMN_PHONE_ID + "=?",
                new String[]{phone.getPhoneId()}
        );
        db.close();
        return rowsAffected > 0;
    }
}