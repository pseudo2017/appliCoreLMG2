package com.lmg.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class lmgData implements EventData  {
    private String lmgId;

    @JsonIgnore
	public String getLmgId() {
		return lmgId;
	}

    @JsonIgnore
	public void setLmgId(String lmgId) {
		this.lmgId = lmgId;
	}
    
    
    

}
