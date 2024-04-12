package com.dice.edgeservice.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "urls")
public class ApplicationProps {

  private List<String> unfiltered;

  private List<String> filtered;

  public List<String> getUnfiltered() {
    return unfiltered;
  }

  public void setUnfiltered(List<String> unfiltered) {
    this.unfiltered = unfiltered;
  }

  public List<String> getFiltered() {
    return filtered;
  }

  public void setFiltered(List<String> filtered) {
    this.filtered = filtered;
  }
}
