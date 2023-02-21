package apilogic;

public class TestColorData {

    public String[] getColor() {
        return color;
    }

    private final String[] color;

    public TestColorData(String[] color){
        this.color = color;
    }

    //для корректного отображения массива строк в отчете
    @Override
    public String toString() {
        return String.join(", ", color);
    }
}
