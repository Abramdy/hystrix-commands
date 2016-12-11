package uk.deltabravo.hystrix.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by dennis on 30/11/2016.
 */
@Getter
@Setter
@AllArgsConstructor

public class CommandResponse
{
    @JsonProperty
    private List<WebCommandObject> webCommandObjects;
    @JsonProperty
    private List<String> execResults;
}
