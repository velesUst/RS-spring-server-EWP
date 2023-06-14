package com.ewp.server.dto;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rd3tAttributes {

    private boolean isSelected;
    private boolean showElementState;
    private boolean isRootElement;
    private String elementType;
    private String displayLabel;
    private String elementNameLabel;
    private String taskVelocity;
    private String tasProgressState;
    
    public String toStringForJS() {
        int tv = 0;
        if(taskVelocity.equals("AgileRVelocityIndicatorState.BLOCKED")) 
            tv = 2;
        if(taskVelocity.equals("AgileRVelocityIndicatorState.FAST")) 
            tv = 1;
        int et = 0;
        if(elementType.equals("GOTElementType.TASK")) 
            et = 3;
        if(elementType.equals("GOTElementType.OBJECTIVE")) 
            et = 2;

        return "{"
            +"\"isSelected\": "+isSelected+","
			+"\"showElementState\": "+showElementState+","
			+"\"isRootElement\": "+isRootElement+","
			+"\"elementType\": "+et+","
			+"\"displayLabel\": \"Goal\","
			+"\"elementNameLabel\": \"This is the root goal\","
			+"\"taskVelocity\": "+tv+","
			+"\"tasProgressState\": 2"
            + "}";
    }
}
