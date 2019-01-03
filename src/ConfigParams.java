public class ConfigParams {

    public int getAreaSize() {
        return areaSize;
    }

    public void setAreaSize(int areaSize) {
        this.areaSize = areaSize;
    }

    private int areaSize;

    private int epochs;

    public ProliferatorsEnum getProliferatorsEnum() {
        return proliferatorsEnum;
    }

    public void setProliferatorsEnum(ProliferatorsEnum proliferatorsEnum) {
        this.proliferatorsEnum = proliferatorsEnum;
    }

    private ProliferatorsEnum proliferatorsEnum;

    public boolean isShowFood() {
        return showFood;
    }

    public void setShowFood(boolean showFood) {
        this.showFood = showFood;
    }

    private boolean showFood;

    public int getEpochs() {
        return epochs;
    }

    public void setEpochs(int epochs) {
        this.epochs = epochs;
    }

    public int getLifeProbability() {
        return lifeProbability;
    }

    public void setLifeProbability(int lifeProbability) {
        this.lifeProbability = lifeProbability;
    }

    public int getMaxFood() {
        return maxFood;
    }

    public void setMaxFood(int maxFood) {
        this.maxFood = maxFood;
    }

    public int getFoodIncrease() {
        return foodIncrease;
    }

    public void setFoodIncrease(int foodIncrease) {
        this.foodIncrease = foodIncrease;
    }

    public int getFoodDecrease() {
        return foodDecrease;
    }

    public void setFoodDecrease(int foodDecrease) {
        this.foodDecrease = foodDecrease;
    }

    private int lifeProbability;
    private int maxFood;
    private int foodIncrease;
    private int foodDecrease;

    public boolean validate(){
        return lifeProbability>0 && maxFood>0;
    }
}
