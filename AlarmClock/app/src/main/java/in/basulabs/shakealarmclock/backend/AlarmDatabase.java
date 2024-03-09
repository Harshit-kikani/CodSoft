
package in.basulabs.shakealarmclock.backend;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {AlarmEntity.class, RepeatEntity.class}, version = 2,
	exportSchema = false)
@TypeConverters({Convertors.class})
public abstract class AlarmDatabase extends RoomDatabase {

	private static AlarmDatabase instance;

	static final Migration MIGRATION_1_2 = new Migration(1, 2) {
		@Override
		public void migrate(@NonNull SupportSQLiteDatabase database) {
			database.execSQL(
				"ALTER TABLE alarm_entity ADD COLUMN alarmMessage VARCHAR(50)");
		}
	};

	public static synchronized AlarmDatabase getInstance(@NonNull Context context) {

		if (instance == null) {

			instance = Room.databaseBuilder(
					Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
						? context.createDeviceProtectedStorageContext()
						: context.getApplicationContext(),
					AlarmDatabase.class, ConstantsAndStatics.DATABASE_NAME)
				.addMigrations(MIGRATION_1_2)
				.fallbackToDestructiveMigrationOnDowngrade()
				.build();
		}
		return instance;
	}

	public abstract AlarmDAO alarmDAO();

}
