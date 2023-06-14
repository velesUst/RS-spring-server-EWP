package com.ewp.server.persistenceR2DB.entity;

public enum Scale {
        MINUTES("1min"), FIVEMINUTES("5min"), FIFTEENTHMINUTES("15min"), HALFHOUR("30min"), HOUR("60min"), DAY("day"), WEEK("week");

        private final String value;

        Scale(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
        
        public int getId() {         
            int ret = 0;
            switch (this.value) {
                case "1min": ret = 1;
                    break;
                case "5min": ret = 2;
                    break;
                case "15min": ret = 3;
                    break;
                case "30min": ret = 4;
                    break;
                case "60min": ret = 5;
                    break;
                case "day": ret = 6;
                    break;
                case "week": ret = 7;
                    break;
                default: ret = 0;
                    break;
            }    
            return ret;
        }
    }
