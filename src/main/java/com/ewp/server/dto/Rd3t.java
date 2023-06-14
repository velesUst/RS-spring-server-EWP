package com.ewp.server.dto;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rd3t {
    private  boolean collapsed;
    private  long depth;
    private  String id;    

/*    public Rd3t() {}

    public Rd3t(boolean collapsed, long depth, String id) {
        this.collapsed = collapsed;
        this.depth = depth;
        this.id = id;
    }

    public boolean getCollapsed() {
        return collapsed;
    }
    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public long getDepth() {
        return depth;
    }
    public void setDepth(long depth) {
        this.depth = depth;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
*/
}
