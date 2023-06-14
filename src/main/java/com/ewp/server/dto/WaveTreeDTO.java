package com.ewp.server.dto;
import java.util.Collection;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;

//@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WaveTreeDTO {
    private String id;
    private String name;
    private Rd3t __rd3t;
    @JsonIgnore
    private Rd3tAttributes __rd3tAttributes;
    private TreeNodeAttributes attributes;

    private Collection<WaveTreeDTO> children;


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Rd3t get__rd3t() {
        return __rd3t;
    }
    public void set__rd3t(Rd3t __rd3t) {
        this.__rd3t = __rd3t;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public Rd3tAttributes get__rd3tAttributes() {
        return __rd3tAttributes;
    }
    public void set__rd3tAttributes(Rd3tAttributes __rd3tAttributes) {
        this.__rd3tAttributes = __rd3tAttributes;
    }

    public Collection<WaveTreeDTO> getChildren() {
        return children;
    }
    public void setChildren(Collection<WaveTreeDTO> children) {
        this.children = children;
    }

    public TreeNodeAttributes getAttributes() { 
        return new TreeNodeAttributes(__rd3tAttributes.toStringForJS());
    }
    public void setAttributes(TreeNodeAttributes attributes) {
        this.attributes = attributes;
    }

    class TreeNodeAttributes {
        private String payload;

        public TreeNodeAttributes(String payload) {
            this.payload = payload;
        }
        public String getPayload() {
            return payload;
        }
        public void setPayload(String payload) {
            this.payload = payload;
        }
    }
}

