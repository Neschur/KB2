package by.siarhei.kb2.app.server.entities;

import by.siarhei.kb2.app.R;
import by.siarhei.kb2.app.server.models.MapPoint;

public class Metro extends EntityImpl {
    public Metro(MapPoint point) {
        super();
    }

    @Override
    public int getID() {
        return R.drawable.metro;
    }
}