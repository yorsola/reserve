package com.ac.reserve.web.api.dto;


import com.ac.reserve.web.api.po.Campaign;
import com.ac.reserve.web.api.po.Round;
import lombok.Data;

import java.util.List;
@Data
public class CampaignDTO extends Campaign {
    private List<Round> roundList;
}
