package com.example.yzeng.Week3AssignYixin.data.source.RoomDBA;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database (entities = {TodoNoteRoom.class},version = 1)
public abstract class TodoRoomDataBase extends RoomDatabase{
    public abstract TodoDao TodoDao();
    private static TodoRoomDataBase INSTANCE;

    public static TodoRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TodoRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TodoRoomDataBase.class, "TODO_ROOM_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
