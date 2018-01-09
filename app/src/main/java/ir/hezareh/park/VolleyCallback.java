package ir.hezareh.park;

import java.util.List;

import ir.hezareh.park.models.ModelComponent;

/**
 * Created by rf on 23/12/2017.
 */

public interface VolleyCallback {
    void onSuccessResponse(List<ModelComponent> result);
}
