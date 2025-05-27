package ewm.dto;

import ewm.dto.request.RequestStatus;
import lombok.Getter;

import java.util.List;

@Getter
public class EventRequestStatusUpdateRequest {
    private List<Long> requestIds;
    private RequestStatus status;
}