package com.ewp.server.dto;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChartPointDTO {
    
    private Long id;
    
    
    private LocalDateTime x;

    
    private double y;

    /*@JMapConversion(from={"id"}, to={"id"})
    public Long conversion(String id){
        return Long.parseLong(id);
    }*/
}