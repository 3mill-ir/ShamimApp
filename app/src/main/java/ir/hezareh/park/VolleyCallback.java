package ir.hezareh.park;

import java.util.List;

import ir.hezareh.park.models.ModelComponent;
import ir.hezareh.park.models.NewsDetails;

/**
 * Created by rf on 23/12/2017.
 */

public interface VolleyCallback {
    void onSuccessResponse(List<ModelComponent> result);

    void onSuccessResponseNewsDetails(NewsDetails newsDetails);
}

