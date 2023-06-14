package com.ewp.server.persistence.entity.analysis;

import java.util.stream.Stream;

public enum PType {
    Impulse(1), LeadingDiagonal(2), EndingDiagonal(3), Zigzag(4), Flat(5), Triangles(6), DoubleThree(7), TripleThree(8);                                                                                         
    
    private final int ptype;

    private  PType(int ptype) {
        this.ptype = ptype;
    }

    public int getPType() {
        return ptype;
    }

    public static PType of(int ptype) {
        return Stream.of(PType.values())
            .filter(p -> p.getPType() == ptype)
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }   
    public static PType of(String ptype) {
        for (PType p : PType.values()) {
            if (p.toString().equalsIgnoreCase(ptype)) {
                return p;
            }
        }
        return null;
    }      
}
