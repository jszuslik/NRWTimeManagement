package norulesweb.com.nrwsports.model;

public class Enums {

    public enum TabType {
        start_timer("Start Timer"),
        stop_timer("Stop Timer");

        private final String text;

        TabType(String text) {this.text = text;}

        @Override
        public String toString() {return text;}

        public static TabType getEnum(String string) {
            for(TabType tt : values()) {
                if(tt.toString().equalsIgnoreCase(string)) return tt;
            }
            throw new IllegalArgumentException();
        }
    }

    public enum ViewType {
        start,
        stop
    }

    public enum FeedType {
        start("Start"),
        stop("Stop");

        private final String text;

        FeedType(String text) { this.text = text; }

        @Override
        public String toString() { return text; }
    }

}

