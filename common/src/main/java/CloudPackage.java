import java.io.Serializable;
//Получить текстовый документ, но это не нужно!
public class CloudPackage implements Serializable {
    private String text;

    CloudPackage(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
