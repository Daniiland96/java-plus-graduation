package ewm.client;

import ewm.ParamDto;
import ewm.ParamHitDto;
import ewm.ViewStats;

import java.util.List;

public interface StatClient {
    void hit(ParamHitDto paramHitDto);

    List<ViewStats> getStat(ParamDto paramDto);
}