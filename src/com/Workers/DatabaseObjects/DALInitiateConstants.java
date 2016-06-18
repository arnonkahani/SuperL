package com.Workers.DatabaseObjects;

/**
 * Created by Barakmen on 5/5/2016.
 */
public class DALInitiateConstants {


    private static final String START_CREATING = "CREATE TABLE IF NOT EXISTS";

    protected static final String TABLE_Worker = "Worker";
    protected static final String TABLE_User = "User";
    protected static final String TABLE_WorkerAbilities = "WorkerAbilities";
    protected static final String TABLE_WorkerInShifts = "WorkerInShifts";
    protected static final String TABLE_ShiftManager = "ShiftManager";
    protected static final String TABLE_WorkerSchedule = "WorkerSchedule";
    protected static final String TABLE_Driver = "Driver";
    protected static final String TABLE_ShiftsHistory = "ShiftsHistory";
    protected static final String TABLE_WeeklyShift = "WeeklyShift";

    protected static final String CREATE_Worker = START_CREATING + " `Worker` (\n" +
            "\t`ID`\tTEXT,\n" +
            "\t`BankNO`\tTEXT NOT NULL UNIQUE,\n" +
            "\t`EmploymentDate`\tTEXT NOT NULL,\n" +
            "\t`EmploymentTerms`\tTEXT,\n" +
            "\t`Name`\tTEXT NOT NULL,\n" +
            "\tPRIMARY KEY(ID)\n" +
            ");";

    protected static final String CREATE_User = START_CREATING + " `User` (\n" +
            "\t`WID`\tTEXT,\n" +
            "\t`Username`\tTEXT NOT NULL UNIQUE,\n" +
            "\t`Password`\tTEXT NOT NULL,\n" +
            "\tPRIMARY KEY(WID),\n" +
            "\tFOREIGN KEY(WID) REFERENCES `Worker`(`ID`) ON UPDATE CASCADE ON DELETE CASCADE\n" +
            ");";

    protected static final String CREATE_WorkerAbilities = START_CREATING + " `WorkerInShifts` (\n" +
            "\t`WID`\tTEXT NOT NULL,\n" +
            "\t`Day`\tTEXT NOT NULL CHECK(Day IN ( 'Sunday' , 'Monday' , 'Tuesday' , 'Wednesday' , 'Thursday' , 'Friday' , 'Saturday' )),\n" +
            "\t`Type`\tTEXT NOT NULL,\n" +
            "\t`Date`\tTEXT NOT NULL,\n" +
            "\t`Ability`\tTEXT NOT NULL,\n" +
            "\tPRIMARY KEY(WID,Day,Type),\n" +
            "\tFOREIGN KEY(WID) REFERENCES `Worker`(`ID`) ON UPDATE CASCADE ON DELETE CASCADE\n" +
            ");";

    protected static final String CREATE_WorkerInShifts = START_CREATING + " `WorkerAbilities` (\n" +
            "\t`WID`\tTEXT NOT NULL,\n" +
            "\t`Ability`\tTEXT NOT NULL,\n" +
            "\tPRIMARY KEY(WID,Ability)\n" +
            "\tFOREIGN KEY(WID) REFERENCES `Worker`(`ID`) ON UPDATE CASCADE ON DELETE CASCADE" +
            ");";

    protected static final String CREATE_ShiftManager = START_CREATING + " `ShiftManager` (\n" +
            "\t`MID`\tTEXT NOT NULL,\n" +
            "\t`Day`\tTEXT NOT NULL CHECK(Day IN ( 'Sunday' , 'Monday' , 'Tuesday' , 'Wednesday' , 'Thursday' , 'Friday' , 'Saturday' )),\n" +
            "\t`Type`\tTEXT NOT NULL,\n" +
            "\t`Date`\tTEXT NOT NULL,\n" +
            "\t`Ability`\tTEXT NOT NULL,\n" +
            "\tPRIMARY KEY(MID,Day,Type),\n" +
            "\tFOREIGN KEY(MID) REFERENCES `Worker`(`ID`) ON UPDATE CASCADE ON DELETE CASCADE\n" +
            ");";

    protected static final String CREATE_WorkerSchedule = START_CREATING + " `WorkerSchedule` (\n" +
            "\t`WID`\tTEXT NOT NULL,\n" +
            "\t`Day`\tTEXT NOT NULL CHECK(Day IN ( 'Sunday' , 'Monday' , 'Tuesday' , 'Wednesday' , 'Thursday' , 'Friday' , 'Saturday' )),\n" +
            "\t`Type`\tTEXT NOT NULL,\n" +
            "\tPRIMARY KEY(WID,Day,Type),\n" +
            "\tFOREIGN KEY(WID) REFERENCES `Worker`(`ID`) ON UPDATE CASCADE ON DELETE CASCADE\n" +
            ");";

    protected static final String CREATE_Driver = START_CREATING + " `Driver` (\n" +
            "\t`WID`\tTEXT NOT NULL,\n" +
            "\t`DriverLicence`\tTEXT NOT NULL CHECK(DriverLicence IN ( 'A' , 'B' , 'C' )),\n" +
            "\tPRIMARY KEY(WID),\n" +
            "\tFOREIGN KEY(WID) REFERENCES `Worker`(`ID`) ON UPDATE CASCADE ON DELETE CASCADE\n" +
            ");";

    protected static final String CREATE_ShiftsHistory = START_CREATING + " `ShiftsHistory` (\n" +
            "\t`WID`\tTEXT NOT NULL,\n" +
            "\t`Day`\tTEXT NOT NULL,\n" +
            "\t`Type`\tTEXT NOT NULL,\n" +
            "\t`Date`\tTEXT NOT NULL,\n" +
            "\t`Ability`\tTEXT NOT NULL,\n" +
            "\tPRIMARY KEY(WID,Day,Type)\n" +
            ");";

    protected static final String CREATE_WeeklyShift = START_CREATING + " `WeeklyShift` (\n" +
            "\t`WID`\tTEXT NOT NULL,\n" +
            "\t`Day`\tTEXT NOT NULL,\n" +
            "\t`Type`\tTEXT NOT NULL,\n" +
            "\t`Ability`\tTEXT NOT NULL,\n" +
            "\tPRIMARY KEY(WID,Day,Type)\n" +
            ");";

    protected static final String Trigger_ShiftsHistory = ""+
            "CREATE TRIGGER IF NOT EXISTS update_" + TABLE_ShiftsHistory + " " +
            "AFTER INSERT ON " + TABLE_WorkerInShifts + " FOR EACH ROW " +
            "BEGIN " +
            "INSERT INTO  " + TABLE_ShiftsHistory + "(WID,Day,Type,Date,Ability) " +
            "VALUES (NEW.WID,NEW.Day,NEW.Type,NEW.Date,NEW.Ability); " +
             "END;";

    protected static final String[] ALL_CREATE_QUARIES = {
            CREATE_Worker,
            CREATE_User,
            CREATE_WorkerAbilities,
            CREATE_WorkerInShifts,
            CREATE_ShiftManager,
            CREATE_WorkerSchedule,
            CREATE_Driver,
            CREATE_ShiftsHistory,
            CREATE_WeeklyShift,
            Trigger_ShiftsHistory
    };

}
