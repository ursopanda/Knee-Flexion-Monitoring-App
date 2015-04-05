package lv.edi.Database;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by ursopanda on 28/03/15.
 */
public class FlexionStats {
    private int id;
    private int flexion_value;
    private Timestamp flexion_time;
    Calendar calendar = Calendar.getInstance();

    public FlexionStats() {}

    public FlexionStats(int flexion_value) {
        super();
        this.flexion_value = flexion_value;
        //this.flexion_time = new java.sql.Timestamp(calendar.getTime().getTime());
        //this.flexion_time = flexion_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFlexion_value() {
        return flexion_value;
    }

    public void setFlexion_value(int flexion_value) {
        this.flexion_value = flexion_value;
    }

    public Timestamp getFlexion_time() {
        return flexion_time;
    }

    public void setFlexion_time(Timestamp flexion_time) {
        this.flexion_time = flexion_time;
    }
}
