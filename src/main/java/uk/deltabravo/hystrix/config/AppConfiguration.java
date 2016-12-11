package uk.deltabravo.hystrix.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by dennis on 30/11/2016.
 */
@Getter
@Setter
public class AppConfiguration extends Configuration
{
    @JsonProperty
    private String appName;
}
