package com.ewp.server.dto;
import java.util.Collection;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

//@Data
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WaveDTO {
    private Long id;
    private String type;
    private String scale;
    private String entetyState;
    private Long headerWave_id;

    private Collection<ChartPointDTO> points;
}
