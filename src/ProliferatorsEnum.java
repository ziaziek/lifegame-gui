import com.pncomp.lifegame.proliferators.Proliferator;
import com.pncomp.lifegame.proliferators.SimpleProliferator;

public enum ProliferatorsEnum {

    DEFAULT_PROLIFERATOR("Simple Default"), PAIR_PROLIFERATOR("Simple Pair");

    String description;

    ProliferatorsEnum( String description){
        this.description=description;
    }

    public static class Factory {

        public static Proliferator createProliferator(ProliferatorsEnum proliferatorsEnum){
            switch (proliferatorsEnum){
                case DEFAULT_PROLIFERATOR:
                    return new SimpleProliferator();
                case PAIR_PROLIFERATOR:
                    return null;
                    default:
                        return null;

            }
        }
    }



}
