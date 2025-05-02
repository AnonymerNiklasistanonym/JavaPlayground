import java.lang.reflect.*;

class Box<T> {
    public String name;
    public T value;
}

abstract class TypeReference<T> {
    Type type;

    public TypeReference() {
        Type superClass = getClass().getGenericSuperclass();
        this.type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    public Type getType() {
        return this.type;
    }
}

public class MainReflections {
    public static void main(String[] args) throws Exception {
        Field fieldName = Box.class.getField("name");
        System.out.println(fieldName.getType().getTypeName());  // Outputs: java.lang.String
        
        Field field = Box.class.getField("value");
        System.out.println(field.getGenericType());  // Output: T

        TypeReference<java.util.List<String>> ref = new TypeReference<>() {};
        System.out.println(ref.getType()); // Output: java.util.List<java.lang.String>
    }
}