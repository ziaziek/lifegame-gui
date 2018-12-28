public class ConfigParams {

    private int epochs;

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
